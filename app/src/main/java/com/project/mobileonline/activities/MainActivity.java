package com.project.mobileonline.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.project.mobileonline.R;
import com.project.mobileonline.adapters.DrawerAdapter;
import com.project.mobileonline.fragments.StoreFragment;
import com.project.mobileonline.models.DrawerItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    boolean login = true;
    ActionBar actionBar;
    DrawerLayout drawerLayout;
    ListView drawerMenu;
    ArrayList<DrawerItem> items = new ArrayList<>();
    DrawerAdapter adapter;
    ActionBarDrawerToggle drawerToggle;
    String mTitle, mDrawerTitle;
    String[] titles;
    int[] iconRes = {
            0,
            R.drawable.ic_store_black_48dp,
            R.drawable.ic_list_black_48dp,
            R.drawable.ic_library_books_black_48dp,
            0,
            R.drawable.ic_info_black_48dp,
            R.drawable.ic_exit_to_app_black_48dp,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        getControlWidget();
    }

    private void initData() {
        titles = getResources().getStringArray(R.array.titles);
        for (int i = 0; i < titles.length; i++) {
            DrawerItem item = new DrawerItem(iconRes[i], titles[i]);
            items.add(item);
        }
    }

    private void changeLogInState() {
        if (login) {
            items.add(3, new DrawerItem(R.drawable.ic_history_black_48dp, "History"));
            addHeaderView();
        } else {
            removeHeaderView();
            items.add(0, new DrawerItem(R.drawable.ic_account_circle_black_48dp, "Log In"));
        }
    }

    private void addHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.drawer_header_view, null);
        drawerMenu.addHeaderView(view);
    }

    private void removeHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.drawer_header_view, null);
        drawerMenu.removeHeaderView(view);
    }

    private void initView() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        //nit parse
        Parse.initialize(this, "OYyLBiBkt53DVu4CXCBbr4UgCpQFMoeUisusPQWa", "i1WGIaqAT2Pvqy0E1SY1HhwHbf15KnSJHGBigdl1");

        mDrawerTitle = getResources().getString(R.string.app_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerMenu = (ListView) findViewById(R.id.drawer_menu);
        adapter = new DrawerAdapter(this, R.layout.drawer_item_layout, items);
        drawerMenu.setAdapter(adapter);
        changeLogInState();
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                actionBar.setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                actionBar.setTitle(mDrawerTitle);
            }
        };
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        selectItem(2);
    }

    private void getControlWidget() {
        drawerLayout.setDrawerListener(drawerToggle);
        drawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

    }

    //item_array_position = list_item_position -1
    private void selectItem(int position) {
        Fragment fragment = new Fragment();
        if (login) {
            switch (position) {
                case 0:
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                    return;
                case 2:
                    fragment = new StoreFragment();
                    break;
                case 8:
                    displayExitDialog();
                    return;
            }
            setTitle(items.get(position - 1).getTitle());
        } else {
            setTitle(items.get(position).getTitle());
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_content, fragment);
        ft.commit();

        drawerMenu.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        drawerMenu.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerMenu);
    }

    private void displayExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titles[6])
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = (String) title;
        actionBar.setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
