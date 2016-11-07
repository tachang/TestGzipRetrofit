package com.example.testgzipretrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    public static class Fruit {


    }

    public interface TestGzip {
        @GET("/withgzip")
        Call<Fruit> withgzip();

        @GET("/withoutgzip")
        Call<Fruit> withoutgzip();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String API_URL = "http://example.com:5000";

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        final TestGzip testGzip = retrofit.create(TestGzip.class);


        Button withGzip = (Button)findViewById(R.id.withGzip);
        Button withoutGzip = (Button)findViewById(R.id.withoutGzip);

        withGzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Testing withGzip");


                // Create a call instance for looking up Retrofit contributors.
                Call<Fruit> call = testGzip.withgzip();

                try {
                    Fruit f = call.execute().body();
                    System.out.println("Response: " + f);

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });


        withoutGzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Testing withoutGzip");

                // Create a call instance for looking up Retrofit contributors.
                Call<Fruit> call = testGzip.withoutgzip();

                try {
                    Fruit f = call.execute().body();
                    System.out.println("Response: " + f);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




    }
}
