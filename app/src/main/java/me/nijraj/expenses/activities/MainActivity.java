package me.nijraj.expenses.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import me.nijraj.expenses.R;
import me.nijraj.expenses.fragments.FragmentAddExpense;
import me.nijraj.expenses.fragments.FragmentMain;
import me.nijraj.expenses.fragments.FragmentPeople;
import me.nijraj.expenses.fragments.FragmentSettings;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Runnable runOnDrawerClosed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.lend, R.string.borrow);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(runOnDrawerClosed != null)
                    runOnDrawerClosed.run();
                runOnDrawerClosed = null;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null)
                return;

            FragmentMain fragmentMain = new FragmentMain();
            FragmentAddExpense fragmentAddExpense = new FragmentAddExpense();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.fragment_container, fragmentMain).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        drawerLayout.closeDrawers();
        runOnDrawerClosed = new Runnable() {
             @Override
             public void run() {
                 Fragment f;
                 switch (item.getItemId()){
                     case R.id.action_dashboard:
                         f = new FragmentMain();
                         break;
                     case R.id.action_people:
                         f = new FragmentPeople();
                         break;
                     case R.id.action_settings:
                         f = new FragmentSettings();
                         break;
                     case R.id.action_accounts:
                         Toast.makeText(getBaseContext(), "Feature not yet implemented", Toast.LENGTH_SHORT).show();
                         f = null;
                     default:
                         f = new FragmentMain();
                 }
                if(f != null)
                     getSupportFragmentManager().beginTransaction()
                         .replace(R.id.fragment_container, f)
                         .commit();
             }
         };
        return true;
    }
}
