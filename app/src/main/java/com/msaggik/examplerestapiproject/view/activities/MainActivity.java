package com.msaggik.examplerestapiproject.view.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.msaggik.examplerestapiproject.R;
import com.msaggik.examplerestapiproject.databinding.ActivityMainBinding;
import com.msaggik.examplerestapiproject.network.HttpsHelper;
import com.msaggik.examplerestapiproject.view.fragments.ProductsFragment;
import com.msaggik.examplerestapiproject.viewmodel.adapters.AdapterProducts;

public class MainActivity extends AppCompatActivity implements Runnable{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;

    private Handler handler;
    private int responseConnectionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_quotes, R.id.nav_products, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        handler = new Handler(Looper.getMainLooper()); // создание объекта обработчика сообщений
        new Thread(this).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void run() {
        // GET запрос на сервер значения соединения
        responseConnectionCheck = new HttpsHelper().connectionCheck(getApplicationContext());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // проверка валидности соединения с сервером
                if (responseConnectionCheck != 200) { // если соединения нет
                    NavInflater navInflater = navController.getNavInflater();
                    NavGraph navGraph = navInflater.inflate(R.navigation.mobile_navigation);
                    navGraph.setStartDestination(R.id.nav_settings); // переброска в настройки
                    navController.setGraph(navGraph);
                    Toast.makeText(MainActivity.this, "Необходимо настроить адрес сервера", Toast.LENGTH_SHORT).show();
                }
            }
        },0);
    }
}