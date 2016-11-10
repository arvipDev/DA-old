package com.dealacceleration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import domain.GetProfileJSONResponse;
import services.TrustConnection;

public class Intermediate extends AppCompatActivity
{

    private int dbUserId;
    private String dbUserName, profileId, auth_token, email_id, _id, facebook, googleplus, twitter,
            blog, website, busdesc, buslook, conname1, conname2, conname3, email1, email2, email3,
            phone1, phone2, phone3, address1, address2, city, state, zipcode, country_code, socname, socwebsite,
            soccity, soczip;
    private GetProfileJSONResponse jsonObj;
    private ArrayList<String> imageURL_list;
    private ArrayList<String> urlimage_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);

        Bundle bundle = getIntent().getExtras();
        dbUserId = bundle.getInt("Login_id");
        dbUserName = bundle.getString("Login_username");
        profileId = bundle.getString("profile_id");
        auth_token = bundle.getString("Auth_token");
        email_id = bundle.getString("email");
        _id = bundle.getString("_id");

        Log.d("User_id ", "" + dbUserId);
        Log.d("User_name ", "" + dbUserName);
        Log.d("profile_id ", "" + profileId);
        Log.d("Auth_token ", "" + auth_token);
        Log.d("email ", "" + email_id);

        BackgroudThread bdt = new BackgroudThread();
        bdt.execute("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intermediate, menu);
        return true;
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    //--------------------------------------------------------------------------------

    public void printPOJO()
    {
        System.out.println("Name: " + jsonObj.getName());
        System.out.println("ProfileURLName: " + jsonObj.getProfileURLname());
        System.out.println("IsBusiness: " + jsonObj.isBusiness());
        System.out.println("LookingFor: " + jsonObj.getLooking_for());
    }

    private class BackgroudThread extends AsyncTask<String, Void, String> {
        String jsonResponse;

        private void jsonGET() throws IOException {
            String urlString = "https://www.dealacceleration.com/mobile/api/v1/profiles/get_profile.json?id=" + profileId;
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
            HttpURLConnection getProfileDAConnection = (HttpURLConnection) urlDA.openConnection();
            getProfileDAConnection.setDoOutput(false);
            getProfileDAConnection.setDoInput(true);
            getProfileDAConnection.setConnectTimeout(5000);
            getProfileDAConnection.setReadTimeout(5000);
            getProfileDAConnection.setUseCaches(false);

            getProfileDAConnection.setRequestProperty("Content-Type", "application/json");
            getProfileDAConnection.setRequestProperty("Accept", "application/json");
            getProfileDAConnection.setRequestProperty("X-User-Token", auth_token);
            getProfileDAConnection.setRequestProperty("X-User-Email", email_id);
            getProfileDAConnection.setRequestMethod("GET");

            StringBuilder sb = new StringBuilder();
            int HttpResult = getProfileDAConnection.getResponseCode();
            Log.d("Json response ", "" + HttpResult + " " + getProfileDAConnection.getResponseMessage());

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(getProfileDAConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                jsonResponse = sb.toString();
                Log.d("Json response ", jsonResponse);

                /*
                String jsub1 = jsonResponse.substring(1, 3000);
                String jsub2 = jsonResponse.substring(3001, jsonResponse.length());
                Log.d("jsub1", jsub1);
                Log.d("jsub2", jsub2);
                */

                jsonObj = new GetProfileJSONResponse();

                try {

                    JSONObject jsonToPOJO = new JSONObject(jsonResponse);
                    Log.d("jsonToPOJO ", jsonToPOJO.toString());

                    jsonObj.setName(jsonToPOJO.getString("name"));
                    jsonObj.setIsBusiness(false);
                    jsonObj.setLooking_for("Nothing");

                    /*
                    String profile = jsonToPOJO.getString("profile");
                    Log.d("jo ", profile);

                    String profilePic = jsonToPOJO.getString("profile_image");
                    Log.d("po ", profilePic);
                    jsonObj.setProfilePicture(profilePic);


                    JSONObject profileString = new JSONObject(profile);
                    jsonObj.setName(profileString.getString("name"));
                    jsonObj.setLooking_for(profileString.getString("personal_look"));
                    jsonObj.setProfileURLname(profileString.getString("name_for_url"));

                    JSONObject user = new JSONObject(profileString.getString("user"));
                    jsonObj.setIsBusiness(Boolean.parseBoolean(user.getString("business_user")));
                    Log.d("jk", jsonObj.toString());

                    facebook = profileString.getString("facebook");
                    twitter = profileString.getString("twitter");
                    googleplus = profileString.getString("googleplus");
                    blog = profileString.getString("blog");
                    website = profileString.getString("website");
                    Log.d("social", facebook + twitter + googleplus + blog + website);

                    //------------------

                    JSONArray picturesJsonArray = profileString.getJSONArray("pictures");
                    imageURL_list = new ArrayList<>();
                    for(int i = 0; i < picturesJsonArray.length(); i++)
                    {
                        Log.d("inside loader", "");
                        loadPictures(picturesJsonArray.getJSONObject(i));
                        System.out.println(" picturesJsonArray " + picturesJsonArray.getJSONObject(i));
                    }


                    if(jsonObj.isBusiness())
                    {
                        busdesc = profileString.getString("busdesc");
                        buslook = profileString.getString("buslook");
                        conname1 = profileString.getString("conname1");
                        conname2 = profileString.getString("conname2");
                        conname3 = profileString.getString("conname3");
                        email1 = profileString.getString("email1");
                        email2 = profileString.getString("email2");
                        email3 = profileString.getString("email3");
                        phone1 = profileString.getString("phone1");
                        phone2 = profileString.getString("phone2");
                        phone3 = profileString.getString("phone3");
                        address1 = profileString.getString("address1");
                        address2 = profileString.getString("address2");
                        city = profileString.getString("city");
                        state = profileString.getString("state");
                        zipcode = profileString.getString("zipcode");
                        country_code = profileString.getString("country_code");
                        socname = profileString.getString("socname");
                        socwebsite = profileString.getString("socwebsite");
                        soccity = profileString.getString("soccity");
                        soczip = profileString.getString("soczip");



                        JSONArray products = profileString.getJSONArray("products");
                        urlimage_list = new ArrayList<>();
                        for(int i = 0; i < products.length(); i++)
                        {
                            JSONArray produrls = products.getJSONObject(i).getJSONArray("produrls");
                            for(int j = 0; j < produrls.length(); j++)
                            {
                                urlimage_list.add(produrls.getJSONObject(j).getString("urlimage"));
                            }
                        }
                        */
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*

            } catch (JSONException e) {
                    e.printStackTrace();
                }
                printPOJO();
                getProfileDAConnection.disconnect();

            } else {
                System.out.println("Response Message - " + getProfileDAConnection.getResponseMessage());
            }

            */
            }

        }

        private void loadPictures(JSONObject picObj)
        {
            String imageURLprereq = "https://www.dealacceleration.com";
            try {
                imageURL_list.add(imageURLprereq + picObj.getString("conpicture"));
                System.out.println(" loadPictures " + imageURLprereq + picObj.getString("conpicture"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                jsonGET();
                return "executed";
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            Log.d("Aruu", jsonObj.getName().toString());
            if (jsonObj == null)
            {
                Log.d("After exec", " wrong cred");
            }
            else if (jsonObj != null && jsonObj.isBusiness() == false)
            {
                Bundle loginCred = new Bundle();
                loginCred.putInt("Login_id", dbUserId);
                loginCred.putString("Login_username", dbUserName);

                loginCred.putString("Auth_Token", auth_token);
                loginCred.putString("email_id", email_id);
                loginCred.putString("profileId", profileId);
                loginCred.putString("facebook", facebook);
                loginCred.putString("twitter", twitter);
                loginCred.putString("googleplus", googleplus);
                loginCred.putString("blog", blog);
                loginCred.putString("website", website);

                loginCred.putString("Name", jsonObj.getName());
                loginCred.putString("ProfileURLName", jsonObj.getProfileURLname());
                loginCred.putString("Looking_for", jsonObj.getLooking_for());
                loginCred.putString("Profile_pic", jsonObj.getProfilePicture());
                loginCred.putStringArrayList("pictures", imageURL_list);

                Intent pIntent = new Intent("android.intent.action.HOME");
                pIntent.putExtras(loginCred);
                startActivity(pIntent);
            }
            else if (jsonObj != null && jsonObj.isBusiness() == true)
            {
                Bundle loginCred = new Bundle();
                loginCred.putInt("Login_id", dbUserId);
                loginCred.putString("Login_username", dbUserName);

                loginCred.putString("Auth_Token", auth_token);
                loginCred.putString("email_id", email_id);
                loginCred.putString("profileId", profileId);

                loginCred.putString("Name", jsonObj.getName());
                loginCred.putString("ProfileURLName", jsonObj.getProfileURLname());
                loginCred.putString("Looking_for", jsonObj.getLooking_for());
                loginCred.putString("Profile_pic", jsonObj.getProfilePicture());
                loginCred.putStringArrayList("pictures", imageURL_list);
                loginCred.putString("website", website);

                loginCred.putStringArrayList("urlimage_list", urlimage_list);
                loginCred.putString("busdesc", busdesc);
                loginCred.putString("buslook", buslook);
                loginCred.putString("conname1", conname1);
                loginCred.putString("conname2", conname2);
                loginCred.putString("conname3", conname3);
                loginCred.putString("email1", email1);
                loginCred.putString("email2", email2);
                loginCred.putString("email3", email3);
                loginCred.putString("phone1", phone1);
                loginCred.putString("phone2", phone2);
                loginCred.putString("phone3", phone3);
                loginCred.putString("address2", address2);
                loginCred.putString("address1", address1);
                loginCred.putString("zipcode", zipcode);
                loginCred.putString("state", state);
                loginCred.putString("city", city);
                loginCred.putString("country_code", country_code);
                loginCred.putString("soccity", soccity);
                loginCred.putString("socname", socname);
                loginCred.putString("socwebsite", socwebsite);
                loginCred.putString("soczip", soczip);

                //System.out.println("busdesc bundle  " + busdesc);
                //System.out.println("urlimage_list bundle " + urlimage_list.size());

                Intent pIntent = new Intent("android.intent.action.BUSINESS");
                pIntent.putExtras(loginCred);
                startActivity(pIntent);
            }
        }
    }

}
