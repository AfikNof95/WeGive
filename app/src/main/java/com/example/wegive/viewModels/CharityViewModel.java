package com.example.wegive.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wegive.models.charity.Charity;
import com.example.wegive.models.charity.CharityAPIModel;

import java.util.List;

public class CharityViewModel extends ViewModel {

    private MutableLiveData<List<Charity>> charities = CharityAPIModel.instance().getCharities("Israel");


    public MutableLiveData<List<Charity>> getCharities() {
        return charities;
    }

}