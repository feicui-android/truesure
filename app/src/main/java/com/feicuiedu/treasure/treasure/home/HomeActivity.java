package com.feicuiedu.treasure.treasure.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.feicuiedu.treasure.R;
import com.feicuiedu.treasure.commons.ActivityUtils;
import com.feicuiedu.treasure.treasure.TreasureRepo;
import com.feicuiedu.treasure.treasure.home.list.TreasureListFragment;
import com.feicuiedu.treasure.treasure.home.map.MapFragment;
import com.feicuiedu.treasure.user.UserPrefs;
import com.feicuiedu.treasure.user.account.AccountActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    private TreasureListFragment listFragment;
    private MapFragment mapFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
        TreasureRepo.getInstance().clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 取出最新photoURL
        String photoUrl = UserPrefs.getInstance().getPhoto();
        if(photoUrl != null){
            ImageLoader.getInstance().displayImage(photoUrl,ivUserIcon);
        }
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
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // ----------------------------------------------------------------------  2
        ivUserIcon = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_userIcon);// 注意：不能用ButterKnife拿到
        ivUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(AccountActivity.class);
            }
        });
    }
    // 创建
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_toggle);
        if(listFragment != null && listFragment.isAdded()){
            item.setIcon(R.drawable.ic_map);
        }
        else {
            item.setIcon(R.drawable.ic_view_list);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    // 选中
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_toggle:
                showListFragment();
                // 通过此方法能使得onPrepareOptionsMenu方法得到触发
                invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showListFragment(){
        if(listFragment != null && listFragment.isAdded()){
            fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().remove(listFragment).commit();
        }else{
            listFragment = new TreasureListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, listFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_hide:
                drawerLayout.closeDrawer(GravityCompat.START);
                mapFragment.onHidePressed();
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

    @Override public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            // 如果mapFragment那边，是做了模式切换,这一次back完成了
            // 如果................没有做模式切换,这一次back还是原操作
            if(mapFragment.onBackPressed()){
                super.onBackPressed();
            }
        }
    }
}



