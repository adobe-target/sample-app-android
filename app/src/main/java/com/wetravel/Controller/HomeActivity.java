package com.wetravel.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wetravel.Fragments.HomeFragment;
import com.wetravel.Fragments.MyProfileFragment;
import com.wetravel.Fragments.SettingsFragment;
import com.wetravel.Fragments.MyTicketsFragment;
import com.wetravel.R;
import com.wetravel.Utils.Utility;

public class HomeActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView ivMenu;
    TextView tvHeading;
    ImageView ivProfile;
    RelativeLayout rlHeader;

    //Drawer Layout
    DrawerLayout drawerLayout;
    NavigationView nView;

    RelativeLayout rlBookTicket,rlMyTicket,rlMyProfile,rlSettings;
    ImageView ivBookTicketSelected,ivMyTicketSelected,ivMyProfileSelected,ivSettingsSelected;
    ImageView ivBookTicket,ivMyTicket,ivMyProfile,ivSettings;
    TextView tvBookTicket,tvMyTicket,tvMyOffers,tvMyProfile,tvSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_E0E0E0));
        setContentView(R.layout.activity_home);

        Utility.init(this);
        initDrawer();
        initLayouts();

        if(!getIntent().hasExtra("navigate")) {
            attachFragment(new HomeFragment(), "Home");
        }else if(getIntent().hasExtra("navigate")){
            if(getIntent().getExtras().getString("navigate").equalsIgnoreCase("MyProfile")) {
                setDefaultUnSelected();
                attachFragment(new MyProfileFragment(), "MyProfile");
                setSelected(ivMyProfileSelected, ivMyProfile, R.drawable.nav_my_profile_selected);
            }
        }else{
            attachFragment(new HomeFragment(), "Home");
        }
    }

    //Drawer initialization
    public void initDrawer(){
        drawerLayout = findViewById(R.id.drawerLayout);
//        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.color_ffffff));
//        drawerLayout.setScrimColor(Color.TRANSPARENT);

        rlHeader = findViewById(R.id.rlHeader);
        rlHeader.setPadding(Utility.deviceWidth*55/1000,Utility.deviceWidth*3/100,Utility.deviceWidth*55/1000,Utility.deviceWidth*2/100);

        nView = findViewById(R.id.nView);
        nView.setNavigationItemSelectedListener(this);
        nView.getLayoutParams().width = Utility.deviceWidth*70/100;

        //Navigation menu items
        //Book Tickets
        rlBookTicket = nView.getHeaderView(0).findViewById(R.id.rlBookTicket);
        RelativeLayout.LayoutParams paramstvtvCode = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramstvtvCode.setMargins(0,Utility.deviceWidth*14/100,0,0);
        rlBookTicket.setLayoutParams(paramstvtvCode);
        rlBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultUnSelected();
                attachFragment(new HomeFragment(),"Home");
                setSelected(ivBookTicketSelected,ivBookTicket,R.drawable.nav_home_selected);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        ivBookTicketSelected = nView.getHeaderView(0).findViewById(R.id.ivBookTicketSelected);
        ivBookTicket = nView.getHeaderView(0).findViewById(R.id.ivBookTicket);
        ivBookTicket.setBackgroundResource(R.drawable.nav_home_unselected);


        ////My Tickets
        rlMyTicket = nView.getHeaderView(0).findViewById(R.id.rlMyTicket);
        rlMyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultUnSelected();
                attachFragment(new MyTicketsFragment(),"MyTickets");
                setSelected(ivMyTicketSelected,ivMyTicket,R.drawable.nav_my_ticket_selected);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        ivMyTicketSelected = nView.getHeaderView(0).findViewById(R.id.ivMyTicketSelected);
        ivMyTicket = nView.getHeaderView(0).findViewById(R.id.ivMyTicket);
        ivMyTicket.setBackgroundResource(R.drawable.nav_my_ticket_unselected);

        //My Profile
        rlMyProfile = nView.getHeaderView(0).findViewById(R.id.rlMyProfile);
        rlMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultUnSelected();
                attachFragment(new MyProfileFragment(),"MyProfile");
                setSelected(ivMyProfileSelected,ivMyProfile,R.drawable.nav_my_profile_selected);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        ivMyProfileSelected = nView.getHeaderView(0).findViewById(R.id.ivMyProfileSelected);
        ivMyProfile = nView.getHeaderView(0).findViewById(R.id.ivMyProfile);
        ivMyProfile.setBackgroundResource(R.drawable.nav_my_profile_unselected);

        //Logout
        rlSettings = nView.getHeaderView(0).findViewById(R.id.rlSettings);
        rlSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultUnSelected();
                attachFragment(new SettingsFragment(),"Settings");
                setSelected(ivSettingsSelected,ivSettings,R.drawable.nav_setting_selected);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        ivSettingsSelected = nView.getHeaderView(0).findViewById(R.id.ivSettingsSelected);
        ivSettings = nView.getHeaderView(0).findViewById(R.id.ivSettings);
        ivSettings.setBackgroundResource(R.drawable.nav_setting_unselected);

        setSelected(ivBookTicketSelected,ivBookTicket,R.drawable.nav_home_selected);
    }

    public void setDefaultUnSelected(){
        ivBookTicket.setBackgroundResource(R.drawable.nav_home_unselected);
        ivMyTicket.setBackgroundResource(R.drawable.nav_my_ticket_unselected);
        ivMyProfile.setBackgroundResource(R.drawable.nav_my_profile_unselected);
        ivSettings.setBackgroundResource(R.drawable.nav_setting_unselected);

        ivBookTicketSelected.setVisibility(View.GONE);
        ivMyTicketSelected.setVisibility(View.GONE);
        ivMyProfileSelected.setVisibility(View.GONE);
        ivSettingsSelected.setVisibility(View.GONE);
    }

    public void setSelected(ImageView img1, ImageView img2, int icon){
        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.VISIBLE);
        img2.setBackgroundResource(icon);
    }

    //View initialization
    public void initLayouts(){
        //Menu
        ivMenu = findViewById(R.id.ivMenu);
        ivMenu.getLayoutParams().height = Utility.deviceWidth*10/100;
        ivMenu.getLayoutParams().width = Utility.deviceWidth*10/100;
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionDrawer();
            }
        });

        //heading
        tvHeading = findViewById(R.id.tvHeading);
        tvHeading.setTextSize(Utility.txtSize_20dp);
        tvHeading.setTypeface(Utility.font_roboto_bold);

        ivProfile = findViewById(R.id.ivProfile);
        ivProfile.getLayoutParams().height = Utility.deviceWidth*6/100;
        ivProfile.getLayoutParams().width = Utility.deviceWidth*6/100;
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultUnSelected();
                attachFragment(new MyProfileFragment(),"MyProfile");
                setSelected(ivMyProfileSelected,ivMyProfile,R.drawable.nav_my_profile_selected);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*if(id == R.id.nBookTickets){
            attachFragment(new HomeFragment(),"Home");
            nView.getMenu().getItem(0).setChecked(true);
        }*/
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Attach fragment in container of activity
    public void attachFragment(Fragment fragment,String backStack){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment,backStack).addToBackStack(backStack).commit();
    }

    //Open and close navigation drawer
    public void actionDrawer(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            FragmentManager fm = getSupportFragmentManager();
            String fTag = fm.getBackStackEntryAt(fm.getBackStackEntryCount()-1).getName();
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(fTag);
            if(currentFragment instanceof HomeFragment){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                setDefaultUnSelected();
                attachFragment(new HomeFragment(),"Home");
                setSelected(ivBookTicketSelected,ivBookTicket,R.drawable.nav_home_selected);
//                fm.popBackStack();
            }
        }
    }

    public void setHeader(String title){
        tvHeading.setText(title);
    }
}
