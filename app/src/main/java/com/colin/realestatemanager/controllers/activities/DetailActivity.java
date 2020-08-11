package com.colin.realestatemanager.controllers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.colin.realestatemanager.R;
import com.colin.realestatemanager.controllers.fragments.EstateDetailsFragment;
import com.colin.realestatemanager.models.EstateWithPhotos;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_activity_toolbar)
    Toolbar toolbar;

    private EstateWithPhotos estateWithPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        estateWithPhotos = (EstateWithPhotos) getIntent().getSerializableExtra("Estate");
        configToolbar();
        configFragment();
    }

    private void configToolbar() {
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void configFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_details, EstateDetailsFragment.newInstance(estateWithPhotos))
                .addToBackStack(null)
                .commit();
    }


}