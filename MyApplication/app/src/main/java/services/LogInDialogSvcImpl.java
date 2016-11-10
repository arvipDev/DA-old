package services;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.dealacceleration.LogInActivity;

public class LogInDialogSvcImpl implements ILogInDialogSvc {

    private LogInActivity context;

    public LogInDialogSvcImpl()
    {

    }
    public LogInDialogSvcImpl(LogInActivity context)
    {
        this.context = context;
    }

    public void createDialogBox(String title, String message)
    {
        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);
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
