package com.colin.realestatemanager.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.colin.realestatemanager.controllers.fragments.BottomSheetFragment;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.controllers.fragments.EstateDetailsFragment;
import com.colin.realestatemanager.controllers.fragments.EstateListFragment;
import com.colin.realestatemanager.views.EstateListAdapter;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class MainActivity extends AppCompatActivity implements EstateListAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    private EstateListFragment estateListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind(this);
        initViews(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        toolbar.setTitle("Estates");
        toggle.setDrawerIndicatorEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }


    private void initViews(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        configFragments(savedInstanceState);
        configureDrawer();
    }

    private void configFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            estateListFragment = EstateListFragment.newInstance(this);
            launchEstateListFragment();
        } else {
            estateListFragment = (EstateListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container1);
        }
    }

    private void configureDrawer() {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    // -----------------
    // Listeners
    // -----------------

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_main_menu_add:
                launchAddEstateActivity();
                break;
            case R.id.activity_main_menu_edit:
                launchModifyEstateActivity();
                break;
            case R.id.activity_main_menu_search:
                launchBottomSheetFragment();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.nav_simulation:
                intent = new Intent(this, SimulationActivity.class);
                break;
            case R.id.nav_map:
                intent = new Intent(this, MapsActivity.class);
                break;
        }
        startActivity(intent);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(EstateWithPhotos estate) {
        if (findViewById(R.id.fragment_container2) == null)
            launchDetailsEstateActivity(estate);
        else
            launchEstateDetailsFragmentTabletLandscape(estate);
    }

    // -----------------
    // Launchers
    // -----------------

    private void launchAddEstateActivity() {
        Intent intent = new Intent(this, CreateEstateActivity.class);
        startActivity(intent);
    }

    private void launchModifyEstateActivity() {
        if (estateListFragment.getAdapter().getSelectedPosition() != -1) {
            Intent intent = new Intent(this, ModifyEstateActivity.class);
            intent.putExtra("Estate", estateListFragment.getPosition(estateListFragment.getAdapter().getSelectedPosition()));
            startActivity(intent);
        }
    }

    private void launchDetailsEstateActivity(EstateWithPhotos estate) {
        if (estateListFragment.getAdapter().getSelectedPosition() != -1) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("Estate", estate);
            startActivity(intent);
        }
    }

    private void launchEstateDetailsFragmentTabletLandscape(EstateWithPhotos estate) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container2, EstateDetailsFragment.newInstance(estate))
                .addToBackStack(null)
                .commit();
    }

    private void launchEstateListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container1, estateListFragment)
                .addToBackStack(null)
                .commit();
    }

    private void launchBottomSheetFragment() {
        (BottomSheetFragment.newInstance()).show(getSupportFragmentManager(), "bottomSheetFragment");
        getSupportFragmentManager().executePendingTransactions();
    }
}
