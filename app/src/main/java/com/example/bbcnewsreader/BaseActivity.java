package com.example.bbcnewsreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView  = findViewById(R.id.navigationMenu);
        toolbar = findViewById(R.id.toolBar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.string_open, R.string.string_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        //navigationView.setBackgroundColor(Color.WHITE);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.nav_first:
                        startActivity(new Intent(BaseActivity.this, MainActivity.class));
                        break;
                    case R.id.nav_second:
//                        Intent i = new Intent(BaseActivity.this, DadJokeActivity.class);
//                        startActivity(i);
                        break;
                    case R.id.nav_third:
                        finishAffinity();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle presses on the action bar items
//        String toastMessage = "";
//        switch (item.getItemId()) {
//            case R.id.item1:
//                toastMessage = getString(R.string.item1_message);
//                break;
//            case R.id.item2:
//                toastMessage = getString(R.string.item2_message);
//                break;
//        }
//
//        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}