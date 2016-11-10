package services;

import android.widget.EditText;

import com.dealacceleration.LogInActivity;
import com.dealacceleration.SignUpActivity;

public interface IDialogSvc
{
    void createLogInDialog(EditText et_username, EditText et_password);
    void setLogInContext(LogInActivity context);
}
