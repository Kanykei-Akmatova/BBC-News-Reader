package com.example.bbcnewsreader;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //initializing the sql helper
        SQLHelper sqlHelper = new SQLHelper(this);
        sqlHelper.getWritableDatabase();

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView  = findViewById(R.id.navigationMenu);
        toolbar = findViewById(R.id.toolBar);

        this.setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.string_open, R.string.string_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

         navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId()){
                case R.id.nav_first:
                    startActivity(new Intent(BaseActivity.this, NewsActivity.class));
                    break;
                case R.id.nav_second:
                    startActivity(new Intent(BaseActivity.this, FavoriteNewsActivity.class));
                    break;
                case R.id.nav_third:
                    finishAffinity();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.itemHelp:
                AlertDialog.Builder alertDiBuilder = new AlertDialog.Builder(this);
                alertDiBuilder.setTitle(R.string.application_help)
                        .setMessage(R.string.help_message)
                        .setPositiveButton(R.string.alert_yes, (dialog, arg) -> {
                            dialog.dismiss();
                        })
                        .setNegativeButton(R.string.alert_no, (dialog, arg) -> {
                        });
                alertDiBuilder.create().show();
                break;
            case R.id.itemFavorite:
                startActivity(new Intent(BaseActivity.this, FavoriteNewsActivity.class));
                Toast.makeText(getApplicationContext(), R.string.my_favorite, Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemHome:
                startActivity(new Intent(BaseActivity.this, NewsActivity.class));
                Toast.makeText(getApplicationContext(), R.string.home, Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}