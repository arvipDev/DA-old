package services;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class SegmentedRadioGroupSignUp extends RadioGroup implements ISegmentedRadioGroupSvc {

    public SegmentedRadioGroupSignUp(Context context) {
        super(context);
    }

    public SegmentedRadioGroupSignUp(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        changeButtonsImages();
    }

    public void changeButtonsImages(){
        int count = super.getChildCount();

        if(count > 1)
        {
            super.getChildAt(0).setBackgroundResource
                    (com.dealacceleration.R.drawable.segment_radio_left);

            super.getChildAt(1).setBackgroundResource
                    (com.dealacceleration.R.drawable.segment_radio_right);
        }
    }
}