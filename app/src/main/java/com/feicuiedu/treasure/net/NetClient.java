package com.feicuiedu.treasure.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class NetClient {

    private static NetClient sClient;

    private final OkHttpClient client;

    private NetClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public OkHttpClient getClient(){
        return client;
    }


    public static NetClient getInstance() {
        if (sClient == null) {
            sClient = new NetClient();
        }
        return sClient;
    }




}
