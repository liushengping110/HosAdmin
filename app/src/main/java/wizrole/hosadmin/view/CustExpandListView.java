package wizrole.hosadmin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by 何人执笔？ on 2018/4/20.
 * liushengping
 */

public class CustExpandListView extends ExpandableListView {
    public CustExpandListView(Context context) {
        super(context);
    }

    public CustExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustExpandListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
