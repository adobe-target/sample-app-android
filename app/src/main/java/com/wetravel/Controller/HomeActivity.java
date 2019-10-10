package com.wetravel.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adobe.mobile.Config;
import com.adobe.mobile.Target;
import com.adobe.mobile.TargetPrefetchObject;
import com.wetravel.Fragments.HomeFragment;
import com.wetravel.Fragments.MyProfileFragment;
import com.wetravel.Fragments.MyTicketsFragment;
import com.wetravel.Fragments.SettingsFragment;
import com.wetravel.R;
import com.wetravel.Utils.Constant;
import com.wetravel.Utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        Config.setContext(this.getApplicationContext());
        Config.setDebugLogging(true);
    }



    private void setUp() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_E0E0E0));
        setContentView(R.layout.activity_home);

        Utility.init(this);
        initDrawer();
        initLayouts();

        if(getIntent().getExtras() == null) {
            attachFragment(new HomeFragment(), "Home");
        }else if(getIntent().getExtras().getString("navigate") != null){
            if(getIntent().getExtras().getString("navigate").equalsIgnoreCase("MyProfile")) {
                setDefaultUnSelected();
                attachFragment(new MyProfileFragment(), "MyProfile");
                setSelected(ivMyProfileSelected, ivMyProfile, R.drawable.nav_my_profile_selected);
            }
        }else{
            attachFragment(new HomeFragment(), "Home");
        }
//        Utility.isFutureDate("25-07-2019 23:45:00");
    }

    //Drawer initialization
    public void initDrawer(){
        drawerLayout = findViewById(R.id.drawerLayout);
//        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.color_ffffff));
//        drawerLayout.setScrimColor(Color.TRANSPARENT);

        rlHeader = findViewById(R.id.rlHeader);
        rlHeader.setPadding(Utility.deviceWidth*55/1000, Utility.deviceWidth*3/100, Utility.deviceWidth*55/1000, Utility.deviceWidth*2/100);

        nView = findViewById(R.id.nView);
        nView.setNavigationItemSelectedListener(this);
        nView.getLayoutParams().width = Utility.deviceWidth*70/100;

        //Navigation menu items
        //Book Tickets
        rlBookTicket = nView.getHeaderView(0).findViewById(R.id.rlBookTicket);
        RelativeLayout.LayoutParams paramstvtvCode = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramstvtvCode.setMargins(0, Utility.deviceWidth*14/100,0,0);
        rlBookTicket.setLayoutParams(paramstvtvCode);
        rlBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultUnSelected();
                attachFragment(new HomeFragment(),"Home");
                setSelected(ivBookTicketSelected,ivBookTicket, R.drawable.nav_home_selected);
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
                setSelected(ivMyTicketSelected,ivMyTicket, R.drawable.nav_my_ticket_selected);
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
                setSelected(ivMyProfileSelected,ivMyProfile, R.drawable.nav_my_profile_selected);
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
                setSelected(ivSettingsSelected,ivSettings, R.drawable.nav_setting_selected);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        ivSettingsSelected = nView.getHeaderView(0).findViewById(R.id.ivSettingsSelected);
        ivSettings = nView.getHeaderView(0).findViewById(R.id.ivSettings);
        ivSettings.setBackgroundResource(R.drawable.nav_setting_unselected);

        setSelected(ivBookTicketSelected,ivBookTicket, R.drawable.nav_home_selected);
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
                setSelected(ivMyProfileSelected,ivMyProfile, R.drawable.nav_my_profile_selected);
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
                setSelected(ivBookTicketSelected,ivBookTicket, R.drawable.nav_home_selected);
//                fm.popBackStack();
            }
        }
    }

    public void setHeader(String title){
        tvHeading.setText(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Config.collectLifecycleData(this);
        targetPrefetchContent();
    }

    public void targetPrefetchContent() {
        List<TargetPrefetchObject> prefetchList = new ArrayList<>();
        Map<String, Object> profileParameters;
        profileParameters = new HashMap<String, Object>();
        //profileParameters.put("ProfileParam18Sep", "1");
        Map<String, Object> mboxParameters1 = new HashMap<String, Object>();
        //mboxParameters1.put("MboxParam18Sep", "1");
        //mboxParameters1.put("at_property", "7962ac68-17db-1579-408f-9556feccb477");
        prefetchList.add(Target.createTargetPrefetchObject(Constant.mbox_engage_home, mboxParameters1));
        prefetchList.add(Target.createTargetPrefetchObject(Constant.mbox_engage_search, mboxParameters1));
        Target.TargetCallback<Boolean> prefetchStatusCallback = new Target.TargetCallback<Boolean>() {
            @Override
            public void call(final Boolean status) {
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String cachingStatus = status ? "YES" : "NO";
                        System.out.println("Received Response from prefetch : " + cachingStatus);
                        engageMessage();
                        setUp();

                    }
                });
            }};
        Target.prefetchContent(prefetchList, profileParameters, prefetchStatusCallback);

    }

    public void engageMessage() {
        Target.loadRequest(Constant.mbox_engage_home, "", null, null, null,
                new Target.TargetCallback<String>(){
                    @Override
                    public void call(final String s) {
                        //final ImageView mbox2 = (ImageView)findViewById(R.id.txtMbox2);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Engage Message : " + s);
                                if(s != null && !s.isEmpty()) Utility.showToast(getApplicationContext(), s);
                            }
                        });
                    }
                });
    }
}
