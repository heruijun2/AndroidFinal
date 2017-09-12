package rn.heruijun.com.androidfinal.inc;

import android.view.View;

/**
 * Created by heruijun on 2017/8/15.
 */

public interface OnItemClickListener {
    
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
