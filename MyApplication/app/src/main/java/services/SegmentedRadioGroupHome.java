package services;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.dealacceleration.R;

public class SegmentedRadioGroupHome extends RadioGroup implements ISegmentedRadioGroupSvc {

    public SegmentedRadioGroupHome(Context context) {
        super(context);
    }

    public SegmentedRadioGroupHome(Context context, AttributeSet attrs) {
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
                    (R.drawable.segment_radio_home_one);

            super.getChildAt(1).setBackgroundResource
                    (R.drawable.segment_radio_home_four);

            super.getChildAt(2).setBackgroundResource
                    (R.drawable.segment_radio_home_two);

            super.getChildAt(3).setBackgroundResource
                    (R.drawable.segment_radio_home_three);

        }
    }
}