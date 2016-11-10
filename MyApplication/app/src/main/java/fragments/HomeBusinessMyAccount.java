package fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dealacceleration.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import domain.BusinessEditJSONAccPro;
import domain.BusinessEditJSONBusSellerInfo;
import domain.BusinessEditJSONContacts;
import domain.BusinessEditJSONProSer;
import domain.BusinessEditJSONSocMedSet;

public class HomeBusinessMyAccount extends Fragment implements View.OnClickListener {


    private ScrollView business_parentScrollView;
    private ScrollView business_childScrollView;
    private Button business_home_myaccount_b_share;

    private Button business_home_myaccount_b_gallery;
    private ImageView business_home_myaccount_iv_profilepic;

    private static int RESULT_LOAD_IMG = 11;
    private String imgDecodableString;

    private Button business_home_myaccount_b_capture_image;
    private static final int CAMERA_REQUEST = 1111;
    private Bitmap photo;

    private Button business_home_myaccount_b_editprofile;
    private Button business_home_myaccount_b_socialmedia, business_home_myaccount_b_products_services;

    private String name, lookingFor,
            profileImage = "https://www.dealacceleration.com";
    private String cmp = "null";
    private String urlName = "http://www.dealacceleration.com/public_profiles/";
    private TextView business_home_myaccount_tv_name, business_home_myaccount_tv_accounturl_filled, business_home_myaccount_tv_lookingfor;
    private Bundle bundle;
    private ArrayList<String> pic_list;
    private BusinessEditJSONProSer products_services = new BusinessEditJSONProSer();
    private BusinessEditJSONBusSellerInfo bus_seller_info = new BusinessEditJSONBusSellerInfo();
    private BusinessEditJSONContacts contacts = new BusinessEditJSONContacts();
    private BusinessEditJSONAccPro account_profile = new BusinessEditJSONAccPro();
    private BusinessEditJSONSocMedSet social_media = new BusinessEditJSONSocMedSet();
    private ProgressBar business_myaccount_progressbar;

    public HomeBusinessMyAccount()
    {

    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_business_home_my_account, container, false);

        //----------------------------------------------------------------------------------
        pic_list = getArguments().getStringArrayList("pictures");
        bundle = new Bundle();
        bundle.putStringArrayList("pictures", pic_list);

        business_parentScrollView = (ScrollView)rootView.findViewById(R.id.business_parentScrollView);
        business_childScrollView = (ScrollView)rootView.findViewById(R.id.business_childScrollView);

        business_myaccount_progressbar = (ProgressBar)rootView.findViewById(R.id.business_myaccount_progressbar);
        business_myaccount_progressbar.setVisibility(View.GONE);

        business_parentScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                rootView.findViewById(R.id.business_childScrollView).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        business_childScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //----------------------------------------------------------------------------------

        business_home_myaccount_b_share = (Button)rootView.findViewById(R.id.business_home_myaccount_b_share);
        business_home_myaccount_b_share.setOnClickListener(this);

        business_home_myaccount_tv_accounturl_filled = (TextView)rootView.findViewById
                (R.id.business_home_myaccount_tv_accounturl_filled);
        //myURL = home_myaccount_tv_accounturl_filled.getText().toString();

        business_home_myaccount_b_gallery = (Button)rootView.findViewById(R.id.business_home_myaccount_b_gallery);
        business_home_myaccount_b_gallery.setOnClickListener(this);

        business_home_myaccount_iv_profilepic = (ImageView)rootView.findViewById(
                R.id.business_home_myaccount_iv_profilepic);
        business_home_myaccount_iv_profilepic.setOnClickListener(this);

        business_home_myaccount_b_capture_image = (Button)rootView.findViewById
                (R.id.business_home_myaccount_b_capture_image);
        business_home_myaccount_b_capture_image.setOnClickListener(this);

        business_home_myaccount_b_editprofile = (Button)rootView.findViewById(R.id.business_home_myaccount_b_editprofile);
        business_home_myaccount_b_editprofile.setOnClickListener(this);

        business_home_myaccount_b_socialmedia = (Button)rootView.findViewById(R.id.business_home_myaccount_b_socialmedia);
        business_home_myaccount_b_socialmedia.setOnClickListener(this);

