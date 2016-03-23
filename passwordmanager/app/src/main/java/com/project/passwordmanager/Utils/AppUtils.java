package com.project.passwordmanager.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by elitecore on 2/26/16.
 */
public class AppUtils {

    public static int MINIMUM_PASSWORD_LENGTH = 8;

    public static void makeLongToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void makeShortToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static String getSaltKey(String str){
           if(str != null && str.length() > 7) {
               str =str.substring(0,8);
               str = str +str;
           }
           return str;
    }
}
