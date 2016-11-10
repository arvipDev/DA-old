package services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealacceleration.R;

import java.util.ArrayList;
import java.util.List;

import domain.Profile;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProfilesListHolder>
{
    List<Profile> profiles = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public RecyclerAdapter(Context context, List<Profile> profiles) {
        this.profiles = profiles;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ProfilesListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.recycle_list, parent, false);
        ProfilesListHolder viewHolder = new ProfilesListHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProfilesListHolder holder, int position)
    {
        Profile currenttProfile = profiles.get(position);
        holder.tv1.setText(currenttProfile.getDescription());
        //holder.tv2.setText(++i);
        holder.imageView.setImageBitmap(currenttProfile.getImage());
        holder.setProfile(currenttProfile);
        //holder.imageView.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class ProfilesListHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv1, tv2;
        ImageView imageView;
        Profile profile;

        public ProfilesListHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            tv1 = (TextView) itemView.findViewById(R.id.list_title);
            tv2 = (TextView) itemView.findViewById(R.id.list_desc);
            imageView = (ImageView) itemView.findViewById(R.id.list_avatar);
        }

        public void setProfile(Profile profile)
        {
            this.profile = profile;
        }

        @Override
        public void onClick(View v)
        {
            System.out.println("profiles " + profile.getDescription());
            Intent intent = new Intent("android.intent.action.HOMEPROFILEDETAILS");
            context.startActivity(intent);
        }
    }

}
