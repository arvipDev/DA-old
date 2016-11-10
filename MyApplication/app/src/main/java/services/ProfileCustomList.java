package services;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealacceleration.R;

import java.util.ArrayList;
import java.util.List;

import domain.Profile;

public class ProfileCustomList extends ArrayAdapter<Profile>
{
    private Activity context = null;
    private List<Profile> profiles = new ArrayList<>();

    public ProfileCustomList(Activity context, List<Profile> profiles)
    {
        super(context, R.layout.list_profiles, profiles);
        this.context = context;
        this.profiles = profiles;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        final Profile currentProfile = profiles.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_profiles, null, true);

        TextView list_profile_tv_description = (TextView) rowView.findViewById(R.id.list_profile_tv_description);
        list_profile_tv_description.setText(currentProfile.getDescription());

        ImageView list_profile_iv_profile = (ImageView) rowView.findViewById(R.id.list_profile_iv_profile);
        list_profile_iv_profile.setImageBitmap(currentProfile.getImage());
        list_profile_iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createImageDialog(currentProfile.getImage());
            }
        });

        return rowView;
    }

    public void createImageDialog(Bitmap img)
    {
        LayoutInflater li = LayoutInflater.from(getContext());
        View dialogView = li.inflate(R.layout.dialog_profile_listview_image, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

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
}