        business_home_myaccount_b_products_services = (Button)rootView.findViewById
                (R.id.business_home_myaccount_b_products_services);
        business_home_myaccount_b_products_services.setOnClickListener(this);

        //-----------------------------------------------------------------------------------------------------------------------

        name = getActivity().getIntent().getStringExtra("Name");
        urlName = urlName + getActivity().getIntent().getStringExtra("ProfileURLName");
        lookingFor = getActivity().getIntent().getStringExtra("Looking_for");
        profileImage =  profileImage + getActivity().getIntent().getStringExtra("Profile_pic");
        if(lookingFor.equals(cmp)) lookingFor = " ";

        Log.d("Name HCMA ", "" + name);
        Log.d("ProfileURLName HCMA ", "" + urlName);
        Log.d("Looking_for HCMA ", "" + lookingFor);

        business_home_myaccount_tv_name = (TextView)rootView.findViewById(R.id.business_home_myaccount_tv_name);
        business_home_myaccount_tv_name.setText(name);

        business_home_myaccount_tv_accounturl_filled = (TextView)rootView.findViewById(R.id.business_home_myaccount_tv_accounturl_filled);
        business_home_myaccount_tv_accounturl_filled.setText(urlName);

        business_home_myaccount_tv_lookingfor = (TextView)rootView.findViewById(R.id.business_home_myaccount_tv_lookingfor);
        business_home_myaccount_tv_lookingfor.setText(lookingFor);

        //----------------------------------------------------------------------------------------------------------
        products_services.setName_for_url(urlName);
        products_services.setBusdesc(getActivity().getIntent().getStringExtra("busdesc"));
        products_services.setUrlImage(getActivity().getIntent().getStringArrayListExtra("urlimage_list"));

        bus_seller_info.setBusdesc(getActivity().getIntent().getStringExtra("busdesc"));
        bus_seller_info.setBuslook(getActivity().getIntent().getStringExtra("buslook"));

        contacts.setContact1(getActivity().getIntent().getStringExtra("conname1"));
        contacts.setContact2(getActivity().getIntent().getStringExtra("conname2"));
        contacts.setContact3(getActivity().getIntent().getStringExtra("conname3"));
        contacts.setEmail1(getActivity().getIntent().getStringExtra("email1"));
        contacts.setEmail2(getActivity().getIntent().getStringExtra("email2"));
        contacts.setEmail3(getActivity().getIntent().getStringExtra("email3"));
        contacts.setPhone1(getActivity().getIntent().getStringExtra("phone1"));
        contacts.setPhone2(getActivity().getIntent().getStringExtra("phone2"));
        contacts.setPhone3(getActivity().getIntent().getStringExtra("phone3"));
        account_profile.setName(getActivity().getIntent().getStringExtra("Name"));
        account_profile.setWebsite(getActivity().getIntent().getStringExtra("website"));
        account_profile.setAddress1(getActivity().getIntent().getStringExtra("address1"));
        account_profile.setAddress2(getActivity().getIntent().getStringExtra("address2"));
        account_profile.setCity(getActivity().getIntent().getStringExtra("city"));
        account_profile.setState(getActivity().getIntent().getStringExtra("state"));
        account_profile.setZipcode(getActivity().getIntent().getStringExtra("zipcode"));
        account_profile.setCountry_code(getActivity().getIntent().getStringExtra("country_code"));
        social_media.setSocname(getActivity().getIntent().getStringExtra("socname"));
        social_media.setSocwebsite(getActivity().getIntent().getStringExtra("socwebsite"));
        social_media.setSoccity(getActivity().getIntent().getStringExtra("soccity"));
        social_media.setSoczip(getActivity().getIntent().getStringExtra("soczip"));


