package fragments;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import domain.GetProfileJSONResponse;
import services.TrustConnection;

public class HomeConsumerMyAccount extends Fragment implements View.OnClickListener {


    private ScrollView parentScrollView;
    private ScrollView childScrollView;
    private Button home_myaccount_b_share;

    private Button home_myaccount_b_gallery;
    private ImageView home_myaccount_iv_profilepic;

    private static int RESULT_LOAD_IMG = 11;
    private String imgDecodableString;

    private Button home_myaccount_b_capture_image;
    private static final int CAMERA_REQUEST = 1111;
    private Bitmap photo;

    private Button home_myaccount_b_editprofile;
    private Button home_myaccount_b_socialmedia;

    private String name, lookingFor, facebook, googleplus, twitter, blog, website,
            profileImage = "https://www.dealacceleration.com";
    private String cmp = "null";
    private String urlName = "http://www.dealacceleration.com/public_profiles/";
    private TextView home_myaccount_tv_name, home_myaccount_tv_accounturl_filled, home_myaccount_tv_lookingfor;
    private Bundle bundle;
    private ArrayList<String> pic_list;
    private ProgressBar consumer_myaccount_progressbar;

    public HomeConsumerMyAccount() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_consumer_home_my_account, container, false);

        //----------------------------------------------------------------------------------
        pic_list = getArguments().getStringArrayList("pictures");
        bundle = new Bundle();
        bundle.putStringArrayList("pictures", pic_list);

        parentScrollView = (ScrollView)rootView.findViewById(R.id.parentScrollView);
        childScrollView = (ScrollView)rootView.findViewById(R.id.childScrollView);

        consumer_myaccount_progressbar = (ProgressBar) rootView.findViewById(R.id.consumer_myaccount_progressbar);
        consumer_myaccount_progressbar.setVisibility(View.GONE);

        parentScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                rootView.findViewById(R.id.childScrollView).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        childScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //----------------------------------------------------------------------------------

        home_myaccount_b_share = (Button)rootView.findViewById(R.id.home_myaccount_b_share);
        home_myaccount_b_share.setOnClickListener(this);

        home_myaccount_tv_accounturl_filled = (TextView)rootView.findViewById(R.id.home_myaccount_tv_accounturl_filled);
        //myURL = home_myaccount_tv_accounturl_filled.getText().toString();

        home_myaccount_b_gallery = (Button)rootView.findViewById(R.id.home_myaccount_b_gallery);
        home_myaccount_b_gallery.setOnClickListener(this);

        home_myaccount_iv_profilepic = (ImageView)rootView.findViewById(R.id.home_myaccount_iv_profilepic);
        home_myaccount_iv_profilepic.setOnClickListener(this);

        home_myaccount_b_capture_image = (Button)rootView.findViewById(R.id.home_myaccount_b_capture_image);
        home_myaccount_b_capture_image.setOnClickListener(this);

        home_myaccount_b_editprofile = (Button)rootView.findViewById(R.id.home_myaccount_b_editprofile);
        home_myaccount_b_editprofile.setOnClickListener(this);

        home_myaccount_b_socialmedia = (Button)rootView.findViewById(R.id.home_myaccount_b_socialmedia);
        home_myaccount_b_socialmedia.setOnClickListener(this);

        //-----------------------------------------------------------------------------------------------------------------------

        name = getActivity().getIntent().getStringExtra("Name");
        urlName = urlName + getActivity().getIntent().getStringExtra("ProfileURLName");
        lookingFor = getActivity().getIntent().getStringExtra("Looking_for");
        profileImage =  profileImage + getActivity().getIntent().getStringExtra("Profile_pic");
        facebook = getActivity().getIntent().getStringExtra("facebook");
        twitter = getActivity().getIntent().getStringExtra("twitter");
        googleplus = getActivity().getIntent().getStringExtra("googleplus");
        blog = getActivity().getIntent().getStringExtra("blog");
        website = getActivity().getIntent().getStringExtra("website");

        if(lookingFor.equals(cmp)) lookingFor = " ";

        bundle.putString("name", name);
        bundle.putString("lookingFor", lookingFor);
        bundle.putString("facebook", facebook);
        bundle.putString("twitter", twitter);
        bundle.putString("googleplus", googleplus);
        bundle.putString("blog", blog);
        bundle.putString("website", website);

        Log.d("Name HCMA ", "" + name);
        Log.d("ProfileURLName HCMA ", "" + urlName);
        Log.d("Looking_for HCMA ", "" + lookingFor);

        home_myaccount_tv_name = (TextView)rootView.findViewById(R.id.home_myaccount_tv_name);
        home_myaccount_tv_name.setText(name);

        home_myaccount_tv_accounturl_filled = (TextView)rootView.findViewById(R.id.home_myaccount_tv_accounturl_filled);
        home_myaccount_tv_accounturl_filled.setText(urlName);

        home_myaccount_tv_lookingfor = (TextView)rootView.findViewById(R.id.home_myaccount_tv_lookingfor);
        home_myaccount_tv_lookingfor.setText(lookingFor);

        Log.d("check", profileImage);

        BackgroudThread bdt = new BackgroudThread();
        bdt.execute(profileImage);
        setImageToProfile(photo);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.home_myaccount_b_share:
                consumer_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Deal Acceleration");
                share.putExtra(Intent.EXTRA_TEXT, urlName);

                startActivity(Intent.createChooser(share, "Share at"));
                break;

            case R.id.home_myaccount_b_gallery:
                consumer_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                break;

            case R.id.home_myaccount_b_capture_image:
                consumer_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.home_myaccount_b_editprofile:
                consumer_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent editprofileIntent = new Intent("android.intent.action.CONSUMERMYACCOUNTEDITPROFILE");
                editprofileIntent.putExtras(bundle);
                startActivity(editprofileIntent);
                break;

            case R.id.home_myaccount_b_socialmedia:
                consumer_myaccount_progressbar.setVisibility(View.VISIBLE);
                Intent socialmediaIntent = new Intent("android.intent.action.MYACCOUNTSOCIALMEDIA");
                startActivity(socialmediaIntent);
                break;

            case R.id.home_myaccount_iv_profilepic:
                consumer_myaccount_progressbar.setVisibility(View.VISIBLE);
                if(home_myaccount_iv_profilepic.getDrawable() == null)
                {
                    consumer_myaccount_progressbar.setVisibility(View.GONE);
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
                            home_myaccount_iv_profilepic.setImageBitmap(null);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.image_dialog, null);
                    ImageView imagedialog_iv_pictures = (ImageView) dialogLayout.findViewById(R.id.imagedialog_iv_pictures);
                    imagedialog_iv_pictures.setImageDrawable(home_myaccount_iv_profilepic.getDrawable());
                    consumer_myaccount_progressbar.setVisibility(View.GONE);
                    dialog.setView(dialogLayout);

                    dialog.show();
                    break;
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        consumer_myaccount_progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && null != data)
        {
            try {
                Bitmap bitmap = scaleImage(getContext(), data.getData());
                home_myaccount_iv_profilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (requestCode == CAMERA_REQUEST && null != data)
        {
            try {
                Bitmap bitmap = scaleImage(getContext(), data.getData());
                home_myaccount_iv_profilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && null == data)
            home_myaccount_iv_profilepic.setImageBitmap(null);
    }

    //------------------------------------------------------------------------------------------------------------

    private void setImageToProfile(Bitmap bitpropic)
    {
        home_myaccount_iv_profilepic.setImageBitmap(bitpropic);
    }

    public Bitmap scaleImage(Context context, Uri photoUri) throws IOException {
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

    public int getOrientation(Context context, Uri photoUri) {
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
        protected void onPostExecute(String result)
        {
            setImageToProfile(photo);
        }
    }

}














