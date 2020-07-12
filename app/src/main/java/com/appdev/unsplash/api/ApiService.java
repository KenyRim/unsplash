package com.appdev.unsplash.api;

import com.appdev.unsplash.model.Wallpaper;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    String BASE_URL = "https://api.unsplash.com/";
    String API_KEY = "0ae0ce713ecf0364310007dd12a371ffc5f043f1538a14d63ff166139e41d8fe";


    @GET("photos/?client_id=" + API_KEY)
    Observable<List<Wallpaper>> getWallpapers(@Query("order_by") String orderBy,
                                    @Query("per_page") int perPage,
                                    @Query("page") int page);



    class Factory {
         public static Observable<List<Wallpaper>> createService(int page) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

            return retrofit.create(ApiService.class).getWallpapers("latest",25,page);
        }

    }

}