        bundle.putString("urlName", urlName);
        bundle.putString("busdesc", products_services.getBusdesc());
        bundle.putStringArrayList("urlimage_list", products_services.getUrlImage());
        bundle.putString("buslook", bus_seller_info.getBuslook());
        bundle.putString("conname1", contacts.getContact1());
        bundle.putString("conname2", contacts.getContact2());
        bundle.putString("conname3", contacts.getContact3());
        bundle.putString("email1", contacts.getEmail1());
        bundle.putString("email2", contacts.getEmail2());
        bundle.putString("email3", contacts.getEmail3());
        bundle.putString("phone1", contacts.getPhone1());
        bundle.putString("phone2", contacts.getPhone2());
        bundle.putString("phone3", contacts.getPhone3());
        bundle.putString("country_code", account_profile.getCountry_code());
        bundle.putString("zipcode", account_profile.getZipcode());
        bundle.putString("state", account_profile.getState());
        bundle.putString("city", account_profile.getCity());
        bundle.putString("address2", account_profile.getAddress2());
        bundle.putString("address1", account_profile.getAddress1());
        bundle.putString("website", account_profile.getWebsite());
        bundle.putString("name", account_profile.getName());
        bundle.putString("socname", social_media.getSocname());
        bundle.putString("socwebsite", social_media.getSocwebsite());
        bundle.putString("soccity", social_media.getSoccity());
        bundle.putString("soczip", social_media.getSoczip());

        //----------------------------------------------------------------------------------------------------------------------

        BackgroudThread getProfilePic = new BackgroudThread();
        getProfilePic.execute("");
        business_home_myaccount_iv_profilepic.setImageBitmap(photo);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.business_home_myaccount_b_share:
                business_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");

                share.putExtra(Intent.EXTRA_SUBJECT, "Deal Acceleration");
                share.putExtra(Intent.EXTRA_TEXT, urlName);

                startActivity(Intent.createChooser(share, "Share at"));
                break;

            case R.id.business_home_myaccount_b_gallery:
                business_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                break;

            case R.id.business_home_myaccount_b_capture_image:
                business_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.business_home_myaccount_b_editprofile:
                business_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent editprofileIntent = new Intent("android.intent.action.BUSINESSMYACCOUNTEDITPROFILE");
                editprofileIntent.putExtras(bundle);
                startActivity(editprofileIntent);
                break;

            case R.id.business_home_myaccount_b_socialmedia:
                business_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent socialmediaIntent = new Intent("android.intent.action.MYACCOUNTSOCIALMEDIA");
                startActivity(socialmediaIntent);
                break;

            case R.id.business_home_myaccount_b_products_services:
                business_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent productsandservicesIntent = new Intent("android.intent.action.PRODUCTSANDSERVICES");
                productsandservicesIntent.putExtras(bundle);
                startActivity(productsandservicesIntent);
                break;

            case R.id.business_home_myaccount_iv_profilepic:

                business_myaccount_progressbar.setVisibility(View.VISIBLE);
                if(business_home_myaccount_iv_profilepic.getDrawable() == null)
                {
                    business_myaccount_progressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No profile picture added.",
                            Toast.LENGTH_LONG).show();
                    break;
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            business_home_myaccount_iv_profilepic.setImageBitmap(null);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.image_dialog, null);
                    ImageView imagedialog_iv_pictures = (ImageView) dialogLayout.findViewById(R.id.imagedialog_iv_pictures);
                    imagedialog_iv_pictures.setImageDrawable(business_home_myaccount_iv_profilepic.getDrawable());
                    business_myaccount_progressbar.setVisibility(View.GONE);
                    dialog.setView(dialogLayout);

                    dialog.show();
                    break;
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        business_myaccount_progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && null != data)
        {
            try {
                Bitmap bitmap = scaleImage(getContext(), data.getData());
                business_home_myaccount_iv_profilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && null != data)
        {
            try {
                Bitmap bitmap = scaleImage(getContext(), data.getData());
                business_home_myaccount_iv_profilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && null == data)
            business_home_myaccount_iv_profilepic.setImageBitmap(null);

    }
    //------------------------------------------------------------------------------------------------------------

    private Bitmap scaleImage(Context context, Uri photoUri) throws IOException {
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

    private int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    private class BackgroudThread extends AsyncTask<String, Void, String> {

        private Bitmap getImageFromUrl(String url)
        {
            URL profilePic_url = null;
            Bitmap profile_picture_from_url = null;
            try {
                profilePic_url = new URL(url);
                profile_picture_from_url = BitmapFactory.decodeStream(profilePic_url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return profile_picture_from_url;
        }


        @Override
        protected String doInBackground(String... urls)
        {
            photo = getImageFromUrl(profileImage);
            return "success";
        }

        @Override
        protected void onPostExecute(String result) {
            business_home_myaccount_iv_profilepic.setImageBitmap(photo);
        }
    }

}