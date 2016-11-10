package com.dealacceleration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private Button forgotPassword_b_cancel, forgotPassword_b_submit;
    private EditText forgotPassword_et_email;

    //------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Forgot Password");
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

        forgotPassword_b_cancel = (Button)findViewById(R.id.forgotPassword_b_cancel);
        forgotPassword_b_cancel.setOnClickListener(this);

        forgotPassword_b_submit = (Button)findViewById(R.id.forgotPassword_b_submit);
        forgotPassword_b_submit.setOnClickListener(this);

        forgotPassword_et_email = (EditText)findViewById(R.id.forgotPassword_et_email);
        forgotPassword_et_email.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.forgotPassword_b_cancel :
                finish();
                break;
            case R.id.forgotPassword_b_submit :
                if(forgotPassword_et_email.getText().toString().isEmpty())
                {
                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
                    // set title
                    alertDialogBuilder1.setTitle("Invalid Entry");

                    // set dialog message
                    alertDialogBuilder1
                            .setMessage("Please enter a valid email id")
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

}
