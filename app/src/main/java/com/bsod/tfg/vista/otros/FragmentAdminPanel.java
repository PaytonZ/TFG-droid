package com.bsod.tfg.vista.otros;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bsod.tfg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminPanel extends Fragment {


    public static String URL_KEY = "FragmentAdminPanel.URL";
    private WebView webView;
    private String url;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url String.
     * @return A new instance of fragment HeroDetailFragment.
     */
    public static Fragment newInstance(String url) {
        Fragment fragment = new FragmentAdminPanel();
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_panel, container, false);
        webView = (WebView) rootView.findViewById(R.id.webView);
        setupWebViewClient();
        return rootView;
    }

    private void setupWebViewClient() {
        webView.setVisibility(View.GONE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            private int running = 0; // Could be public if you want a timer to check.

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                running++;
                webView.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                running = Math.max(running, 1); // First request move it to 1.
                webView.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (--running == 0) { // just "running--;" if you add a timer.
                    // TODO: finished... if you want to fire a method.
                }
                webView.setVisibility(View.VISIBLE);
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });
    }
}

