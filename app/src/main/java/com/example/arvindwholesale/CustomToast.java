package com.example.arvindwholesale;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

class CustomToast {
    Toast T;

    CustomToast(Context context, String message, int Length, boolean good) {
        Toast toast = Toast.makeText(context, message, Length);
        View v = toast.getView();
        if (good)
            v.setBackgroundResource(R.drawable.toastbackgood);
        if (!good)
            v.setBackgroundResource(R.drawable.toastbackbad);
        toast.setView(v);
        //toast.setGravity(Gravity.CLIP_VERTICAL, 0, 0);
        T = toast;
    }
}
