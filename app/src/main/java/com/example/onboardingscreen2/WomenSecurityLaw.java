package com.example.onboardingscreen2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.github.barteksc.pdfviewer.PDFView;

public class WomenSecurityLaw extends AppCompatActivity {
    private PDFView pdfView;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women_security_law);
        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("Women Protection In India.pdf").load();
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WomenSecurityLaw.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}