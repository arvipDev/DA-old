package com.dealacceleration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import services.DialogSvcImpl;
import services.SegmentedRadioGroupSignUp;
import services.SignUpCustomDialogSvcImpl;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView signUp_tv_choose, signup_tv_subtext, signUp_tv_footer;
    private SignUpCustomDialogSvcImpl cDialog;
    private Button signUp_b_signUp;
    private EditText signUp_et_fname, signUp_et_lname, signUp_et_email, signUp_et_question, signUp_et_answer,
           signUp_et_password, signUp_et_confirmPassword;
    private String firstname, lastName, email, question, answer, password, confirmPassword, isBusiness = "false";
    private ProgressBar signUp_pb_signUp;

    private SegmentedRadioGroupSignUp signUp_rb_segment_type;
    private Toast mToast;

    private DialogSvcImpl cd;


    //------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Sign up");
        custom_ab_title.setGravity(View.TEXT_ALIGNMENT_CENTER);

        ImageButton custom_iv_sample = (ImageButton)customView.findViewById(R.id.custom_iv_sample);
        custom_iv_sample.setVisibility(View.GONE);

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

        signUp_tv_choose = (TextView)findViewById(R.id.signUp_tv_choose);
        signUp_tv_choose.setOnClickListener(this);

        signup_tv_subtext = (TextView)findViewById(R.id.signup_tv_subtext);
        signup_tv_subtext.setText(
                Html.fromHtml("*By clicking Sign Up, you have read and agreed to our " +
                        "<a href=\"https://www.dealacceleration.com/terms\">Terms of Service</a> " +
                        " and our " + "<a href=\"https://www.dealacceleration.com/cookies\">Cookie Policy.</a> "));
        signup_tv_subtext.setMovementMethod(LinkMovementMethod.getInstance());
        signup_tv_subtext.setTextSize(15);
        signup_tv_subtext.setLinkTextColor(getResources().getColor(R.color.button_login));

        signUp_tv_footer = (TextView)findViewById(R.id.signUp_tv_footer);
        signUp_tv_footer.setText(
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
        signUp_tv_footer.setMovementMethod(LinkMovementMethod.getInstance());
        signUp_tv_footer.setTextSize(15);
        signUp_tv_footer.setLinkTextColor(getResources().getColor(R.color.button_login));

        signUp_rb_segment_type = (SegmentedRadioGroupSignUp)findViewById(R.id.signUp_rb_segment_type);
        signUp_rb_segment_type.check(R.id.signUp_rb_customer);
        signUp_rb_segment_type.setOnCheckedChangeListener(this);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        signUp_et_fname = (EditText)findViewById(R.id.signUp_et_fname);
        signUp_et_lname = (EditText)findViewById(R.id.signUp_et_lname);
        signUp_et_email = (EditText)findViewById(R.id.signUp_et_email);
        signUp_et_question = (EditText)findViewById(R.id.signUp_et_question);
        signUp_et_answer = (EditText)findViewById(R.id.signUp_et_answer);
        signUp_et_password = (EditText)findViewById(R.id.signUp_et_password);
        signUp_et_confirmPassword = (EditText)findViewById(R.id.signUp_et_confirmPassword);

        signUp_pb_signUp = (ProgressBar)findViewById(R.id.signUp_pb_signUp);
        signUp_pb_signUp.setVisibility(View.GONE);

        signUp_b_signUp = (Button)findViewById(R.id.signUp_b_signUp);
        signUp_b_signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.signUp_tv_choose :
                cDialog = new SignUpCustomDialogSvcImpl(this);
                cDialog.createDialogBox("Pick One");
                break;

            case R.id.signUp_b_signUp :
                Log.d("clicked", "sign up");
                firstname = signUp_et_fname.getText().toString();
                question = signUp_et_question.getText().toString();
                email = signUp_et_email.getText().toString();
                lastName = signUp_et_lname.getText().toString();
                password = signUp_et_password.getText().toString();
                answer = signUp_et_answer.getText().toString();
                confirmPassword = signUp_et_confirmPassword.getText().toString();

                BackgroudThread bdt = new BackgroudThread();
                bdt.execute("https://www.dealacceleration.com/users.json");
                signUp_pb_signUp.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        if (group == signUp_rb_segment_type)
        {
            if (checkedId == R.id.signUp_rb_customer)
            {
                isBusiness = "false";
                mToast.setText("Consumer");
                mToast.show();

            }
            else if (checkedId == R.id.signUp_rb_business)
            {
                isBusiness = "true";
                mToast.setText("Business");
                mToast.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        //return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    private class BackgroudThread extends AsyncTask<String, Void, String>
    {
        String jsonResponse;
        int jsonResponseCode;
        private void jsonPOST() throws IOException {
            String urlString = "https://www.dealacceleration.com/users.json";
            URL urlDA = new URL(urlString);

            HttpURLConnection signupDAConnection = (HttpURLConnection) urlDA.openConnection();
            signupDAConnection.setDoOutput(true);
            signupDAConnection.setDoInput(true);
            signupDAConnection.setConnectTimeout(5000);
            signupDAConnection.setReadTimeout(5000);
            signupDAConnection.setUseCaches(false);

            signupDAConnection.setRequestProperty("Content-Type", "application/json");
            signupDAConnection.setRequestProperty("Accept", "application/json");
            signupDAConnection.setRequestMethod("POST");

            JSONObject cred = new JSONObject();
            try {
                cred.put("security_answer", answer);
                cred.put("secur_quest", question);
                cred.put("lastName", lastName);
                cred.put("firstname", firstname);
                cred.put("business_user", isBusiness);
                cred.put("password_confirmation", confirmPassword);
                cred.put("password", password);
                cred.put("email", email);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("print", cred.toString());

            String jsonRequest = "{\"user\":{\"email\":\"" + email + "\" , \"password\":\"" + password +
                    "\",\"password_confirmation\":\"" + confirmPassword + "\",\"business_user\":\" " +
                    isBusiness + "\",\"first_name\":\"" + firstname + "\",\"last_name\":\"" + lastName +
                    "\",\"secr_quest\":\"" + question + "\",\"security_answer \":\"" + answer + "\"}}";

            Log.d("print", jsonRequest);

            OutputStreamWriter wr = new OutputStreamWriter(signupDAConnection.getOutputStream());
            wr.write(jsonRequest);
            wr.flush();
            wr.close();

            StringBuilder sb = new StringBuilder();
            jsonResponseCode = signupDAConnection.getResponseCode();
            jsonResponse = signupDAConnection.getResponseMessage();
            Log.d("Json response ", "" + jsonResponseCode + " " + jsonResponse);
            signupDAConnection.disconnect();

        }

        @Override
        protected String doInBackground(String... params)
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
            if(jsonResponseCode == 200 || jsonResponseCode == 201)
            {
                signUp_pb_signUp.setVisibility(View.GONE);
                createDialogBox("Success!!! ", "Accound Created....");
            }
            else
            {
                signUp_pb_signUp.setVisibility(View.GONE);
                createDialogBox("Failed ", "Please try again with a different email id");
            }
        }
    }

//------------------------------------------------------------------------------------------------------------------------
//Helper

    public void createDialogBox(String title, String message)
    {
        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder1.setTitle(title);

        // set dialog message
        alertDialogBuilder1
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // the dialog
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog1 = alertDialogBuilder1.create();

        // show it
        alertDialog1.show();
    }


}
