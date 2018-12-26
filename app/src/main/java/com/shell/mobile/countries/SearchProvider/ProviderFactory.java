package com.shell.mobile.countries.SearchProvider;

import android.content.Context;

import com.shell.mobile.countries.Persistent.PersistentProvider;
import com.shell.mobile.countries.network.NetworkProvider;
import com.shell.mobile.countries.utility.Utility;

public class ProviderFactory {

    private NetworkProvider mNetworkProvider;
    private PersistentProvider mPersistentProvider;

    private static ProviderFactory providerFactory = null;


    private ProviderFactory(Context context) {
        mNetworkProvider = new NetworkProvider(context);
        mPersistentProvider = new PersistentProvider(context.getApplicationContext());
    }

    public static ProviderFactory getInstance(Context context) {
        if (null == providerFactory) {
            providerFactory = new ProviderFactory(context);
        }
        return providerFactory;
    }

    public NetworkProvider getNetworkProvider() {
        return mNetworkProvider;
    }

    public PersistentProvider getPersistentProvider() {
        return mPersistentProvider;

    }

}
