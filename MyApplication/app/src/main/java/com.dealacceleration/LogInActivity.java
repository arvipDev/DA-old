package com.dealacceleration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import database.DatabaseOperation;
import domain.LoginJSONResponse;
import domain.UserCred;
import services.HostNameVerifier;
import services.LogInDialogSvcImpl;
import services.TrustConnection;

//------------------------------------------------------------------------------------------------------------------
public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login_b_login, login_b_forgotPassword, login_b_newUser, login_b_contactUs;
    private EditText login_et_password;
    private TextView login_tv_footer;
    private AutoCompleteTextView login_actv_login;
    private Switch login_s_saveInfo;
    private Boolean saveLogin = false;
    private int dbCounter = 0;
    private String username, password;
    private LoginJSONResponse jsonObj;
    private ProgressBar login_pb_login;
    private List<String> userNameList = new ArrayList<>();

    private UserCred userCred;

//------------------------------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        login_b_login = (Button) findViewById(R.id.login_b_login);
        login_b_login.setOnClickListener(this);

        login_b_forgotPassword = (Button) findViewById(R.id.login_b_forgotPassword);
        login_b_forgotPassword.setOnClickListener(this);

        login_b_contactUs = (Button) findViewById(R.id.login_b_contactUs);
        login_b_contactUs.setOnClickListener(this);

        login_b_newUser = (Button) findViewById(R.id.login_b_newUser);
        login_b_newUser.setOnClickListener(this);

        login_s_saveInfo = (Switch) findViewById(R.id.login_s_saveInfo);
        login_s_saveInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                saveLogin = isChecked;
                Log.v("saveLogin ", "" + saveLogin);
            }
        });

        updateSuggestionList();

        login_actv_login = (AutoCompleteTextView) findViewById(R.id.login_actv_login);
        login_actv_login.setOnClickListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, userNameList);
        login_actv_login.setThreshold(1);
        login_actv_login.setAdapter(adapter);
        login_actv_login.setTextColor(getResources().getColor(R.color.button_login));
        login_actv_login.setDropDownHeight(300);
        login_actv_login.setDropDownBackgroundResource(R.color.button_login);

        login_et_password = (EditText) findViewById(R.id.login_et_password);
        login_et_password.setOnClickListener(this);

        login_tv_footer = (TextView) findViewById(R.id.login_tv_footer);
        login_tv_footer.setText(
                Html.fromHtml("<a href=\"https://www.dealacceleration.com/business\">Business</a> " +
                        " | " + "<a href=\"https://www.dealacceleration.com/consumer\">Consumer</a> " +
                        " | " + "<a href=\"https://www.dealacceleration.com/ads\">Ads</a> " +
                        " | " + "<a href=\"https://www.dealacceleration.com/press\">Press</a> " +
                        " | " + "<a href=\"https://www.dealacceleration.com/icons\">Icons</a> " +
                        " | " + "<a href=\"https://www.dealacceleration.com/help\">Contact Us</a> " +
                        " | " + "<a href=\"https://www.dealacceleration.com/privacy\">Privacy</a> " +
                        " | " + "<a href=\"https://www.dealacceleration.com/terms\">Terms</a> " +
                        " | " + "<a href=\"https://www.dealacceleration.com/cookies\">Cookies</a> " +
                        " | " + "<a href=\"https://www.dealacceleration.com/faq\">FAQ</a> "));
        login_tv_footer.setMovementMethod(LinkMovementMethod.getInstance());
        login_tv_footer.setTextSize(15);
        login_tv_footer.setLinkTextColor(getResources().getColor(R.color.button_login));

        login_pb_login = (ProgressBar) findViewById(R.id.login_pb_login);
        login_pb_login.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_b_login:

                username = login_actv_login.getText().toString();
                password = login_et_password.getText().toString();

                BackgroudThread bdt = new BackgroudThread();
                bdt.execute("https://www.dealacceleration.com/users/sign_in.json");
                login_pb_login.setVisibility(View.VISIBLE);
                break;
            case R.id.login_b_forgotPassword:
                Intent fpIntent = new Intent("android.intent.action.FORGOTPASSWORD");
                login_pb_login.setVisibility(View.VISIBLE);
                startActivity(fpIntent);
                break;
            case R.id.login_b_newUser:
                Intent spIntent = new Intent("android.intent.action.SIGNUP");
                login_pb_login.setVisibility(View.VISIBLE);
                startActivity(spIntent);
                break;
            case R.id.login_b_contactUs:
                Intent cpIntent = new Intent("android.intent.action.CONTACTUS");
                login_pb_login.setVisibility(View.VISIBLE);
                startActivity(cpIntent);
                break;
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
    public boolean onOptionsItemSelected(MenuItem item) {
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
        login_pb_login.setVisibility(View.GONE);
        jsonObj = null;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Helper methods

    public void updateSuggestionList() {
        DatabaseOperation dbo = new DatabaseOperation(this);
        dbo.createTableIfNotExists(dbo);
        userNameList = dbo.getUserNameListfromDB(dbo);
    }

    public void printPOJO()
    {
        System.out.println("Success: " + jsonObj.isSuccess());
        System.out.println("_id: " + jsonObj.get_id());
        System.out.println("auth_token: " + jsonObj.getAuth_token());
        System.out.println("email: " + jsonObj.getEmail());
        System.out.println("profile_id" + jsonObj.getProfile_id());
    }

    private class BackgroudThread extends AsyncTask<String, Void, String>
    {
        String jsonResponse;

            private void jsonPOST() throws IOException
            {
                String urlString = "https://www.dealacceleration.com/users/sign_in.json";
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
                HttpsURLConnection loginDAConnection = (HttpsURLConnection) urlDA.openConnection();
                loginDAConnection.setHostnameVerifier(new HostNameVerifier());

                loginDAConnection.setDoOutput(true);
                loginDAConnection.setDoInput(true);
                loginDAConnection.setConnectTimeout(5000);
                loginDAConnection.setReadTimeout(5000);
                loginDAConnection.setUseCaches(false);

                loginDAConnection.setRequestProperty("Content-Type", "application/json");
                loginDAConnection.setRequestProperty("Accept", "application/json");
                loginDAConnection.setRequestMethod("POST");

                JSONObject cred = new JSONObject();
                try {
                    cred.put("email", username);
                    cred.put("password", password);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("password", password);

                OutputStreamWriter wr = new OutputStreamWriter(loginDAConnection.getOutputStream());
                wr.write(cred.toString());
                wr.flush();
                wr.close();

                StringBuilder sb = new StringBuilder();
                int HttpResult = loginDAConnection.getResponseCode();
                Log.d("Json response ", ""+ HttpResult + " " + loginDAConnection.getResponseMessage());

                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(loginDAConnection.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    jsonResponse = ""+sb.toString();
                    jsonObj = new LoginJSONResponse();

                    Log.d("jsonResponse", jsonResponse);

                    try {
                        JSONObject jsonToPOJO = new JSONObject(jsonResponse);
                        jsonObj.setSuccess(Boolean.parseBoolean(jsonToPOJO.getString("success")));
                        jsonObj.setAuth_token(jsonToPOJO.getString("auth_token"));
                        jsonObj.setEmail(jsonToPOJO.getString("email"));
                        jsonObj.set_id(jsonToPOJO.getString("_id"));
                        jsonObj.setProfile_id(jsonToPOJO.getString("profile_id"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    printPOJO();
                    loginDAConnection.disconnect();

                } else {
                    System.out.println("Response Message - " + loginDAConnection.getResponseMessage());
                }

        }

        @Override
        protected String doInBackground(String... urls)
        {
            try {
                jsonPOST();
                return "executed";
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result)
        {

            if(jsonObj == null )
            {
                login_pb_login.setVisibility(View.GONE);
                Log.d("After exec", " wrong cred");
                LogInDialogSvcImpl logInDialog = new LogInDialogSvcImpl(LogInActivity.this);
                logInDialog.createDialogBox("Log in failed ", "Email and/or password is incorrect");
            }
            else if (jsonObj != null && saveLogin == false)
            {
                Bundle loginCred = new Bundle();
                loginCred.putInt("Login_id", -1);
                loginCred.putString("Login_username", "empty");
                loginCred.putString("profile_id", jsonObj.getProfile_id());
                loginCred.putString("email", jsonObj.getEmail());
                loginCred.putString("Auth_token", jsonObj.getAuth_token());
                loginCred.putString("_id", jsonObj.get_id());
                Intent pIntent = new Intent("android.intent.action.INTERMEDIATE");
                pIntent.putExtras(loginCred);
                startActivity(pIntent);
            }
            else if (jsonObj != null && saveLogin == true)
            {
                Bundle loginCred = new Bundle();
                DatabaseOperation dbo = new DatabaseOperation(LogInActivity.this);
                dbCounter = (int) dbo.getDBRowSize(dbo);
                loginCred.putInt("Login_id", ++dbCounter);
                loginCred.putString("Login_username", username);
                loginCred.putString("profile_id", jsonObj.getProfile_id());
                loginCred.putString("email", jsonObj.getEmail());
                loginCred.putString("Auth_token", jsonObj.getAuth_token());
                loginCred.putString("_id", jsonObj.get_id());
                Intent pIntent = new Intent("android.intent.action.INTERMEDIATE");
                pIntent.putExtras(loginCred);
                startActivity(pIntent);
            }
        }
    }

}
