package vn.com.esolutions.es_bienap;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by VINH-PC on 2/9/2018.
 */

public abstract class AbModuleReport extends LinearLayout {
    public AbModuleReport(Context context) {
        super(context);
    }

    protected void invalidateView(final View view){
         view.post(new Runnable() {
             @Override
             public void run() {
                 view.invalidate();
             }
         });
     }
}
