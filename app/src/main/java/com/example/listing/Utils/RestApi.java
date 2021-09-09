package com.example.listing.Utils;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

import android.app.Application;
import android.util.Log;

import com.example.listing.models.User;
import com.example.listing.R;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;

import retrofit2.converter.jackson.JacksonConverterFactory;


public class RestApi {

    private static RestApi instance;
    private static Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    public RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
    }

    private RestApi(Application application, User user){
        retrofitInterface=Login(application,user);
    }


    public static void initializer(Application application ,User user){
        if(instance == null){
            synchronized (RestApi.class){
                instance = new RestApi(application,user);
            }
        }
    }

    public static RestApi getInstance(){
        if(instance == null){
           return null;
        }else{
            Log.i("response","here");
            return instance;
        }

    }



    public static RetrofitInterface Login(Application application, User user) {
        Log.i("Login-Repo","2");
        return CreateClient(application ,user).create(RetrofitInterface.class);
    }

    public static Retrofit CreateClient(Application application, User user) {
        if (retrofit == null) {
            Log.i("Login-create","3");
            Gson gson=new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(application.getString(R.string.dvcURL))
                    .client(headersInterceptors(user, application))
                    .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper().setPropertyNamingStrategy(new PropertyNamingStrategies.UpperCamelCaseStrategy()))).build();

        }
        return retrofit;
    }
    static OkHttpClient headersInterceptors(User user, Application application) {
        CookieHandler cookieHandler = new CookieManager();
        OkHttpClient  client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuth(user, application))
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(900, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        return client;
    }

    private static class BasicAuth implements Interceptor {
        User user;
        Application application;

        public BasicAuth(User user, Application application) {
            this.application = application;
            this.user = user;
        }


        @Override
        public Response intercept(Chain chain) throws IOException {
            User user1=new User("alsoaia","Abeer28121997@");
            String creds = Credentials.basic(user.getUsername(),user.getPassword());
            Headers headers = chain.request().headers().newBuilder()
                    .add("Authorization", creds)
                    .add("Content-Type", application.getResources().getString(R.string.Content_Type))
                    .add("Accept", application.getResources().getString(R.string.accept))
                    .add("sap-client", application.getResources().getString(R.string.sapclient_25))
                    .build();

            Request request = chain.request().newBuilder().headers(headers).build();
            Log.i("header",creds);


            if(request.body()!=null){
                final Buffer buffer=new Buffer();
                request.body().writeTo(buffer);
                Log.i("headers",buffer.readUtf8());
            }
            return chain.proceed(request);
        }
    }


}