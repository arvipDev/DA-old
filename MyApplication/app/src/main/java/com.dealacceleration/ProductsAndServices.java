package com.dealacceleration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import domain.BusinessEditJSONProSer;

public class ProductsAndServices extends AppCompatActivity
{
    private ListView products_services_lv_products;
    private List<Bitmap> listImages = new ArrayList<>();
    private ArrayAdapter<Bitmap> adapter;
    private Button products_services_b_share;
    private String myURL = "https://www.dealacceleration.com/";
    private BusinessEditJSONProSer products_services = new BusinessEditJSONProSer();
    private String curImg;
    private List<String> curImg_list = new ArrayList<>();
    private ProgressBar prod_ser_progressbar;
    private TextView products_services_tv_business_info_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_and_services);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Products/Services");
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

        products_services_lv_products = (ListView)findViewById(R.id.products_services_lv_products);
        products_services_lv_products.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        products_services_b_share = (Button) findViewById(R.id.products_services_b_share);
        products_services_b_share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");

                share.putExtra(Intent.EXTRA_SUBJECT, "Deal Acceleration");
                share.putExtra(Intent.EXTRA_TEXT, myURL);

                startActivity(Intent.createChooser(share, "Share at"));
            }
        });

        prod_ser_progressbar = (ProgressBar)findViewById(R.id.prod_ser_progressbar);
        prod_ser_progressbar.setVisibility(View.VISIBLE);

        products_services.setName_for_url(this.getIntent().getStringExtra("urlName"));
        products_services.setBusdesc(this.getIntent().getStringExtra("busdesc"));
        products_services.setUrlImage(this.getIntent().getStringArrayListExtra("urlimage_list"));

        products_services_tv_business_info_text = (TextView)findViewById(R.id.products_services_tv_business_info_text);
        products_services_tv_business_info_text.setText(products_services.getBusdesc());

        for(int i = 0; i < products_services.getUrlImage().size(); i++)
        {
            curImg = "https://www.dealacceleration.com" + products_services.getUrlImage().get(i);
            System.out.println("curImg test " + curImg);
            curImg_list.add(curImg);
        }

        BackgroudThreadPicture pictureThread = new BackgroudThreadPicture();
        pictureThread.execute("");
        myURL = myURL + products_services.getName_for_url();

        populateListView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    private class MyListAdapter extends ArrayAdapter<Bitmap>
    {
        public MyListAdapter()
        {
            super(ProductsAndServices.this, R.layout.list_products_services, listImages);
        }

        public View getView(final int position, View convertView, ViewGroup parent ) {
            View itemView = convertView;
            Bitmap currentImage = listImages.get(position);

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.list_products_services, parent, false);
            }

            ImageView products_services_list_picture = (ImageView) itemView.findViewById
                    (R.id.products_services_list_picture);

            try {
                System.out.println("inside pop list " + currentImage);
                products_services_list_picture.setImageBitmap(currentImage);
            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.e(e.getMessage(), "Error Arvind");
            }

            return itemView;
        }
    }


    public void populateListView() {
        Log.e("Here", "Inside populateListView");
        adapter = new MyListAdapter();
        products_services_lv_products.setAdapter(adapter);
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
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    //-----------------------------------------------------------------------------------------------------------

    private class BackgroudThreadPicture extends AsyncTask<String, Void, String>
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
            for(int i = 0; i < curImg_list.size(); i++)
            {
                System.out.println("piclist size " + curImg_list.size());
                listImages.add(convertURLtoBitmap(curImg_list.get(i)));
                System.out.println("inside getPictures " + curImg_list.get(i));
                System.out.println("inside getPictures " + listImages.get(i));
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            populateListView();
            prod_ser_progressbar.setVisibility(View.GONE);
        }
    }
}
