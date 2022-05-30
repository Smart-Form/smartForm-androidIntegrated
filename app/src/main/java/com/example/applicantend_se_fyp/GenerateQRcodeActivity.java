package com.example.applicantend_se_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

public class GenerateQRcodeActivity extends AppCompatActivity {

    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button btn_ToSelectService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_generate_qrcode);

        qrCodeIV = findViewById(R.id.idIVQrcode);
        btn_ToSelectService = findViewById(R.id.btn_ToSelectService);

        String qrCodeContent = getIntent().getStringExtra("json");

        try {
            qrCodeIV.setImageBitmap(createORcode(qrCodeContent));
        } catch (Exception e){
            Log.d("QRcode", "QR code Error.");
        }

        btn_ToSelectService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // newView 2.1.5
                Intent intent = new Intent(GenerateQRcodeActivity.this, select.class);
                Log.d("TAG", "GQA : " + qrCodeContent);
                intent.putExtra("result", qrCodeContent);
                startActivity(intent);
            }
        });

    }

    public static Bitmap createORcode(String str) throws WriterException {

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300, hints);

        int width = matrix.getWidth();
        int height = matrix.getHeight();

        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;

    }

}

//psuh