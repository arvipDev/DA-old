package com.dealacceleration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseOperation;
import fragments.HomeConsumerMyAccount;
import fragments.HomeFavourites;
import fragments.HomeMail;
import fragments.HomeProfile;
import services.SegmentedRadioGroupHome;

public class HomeConsumerActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener
{

    private SegmentedRadioGroupHome home_rb_segment_type;
    private Toast mToast;
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private int dbUserId;
    private String dbUserName;
    public List<String> userNameList = new ArrayList<>();
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_consumer);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Consumer Account");
        custom_ab_title.setGravity(View.TEXT_ALIGNMENT_CENTER);

        ImageButton custom_iv_sample = (ImageButton)customView.findViewById(R.id.custom_iv_sample);
        custom_iv_sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Refresh Clicked!",
                        Toast.LENGTH_LONG).show();
            }
        });

        ImageButton custom_iv_return_others = (ImageButton)customView.findViewById(R.id.custom_iv_return_others);
        custom_iv_return_others.setVisibility(View.GONE);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(customView, layoutParams);
        actionBar.setDisplayShowCustomEnabled(true);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);


        home_rb_segment_type = (SegmentedRadioGroupHome)findViewById(R.id.home_rb_segment_type);
        home_rb_segment_type.check(R.id.home_rb_myaccount);
        home_rb_segment_type.setOnCheckedChangeListener(this);

        bundle = getIntent().getExtras();
        dbUserId = bundle.getInt("Login_id");
        dbUserName = bundle.getString("Login_username");
        Log.d("User_id ", "" + dbUserId);
        Log.d("User_name ", "" + dbUserName);
        Log.d("User_name ", "" + bundle.getStringArrayList("pictures"));

        fragment = new HomeConsumerMyAccount();
        fragment.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment);
        fragmentTransaction.commit();

        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        putUserNameDB();

        DatabaseOperation dbo = new DatabaseOperation(this);
        long DBsize = dbo.getDBRowSize(dbo);
        Log.v("DBsize", "" + DBsize);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == home_rb_segment_type) {

            if (checkedId == R.id.home_rb_favourites) {
                Fragment fragmentFour = new HomeFavourites();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentFour.setArguments(bundle);

                if (fragmentManager.findFragmentByTag("Four") != null) {
                    Log.d("Inside reuse", "");
                    changeFragmentView(fragmentManager, "Four");
                    fragmentManager.beginTransaction().show
                            (fragmentManager.findFragmentByTag("Four")).commit();
                    toaster(0);
                } else {
                    Log.d("Inside Create", "");
                    changeFragmentView(fragmentManager, "Four");
                    fragmentManager.beginTransaction().add
                            (R.id.fl_container, fragmentFour, "Four").commit();
                    toaster(0);
                }
            } else if (checkedId == R.id.home_rb_myaccount) {
                Fragment fragmentOne = new HomeConsumerMyAccount();
                fragmentOne.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                System.out.println("argument" + bundle);

                if (fragmentManager.findFragmentByTag("One") != null) {
                    Log.d("Inside reuse", "");
                    changeFragmentView(fragmentManager, "One");
                    fragmentManager.beginTransaction().show
                            (fragmentManager.findFragmentByTag("One")).commit();
                    toaster(1);
                } else {
                    Log.d("Inside Create", "");
                    changeFragmentView(fragmentManager, "One");
                    fragmentManager.beginTransaction().add
                            (R.id.fl_container, fragmentOne, "One").commit();
                    toaster(1);
                }
            } else if (checkedId == R.id.home_rb_mail) {
                Fragment fragmentThree = new HomeMail();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentThree.setArguments(bundle);

                changeFragmentView(fragmentManager, "Three");
                fragmentManager.beginTransaction().remove(fragmentThree);
                changeFragmentView(fragmentManager, "Three");
                fragmentManager.beginTransaction().add
                        (R.id.fl_container, fragmentThree, "Three").commit();
                toaster(2);

            } else if (checkedId == R.id.home_rb_profiles) {
                Fragment fragmentTwo = new HomeProfile();
                FragmentManager fragmentManager = getSupportFragmentManager();

                if (fragmentManager.findFragmentByTag("Two") != null) {
                    Log.d("Inside reuse", "");
                    changeFragmentView(fragmentManager, "Two");
                    fragmentManager.beginTransaction().show
                            (fragmentManager.findFragmentByTag("Two")).commit();
                    toaster(3);
                } else {
                    Log.d("Inside Create", "");
                    changeFragmentView(fragmentManager, "Two");
                    fragmentManager.beginTransaction().add
                            (R.id.fl_container, fragmentTwo, "Two").commit();
                    toaster(3);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        //return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(HomeConsumerActivity.this, LogInActivity.class);
        HomeConsumerActivity.this.finish();
        startActivity(i);
    }

    // -------------------------------------------------------------------------------------------
    // Supporting methods

    // Hides the fragment that is inactive
    private void hideFragmentViews(FragmentManager fm, String s1, String s2, String s3)
    {
        if(fm.findFragmentByTag(s1) != null)  fm.beginTransaction().hide
                (fm.findFragmentByTag(s1)).commit();
        if(fm.findFragmentByTag(s2) != null)  fm.beginTransaction().hide
                (fm.findFragmentByTag(s2)).commit();
        if(fm.findFragmentByTag(s3) != null)  fm.beginTransaction().hide
                (fm.findFragmentByTag(s3)).commit();
    }

    //Calls the method that hides the inactive fragments and chooses between active and inactive fragments
    private void changeFragmentView(FragmentManager fragmentManager, String fragment)
    {
        if(fragment.equals("One"))
            hideFragmentViews(fragmentManager, "Two", "Three", "Four");

        else if(fragment.equals("Two"))
            hideFragmentViews(fragmentManager, "One", "Three", "Four");

        else if(fragment.equals("Three"))
            hideFragmentViews(fragmentManager, "Two", "One", "Four");

        else if(fragment.equals("Four"))
            hideFragmentViews(fragmentManager, "Two", "Three", "One");
    }

    private void putUserNameDB()
    {
        if(dbUserId != -1)
        {
            DatabaseOperation db = new DatabaseOperation(this);
            userNameList = db.getUserNameListfromDB(db);
            Boolean isSame = false;
            if(userNameList.size() == 0) db.insertLoginCred(db, dbUserId, dbUserName);
            else
            {
                for(int i = 0; i < userNameList.size(); i++)
                {
                    if(dbUserName.equals(userNameList.get(i))){
                        isSame = true;
                        Log.d("isSame", ""+ isSame);
                        break;
                    }
                    else isSame = false;
                }
                if(isSame != true)  db.insertLoginCred(db, dbUserId, dbUserName);
            }
        }
    }

    private void toaster(int id)
    {
        switch (id)
        {
            case 0:
                mToast.setText("Favourites");
                mToast.show();
                break;
            case 2:
                mToast.setText(" Email, wait for the list to load");
                mToast.show();
                break;
            case 1:
                mToast.setText("My Account");
                mToast.show();
                break;
            case 3:
                mToast.setText("Profiles");
                mToast.show();
                break;
        }
    }
}
