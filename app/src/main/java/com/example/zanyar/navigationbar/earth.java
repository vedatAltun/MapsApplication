package com.example.zanyar.navigationbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;

public class earth extends Fragment {

    MainActivity main=new MainActivity();
WebView webView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.earth, container, false);
        webView = ButterKnife.findById(view, R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://earth.google.com/web/@0,0,0a,22251752.77375655d,35y,0h,0t,0r");
        webView.setWebViewClient(new WebViewClient());
        return view;
    }


        }

