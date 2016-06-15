package com.feicuiedu.treasure.treasure;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.commons.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private ImageView ivUserIcon;

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_home);
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        // ----------------------------------------------------------------------  1
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// TITLE --- false
        navigationView.setNavigationItemSelectedListener(this); // 对navigationView进行Menu监听
        // ----------------------------------------------------------------------  3
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( // drawerLayout监听(Toolbar VS DrawLayout)
                this,
                drawerLayout,     // Drawer     抽屉
                toolbar,          // ActionBar
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        // ----------------------------------------------------------------------  2
        ivUserIcon = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_userIcon);// 注意：不能用ButterKnife拿到
        ivUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.showToast("进入个人中心");
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_hide:
                activityUtils.showToast(R.string.hide_treasure);
                break;
            case R.id.menu_item_my_list:
                activityUtils.showToast(R.string.my_list);
                break;
            case R.id.menu_item_help:
                activityUtils.showToast(R.string.about_help);
                break;
            case R.id.menu_item_logout:
                activityUtils.showToast(R.string.log_out);
                break;
        }
        return false;
    }
}
