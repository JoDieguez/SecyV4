package com.example.login;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class LlamEmergencia extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llamemergencia);
        // configurar video de youtube
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String videoUrl = "https://www.youtube.com/embed/01JkSIX8gVI?si=rIhTel_Yqs__WJeX";
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(videoUrl);

    }
}
