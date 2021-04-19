package com.smitdesai16.chromecastforandroidwithcustomreceiverchat;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;

import java.util.List;

public class CastOptionsProvider implements OptionsProvider {
    @Override
    public CastOptions getCastOptions(Context context) {
        return new CastOptions.Builder().setReceiverApplicationId(context.getString(R.string.receiver_app_id)).build();
    }

    @Nullable
    @Override
    public List<SessionProvider> getAdditionalSessionProviders(Context context) {
        return null;
    }
}
