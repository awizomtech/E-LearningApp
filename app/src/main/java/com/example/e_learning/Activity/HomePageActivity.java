package com.example.e_learning.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.e_learning.R;
import com.example.e_learning.SharePrefrence.SharedPrefManager;
import com.example.e_learning.fragments.MyPaymentFragment;
import com.example.e_learning.fragments.MyCourseFragment;
import com.example.e_learning.fragments.ProfileFragment;
import com.example.e_learning.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FrameLayout fl_container;
    BottomNavigationView bnv_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_my_courses, R.id.nav_payment,R.id.nav_course,R.id.nav_logout,R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawer);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_profile) {

                    Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
                    startActivity(intent);

                } else if (id == R.id.nav_my_courses) {
                    Intent intent = new Intent(HomePageActivity.this, MyCourseActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_payment) {
                    Intent intent = new Intent(HomePageActivity.this, MyPaymentActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_course) {
                    Intent intent = new Intent(HomePageActivity.this, CourseListActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_share) {

                }else if (id == R.id.nav_logout) {
                    SharedPrefManager sp = new SharedPrefManager(HomePageActivity.this);
                    sp.logout();
                    startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        fl_container = findViewById(R.id.fl_container);
        bnv_menu = findViewById(R.id.bnv_menu);
//test
        bnv_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_my_library:
                        fragment = new MyCourseFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_plus:
                        fragment = new MyPaymentFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_profile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigationMenu:
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.openDrawer(GravityCompat.START);
                        return true;
                }

                return false;
            }
        });

        bnv_menu.setSelectedItemId(R.id.navigation_home);
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            final AlertDialog.Builder alertbox = new AlertDialog.Builder(HomePageActivity.this);

            alertbox.setIconAttribute(90);
            alertbox.setIcon(R.drawable.exit);
            alertbox.setTitle("Do You Want To Exit ?");

            alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    // finish used for destroyed activity

                    finishAffinity();
                    System.exit(0);
                }
            });

            alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
            alertbox.show();
        }
        return super.onKeyDown(keyCode, event);
    }

}
