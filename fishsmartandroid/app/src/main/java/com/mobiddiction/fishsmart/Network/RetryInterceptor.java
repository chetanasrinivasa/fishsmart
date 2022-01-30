package com.mobiddiction.fishsmart.Network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        boolean responseOK = false;
        int tryCount = 0;


        while (!responseOK && tryCount < 1) {

            response = chain.proceed(request);
            responseOK = response.isSuccessful();
            if (responseOK) {
                break;
            }
            tryCount++;
        }

        // otherwise just pass the original response on
        return response;
    }
}
