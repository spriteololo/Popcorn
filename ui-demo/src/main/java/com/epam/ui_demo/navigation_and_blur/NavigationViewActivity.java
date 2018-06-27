package com.epam.ui_demo.navigation_and_blur;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.epam.ui_demo.R;

public class NavigationViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        initViews();

        final MenuItem item = navigationView.getMenu().findItem(R.id.nav_home);
        setFragment(item);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.home_toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.icon_menu_hamburger);

        drawerLayout = findViewById(R.id.drawer_layout);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_home:
                setFragment(item);
                break;
            case R.id.nav_movies:
                setFragment(item);
                break;
            case R.id.nav_TVset:
                setFragment(item);
                break;
            case R.id.nav_people:
                setFragment(item);
                break;
            case R.id.nav_settings:
                setFragment(item);
                break;
        }

        return true;
    }

    public void setFragment(final MenuItem item) {
        toolbar.setTitle(item.getTitle());

        final Fragment fr = getSupportFragmentManager().findFragmentByTag(item.getTitle().toString());

        final Fragment fragment = NavigationViewFragment.getInstance();
        final Bundle b = new Bundle();

        if (fr == null) {
            b.putString("data", item.getTitle().toString());
            fragment.setArguments(b);//Set Arguments
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            fragment, item.getTitle().toString())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}
