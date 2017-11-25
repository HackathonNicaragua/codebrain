package com.codebrain.minato.tragua;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by username on 11/25/2017.
 */

public abstract class NavigationDrawerBaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener{
    private FrameLayout view_stub;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Menu drawerMenu;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.navigation_drawer_base_activity);

        view_stub = (FrameLayout) findViewById(R.id.view_stub_1);
        navigationView = (NavigationView) findViewById(R.id.nav_view_1);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_1);
        if (drawerLayout == null)
        {
            Log.d("Drawer layout", "Es null");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        catch (NullPointerException e)
        {
            Log.d("Exception", e.getMessage());
        }

        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);


        drawerMenu = navigationView.getMenu();
        for (int i=0; i<drawerMenu.size(); i++)
        {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConf) {
        super.onConfigurationChanged(newConf);
        toggle.onConfigurationChanged(newConf);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_camera:
                break;
        }
        return false;
    }

    @Override
    public void setContentView(int resLayoutId)
    {
        try
        {
            if (view_stub != null)
            {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                View view = inflater.inflate(resLayoutId, view_stub, false);
                view_stub.addView(view, layoutParams);
            }
        }
        catch (java.lang.NullPointerException e)
        {
            Log.d("Exception", e.getMessage());
        }
    }
}
