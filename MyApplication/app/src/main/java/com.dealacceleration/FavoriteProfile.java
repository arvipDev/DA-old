package com.dealacceleration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import domain.FavoriteProfileJSONResponse;
import domain.Profile;
import services.ProfileCustomList;
import services.TrustConnection;

public class FavoriteProfile extends AppCompatActivity {

    private String auth_token, email_id, profile_id;
    private FavoriteProfileJSONResponse jsonObj;
    private TextView favorite_profile_tv_name, favorite_profile_tv_bus;
    private ImageView favorite_profile_iv;
    private Button favorite_profile_b_share, favorite_profile_b_email;
    private ListView favorite_profile_lv_products_services;
    private ProfileCustomList adapter;
    private Bitmap picture;
    private List<Profile> profiles = new ArrayList<>();
    private Context context = this;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_profile);

        Bundle bundle = getIntent().getExtras();
        auth_token = bundle.getString("auth_token");
        email_id = bundle.getString("email_id");
        profile_id = bundle.getString("profile_id");

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Favorite Profile");
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

        //---------------------------------------------------------------------------------------------

        favorite_profile_tv_name = (TextView) findViewById(R.id.favorite_profile_tv_name);
        favorite_profile_iv = (ImageView)findViewById(R.id.favorite_profile_iv);
        favorite_profile_b_email = (Button) findViewById(R.id.favorite_profile_b_email);
        favorite_profile_b_share = (Button) findViewById(R.id.favorite_profile_b_share);
        favorite_profile_tv_bus = (TextView) findViewById(R.id.favorite_profile_tv_bus);
        favorite_profile_lv_products_services = (ListView) findViewById(R.id.favorite_profile_lv_products_services);
        progress = (ProgressBar)findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        BackgroudThread bt = new BackgroudThread();
        bt.execute("");

        BackgroudThreadPicture pictureThread = new BackgroudThreadPicture();
        pictureThread.execute("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        //return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //------------------------------------------------------------------------------------------------------

    private class BackgroudThread extends AsyncTask<String, Void, String> {
        String jsonResponse;

        private void jsonGET() throws IOException {
            String urlString = "https://www.dealacceleration.com/mobile/api/v1/profiles/get_profile.json?id=" + profile_id;
            URL urlDA = new URL(urlString);
            TrustManager[] trustManager = new TrustManager[] {new TrustConnection()};

            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustManager, new java.security.SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                Log.e("No Algo", " Exception");
            }catch (KeyManagementException e) {
                Log.e("Key Management", " Exception");
            }

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpURLConnection favoriteProfileDAConnection = (HttpURLConnection) urlDA.openConnection();
            favoriteProfileDAConnection.setDoOutput(false);
            favoriteProfileDAConnection.setDoInput(true);
            favoriteProfileDAConnection.setConnectTimeout(5000);
            favoriteProfileDAConnection.setReadTimeout(5000);
            favoriteProfileDAConnection.setUseCaches(false);

            favoriteProfileDAConnection.setRequestProperty("Content-Type", "application/json");
            favoriteProfileDAConnection.setRequestProperty("Accept", "application/json");
            favoriteProfileDAConnection.setRequestProperty("X-User-Token", auth_token);
            favoriteProfileDAConnection.setRequestProperty("X-User-Email", email_id);
            favoriteProfileDAConnection.setRequestMethod("GET");

            StringBuilder sb = new StringBuilder();
            int HttpResult = favoriteProfileDAConnection.getResponseCode();
            Log.d("Json response ", "" + HttpResult + " " + favoriteProfileDAConnection.getResponseMessage());

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(favoriteProfileDAConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                jsonResponse = "" + sb.toString();

                jsonObj = new FavoriteProfileJSONResponse();

                try {
                    JSONObject jsonToPOJO = new JSONObject(jsonResponse);
                    jsonObj.setName(jsonToPOJO.getString("merchant_full_name"));
                    jsonObj.setProfile_pic(jsonToPOJO.getString("affiliate_url"));
                    jsonObj.setShare("http://www.dealacceleration.com/public_profiles/" + jsonToPOJO.getString("name_for_url"));
                    jsonObj.setBus_desc(jsonToPOJO.getString("busdesc"));
                    jsonObj.setEmail(jsonToPOJO.getString("busemail"));

                    /*
                    JSONObject user = new JSONObject();
                    user = jsonToPOJO.getJSONObject("user");
                    if(mail == null)
                        jsonObj.setEmail(user.getString("email"));
                    else
                        jsonObj.setEmail(mail);
                    */

                    JSONArray products = new JSONArray();
                    products = jsonToPOJO.getJSONArray("products");

                    JSONArray produrls = new JSONArray();
                    JSONArray prodbuts = new JSONArray();
                    List<String> prod = new ArrayList<>();

                    for(int i = 0; i < products.length(); i++)
                    {
                        produrls = (products.getJSONObject(i).getJSONArray("produrls"));
                        for(int j = 0; j < produrls.length(); j++)
                        {
                            prodbuts = (produrls.getJSONObject(j).getJSONArray("prodbuts"));
                            for(int k = 0; k < prodbuts.length(); k++)
                            {
                                prod.add(prodbuts.getJSONObject(k).getString("url"));
                            }
                        }
                    }
                    jsonObj.setProduct_url(prod);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                favoriteProfileDAConnection.disconnect();

            } else {
                System.out.println("Response Message - " + favoriteProfileDAConnection.getResponseMessage());
            }

        }

        @Override
        protected String doInBackground(String... urls)
        {
            try {
                jsonGET();
                return "executed";
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            //favorite_profile_tv_name.setText(Html.fromHtml("<a href=\"" + "\">Business</a> "));
            //BackgroudThreadPicture pictureThread = new BackgroudThreadPicture();
            //pictureThread.execute("");

        }
    }

    public void createImageDialog(Bitmap img)
    {
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.dialog_profile_listview_image, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(dialogView);

        final ImageView profile_listview_dialog_iv  = (ImageView)dialogView.findViewById(R.id.profile_listview_dialog_iv);
        profile_listview_dialog_iv.setImageBitmap(img);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button profile_listview_dialog_b_back = (Button) dialogView.findViewById(R.id.profile_listview_dialog_b_back);
        profile_listview_dialog_b_back.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private class BackgroudThreadPicture extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            try {
                System.out.println(jsonObj.getProfile_pic());
                URL url = new URL(jsonObj.getProfile_pic().replaceAll(" ", "%20"));
                System.out.println(url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(true);
                HttpURLConnection.setFollowRedirects(true);
                connection.setDoInput(true);
                connection.connect();
                System.out.println("Here after connect");
                InputStream input = connection.getInputStream();
                System.out.println(input);
                picture = BitmapFactory.decodeStream(input);
                System.out.println(picture);
                System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage());

                int status = connection.getResponseCode();
                Boolean redirect = false;
                if (status != HttpURLConnection.HTTP_OK) {
                    if (status == HttpURLConnection.HTTP_MOVED_TEMP
                            || status == HttpURLConnection.HTTP_MOVED_PERM
                            || status == HttpURLConnection.HTTP_SEE_OTHER)
                        redirect = true;
                }

                System.out.println("Response Code ... " + status);

                if (redirect) {

                    String newUrl = connection.getHeaderField("Location").replaceAll(" ", "%20");

                    connection = (HttpURLConnection) new URL(newUrl).openConnection();
                    System.out.println("new url " + newUrl);
                    input = connection.getInputStream();
                    System.out.println(input);
                    picture = BitmapFactory.decodeStream(input);
                    System.out.println(picture);
                    System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage());

                }

                connection.disconnect();
                return "executed";
            } catch (IOException e) {
                return "error in execution";
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            progress.setVisibility(View.GONE);
            Profile pro = new Profile();

            for(int i = 0; i < jsonObj.getProduct_url().size(); i++)
            {
                pro.setImage(picture);
                pro.setDescription(jsonObj.getProduct_url().get(i));
                profiles.add(pro);
            }

            adapter = new ProfileCustomList((Activity) context, profiles);
            favorite_profile_lv_products_services.setAdapter(adapter);
            favorite_profile_lv_products_services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Profile p = profiles.get(position);
                    Uri uri = Uri.parse(p.getDescription());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            if(picture == null)
                favorite_profile_iv.setImageResource(R.drawable.noimage);
            else
            {
                favorite_profile_iv.setImageBitmap(picture);
                favorite_profile_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createImageDialog(picture);
                    }
                });
            }

            favorite_profile_tv_name.setText(jsonObj.getName());
            favorite_profile_b_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_SUBJECT, "Deal Acceleration");
                    share.putExtra(Intent.EXTRA_TEXT, jsonObj.getShare());
                    startActivity(Intent.createChooser(share, "Share at"));
                }
            });

            favorite_profile_b_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.intent.action.REPLYEMAIL");
                    Bundle bundle = getIntent().getExtras();
                    bundle.putString("name", jsonObj.getName());
                    bundle.putString("subject", "");
                    bundle.putString("email", jsonObj.getEmail());
                    bundle.putString("body", "");
                    bundle.putString("datetime", "");
                    bundle.putString("id", profile_id);
                    bundle.putString("auth_token", auth_token);
                    bundle.putString("email_id", email_id);
                    if(jsonObj.getEmail() == "null" || jsonObj.getEmail().equals("null") )
                    {
                        Toast.makeText(context, "This business does not have a registered email", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else
                    {
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            });

            favorite_profile_tv_bus.setText(jsonObj.getBus_desc());
        }

    }
}
