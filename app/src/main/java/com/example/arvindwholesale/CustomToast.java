package com.example.arvindwholesale;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.arvindwholesale.R;

public class CustomToast{
    public Toast T;
   CustomToast(Context context,String message,int Length,boolean good)
    {
        Toast toast = Toast.makeText(context, message, Length);
        View v=toast.getView();
        if(good)
            v.setBackgroundResource(R.drawable.toastbackgoodground);
        if(!good)
            v.setBackgroundResource(R.drawable.toastbackbadground);
        toast.setView(v);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        T=toast;
    }
}
