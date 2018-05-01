package com.sriramr.movieinfo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.movies.movielist.MovieListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this, MovieListActivity.class);
            startActivity(i);
            finish();
        }, 2000);

    }
}
