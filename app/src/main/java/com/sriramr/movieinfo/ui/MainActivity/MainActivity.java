package com.sriramr.movieinfo.ui.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.sriramr.movieinfo.ui.Movies.MovieListActivity.MovieListActivity;
import com.sriramr.movieinfo.R;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.main_progressbar);

        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this,MovieListActivity.class);
            startActivity(i);
            finish();
        },2000);

    }
}
