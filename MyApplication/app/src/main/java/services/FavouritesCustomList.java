package services;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dealacceleration.R;

import java.util.ArrayList;
import java.util.List;

import domain.FavoritesJSONResponse;


public class FavouritesCustomList extends ArrayAdapter<FavoritesJSONResponse> {
    private Activity context = null;
    private List<FavoritesJSONResponse> favorites = new ArrayList<>();

    public FavouritesCustomList(Activity context, List<FavoritesJSONResponse> favorites)
    {
        super(context, R.layout.list_favorites, favorites);
        this.context = context;
        this.favorites = favorites;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        final FavoritesJSONResponse currentFavorite = favorites.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_favorites, null, true);

        String dateTime = currentFavorite.getCreated_at();
        System.out.println("fcl " +  currentFavorite.getCreated_at());
        System.out.println("fcl " + currentFavorite.getProfile_id());
        System.out.println("fcl " + favorites.size());

        TextView list_favourites_profilename = (TextView) rowView.findViewById(R.id.list_favourites_profilename);
        String date, time;
        date = dateTime.substring(0,10);
        time = dateTime.substring(12,19);
        list_favourites_profilename.setText("Favorited on " + date + " at " + time);

        /*
        ImageView list_profile_iv_profile = (ImageView) rowView.findViewById(R.id.list_profile_iv_profile);
        list_profile_iv_profile.setImageBitmap(currentFavorite.getImage());
        list_profile_iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createImageDialog(currentFavorite.getImage());
            }
        });
        */

        return rowView;
    }

    /*
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
    */
}
