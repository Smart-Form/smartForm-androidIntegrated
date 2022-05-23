package com.example.applicantend_se_fyp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

 public class GetAPIActivity extends AppCompatActivity {

    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;

    Button button2;

    int SELECT_PICTURE = 898;

    //for intent return value
    public static int RESULT_CODE = 1;

    String service_doc_id;



    ActivityResultLauncher<String> mGetImage = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    String photoUrl = getIntent().getStringExtra("photoUri");
                    Uri photoUri = Uri.parse(photoUrl);
                    Log.d("testlog",uri.getPath());
                    FetchDate thread = new FetchDate();
                    thread.imageUri = photoUri;
                    thread.start();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_get_apiactivity);


        String photoUrl = getIntent().getStringExtra("photoUri");
        Uri photoUri = Uri.parse(photoUrl);
        Log.d("testlog",photoUri.getPath());
        FetchDate thread = new FetchDate();
        thread.imageUri = photoUri;
        thread.start();


        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // newView 2.1.3
                Intent intent = new Intent(GetAPIActivity.this, GenerateQRcodeActivity.class);

                intent.putExtra("json", data);
                //fake data
                //intent.putExtra("json", "{'chname':'李智','engname':'Lee Chi','id':'A1234567(8)',"
                // + "'brithday':'1997-12-01','gender':'M'}");
                startActivity(intent);


                Log.d("testlog", "Click");
                //!!!!!!service tmp down!!!!!!!
                //mGetImage.launch("image/*");

            }
        });


    }




    String data = "";

    class FetchDate extends Thread {
        public Uri imageUri;
        @Override
        public void run() {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(GetAPIActivity.this);
                    progressDialog.setMessage("Sending Data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Log.d("testlog", "Sending Data");
                    Log.d("testlog", imageUri.getPath());
                }
            });

            try {
                Log.d("testlog", "try create url");
                URL url = new URL("http://52.26.49.130:8080/hkid");
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "***";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                int serverResponseCode = 0;
                DataOutputStream dos = null;
                InputStream fis = getContentResolver().openInputStream(imageUri);
                String[] path_arr = imageUri.getPath().split("/");
                String filename = path_arr[path_arr.length-1];

                Log.d("testlog",filename);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("file", filename);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
                        + filename+ "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fis.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fis.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fis.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fis.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);


                System.out.println("Response Code: " + conn.getResponseCode());

                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                Log.d("testlog", "create url");
                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                }
                if (!data.isEmpty()) {
                    //Log.d("testlog", data);
                    JSONObject jsonObject = new JSONObject(data);
                    //Log.d("testlog",jsonObject.getString("gender"));

                    progressDialog.cancel();

                    Intent intent = new Intent(GetAPIActivity.this, GenerateQRcodeActivity.class);
                    //intent.putExtra("service_doc_id", service_doc_id);
                    intent.putExtra("json", data);
                    startActivity(intent);

                }

                //close the streams //
                fis.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}