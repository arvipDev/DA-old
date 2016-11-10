package fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dealacceleration.R;

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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import services.EmailCustomList;
import domain.EmailJSONResponse;
import services.SegmentedRadioGroupEmail;
import services.TrustConnection;


public class HomeMail extends Fragment implements  RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private SegmentedRadioGroupEmail email_rb_segment_type;
    private Toast mToast;

    private RelativeLayout home_email_fl_rl_sent, home_email_fl_rl_inbox, home_email_fl_rl_trash,
            home_email_fl_rl_details;
    private String auth_token, email_id;
    private List<EmailJSONResponse> list_inbox = new ArrayList<>();
    private List<EmailJSONResponse> list_sent = new ArrayList<>();
    private List<EmailJSONResponse> list_trash = new ArrayList<>();
    private EmailCustomList adapter;
    private ListView home_email_fl_rl_lv_inbox, home_email_fl_rl_lv_sent, home_email_fl_rl_lv_trash;
    private TextView home_email_rl_tv_subject_text, home_email_rl_tv_name_text,
            home_email_rl_tv_timestamp_text, home_email_rl_tv_body_text;
    private View root ;
    private Button home_email_fl_b_delete, home_email_fl_b_reply, home_email_fl_b_forward;
    private Boolean isClicked = false;
    private int pos;
    private ProgressBar progressBar_email;

    public HomeMail()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_home_email,container, false);

        auth_token = getArguments().getString("Auth_Token");
        email_id = getArguments().getString("email_id");

        BackgroudThread bdt = new BackgroudThread();
        bdt.execute("");

        email_rb_segment_type = (SegmentedRadioGroupEmail)rootView.findViewById(R.id.email_rb_segment_type);
        //email_rb_segment_type.check(R.id.email_rb_inbox);
        email_rb_segment_type.setOnCheckedChangeListener(this);
        mToast = Toast.makeText(getActivity(), "",Toast.LENGTH_SHORT);
        //---------------------------------------------------------------------------------------------------------------
        home_email_fl_rl_inbox = (RelativeLayout)rootView.findViewById(R.id.home_email_fl_rl_inbox);
        home_email_fl_rl_inbox.setVisibility(View.GONE);
        home_email_fl_rl_sent = (RelativeLayout)rootView.findViewById(R.id.home_email_fl_rl_sent);
        home_email_fl_rl_sent.setVisibility(View.GONE);
        home_email_fl_rl_trash = (RelativeLayout)rootView.findViewById(R.id.home_email_fl_rl_trash);
        home_email_fl_rl_trash.setVisibility(View.GONE);
        //---------------------------------------------------------------------------------------------------------------
        home_email_fl_b_delete  = (Button)rootView.findViewById(R.id.home_email_fl_b_delete);
        home_email_fl_b_delete.setOnClickListener(this);
        home_email_fl_b_delete.setVisibility(View.GONE);

        home_email_fl_b_reply  = (Button)rootView.findViewById(R.id.home_email_fl_b_reply);
        home_email_fl_b_reply.setOnClickListener(this);
        home_email_fl_b_reply.setVisibility(View.GONE);

        home_email_fl_b_forward  = (Button)rootView.findViewById(R.id.home_email_fl_b_forward);
        home_email_fl_b_forward.setOnClickListener(this);
        home_email_fl_b_forward.setVisibility(View.GONE);

        progressBar_email = (ProgressBar)rootView.findViewById(R.id.progressBar_email);
        progressBar_email.setVisibility(View.VISIBLE);
        //---------------------------------------------------------------------------------------------------------------

        home_email_fl_rl_lv_inbox = (ListView) rootView.findViewById(R.id.home_email_fl_rl_lv_inbox);
        home_email_fl_rl_lv_sent = (ListView)rootView.findViewById(R.id.home_email_fl_rl_lv_sent);
        home_email_fl_rl_lv_trash = (ListView)rootView.findViewById(R.id.home_email_fl_rl_lv_trash);

        addFooter(home_email_fl_rl_lv_inbox);
        addFooter(home_email_fl_rl_lv_sent);
        addFooter(home_email_fl_rl_lv_trash);

        home_email_fl_rl_details = (RelativeLayout)rootView.findViewById(R.id.home_email_fl_rl_details);
        home_email_fl_rl_details.setVisibility(View.GONE);

        root = rootView;
        return rootView;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == email_rb_segment_type)
        {
            if (checkedId == R.id.email_rb_inbox)
            {
                home_email_fl_rl_inbox.setVisibility(View.VISIBLE);
                home_email_fl_rl_sent.setVisibility(View.GONE);
                home_email_fl_rl_trash.setVisibility(View.GONE);
                home_email_fl_b_reply.setVisibility(View.VISIBLE);
                home_email_fl_b_forward.setVisibility(View.VISIBLE);
                home_email_fl_b_delete.setVisibility(View.VISIBLE);

                adapter = new EmailCustomList(getActivity(), list_inbox);
                home_email_fl_rl_lv_inbox.setAdapter(adapter);
                home_email_fl_rl_lv_inbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        home_email_fl_rl_details.setVisibility(View.VISIBLE);
                        System.out.println("ccc " + list_inbox.size());
                        setIndividualEmailValues(list_inbox, position);
                        pos = position;
                        isClicked = true;
                    }
                });

                home_email_fl_rl_lv_inbox.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });

                toaster(0);
            }
            else if (checkedId == R.id.email_rb_sent)
            {
                home_email_fl_rl_inbox.setVisibility(View.GONE);
                home_email_fl_rl_sent.setVisibility(View.VISIBLE);
                home_email_fl_rl_trash.setVisibility(View.GONE);
                home_email_fl_b_reply.setVisibility(View.GONE);
                home_email_fl_b_forward.setVisibility(View.VISIBLE);
                home_email_fl_b_delete.setVisibility(View.VISIBLE);


                adapter = new EmailCustomList(getActivity(), list_sent);
                home_email_fl_rl_lv_sent.setAdapter(adapter);
                home_email_fl_rl_lv_sent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        home_email_fl_rl_details.setVisibility(View.VISIBLE);
                        System.out.println("ccc " + list_sent.size());
                        setIndividualEmailValues(list_sent, position);
                    }
                });

                home_email_fl_rl_lv_sent.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });

                toaster(1);
            }
            else if (checkedId == R.id.email_rb_trash)
            {
                home_email_fl_rl_inbox.setVisibility(View.GONE);
                home_email_fl_rl_sent.setVisibility(View.GONE);
                home_email_fl_rl_trash.setVisibility(View.VISIBLE);
                home_email_fl_b_reply.setVisibility(View.GONE);
                home_email_fl_b_forward.setVisibility(View.GONE);

                adapter = new EmailCustomList(getActivity(), list_trash);
                home_email_fl_rl_lv_trash.setAdapter(adapter);
                home_email_fl_rl_lv_trash.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        home_email_fl_rl_details.setVisibility(View.VISIBLE);
                        System.out.println("ccc " + list_trash.size());
                        setIndividualEmailValues(list_trash, position);
                    }
                });

                home_email_fl_rl_lv_trash.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });

                toaster(2);
            }
        }
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.home_email_fl_b_reply:
                if(isClicked)
                {
                    Intent intent = new Intent("android.intent.action.REPLYEMAIL");
                    EmailJSONResponse ejson = new EmailJSONResponse();
                    ejson = list_inbox.get(pos);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", ejson.getName());
                    bundle.putString("email", ejson.getEmail());
                    bundle.putString("subject", ejson.getSubject());
                    bundle.putString("body", ejson.getBody());
                    bundle.putString("datetime", ejson.getDate_time());
                    bundle.putString("auth_token", auth_token);
                    bundle.putString("email_id", email_id);
                    bundle.putString("id", ejson.get_id());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else Toast.makeText(getContext(), "Select a message to reply", Toast.LENGTH_LONG).show();
                break;
        }
    }

    // -------------------------------------------------------------------------------------------
    // Supporting methods

    public void setIndividualEmailValues (List<EmailJSONResponse> list, int position)
    {
        EmailJSONResponse values = list.get(position);

        home_email_rl_tv_subject_text = (TextView) root.findViewById(R.id.home_email_rl_tv_subject_text);
        home_email_rl_tv_name_text = (TextView) root.findViewById(R.id.home_email_rl_tv_name_text);
        home_email_rl_tv_timestamp_text = (TextView) root.findViewById(R.id.home_email_rl_tv_timestamp_text);
        home_email_rl_tv_body_text = (TextView) root.findViewById(R.id.home_email_rl_tv_body_text);

        home_email_rl_tv_subject_text.setText(values.getName() + " <" + values.getEmail() + ">");
        home_email_rl_tv_subject_text.setTextSize(15);
        home_email_rl_tv_name_text.setText("Subject: " + values.getSubject());
        home_email_rl_tv_subject_text.setTextSize(15);

        String date_time = values.getDate_time();
        String date = date_time.substring(0, 10);
        String time = date_time.substring(12,19);

        home_email_rl_tv_timestamp_text.setText("Received on " + date + " at " + time);
        home_email_rl_tv_timestamp_text.setTextSize(10);
        home_email_rl_tv_body_text.setText(Html.fromHtml(values.getBody()));
        home_email_rl_tv_body_text.setTextSize(15);
    }

    public void addFooter(ListView listview) {
        Log.d("Inside addfooter", " here");
        Button btnLoadMore = new Button(getActivity());
        btnLoadMore.setText("Load More");
        btnLoadMore.setHeight(30);
        btnLoadMore.setBackgroundResource(R.drawable.button_grey);
        listview.addFooterView(btnLoadMore);
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "No more emails to load", Toast.LENGTH_LONG).show();
            }
        });
        Log.d("Inside addfooter", " out");
    }

    public void printPOJO()
    {
        /*
        System.out.println("Name: " + jsonObj.getName());
        System.out.println("ProfileURLName: " + jsonObj.getProfileURLname());
        System.out.println("IsBusiness: " + jsonObj.isBusiness());
        System.out.println("LookingFor: " + jsonObj.getLooking_for());
        */
    }

    private class BackgroudThread extends AsyncTask<String, Void, String> {

        String jsonResponse;

        private void jsonGET() throws IOException {

            String urlString = "https://www.dealacceleration.com/mobile/api/v1/emails/get_all_mail.json";
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
            HttpURLConnection getEmailDAConnection = (HttpURLConnection) urlDA.openConnection();
            getEmailDAConnection.setDoOutput(false);
            getEmailDAConnection.setDoInput(true);
            getEmailDAConnection.setConnectTimeout(5000);
            getEmailDAConnection.setReadTimeout(5000);
            getEmailDAConnection.setUseCaches(false);

            Log.d("Json response ", "" + auth_token);
            Log.d("Json response ", "" + email_id);
            getEmailDAConnection.setRequestProperty("Content-Type", "application/json");
            getEmailDAConnection.setRequestProperty("Accept", "application/json");
            getEmailDAConnection.setRequestProperty("X-User-Token", auth_token);
            getEmailDAConnection.setRequestProperty("X-User-Email", email_id);
            getEmailDAConnection.setRequestMethod("GET");

            StringBuilder sb = new StringBuilder();
            int HttpResult = getEmailDAConnection.getResponseCode();
            Log.d("Json response ", "" + HttpResult + " " + getEmailDAConnection.getResponseMessage());

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(getEmailDAConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                jsonResponse = sb.toString();

                try {

                    JSONObject json_full_response = new JSONObject(jsonResponse);
                    System.out.println("jsonResponse" + jsonResponse);
                    //JSONObject json_full_response = new JSONObject(jsonResponse);

                    JSONArray jarray_inbox = json_full_response.getJSONArray("inbox");
                    JSONArray jarray_sent = json_full_response.getJSONArray("sent");
                    JSONArray jarray_trash = json_full_response.getJSONArray("trash");

                    // populating list
                    for(int i = 0; i < jarray_inbox.length(); i++)
                    {
                        JSONObject jsonToPOJO_inbox = jarray_inbox.getJSONObject(i);
                        loadEmaiList(jsonToPOJO_inbox, list_inbox);
                    }

                    for(int i = 0; i < jarray_sent.length(); i++)
                    {
                        JSONObject jsonToPOJO_sent = jarray_sent.getJSONObject(i);
                        loadEmaiList(jsonToPOJO_sent, list_sent);
                    }

                    for(int i = 0; i < jarray_trash.length(); i++)
                    {
                        JSONObject jsonToPOJO_trash = jarray_trash.getJSONObject(i);
                        loadEmaiList(jsonToPOJO_trash, list_trash);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                printPOJO();
                getEmailDAConnection.disconnect();

            } else {
                System.out.println("Response Message - " + getEmailDAConnection.getResponseMessage());
            }

        }

        private void loadEmaiList(JSONObject jsonToPOJO, List<EmailJSONResponse> jsonList)
        {
            EmailJSONResponse temp = new EmailJSONResponse();
            try {
                temp.setBody(jsonToPOJO.getString("body"));
                temp.setSubject(jsonToPOJO.getString("subject"));
                temp.setDate_time(jsonToPOJO.getString("created_at"));
                JSONObject jsonToPOJO_inbox_user = jsonToPOJO.getJSONObject("user");
                temp.setName(jsonToPOJO_inbox_user.getString("full_name"));
                temp.setEmail(jsonToPOJO_inbox_user.getString("email"));
                temp.set_id(jsonToPOJO_inbox_user.getString("_id"));

                System.out.println("body  " + temp.getBody());
                System.out.println("dateTime " + temp.getDate_time());
                System.out.println("subject " + temp.getSubject());
                System.out.println("name "+ temp.getName());
                System.out.println("email  " + temp.getEmail());
                System.out.println("_id  " + temp.get_id());

                jsonList.add(temp);

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
        protected void onPostExecute(String result)
        {
            progressBar_email.setVisibility(View.GONE);
        }
    }

    private void toaster(int id)
    {
        switch (id)
        {
            case 0:
                mToast.setText("Inbox");
                mToast.show();
                break;
            case 1:
                mToast.setText("Sent");
                mToast.show();
                break;
            case 2:
                mToast.setText("Trash");
                mToast.show();
                break;
        }
    }
}
