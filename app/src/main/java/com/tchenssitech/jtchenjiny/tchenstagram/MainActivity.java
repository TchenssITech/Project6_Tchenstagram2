package com.tchenssitech.jtchenjiny.tchenstagram;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.tchenssitech.jtchenjiny.tchenstagram.Fragment.ComposeFragment;
import com.tchenssitech.jtchenjiny.tchenstagram.Fragment.FeedFragment;
import com.tchenssitech.jtchenjiny.tchenstagram.Fragment.MyProfileFragment;

public class MainActivity extends AppCompatActivity {

        public static final String TAG = "MainActivity";
        final FragmentManager fragmentManager = getSupportFragmentManager();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);


            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = new Fragment();
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            Toast.makeText(MainActivity.this, "Home!", Toast.LENGTH_SHORT).show();
                            fragment = new FeedFragment();
                            break;

                        case R.id.action_compose:
                            // Something Happens
                            Toast.makeText(MainActivity.this, "Compose!", Toast.LENGTH_SHORT).show();
                            fragment = new ComposeFragment();
                            break;


                        case R.id.action_profile:
                            Toast.makeText(MainActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
                            fragment = new MyProfileFragment();
                            break;
                    }
                    fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                    return true;
                }
            });

            //Set default Selection
            bottomNavigationView.setSelectedItemId(R.id.action_home);
        }
    }