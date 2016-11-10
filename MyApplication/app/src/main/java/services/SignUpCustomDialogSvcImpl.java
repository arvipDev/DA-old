package services;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;

import com.dealacceleration.R;
import com.dealacceleration.SignUpActivity;

public class SignUpCustomDialogSvcImpl implements ISignUpCustomDialogSvc
{
    private SignUpActivity context;
    public int question;
    private EditText signUp_et_question;

    public SignUpCustomDialogSvcImpl(SignUpActivity context)
    {
        this.context = context;
        signUp_et_question = (EditText)context.findViewById(R.id.signUp_et_question);
    }

    public void createDialogBox(String title)
    {
        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);
        // set title
        final CharSequence[] items = {" Mother's Maiden Name "," First Pet's Name "," City Born In "};
        alertDialogBuilder1.setTitle(title);

        // set dialog message
        alertDialogBuilder1.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        // Your code when first option seletced
                        question = 0;
                        break;
                    case 1:
                        // Your code when 2nd  option seletced
                        question = 1;
                        break;
                    case 2:
                        // Your code when 3rd option seletced
                        question = 2;
                        break;

                }
            }
        }).setCancelable(false)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Enter code here to perform action on question
                        if(question == 0)signUp_et_question.setText("  Mother's Maiden Name");
                        else if(question == 1)signUp_et_question.setText("  First Pet's Name");
                        else if(question == 2)signUp_et_question.setText("  City Born In");
                        else signUp_et_question.setText("");
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog2 = alertDialogBuilder1.create();

        // show it
        alertDialog2.show();
    }

}
