package com.pop.commandcenter.constants;

import android.content.Context;

import com.pop.commandcenter.R;

/**
 * Created by joemc on 12/23/2017.
 */

public class RemoteAuth {
    public static String getBasicAuthValue(Context context) {
        String basic = "Basic ";
        String hash = context.getResources().getString(R.string.auth_hash);
        return basic + hash;
    }
}
