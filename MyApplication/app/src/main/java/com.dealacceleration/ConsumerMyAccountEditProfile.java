package com.dealacceleration;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ConsumerMyAccountEditProfile extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener
{
    private ToggleButton myaccount_editprofile_tb_accountprofile, myaccount_editprofile_tb_picture ;
    private RelativeLayout myaccount_editprofile_rl_picture, myaccount_editprofile_rl_accountprofile;

    private Integer images[] = {R.drawable.img2, R.drawable.img3, R.drawable.img1, R.drawable.img4};
    private List<Bitmap> new_img_list = new ArrayList<Bitmap>();
    private List<Bitmap> prev_img_list = new ArrayList<Bitmap>();
    private Boolean oriLandscape = false;
    private Button consumer_myaccount_editprofile_b_browse, consumer_myaccount_editprofile_b_capture,
            consumer_myaccount_editprofile_b_submit;
    private static int RESULT_LOAD_IMG = 11;
    private static final int CAMERA_REQUEST = 1111;
    private ArrayList<String> urlPictures_list;
    private Bundle bundle;
    private String name, lookingFor, facebook, googleplus, twitter, blog, website;
    private EditText consumer_myaccount_editprofile_ev_fullname, consumer_myaccount_editprofile_ev_facebookurl, consumer_myaccount_editprofile_ev_twitterurl,
            consumer_myaccount_editprofile_ev_googleurl, consumer_myaccount_editprofile_ev_blogurl,
            consumer_myaccount_editprofile_ev_website, consumer_myaccount_editprofile_ev_enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_consumer_my_account_edit_profile);

        bundle = getIntent().getExtras();
        urlPictures_list = bundle.getStringArrayList("pictures");
        name = bundle.getString("name");
        lookingFor = bundle.getString("lookingFor");
        facebook = bundle.getString("facebook");
        twitter = bundle.getString("twitter");
        googleplus = bundle.getString("googleplus");
        blog = bundle.getString("blog");
        website = bundle.getString("website");

        System.out.println("picture_list " + urlPictures_list.get(0));

        createBitmapList();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Edit Profile");
        custom_ab_title.setGravity(View.TEXT_ALIGNMENT_CENTER);

        ImageButton custom_iv_sample = (ImageButton) customView.findViewById(R.id.custom_iv_sample);
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

        myaccount_editprofile_tb_accountprofile = (ToggleButton) findViewById(R.id.consumer_myaccount_editprofile_tb_accountprofile);
        myaccount_editprofile_tb_accountprofile.setText(" Account Profile");
        myaccount_editprofile_tb_accountprofile.setTextOff(" Account Profile");
        myaccount_editprofile_tb_accountprofile.setTextOn(" Account Profile");
        myaccount_editprofile_tb_accountprofile.setTextColor(getResources().getColor(R.color.grey_button_text_color));
        myaccount_editprofile_tb_accountprofile.setOnCheckedChangeListener(this);

        myaccount_editprofile_tb_picture = (ToggleButton) findViewById(R.id.consumer_myaccount_editprofile_tb_picture);
        myaccount_editprofile_tb_picture.setText(" Pictures");
        myaccount_editprofile_tb_picture.setTextOn(" Pictures");
        myaccount_editprofile_tb_picture.setTextOff(" Pictures");
        myaccount_editprofile_tb_picture.setTextColor(getResources().getColor(R.color.grey_button_text_color));
        myaccount_editprofile_tb_picture.setOnCheckedChangeListener(this);

        myaccount_editprofile_rl_accountprofile = (RelativeLayout) findViewById(R.id.consumer_myaccount_editprofile_rl_accountprofile);
        myaccount_editprofile_rl_picture = (RelativeLayout) findViewById(R.id.consumer_myaccount_editprofile_rl_picture);

        consumer_myaccount_editprofile_b_browse = (Button)findViewById(R.id.consumer_myaccount_editprofile_b_browse);
        consumer_myaccount_editprofile_b_browse.setOnClickListener(this);

        consumer_myaccount_editprofile_b_capture = (Button)findViewById(R.id.consumer_myaccount_editprofile_b_capture);
        consumer_myaccount_editprofile_b_capture.setOnClickListener(this);

        consumer_myaccount_editprofile_b_submit = (Button)findViewById(R.id.consumer_myaccount_editprofile_b_submit);
        consumer_myaccount_editprofile_b_submit.setOnClickListener(this);

        consumer_myaccount_editprofile_ev_fullname = (EditText)findViewById(R.id.consumer_myaccount_editprofile_ev_fullname);
        consumer_myaccount_editprofile_ev_fullname.setText(name);

        consumer_myaccount_editprofile_ev_facebookurl = (EditText)findViewById(R.id.consumer_myaccount_editprofile_ev_facebookurl);
        consumer_myaccount_editprofile_ev_facebookurl.setText(facebook);

        consumer_myaccount_editprofile_ev_twitterurl = (EditText)findViewById(R.id.consumer_myaccount_editprofile_ev_twitterurl);
        consumer_myaccount_editprofile_ev_twitterurl.setText(twitter);

        consumer_myaccount_editprofile_ev_googleurl = (EditText)findViewById(R.id.consumer_myaccount_editprofile_ev_googleurl);
        consumer_myaccount_editprofile_ev_googleurl.setText(googleplus);

        consumer_myaccount_editprofile_ev_blogurl = (EditText)findViewById(R.id.consumer_myaccount_editprofile_ev_blogurl);
        consumer_myaccount_editprofile_ev_blogurl.setText(blog);

        consumer_myaccount_editprofile_ev_website = (EditText)findViewById(R.id.consumer_myaccount_editprofile_ev_website);
        consumer_myaccount_editprofile_ev_website.setText(website);

        consumer_myaccount_editprofile_ev_enter = (EditText)findViewById(R.id.consumer_myaccount_editprofile_ev_enter);
        consumer_myaccount_editprofile_ev_enter.setText(lookingFor);

        setVisibilityOfLayoutToGone();
        showPrevImgList();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.consumer_myaccount_editprofile_b_browse:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                break;

            case R.id.consumer_myaccount_editprofile_b_capture:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.consumer_myaccount_editprofile_b_submit:
                for(Bitmap image : new_img_list) prev_img_list.add(image);
                Toast toast = new Toast(this);
                Toast.makeText(getApplicationContext(), "Successfully submitted",
                Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && null != data)
        {
            try {
                Bitmap bitmap = scaleImage(this, data.getData());
                new_img_list.add(bitmap);
                addImagesToThegallery();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (requestCode == CAMERA_REQUEST && null != data)
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        setVisibilityOfToggleButtonToGone();
        if(buttonView == myaccount_editprofile_tb_accountprofile) {
            if (isChecked)
            {
                myaccount_editprofile_tb_picture.setChecked(false);

                myaccount_editprofile_tb_picture.setVisibility(View.GONE);
                myaccount_editprofile_rl_accountprofile.setVisibility(View.VISIBLE);
                myaccount_editprofile_rl_picture.setVisibility(View.GONE);
            }
            else
            {
                setVisibilityOfLayoutToGone();
                setVisibilityOfToggleButtonToGone();
            }
        }
        else if(buttonView == myaccount_editprofile_tb_picture)
        {
            if(isChecked)
            {
                myaccount_editprofile_tb_accountprofile.setChecked(false);

                myaccount_editprofile_rl_picture.setVisibility(View.VISIBLE);
                myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
            }
            else
            {
                setVisibilityOfLayoutToGone();
                setVisibilityOfToggleButtonToGone();
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

    //------------------------------------------------------------------------------------------------------------
    // Helper Methods

    private void createBitmapList()
    {
        System.out.println("piclist size " + urlPictures_list.size());
        System.out.println("piclist size " + prev_img_list.size());
        BackgroudThread bt = new BackgroudThread();
        bt.execute("");
        for(int i = 0; i < prev_img_list.size(); i++)
            System.out.println("" + prev_img_list.get(0));
    }

    private class BackgroudThread extends AsyncTask<String, Void, String>
    {

        public Bitmap convertURLtoBitmap(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap pictures = BitmapFactory.decodeStream(input);
                return pictures;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }

        private void getPictures() throws IOException
        {
            for(int i = 0; i < urlPictures_list.size(); i++)
            {
                System.out.println("piclist size " + urlPictures_list.size());
                prev_img_list.add(convertURLtoBitmap(urlPictures_list.get(i)));
                System.out.println("inside getPictures " + urlPictures_list.get(i));
                System.out.println("inside getPictures " + prev_img_list.get(i));
            }
        }

        @Override
        protected String doInBackground(String... urls)
        {
            try {
                getPictures();
                return "executed";
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            showPrevImgList();
        }
    }

    public static Bitmap scaleImage(Context context, Uri photoUri) throws IOException
    {
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
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }
// -------------------------------------------------------------------------------------------------------------
    private void addImagesToThegallery()
    {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.consumer_mygallery);
        Bitmap image = new_img_list.get(new_img_list.size() - 1);
        imageGallery.addView(getImageView(image));
    }

    private void showPrevImgList()
    {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.consumer_mygallery);
        for (Bitmap image : prev_img_list) {
            imageGallery.addView(getImageView(image));
        }
    }

    private void updateGallery()
    {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.consumer_mygallery);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(ConsumerMyAccountEditProfile.this);
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

    private void setVisibilityOfLayoutToGone()
    {
        myaccount_editprofile_rl_picture.setVisibility(View.GONE);
        myaccount_editprofile_rl_accountprofile.setVisibility(View.GONE);
    }

    private void setVisibilityOfToggleButtonToGone()
    {
        myaccount_editprofile_tb_picture.setVisibility(View.VISIBLE);
        myaccount_editprofile_tb_accountprofile.setVisibility(View.VISIBLE);
    }

    //----------------------------------------------------------------------------------------------

}
