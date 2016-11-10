package services;

import android.widget.EditText;

public interface IValuesSvc
{
    void setItem(EditText tv_login);
    String getValues();
    boolean isEmpty();
}
