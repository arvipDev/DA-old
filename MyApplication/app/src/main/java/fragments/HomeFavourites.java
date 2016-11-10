package fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

import domain.FavoritesJSONResponse;
import services.FavouritesCustomList;
import services.TrustConnection;

public class HomeFavourites extends Fragment
{

    private ListView favourites_listview;
    private FavouritesCustomList adapter;
    private List<FavoritesJSONResponse> favorites = new ArrayList<>();
    private String auth_token, email_id, profileId;

    public HomeFavourites()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_home_favourites,container, false);
        auth_token = getArguments().getString("Auth_Token");
        email_id = getArguments().getString("email_id");
        profileId = getArguments().getString("profileId");

        favourites_listview = (ListView)rootView.findViewById(R.id.favourites_listview);

        BackgroudThread bt = new BackgroudThread();
        bt.execute("");

        return rootView;
    }

    //-----------------------------------------------------------------------------------------------------------------------

    private class BackgroudThread extends AsyncTask<String, Void, String> {
        String jsonResponse;

        private void jsonGET() throws IOException {
            String urlString = "https://www.dealacceleration.com/mobile/api/v1/profiles/get_favorites.json?type=Profile";
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
            HttpURLConnection getFavoritesDAConnection = (HttpURLConnection) urlDA.openConnection();
            getFavoritesDAConnection.setDoOutput(false);
            getFavoritesDAConnection.setDoInput(true);
            getFavoritesDAConnection.setConnectTimeout(5000);
            getFavoritesDAConnection.setReadTimeout(5000);
            getFavoritesDAConnection.setUseCaches(false);

            getFavoritesDAConnection.setRequestProperty("Content-Type", "application/json");
            getFavoritesDAConnection.setRequestProperty("Accept", "application/json");
            getFavoritesDAConnection.setRequestProperty("X-User-Token", auth_token);
            getFavoritesDAConnection.setRequestProperty("X-User-Email", email_id);
            getFavoritesDAConnection.setRequestMethod("GET");

            StringBuilder sb = new StringBuilder();
            int HttpResult = getFavoritesDAConnection.getResponseCode();
            Log.d("Json response ", "" + HttpResult + " " + getFavoritesDAConnection.getResponseMessage());

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(getFavoritesDAConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                jsonResponse = "" + sb.toString();

                try {
                    JSONArray jsonToPOJO = new JSONArray(jsonResponse);
                    System.out.println("favourites jsonResponse - " + jsonResponse);
                    for(int i = 0; i < jsonToPOJO.length(); i++)
                    {
                        fillList(jsonToPOJO.getJSONObject(i));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < favorites.size(); i++)
                {
                    System.out.println("list profile_id " + favorites.get(i).getProfile_id());
                    System.out.println("list get_id " + favorites.get(i).get_id());
                    System.out.println("list getCreated_at " + favorites.get(i).getCreated_at());
                    System.out.println("list getImage_url " + favorites.get(i).getImage_url());
                }

                getFavoritesDAConnection.disconnect();

            } else {
                System.out.println("Response Message - " + getFavoritesDAConnection.getResponseMessage());
            }

        }

        private void fillList(JSONObject obj)
        {
            FavoritesJSONResponse favJR = new FavoritesJSONResponse();
            try {
                favJR.set_id(obj.getString("_id"));
                favJR.setProfile_id(obj.getString("profile_id"));
                favJR.setImage_url("unavailable");
                favJR.setCreated_at(obj.getString("created_at"));

                System.out.println("profileId" + profileId);
                System.out.println("profileId 2 " + obj.getString("profile_id"));

                if(!favJR.getProfile_id().equals(profileId))
                {
                    favorites.add(favJR);
                }

                if(favorites.size() > 0)
                {
                    System.out.println("profile_id (fillList) - " + obj.getString("profile_id"));
                    System.out.println("profile_id (fillList) - " + favorites.get((favorites.size()-1)).getProfile_id());
                    System.out.println("profile_id (fillList) - " + favJR.getProfile_id());
                }

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

            adapter = new FavouritesCustomList(getActivity(), favorites);
            favourites_listview.setAdapter(adapter);

            favourites_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent favoriteProfileDetails = new Intent("android.intent.action.FAVORITEPROFILE");
                    Bundle bund = new Bundle();
                    bund.putString("auth_token", auth_token);
                    bund.putString("email_id", email_id);
                    bund.putString("profile_id", favorites.get(position).getProfile_id());
                    bund.putString("email_id", email_id);
                    favoriteProfileDetails.putExtras(bund);
                    startActivity(favoriteProfileDetails);
                }
            });

        /*
            favourites_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent homeprofileIntent = new Intent("android.intent.action.HOMEPROFILEDETAILS");
                    homeprofileIntent.putExtra("position", position);
                    homeprofileIntent.putExtra("profile_id", favorites.get(position).getProfile_id());
                    startActivity(homeprofileIntent);
                }
            });

            */

        }
    }


}
