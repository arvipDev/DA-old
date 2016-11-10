package com.dealacceleration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import domain.EmailJSONResponse;
import services.HostNameVerifier;
import services.TrustConnection;

public class ReplyEmail extends AppCompatActivity implements View.OnClickListener {

    private EmailJSONResponse emailJSON;
    private String auth_token, email_id;
    private Boolean success = false;
    private TextView compose_et_email;
    private EditText compose_et_subject, compose_et_body;
    private Button compose_b_send, compose_b_cancel;
    private String sub, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_email);

        //---------------------------------------------------------------------------------------------------------------
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Compose Email");
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
        //---------------------------------------------------------------------------------------------------------------

        Bundle bundle = getIntent().getExtras();
        emailJSON = new EmailJSONResponse();
        emailJSON.setName(bundle.getString("name"));
        emailJSON.setSubject(bundle.getString("subject"));
        emailJSON.setEmail(bundle.getString("email"));
        emailJSON.setBody(bundle.getString("body"));
        emailJSON.setDate_time(bundle.getString("datetime"));
        emailJSON.set_id(bundle.getString("id"));
        auth_token = bundle.getString("auth_token");
        email_id = bundle.getString("email_id");
        //---------------------------------------------------------------------------------------------------------------

        compose_et_email = (TextView)findViewById(R.id.compose_et_email);
        compose_et_subject = (EditText)findViewById(R.id.compose_et_subject);
        compose_et_body = (EditText)findViewById(R.id.compose_et_body);
        compose_b_cancel = (Button)findViewById(R.id.compose_b_cancel);
        compose_b_send = (Button)findViewById(R.id.compose_b_send);

        compose_b_cancel.setOnClickListener(this);
        compose_b_send.setOnClickListener(this);
        compose_et_email.setText(emailJSON.getEmail());
        String d = emailJSON.getDate_time();
        String sd, st;

        if(d.length() > 10)
        {
            sd = d.substring(0, 10);
            st = d.substring(12, 19);
            compose_et_subject.setText("Subject:RE " + emailJSON.getSubject() );
            compose_et_body.setText("\n\n----------------------------------------------\nSent on - " +
                    sd + " at " + st + "\nFrom - " + emailJSON.getName() + "\n\n" + emailJSON.getBody());
        }
        else
        {
            compose_et_subject.setText(emailJSON.getSubject() );
            compose_et_body.setText(emailJSON.getBody());
        }


    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.compose_b_send:
                sub = compose_et_subject.getText().toString();
                sub = sub.substring(8);
                body = compose_et_body.getText().toString();
                BackgroudThread backThread = new BackgroudThread();
                backThread.execute("");
                System.out.println("success in send " + success);
                Toast.makeText(ReplyEmail.this, "Email sent...", Toast.LENGTH_LONG).show();
                break;

            case R.id.compose_b_cancel:
                finish();
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

    //------------------------------------------------------------------------------------------



    private class BackgroudThread extends AsyncTask<String, Void, String>
    {
        String jsonResponse;

        private void jsonPOST() throws IOException
        {
            String urlString = "https://www.dealacceleration.com/mobile/api/v1/emails/send_mail.json";
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
            HttpsURLConnection sendEmailDAConnection = (HttpsURLConnection) urlDA.openConnection();
            sendEmailDAConnection.setHostnameVerifier(new HostNameVerifier());

            sendEmailDAConnection.setDoOutput(true);
            sendEmailDAConnection.setDoInput(true);
            sendEmailDAConnection.setConnectTimeout(5000);
            sendEmailDAConnection.setReadTimeout(5000);
            sendEmailDAConnection.setUseCaches(false);

            sendEmailDAConnection.setRequestProperty("Content-Type", "application/json");
            sendEmailDAConnection.setRequestProperty("Accept", "application/json");
            sendEmailDAConnection.setRequestProperty("X-User-Token", auth_token);
            sendEmailDAConnection.setRequestProperty("X-User_Email", email_id);
            sendEmailDAConnection.setRequestMethod("POST");

            JSONObject cred = new JSONObject();
            try {
                cred.put("to", emailJSON.get_id());
                cred.put("subject", sub);
                cred.put("body", body);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("_id", emailJSON.get_id());
            Log.d("getSubject", sub);
            Log.d("getBody", body);

            OutputStreamWriter wr = new OutputStreamWriter(sendEmailDAConnection.getOutputStream());
            wr.write(cred.toString());
            wr.flush();
            wr.close();

            StringBuilder sb = new StringBuilder();
            int HttpResult = sendEmailDAConnection.getResponseCode();
            Log.d("Json response - send ", "" + HttpResult + " " + sendEmailDAConnection.getResponseMessage());

            if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                BufferedReader br = new BufferedReader(new InputStreamReader(sendEmailDAConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                jsonResponse = ""+sb.toString();

                try {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    success = Boolean.valueOf(jsonObject.getString("success"));
                    String message = (jsonObject.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("success", success.toString());
                sendEmailDAConnection.disconnect();

            } else {
                System.out.println("Response Message - " + sendEmailDAConnection.getResponseMessage());
            }

        }

        @Override
        protected String doInBackground(String... urls) {

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
        }
    }
}
