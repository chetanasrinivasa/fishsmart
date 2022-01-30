package com.mobiddiction.fishsmart.Network;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by andrewi on 24/05/2017.
 */

public class ConnectivityInterceptor implements Interceptor {

    private Context mContext;

    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isOnline(mContext)) {
            EventBus.getDefault().post(NetworkEvent.CONNECTION_LOST);
            throw new NoConnectivityException();
        }
        EventBus.getDefault().post(NetworkEvent.CONNECTION_GAINED);
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

    public class NoConnectivityException extends IOException {

        @Override
        public String getMessage() {
            return "No connectivity exception";
        }

    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

}