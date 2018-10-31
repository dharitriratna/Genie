package com.example.user.genie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TermsAndConditions extends AppCompatActivity {
    TextView textView;
    WebView webview;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        webview = (WebView) findViewById(R.id.web);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        textView = (TextView) findViewById(R.id.load);

        webview.loadUrl("http://demo.ratnatechnology.co.in/genie/main/terms_condition");
    }
}
