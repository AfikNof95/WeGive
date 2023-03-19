package com.example.wegive.models.charity;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.wegive.models.post.Post;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class CharityAPIModel implements Serializable {

    private final String apiKey = "3ef22f8a-e79d-40f4-9edc-d2d41f105898";

    private final List<Post> apiPostsList = new ArrayList<>();
    private static final CharityAPIModel _instance = new CharityAPIModel();

    private MutableLiveData<List<Charity>> data;

    Retrofit retrofit;
    CharityAPI charityAPI;



    public static CharityAPIModel instance() {
        return _instance;
    }

    private CharityAPIModel() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.globalgiving.org/api/public/services/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        charityAPI = retrofit.create(CharityAPI.class);
    }

    public MutableLiveData<List<Charity>> getCharities(String query) {
        if(data == null){
            data = new MutableLiveData<>();
        }
        Call<SearchResponse> call = charityAPI.getAPIPosts(apiKey, query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    List<Charity> charityList = new ArrayList<>();
                    List<ProjectsResponse> projectList = response.body().getResponse().getProjectList();

                    if(projectList != null){
                        projectList.stream().forEach(project -> {
                            String title = project.getTitle();
                            String content = project.getActivities();
                            String imageUrl = project.getImageLink();
                            String date = project.getApprovedDate();
                            String creatorName = project.getContactName();
                            String creatorEmail = project.getContactEmail();
                            String projectUrl = project.getProjectLink();
                            String country = project.getCountry();
                            String contactPhoneNumber = project.getContactPhone();
                            Charity charity = new Charity(title, content, imageUrl, creatorName, creatorEmail,contactPhoneNumber ,date, projectUrl, country);
                            charityList.add(charity);
                        });
                    }

                    data.setValue(charityList);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d("TAG", "----- ProjectsResponse fail");
                Log.d("CharityAPI", t.getMessage());
            }
        });
        return data;
    }

    public List<Post> getApiPostsList() {
        return apiPostsList;
    }


}
