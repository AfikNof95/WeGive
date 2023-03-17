package com.example.wegive.models.charity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharityAPI {

    @GET("search/projects?filter=country:IL,theme:disability")
    Call<SearchResponse> getAPIPosts(@Query("api_key") String apiKey, @Query("q") String query);

}
