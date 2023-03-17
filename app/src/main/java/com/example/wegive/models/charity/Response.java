package com.example.wegive.models.charity;

import com.google.firebase.firestore.IgnoreExtraProperties;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "response", strict = false)
@IgnoreExtraProperties
public class Response {

    public Response(){}

    public Response(List<ProjectsResponse> projectList) {
        this.projectList = projectList;
    }

    public List<ProjectsResponse> getProjectList(){
        return this.projectList;
    }


    @ElementList(entry = "project",inline = true, required = false)
    @Path("projects")
    List<ProjectsResponse> projectList;
}
