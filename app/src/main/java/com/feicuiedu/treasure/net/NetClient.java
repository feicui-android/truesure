package com.feicuiedu.treasure.net;

import com.feicuiedu.treasure.user.UserApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class NetClient {

    public static final String BASE_URL = "http://admin.syfeicuiedu.com";
    private static NetClient sClient;

    private final OkHttpClient client;
    private final Retrofit retrofit;

    private NetClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .build();
    }

    public OkHttpClient getClient(){
        return client;
    }

    private UserApi userApi;

    public UserApi getUserApi() {
        if(userApi == null){
            userApi = retrofit.create(UserApi.class);
        }
        return userApi;
    }


    public static NetClient getInstance() {
        if (sClient == null) {
            sClient = new NetClient();
        }
        return sClient;
    }




}
