package com.xw.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by XWCHQ on 2017/6/2-19:09
 */

public class IntentUtil {
    public static Intent actionDial(String mobile) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
        return intent;
    }

    public static Intent actionLauncher(Context context) {
        return context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    }
}
