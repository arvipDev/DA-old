package services;

import android.widget.EditText;

public class ValuesSvcImpl implements IValuesSvc {

    private EditText text;

    public void setItem(EditText text)
    {
        this.text = text;
    }

    public String getValues()
    {
        String send;
        send = String.valueOf(text.getText());
        return send;
    }

    public boolean isEmpty()
    {
        if(getValues().isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
