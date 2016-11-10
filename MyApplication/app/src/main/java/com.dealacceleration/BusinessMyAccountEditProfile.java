package com.dealacceleration;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import domain.BusinessEditJSONAccPro;
import domain.BusinessEditJSONBusSellerInfo;
import domain.BusinessEditJSONContacts;
import domain.BusinessEditJSONProSer;
import domain.BusinessEditJSONSocMedSet;


public class BusinessMyAccountEditProfile extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ToggleButton business_myaccount_editprofile_tb_accountprofile, business_myaccount_editprofile_tb_contacts,
            business_myaccount_editprofile_tb_businesssellerinfo, business_myaccount_editprofile_tb_productservuces,
            business_myaccount_editprofile_tb_socialmediasetting;

    private RelativeLayout business_myaccount_editprofile_rl_socialmediasettings,
            business_myaccount_editprofile_rl_productsservices,
            business_myaccount_editprofile_rl_businesssellerinfo, business_myaccount_editprofile_rl_contacts,
            business_myaccount_editprofile_rl_accountprofile;

    private ScrollView business_home_account_sv_accountprofile, business_home_account_sv_contacts,
            business_home_account_sv_businesssellerinfo, business_home_account_sv_socialmediasettings;

    private Button business_myaccount_editprofile_socialmediasettings_b_browseimage,
            business_myaccount_editprofile_socialmediasettings_b_usecamera;

    private EditText business_myaccount_editprofile_et_companyname, business_myaccount_editprofile_et_website,
            business_myaccount_editprofile_et_address1, business_myaccount_editprofile_et_address2,
            business_myaccount_editprofile_et_city, business_myaccount_editprofile_et_state,
            business_myaccount_editprofile_et_zipcode, business_myaccount_editprofile_et_country,
            business_myaccount_editprofile_et_name, business_myaccount_editprofile_et_email,
            business_myaccount_editprofile_et_phone, business_myaccount_editprofile_et_secondaryname,
            business_myaccount_editprofile_et_secondaryemail, business_myaccount_editprofile_et_secondaryphone,
            business_myaccount_editprofile_et_additionalname, business_myaccount_editprofile_et_additionalemail,
            business_myaccount_editprofile_et_additionalphone, business_myaccount_editprofile_et_businessdescription,
            business_myaccount_editprofile_et_businesslookingfor, business_myaccount_editprofile_socialmediasettings_ev_name,
            business_myaccount_editprofile_socialmediasettings_ev_website, business_myaccount_editprofile_socialmediasettings_ev_city,
            business_myaccount_editprofile_socialmediasettings_ev_zipcode;

    private List<Bitmap> new_img_list = new ArrayList<>();
    private List<Bitmap> prev_img_list = new ArrayList<>();
    private Boolean oriLandscape = false;

    private static int RESULT_LOAD_IMG_2 = 11;
    private static final int CAMERA_REQUEST_2 = 1111;
    private ArrayList<String> urlPictures_list;
    private Bundle bundle;

    private BusinessEditJSONProSer products_services = new BusinessEditJSONProSer();
    private BusinessEditJSONBusSellerInfo bus_seller_info = new BusinessEditJSONBusSellerInfo();
    private BusinessEditJSONContacts contacts = new BusinessEditJSONContacts();
    private BusinessEditJSONAccPro account_profile = new BusinessEditJSONAccPro();
    private BusinessEditJSONSocMedSet social_media = new BusinessEditJSONSocMedSet();


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_business_my_account_edit_profile);

        bundle = getIntent().getExtras();
        urlPictures_list = bundle.getStringArrayList("pictures");
        //---------------------------------------------------------------------------------------------------------------------
        products_services.setBusdesc(bundle.getString("busdesc"));
        products_services.setUrlImage(bundle.getStringArrayList("urlimage_list"));
        products_services.setName_for_url(bundle.getString("urlName"));

        bus_seller_info.setBusdesc(bundle.getString("busdesc"));
        bus_seller_info.setBuslook(bundle.getString("buslook"));

        contacts.setContact1(bundle.getString("conname1"));
        contacts.setContact2(bundle.getString("conname2"));
        contacts.setContact3(bundle.getString("conname3"));
        contacts.setEmail1(bundle.getString("email1"));
        contacts.setEmail2(bundle.getString("email2"));
        contacts.setEmail3(bundle.getString("email3"));
        contacts.setPhone1(bundle.getString("phone1"));
        contacts.setPhone2(bundle.getString("phone2"));
        contacts.setPhone3(bundle.getString("phone3"));

        account_profile.setName(bundle.getString("name"));
        account_profile.setWebsite(bundle.getString("website"));
        account_profile.setCity(bundle.getString("city"));
        account_profile.setState(bundle.getString("state"));
        account_profile.setAddress1(bundle.getString("address1"));
        account_profile.setAddress2(bundle.getString("address2"));
        account_profile.setZipcode(bundle.getString("zipcode"));
        account_profile.setCountry_code(bundle.getString("country_code"));

        social_media.setSocname(bundle.getString("socname"));
        social_media.setSocwebsite(bundle.getString("socwebsite"));
        social_media.setSoccity(bundle.getString("soccity"));
        social_media.setSoczip(bundle.getString("soczip"));

        //---------------------------------------------------------------------------------------------------------------------
        business_myaccount_editprofile_et_companyname = (EditText)findViewById(R.id.business_myaccount_editprofile_et_companyname);
        business_myaccount_editprofile_et_companyname.setText(account_profile.getName());

        business_myaccount_editprofile_et_website = (EditText)findViewById(R.id.business_myaccount_editprofile_et_website);
        business_myaccount_editprofile_et_website.setText(account_profile.getWebsite());

        business_myaccount_editprofile_et_address1 = (EditText)findViewById(R.id.business_myaccount_editprofile_et_address1);
        business_myaccount_editprofile_et_address1.setText(account_profile.getAddress1());

        business_myaccount_editprofile_et_address2 = (EditText)findViewById(R.id.business_myaccount_editprofile_et_address2);
        business_myaccount_editprofile_et_address2.setText(account_profile.getAddress2());

        business_myaccount_editprofile_et_city = (EditText)findViewById(R.id.business_myaccount_editprofile_et_city);
        business_myaccount_editprofile_et_city.setText(account_profile.getCity());

        business_myaccount_editprofile_et_state = (EditText)findViewById(R.id.business_myaccount_editprofile_et_state);
        business_myaccount_editprofile_et_state.setText(account_profile.getState());

        business_myaccount_editprofile_et_zipcode = (EditText)findViewById(R.id.business_myaccount_editprofile_et_zipcode);
        business_myaccount_editprofile_et_zipcode.setText(account_profile.getZipcode());

        business_myaccount_editprofile_et_country = (EditText)findViewById(R.id.business_myaccount_editprofile_et_country);
        business_myaccount_editprofile_et_country.setText(account_profile.getCountry_code());

        business_myaccount_editprofile_et_additionalphone = (EditText)findViewById(R.id.business_myaccount_editprofile_et_additionalphone);
        business_myaccount_editprofile_et_additionalphone.setText(contacts.getPhone3());

        business_myaccount_editprofile_et_secondaryphone = (EditText)findViewById(R.id.business_myaccount_editprofile_et_secondaryphone);
        business_myaccount_editprofile_et_secondaryphone.setText(contacts.getPhone2());

        business_myaccount_editprofile_et_phone = (EditText)findViewById(R.id.business_myaccount_editprofile_et_phone);
        business_myaccount_editprofile_et_phone.setText(contacts.getPhone1());

        business_myaccount_editprofile_et_additionalemail = (EditText)findViewById(R.id.business_myaccount_editprofile_et_additionalemail);
        business_myaccount_editprofile_et_additionalemail.setText(contacts.getEmail3());

        business_myaccount_editprofile_et_email = (EditText)findViewById(R.id.business_myaccount_editprofile_et_email);
        business_myaccount_editprofile_et_email.setText(contacts.getEmail1());

        business_myaccount_editprofile_et_secondaryemail = (EditText)findViewById(R.id.business_myaccount_editprofile_et_secondaryemail);
        business_myaccount_editprofile_et_secondaryemail.setText(contacts.getContact2());

        business_myaccount_editprofile_et_additionalname = (EditText)findViewById(R.id.business_myaccount_editprofile_et_additionalname);
        business_myaccount_editprofile_et_additionalname.setText(contacts.getContact3());

        business_myaccount_editprofile_et_secondaryname = (EditText)findViewById(R.id.business_myaccount_editprofile_et_secondaryname);
        business_myaccount_editprofile_et_secondaryname.setText(contacts.getContact2());

        business_myaccount_editprofile_et_name = (EditText)findViewById(R.id.business_myaccount_editprofile_et_name);
        business_myaccount_editprofile_et_name.setText(contacts.getContact1());

        business_myaccount_editprofile_et_businessdescription = (EditText)findViewById(R.id.business_myaccount_editprofile_et_businessdescription);
        business_myaccount_editprofile_et_businessdescription.setText(bus_seller_info.getBusdesc());

        business_myaccount_editprofile_et_businesslookingfor = (EditText)findViewById(R.id.business_myaccount_editprofile_et_businesslookingfor);
        business_myaccount_editprofile_et_businesslookingfor.setText(bus_seller_info.getBuslook());

        business_myaccount_editprofile_socialmediasettings_ev_name = (EditText)findViewById(R.id.business_myaccount_editprofile_socialmediasettings_ev_name);
        business_myaccount_editprofile_socialmediasettings_ev_name.setText(social_media.getSocname());

        business_myaccount_editprofile_socialmediasettings_ev_website = (EditText)findViewById(R.id.business_myaccount_editprofile_socialmediasettings_ev_website);
        business_myaccount_editprofile_socialmediasettings_ev_website.setText(social_media.getSocwebsite());

        business_myaccount_editprofile_socialmediasettings_ev_city = (EditText)findViewById(R.id.business_myaccount_editprofile_socialmediasettings_ev_city);
        business_myaccount_editprofile_socialmediasettings_ev_city.setText(social_media.getSoccity());

        business_myaccount_editprofile_socialmediasettings_ev_zipcode = (EditText)findViewById(R.id.business_myaccount_editprofile_socialmediasettings_ev_zipcode);
        business_myaccount_editprofile_socialmediasettings_ev_zipcode.setText(social_media.getSoczip());

        //-----------------------------------------------------------------------------------------------------------------------

        System.out.println("picture_list " + urlPictures_list.get(0));

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Edit Profile");
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
        custom_iv_return_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(customView, layoutParams);
        actionBar.setDisplayShowCustomEnabled(true);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        business_myaccount_editprofile_tb_accountprofile = (ToggleButton) findViewById(R.id.business_myaccount_editprofile_tb_accountprofile);
        business_myaccount_editprofile_tb_accountprofile.setText(" Account Profile");
        business_myaccount_editprofile_tb_accountprofile.setTextOff(" Account Profile");
        business_myaccount_editprofile_tb_accountprofile.setTextOn(" Account Profile");
        business_myaccount_editprofile_tb_accountprofile.setTextColor(getResources().getColor(R.color.grey_button_text_color));
        business_myaccount_editprofile_tb_accountprofile.setOnCheckedChangeListener(this);

        business_myaccount_editprofile_tb_contacts = (ToggleButton) findViewById(R.id.business_myaccount_editprofile_tb_contacts);
        business_myaccount_editprofile_tb_contacts.setText(" Contacts");
        business_myaccount_editprofile_tb_contacts.setTextOff(" Contacts ");
        business_myaccount_editprofile_tb_contacts.setTextOn(" Contacts ");
        business_myaccount_editprofile_tb_contacts.setTextColor(getResources().getColor(R.color.grey_button_text_color));
        business_myaccount_editprofile_tb_contacts.setOnCheckedChangeListener(this);

        business_myaccount_editprofile_tb_businesssellerinfo = (ToggleButton) findViewById(R.id.business_myaccount_editprofile_tb_businesssellerinfo);
        business_myaccount_editprofile_tb_businesssellerinfo.setText(" Business/seller information");
        business_myaccount_editprofile_tb_businesssellerinfo.setTextOff(" Business/seller information ");
        business_myaccount_editprofile_tb_businesssellerinfo.setTextOn(" Business/seller information ");
        business_myaccount_editprofile_tb_businesssellerinfo.setTextColor(getResources().getColor(R.color.grey_button_text_color));
        business_myaccount_editprofile_tb_businesssellerinfo.setOnCheckedChangeListener(this);

        business_myaccount_editprofile_tb_productservuces = (ToggleButton) findViewById(R.id.business_myaccount_editprofile_tb_productservuces);
        business_myaccount_editprofile_tb_productservuces.setText(" Products and Services ");
        business_myaccount_editprofile_tb_productservuces.setTextOff(" Products and Services ");
        business_myaccount_editprofile_tb_productservuces.setTextOn(" Products and Services ");
        business_myaccount_editprofile_tb_productservuces.setTextColor(getResources().getColor(R.color.grey_button_text_color));
        business_myaccount_editprofile_tb_productservuces.setOnCheckedChangeListener(this);

        business_myaccount_editprofile_tb_socialmediasetting = (ToggleButton) findViewById(R.id.business_myaccount_editprofile_tb_socialmediasetting);
        business_myaccount_editprofile_tb_socialmediasetting.setText(" Social media settings ");
        business_myaccount_editprofile_tb_socialmediasetting.setTextOff(" Social media settings ");
        business_myaccount_editprofile_tb_socialmediasetting.setTextOn(" Social media settings ");
        business_myaccount_editprofile_tb_socialmediasetting.setTextColor(getResources().getColor(R.color.grey_button_text_color));
        business_myaccount_editprofile_tb_socialmediasetting.setOnCheckedChangeListener(this);

        business_myaccount_editprofile_rl_socialmediasettings = (RelativeLayout) findViewById(R.id.business_myaccount_editprofile_rl_socialmediasettings);
        business_myaccount_editprofile_rl_productsservices = (RelativeLayout) findViewById(R.id.business_myaccount_editprofile_rl_productsservices);
        business_myaccount_editprofile_rl_businesssellerinfo = (RelativeLayout) findViewById(R.id.business_myaccount_editprofile_rl_businesssellerinfo);
        business_myaccount_editprofile_rl_contacts = (RelativeLayout) findViewById(R.id.business_myaccount_editprofile_rl_contacts);
        business_myaccount_editprofile_rl_accountprofile = (RelativeLayout) findViewById(R.id.business_myaccount_editprofile_rl_accountprofile);

        business_home_account_sv_accountprofile = (ScrollView)findViewById(R.id.business_home_account_sv_accountprofile);
        business_home_account_sv_contacts = (ScrollView)findViewById(R.id.business_home_account_sv_contacts);
        business_home_account_sv_businesssellerinfo = (ScrollView)findViewById(R.id.business_home_account_sv_businesssellerinfo);
        business_home_account_sv_socialmediasettings = (ScrollView)findViewById(R.id.business_home_account_sv_socialmediasettings);

        setVisibilityOfLayoutToGone();

        business_myaccount_editprofile_socialmediasettings_b_browseimage = (Button)findViewById
                (R.id.business_myaccount_editprofile_socialmediasettings_b_browseimage);
        business_myaccount_editprofile_socialmediasettings_b_browseimage.setOnClickListener(this);

        business_myaccount_editprofile_socialmediasettings_b_usecamera = (Button)findViewById
                (R.id.business_myaccount_editprofile_socialmediasettings_b_usecamera);
        business_myaccount_editprofile_socialmediasettings_b_usecamera.setOnClickListener(this);

        showPrevImgList();


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.business_myaccount_editprofile_socialmediasettings_b_browseimage:
                Intent galleryIntent2 = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent2, RESULT_LOAD_IMG_2);
                break;

            case R.id.business_myaccount_editprofile_socialmediasettings_b_usecamera:
                Intent cameraIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent2, CAMERA_REQUEST_2);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        setVisibilityOfToggleButtonToGone();
        if(buttonView == business_myaccount_editprofile_tb_accountprofile)
        {
            if (isChecked)
            {
                if(oriLandscape)
                {
                    business_myaccount_editprofile_tb_contacts.setChecked(false);
                    business_myaccount_editprofile_tb_contacts.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_businesssellerinfo.setChecked(false);
                    business_myaccount_editprofile_tb_businesssellerinfo.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_productservuces.setChecked(false);
                    business_myaccount_editprofile_tb_productservuces.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_socialmediasetting.setChecked(false);
                    business_myaccount_editprofile_tb_socialmediasetting.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_accountprofile.setVisibility(View.VISIBLE);

                    business_home_account_sv_accountprofile.setVisibility(View.VISIBLE);
                    business_home_account_sv_contacts.setVisibility(View.GONE);
                    business_home_account_sv_businesssellerinfo.setVisibility(View.GONE);
                    business_home_account_sv_socialmediasettings.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_contacts.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_productsservices.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.GONE);
                    return;
                }
                business_myaccount_editprofile_tb_contacts.setChecked(false);
                business_myaccount_editprofile_tb_contacts.setVisibility(View.GONE);
                business_myaccount_editprofile_tb_businesssellerinfo.setChecked(false);
                business_myaccount_editprofile_tb_businesssellerinfo.setVisibility(View.GONE);
                business_myaccount_editprofile_tb_productservuces.setChecked(false);
                business_myaccount_editprofile_tb_productservuces.setVisibility(View.GONE);
                business_myaccount_editprofile_tb_socialmediasetting.setChecked(false);
                business_myaccount_editprofile_tb_socialmediasetting.setVisibility(View.GONE);

                business_myaccount_editprofile_rl_accountprofile.setVisibility(View.VISIBLE);

                business_home_account_sv_accountprofile.setVisibility(View.VISIBLE);
                business_home_account_sv_contacts.setVisibility(View.GONE);
                business_home_account_sv_businesssellerinfo.setVisibility(View.GONE);
                business_home_account_sv_socialmediasettings.setVisibility(View.GONE);

                business_myaccount_editprofile_rl_contacts.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_productsservices.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.GONE);
            }
            else
            {
                setVisibilityOfLayoutToGone();
                setVisibilityOfToggleButtonToGone();
            }
        }
        else if(buttonView == business_myaccount_editprofile_tb_contacts)
        {
            if(isChecked)
            {
                if(oriLandscape)
                {
                    business_myaccount_editprofile_tb_accountprofile.setChecked(false);
                    business_myaccount_editprofile_tb_accountprofile.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_businesssellerinfo.setChecked(false);
                    business_myaccount_editprofile_tb_businesssellerinfo.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_productservuces.setChecked(false);
                    business_myaccount_editprofile_tb_productservuces.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_socialmediasetting.setChecked(false);
                    business_myaccount_editprofile_tb_socialmediasetting.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_contacts.setVisibility(View.VISIBLE);

                    business_home_account_sv_contacts.setVisibility(View.VISIBLE);
                    business_home_account_sv_accountprofile.setVisibility(View.GONE);
                    business_home_account_sv_businesssellerinfo.setVisibility(View.GONE);
                    business_home_account_sv_socialmediasettings.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_productsservices.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.GONE);
                    return;
                }
                business_myaccount_editprofile_tb_accountprofile.setChecked(false);
                business_myaccount_editprofile_tb_businesssellerinfo.setChecked(false);
                business_myaccount_editprofile_tb_businesssellerinfo.setVisibility(View.GONE);
                business_myaccount_editprofile_tb_productservuces.setChecked(false);
                business_myaccount_editprofile_tb_productservuces.setVisibility(View.GONE);
                business_myaccount_editprofile_tb_socialmediasetting.setChecked(false);
                business_myaccount_editprofile_tb_socialmediasetting.setVisibility(View.GONE);

                business_myaccount_editprofile_rl_contacts.setVisibility(View.VISIBLE);

                business_home_account_sv_contacts.setVisibility(View.VISIBLE);
                business_home_account_sv_accountprofile.setVisibility(View.GONE);
                business_home_account_sv_businesssellerinfo.setVisibility(View.GONE);
                business_home_account_sv_socialmediasettings.setVisibility(View.GONE);

                business_myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_productsservices.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.GONE);
            }
            else
            {
                setVisibilityOfLayoutToGone();
                setVisibilityOfToggleButtonToGone();
            }
        }
        else if(buttonView == business_myaccount_editprofile_tb_businesssellerinfo)
        {
            if(isChecked)
            {
                if(oriLandscape)
                {
                    business_myaccount_editprofile_tb_accountprofile.setChecked(false);
                    business_myaccount_editprofile_tb_accountprofile.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_contacts.setChecked(false);
                    business_myaccount_editprofile_tb_contacts.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_productservuces.setChecked(false);
                    business_myaccount_editprofile_tb_productservuces.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_socialmediasetting.setChecked(false);
                    business_myaccount_editprofile_tb_socialmediasetting.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.VISIBLE);

                    business_home_account_sv_businesssellerinfo.setVisibility(View.VISIBLE);
                    business_home_account_sv_contacts.setVisibility(View.GONE);
                    business_home_account_sv_accountprofile.setVisibility(View.GONE);
                    business_home_account_sv_socialmediasettings.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_contacts.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_productsservices.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.GONE);
                    return;
                }
                business_myaccount_editprofile_tb_accountprofile.setChecked(false);
                business_myaccount_editprofile_tb_contacts.setChecked(false);
                business_myaccount_editprofile_tb_productservuces.setChecked(false);
                business_myaccount_editprofile_tb_productservuces.setVisibility(View.GONE);
                business_myaccount_editprofile_tb_socialmediasetting.setChecked(false);
                business_myaccount_editprofile_tb_socialmediasetting.setVisibility(View.GONE);

                business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.VISIBLE);

                business_home_account_sv_businesssellerinfo.setVisibility(View.VISIBLE);
                business_home_account_sv_contacts.setVisibility(View.GONE);
                business_home_account_sv_accountprofile.setVisibility(View.GONE);
                business_home_account_sv_socialmediasettings.setVisibility(View.GONE);

                business_myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_contacts.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_productsservices.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.GONE);
            }
            else
            {
                setVisibilityOfLayoutToGone();
                setVisibilityOfToggleButtonToGone();
            }
        }
        else if(buttonView == business_myaccount_editprofile_tb_productservuces)
        {
            if(isChecked)
            {
                if(oriLandscape)
                {
                    business_myaccount_editprofile_tb_accountprofile.setChecked(false);
                    business_myaccount_editprofile_tb_accountprofile.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_businesssellerinfo.setChecked(false);
                    business_myaccount_editprofile_tb_businesssellerinfo.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_contacts.setChecked(false);
                    business_myaccount_editprofile_tb_contacts.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_socialmediasetting.setChecked(false);
                    business_myaccount_editprofile_tb_socialmediasetting.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_productsservices.setVisibility(View.VISIBLE);

                    business_home_account_sv_contacts.setVisibility(View.GONE);
                    business_home_account_sv_accountprofile.setVisibility(View.GONE);
                    business_home_account_sv_businesssellerinfo.setVisibility(View.GONE);
                    business_home_account_sv_socialmediasettings.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_contacts.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.GONE);
                    return;
                }
                business_myaccount_editprofile_tb_accountprofile.setChecked(false);
                business_myaccount_editprofile_tb_businesssellerinfo.setChecked(false);
                business_myaccount_editprofile_tb_contacts.setChecked(false);
                business_myaccount_editprofile_tb_socialmediasetting.setChecked(false);
                business_myaccount_editprofile_tb_socialmediasetting.setVisibility(View.GONE);

                business_myaccount_editprofile_rl_productsservices.setVisibility(View.VISIBLE);

                business_home_account_sv_contacts.setVisibility(View.GONE);
                business_home_account_sv_accountprofile.setVisibility(View.GONE);
                business_home_account_sv_businesssellerinfo.setVisibility(View.GONE);
                business_home_account_sv_socialmediasettings.setVisibility(View.GONE);

                business_myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_contacts.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.GONE);
            }
            else
            {
                setVisibilityOfLayoutToGone();
                setVisibilityOfToggleButtonToGone();
            }
        }
        else if(buttonView == business_myaccount_editprofile_tb_socialmediasetting)
        {
            if(isChecked)
            {
                if(oriLandscape)
                {
                    business_myaccount_editprofile_tb_accountprofile.setChecked(false);
                    business_myaccount_editprofile_tb_accountprofile.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_businesssellerinfo.setChecked(false);
                    business_myaccount_editprofile_tb_businesssellerinfo.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_productservuces.setChecked(false);
                    business_myaccount_editprofile_tb_productservuces.setVisibility(View.GONE);
                    business_myaccount_editprofile_tb_contacts.setChecked(false);
                    business_myaccount_editprofile_tb_contacts.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.VISIBLE);

                    business_home_account_sv_socialmediasettings.setVisibility(View.VISIBLE);
                    business_home_account_sv_contacts.setVisibility(View.GONE);
                    business_home_account_sv_accountprofile.setVisibility(View.GONE);
                    business_home_account_sv_businesssellerinfo.setVisibility(View.GONE);

                    business_myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_productsservices.setVisibility(View.GONE);
                    business_myaccount_editprofile_rl_contacts.setVisibility(View.GONE);
                    return;
                }
                business_myaccount_editprofile_tb_accountprofile.setChecked(false);
                business_myaccount_editprofile_tb_businesssellerinfo.setChecked(false);
                business_myaccount_editprofile_tb_productservuces.setChecked(false);
                business_myaccount_editprofile_tb_contacts.setChecked(false);

                business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.VISIBLE);

                business_home_account_sv_socialmediasettings.setVisibility(View.VISIBLE);
                business_home_account_sv_contacts.setVisibility(View.GONE);
                business_home_account_sv_accountprofile.setVisibility(View.GONE);
                business_home_account_sv_businesssellerinfo.setVisibility(View.GONE);

                business_myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_productsservices.setVisibility(View.GONE);
                business_myaccount_editprofile_rl_contacts.setVisibility(View.GONE);
            }
            else
            {
                setVisibilityOfLayoutToGone();
                setVisibilityOfToggleButtonToGone();
            }
        }
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        //return true;
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG_2 && null != data)
        {
            try {
                Bitmap bitmap = scaleImage(this, data.getData());
                new_img_list.add(bitmap);
                addImagesToThegallery();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (requestCode == CAMERA_REQUEST_2 && null != data)
        {
            try {
                Bitmap bitmap = scaleImage(this, data.getData());
                new_img_list.add(bitmap);
                addImagesToThegallery();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            oriLandscape = true;
            updateGallery();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            oriLandscape = false;
            updateGallery();
        }
    }

    //------------------------------------------------------------------------------------------------------------
    // Helper classes

    private void setVisibilityOfLayoutToGone()
    {
        business_myaccount_editprofile_rl_socialmediasettings.setVisibility(View.GONE);
        business_myaccount_editprofile_rl_productsservices.setVisibility(View.GONE);
        business_myaccount_editprofile_rl_businesssellerinfo.setVisibility(View.GONE);
        business_myaccount_editprofile_rl_contacts.setVisibility(View.GONE);
        business_myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
    }

    private void setVisibilityOfToggleButtonToGone()
    {
        business_myaccount_editprofile_tb_accountprofile.setVisibility(View.VISIBLE);
        business_myaccount_editprofile_tb_contacts.setVisibility(View.VISIBLE);
        business_myaccount_editprofile_tb_businesssellerinfo.setVisibility(View.VISIBLE);
        business_myaccount_editprofile_tb_productservuces.setVisibility(View.VISIBLE);
        business_myaccount_editprofile_tb_socialmediasetting.setVisibility(View.VISIBLE);
    }

    public static Bitmap scaleImage(Context context, Uri photoUri) throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > 200 || rotatedHeight > 200) {
            float widthRatio = ((float) rotatedWidth) / ((float) 200);
            float heightRatio = ((float) rotatedHeight) / ((float) 200);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        is.close();

        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        String type = context.getContentResolver().getType(photoUri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (type.equals("image/png")) {
            srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        } else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
            srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        byte[] bMapArray = baos.toByteArray();
        baos.close();
        return BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }


    public Bitmap resizeImage(Bitmap image)
    {
        Bitmap img;
        if((image.getHeight() < 200 || image.getWidth() < 200) && oriLandscape == false)
        {
            img = Bitmap.createScaledBitmap(image, 200, 200, false);
            Toast.makeText(getApplicationContext(), "Min image size = 200x200, this image is resized.",
                    Toast.LENGTH_LONG).show();
        }
        else if ((image.getHeight() > 400 || image.getWidth() > 400)&& oriLandscape == false)
        {
            img = Bitmap.createScaledBitmap(image, 400, 400, false);
            Toast.makeText(getApplicationContext(), "Max image size = 400x400, this image is resized.",
                    Toast.LENGTH_LONG).show();
        }
        else if ( oriLandscape )
        {
            img = Bitmap.createScaledBitmap(image, 600, 600, false);
            Toast.makeText(getApplicationContext(), "Orientation changed",
                    Toast.LENGTH_LONG).show();
        }
        else img = Bitmap.createScaledBitmap(image, image.getWidth(), image.getHeight(), false);
        return img;
    }

    private void addImagesToThegallery() {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.business_myaccount_editprofile_socialmediasettings4_mygallery);
        Bitmap image = new_img_list.get(new_img_list.size() - 1);
        imageGallery.addView(getImageView(image));
    }

    private void showPrevImgList()
    {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.business_myaccount_editprofile_socialmediasettings4_mygallery);
        for (Bitmap image : prev_img_list) {
            imageGallery.addView(getImageView(image));
        }
    }

    private void updateGallery()
    {
        LinearLayout imageGallery = (LinearLayout) findViewById
                (R.id.business_myaccount_editprofile_socialmediasettings4_mygallery);
        imageGallery.removeAllViews();
        for (Bitmap image : prev_img_list) {
            imageGallery.addView(getImageView(image));
        }
        for (Bitmap image : new_img_list) {
            imageGallery.addView(getImageView(image));
        }
    }

    private View getImageView(final Bitmap image)
    {
        Log.d("baa "+image.getWidth(), ""+image.getHeight());
        final Bitmap img = resizeImage(image);
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageBitmap(img);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(10, 0, 10, 0);
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setAdjustViewBounds(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BusinessMyAccountEditProfile.this);
                builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(prev_img_list.contains(image)) prev_img_list.remove(image);
                        else new_img_list.remove(image);
                        updateGallery();
                    }
                });
                AlertDialog dialog = builder.create();
                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.image_dialog, null);
                ImageView imagedialog_iv_pictures = (ImageView) dialogLayout.findViewById(R.id.imagedialog_iv_pictures);
                imagedialog_iv_pictures.setImageBitmap(image);
                dialog.setView(dialogLayout);

                dialog.show();
            }
        });
        return imageView;
    }

}
