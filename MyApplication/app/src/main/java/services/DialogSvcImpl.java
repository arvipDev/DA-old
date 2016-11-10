package services;

import android.widget.EditText;
import com.dealacceleration.LogInActivity;
import com.dealacceleration.SignUpActivity;

public class DialogSvcImpl implements IDialogSvc
{
    private LogInDialogSvcImpl logInDialog;
    private SignUpCustomDialogSvcImpl signUpDialog;

    public void setLogInContext(LogInActivity context)
    {
        logInDialog = new LogInDialogSvcImpl(context);
    }

    public void setSignUpContext(SignUpActivity context)
    {
        signUpDialog = new SignUpCustomDialogSvcImpl(context);
    }

    public void invalidCred(Boolean cred)
    {
        if(cred == false)
        {
            logInDialog.createDialogBox("Incorrect entry ", "Eemail and/or password is incorrect");
        }
    }

    public void createLogInDialog(EditText et_username, EditText et_password)
    {
        ValuesSvcImpl values = new ValuesSvcImpl();
        values.setItem(et_username);

        ValuesSvcImpl values2 = new ValuesSvcImpl();
        values2.setItem(et_password);

        if(values.isEmpty() && values2.isEmpty())
        {
            logInDialog.createDialogBox("Empty", "Enter a valid email id and password");
        }
        else if(values.isEmpty() && !(values2.isEmpty()))
        {
            logInDialog.createDialogBox("Invalid Entry", "Enter a valid username or email id");
        }
        else if(!(values.isEmpty()) && values2.isEmpty())
        {
            logInDialog.createDialogBox("Invalid Entry", "Enter a valid password");
        }
    }

    /*
    public void createSignUpDialog(EditText et_fname, EditText et_lname, EditText et_email,
                                   EditText et_question, EditText et_answer,
                                   EditText et_password, EditText et_confirmPassword)
    {
        ValuesSvcImpl values = new ValuesSvcImpl();
        values.setItem(et_fname);

        ValuesSvcImpl values2 = new ValuesSvcImpl();
        values2.setItem(et_lname);

        ValuesSvcImpl values3 = new ValuesSvcImpl();
        values2.setItem(et_email);

        ValuesSvcImpl values4 = new ValuesSvcImpl();
        values2.setItem(et_question);

        ValuesSvcImpl values5 = new ValuesSvcImpl();
        values2.setItem(et_answer);

        ValuesSvcImpl values6 = new ValuesSvcImpl();
        values2.setItem(et_password);

        ValuesSvcImpl values7 = new ValuesSvcImpl();
        values2.setItem(et_confirmPassword);

        if(values.isEmpty() && values2.isEmpty() && values3.isEmpty() && values4.isEmpty() && values5.isEmpty()
                && values6.isEmpty()
                && values7.isEmpty())
        {
            signUpDialog.createDialogBox("Invalid Entry", "Unable to sign up, please fill all the fields.");
        }
        else if(values.isEmpty() && !(values2.isEmpty()) && !values3.isEmpty() && !values4.isEmpty() && !values5.isEmpty()
                && !values6.isEmpty()
                && !values7.isEmpty())
        {
            signUpDialog.createDialogBox("Invalid Entry", "Enter First Name.");
        }
        else if(!values.isEmpty() && (values2.isEmpty()) && !values3.isEmpty() && !values4.isEmpty() && !values5.isEmpty()
                && !values6.isEmpty()
                && !values7.isEmpty())
        {
            signUpDialog.createDialogBox("Invalid Entry", "Enter Last Name.");
        }
        else if(!values.isEmpty() && !(values2.isEmpty()) && values3.isEmpty() && !values4.isEmpty() && !values5.isEmpty()
                && !values6.isEmpty()
                && !values7.isEmpty())
        {
            signUpDialog.createDialogBox("Invalid Entry", "Enter Email.");
        }
        else if(!values.isEmpty() && !(values2.isEmpty()) && !values3.isEmpty() && values4.isEmpty() && !values5.isEmpty()
                && !values6.isEmpty()
                && !values7.isEmpty())
        {
            signUpDialog.createDialogBox("Invalid Entry", "Enter Security Question.");
        }
        else if(!values.isEmpty() && !(values2.isEmpty()) && !values3.isEmpty() && !values4.isEmpty() && values5.isEmpty()
                && !values6.isEmpty()
                && !values7.isEmpty())
        {
            signUpDialog.createDialogBox("Invalid Entry", "Enter Security Answer.");
        }

        else if(!values.isEmpty() && !(values2.isEmpty()) && !values3.isEmpty() && !values4.isEmpty() && !values5.isEmpty()
                && values6.isEmpty()
                && !values7.isEmpty())
        {
            signUpDialog.createDialogBox("Invalid Entry", "Enter Password.");
        }

        else if(!values.isEmpty() && !(values2.isEmpty()) && !values3.isEmpty() && !values4.isEmpty() && !values5.isEmpty()
                && !values6.isEmpty()
                && values7.isEmpty())
        {
            signUpDialog.createDialogBox("Invalid Entry", "Enter Confirm Password.");
        }
        else
        {
            signUpDialog.createDialogBox("Invalid Entry", "Unable to sign up, please fill all the fields.");
        }
    }
    */
}
