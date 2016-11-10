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

import domain.EmailJSONResponse;

public class EmailCustomList extends ArrayAdapter<EmailJSONResponse>
{
    private Activity context = null;
    private List<EmailJSONResponse> emails = new ArrayList<>();

    public EmailCustomList(Activity context, List<EmailJSONResponse> emails)
    {
        super(context, R.layout.list_email, emails);
        this.context = context;
        this.emails = emails;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        EmailJSONResponse currentEmail = emails.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_email, null, true);

        TextView list_email_tv_from = (TextView) rowView.findViewById(R.id.list_email_tv_from);
        list_email_tv_from.setText(currentEmail.getName());

        TextView list_email_tv_dateTime = (TextView) rowView.findViewById(R.id.list_email_tv_dateTime);
        String dateTime = currentEmail.getDate_time();
        String date  = dateTime.substring(0,10);
        String time = dateTime.substring(12,19);
        list_email_tv_dateTime.setText(date + " at " + time);

        TextView list_email_tv_subject = (TextView) rowView.findViewById(R.id.list_email_tv_subject);
        list_email_tv_subject.setText("Subject: " + currentEmail.getSubject());
        list_email_tv_subject.setGravity(View.TEXT_ALIGNMENT_CENTER);

        return rowView;
    }

}
