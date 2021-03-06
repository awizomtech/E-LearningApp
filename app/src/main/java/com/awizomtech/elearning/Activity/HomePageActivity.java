package com.awizomtech.elearning.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import com.awizomtech.elearning.Adapter.MyCourseAdapter;
import com.awizomtech.elearning.AppConfig.AppConfig;
import com.awizomtech.elearning.BuildConfig;
import com.awizomtech.elearning.Helper.UserHelper;
import com.awizomtech.elearning.Model.CourseListModel;
import com.awizomtech.elearning.R;
import com.awizomtech.elearning.SharePrefrence.SharedPrefManager;
import com.awizomtech.elearning.fragments.MyPaymentFragment;
import com.awizomtech.elearning.fragments.MyCourseFragment;
import com.awizomtech.elearning.fragments.ProfileFragment;
import com.awizomtech.elearning.fragments.HomeFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    static HomePageActivity INSTANCE;
    private List<CourseListModel> courseListModels;
    public ArrayList<String> CourseId = new ArrayList<>();
    private AppBarConfiguration mAppBarConfiguration;
    FrameLayout fl_container;
    BottomNavigationView bnv_menu;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_home_page);
        INSTANCE = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_my_courses, R.id.nav_payment, R.id.nav_course, R.id.nav_logout, R.id.nav_share, R.id.nav_quiz, R.id.nav_edit_profile, R.id.nav_rating)
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
                } else if (id == R.id.nav_payment) {
                    Intent intent = new Intent(HomePageActivity.this, MyPaymentActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_course) {
                    Intent intent = new Intent(HomePageActivity.this, CourseListActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_contactus) {
                    Intent intent = new Intent(HomePageActivity.this, ContactUsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_quiz) {
                    Intent intent = new Intent(HomePageActivity.this, StartFitnessActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_edit_profile) {
                    Intent intent = new Intent(HomePageActivity.this, EditProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_rating) {
                    String url = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else if (id == R.id.nav_share) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } else if (id == R.id.nav_logout) {
                    ConnectivityManager mgr = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = mgr.getActiveNetworkInfo();

                    if (netInfo != null) {
                        if (netInfo.isConnected()) {
                            LogOut();
                           /* SharedPrefManager sp = new SharedPrefManager(HomePageActivity.this);
                            sp.logout();
                            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));*/
                        }else {
                            Toast.makeText(HomePageActivity.this,"Please Enable Your Internet", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomePageActivity.this,"Please Enable Your Internet", Toast.LENGTH_SHORT).show();
                    }

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

        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.usernames);
        de.hdodenhof.circleimageview.CircleImageView nav_image = hView.findViewById(R.id.imageView);
        String nme = SharedPrefManager.getInstance(this).getUser().getName();
        String photo = SharedPrefManager.getInstance(this).getUser().getProfilePhoto();
        nav_user.setText(nme);
        try {
            Glide.with(this).load(AppConfig.BASE_URL + photo.toString()).into(nav_image);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public static HomePageActivity getActivityInstance() {
        return INSTANCE;
    }

    public ArrayList<String> getData() {
        return this.CourseId;
    }

    private void LogOut() {
        try {
            String userid= SharedPrefManager.getInstance(this).getUser().getUserID();
            result = new UserHelper.Logout().execute(userid.toString()).get();
            if (result.isEmpty()) {
                SharedPrefManager sp = new SharedPrefManager(HomePageActivity.this);
                sp.logout();
                startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            } else {
                SharedPrefManager sp = new SharedPrefManager(HomePageActivity.this);
                sp.logout();
                startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
