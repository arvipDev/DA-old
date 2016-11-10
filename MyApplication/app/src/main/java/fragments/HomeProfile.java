package fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.dealacceleration.R;

import java.util.ArrayList;
import java.util.List;

import domain.Profile;
import services.ProfileCustomList;
import services.RecyclerAdapter;

public class HomeProfile extends Fragment implements View.OnClickListener {

    //private ListView home_profiles_lv_profiles;
    private List<Profile> profiles = new ArrayList<>();
    private ProfileCustomList adapter;
    private List<Profile> profileTest = new ArrayList<>();
    private Spinner home_profiles_s_category;

    private RecyclerView recyclerView;

    public HomeProfile()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_home_profiles, container, false);

        populateProfile();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.home_profiles_lv_profiles);

        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), profiles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        //Layout manager for Recycler view

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        home_profiles_s_category = (Spinner)home_profiles_s_category.findViewById(R.id.home_profiles_s_category);

       /*
        View rootView = inflater.inflate(R.layout.activity_home_profiles, container, false);

        populateProfile();

        adapter = new ProfileCustomList(getActivity(), profiles);
        home_profiles_lv_profiles = (ListView)rootView.findViewById(R.id.home_profiles_lv_profiles);
        addFooter();
        home_profiles_lv_profiles.setAdapter(adapter);
        home_profiles_lv_profiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent homeprofileIntent = new Intent("android.intent.action.HOMEPROFILEDETAILS");
                homeprofileIntent.putExtra("position", position);
                startActivity(homeprofileIntent);
            }
        });
        */

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }

    /*

    public void addFooter() {
        Log.d("Inside addfooter", " here");
        Button btnLoadMore = new Button(getActivity());
        btnLoadMore.setText("Load More");
        btnLoadMore.setBackgroundResource(R.drawable.button_grey);
        home_profiles_lv_profiles.addFooterView(btnLoadMore);
        btnLoadMore.setOnClickListener(this);
        Log.d("Inside addfooter", " out");
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    public void loadMore()
    {
        adapter.clear();
        populateProfile();
        Profile proTest = new Profile();
        profileTest.add(proTest);
        profileTest.add(proTest);
        profileTest.add(proTest);
        profileTest.add(proTest);
        profileTest.add(proTest);
        profileTest.add(proTest);
        profileTest.add(proTest);
        profileTest.add(proTest);
        profiles.addAll(profileTest);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v)
    {
        loadMore();
    }

        */

    public List<Profile> populateProfile() {
        //Dummy test profile
        Profile pro1 = new Profile();
        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.img2);
        pro1.setImage(Bitmap.createBitmap(bmp1));
        pro1.setDescription("Test1");

        Profile pro2 = new Profile();
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.img3);
        //pro2.setImage(Bitmap.createBitmap(bmp2));
        pro2.setDescription("Test2");

        Profile pro3 = new Profile();
        Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.img4);
        //pro3.setImage(Bitmap.createBitmap(bmp3));
        pro3.setDescription("Test3");

        Profile pro4 = new Profile();
        Bitmap bmp4 = BitmapFactory.decodeResource(getResources(), R.drawable.img5);
        //pro4.setImage(Bitmap.createBitmap(bmp4));
        pro4.setDescription("Test4");

        Profile pro5 = new Profile();
       // pro5.setImage(Bitmap.createBitmap(bmp1));
        pro5.setDescription("Test5");

        Profile pro6 = new Profile();
        //pro6.setImage(Bitmap.createBitmap(bmp2));
        pro6.setDescription("Test6");

        Profile pro7 = new Profile();
        //pro7.setImage(Bitmap.createBitmap(bmp3));
        pro7.setDescription("Test7");

        Profile pro8 = new Profile();
        //pro8.setImage(Bitmap.createBitmap(bmp4));
        pro8.setDescription("Test8");

        try {
            profiles.add(pro1);
            Log.d("obj 1 added", "Obj 1");
            profiles.add(pro2);
            Log.d("obj 2 added", "Obj 2");
            profiles.add(pro3);
            profiles.add(pro4);
            profiles.add(pro5);
            profiles.add(pro6);
            profiles.add(pro7);
            profiles.add(pro8);
            profiles.add(pro3);
            profiles.add(pro4);
            profiles.add(pro5);
            profiles.add(pro6);
            profiles.add(pro7);
            profiles.add(pro8);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Cant add", "Dint add");
        }
        return profiles;
    }

}