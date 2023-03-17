package com.example.wegive.models.charity;

import com.google.firebase.firestore.IgnoreExtraProperties;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@IgnoreExtraProperties
@Root(name = "search", strict = false)
public class SearchResponse {

    public SearchResponse(){}

    public SearchResponse(Response response) {
        this.response = response;
    }

    public Response getResponse(){
        return this.response;
    }

    @Element(name = "response", required = false)
    Response response;
}
