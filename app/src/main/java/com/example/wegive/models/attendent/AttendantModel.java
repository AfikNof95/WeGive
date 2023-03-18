package com.example.wegive.models.attendent;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wegive.IListener;
import com.example.wegive.firebase.FireBaseStorage;
import com.example.wegive.firebase.FirebaseAttendantDB;
import com.example.wegive.models.AppLocalDB;
import com.example.wegive.models.AppLocalDbRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AttendantModel {

    private static final AttendantModel _instance = new AttendantModel();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final FirebaseAttendantDB db = new FirebaseAttendantDB();
    private final FireBaseStorage storage = new FireBaseStorage();
    AppLocalDbRepository localDB = AppLocalDB.getAppDb();
    final public MutableLiveData<AttendantModel.LoadingState> EventAttendantLoadingState = new MutableLiveData<AttendantModel.LoadingState>(AttendantModel.LoadingState.NOT_LOADING);


    public static AttendantModel getInstance() {
        return _instance;
    }

    private AttendantModel() {

    }


    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }


    private LiveData<List<Attendant>> attendantList;

    public LiveData<List<Attendant>> getAllAttendants() {
        if (attendantList == null) {
            attendantList = localDB.attendantDao().getAll();
            refreshAllAttendants();
        }
        return attendantList;
    }


    public void refreshAllAttendants() {
        EventAttendantLoadingState.setValue(AttendantModel.LoadingState.LOADING);
        Long localLastUpdate = Attendant.getLocalLastUpdated();
        db.getAllSince(localLastUpdate, response -> {
            executor.execute(() -> {
                Long time = localLastUpdate;
                for (Attendant attendant : response) {
                    // insert new records into ROOM
                    localDB.attendantDao().insertAll(attendant);
                    if (time < attendant.getLastUpdated()) {
                        time = attendant.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Attendant.setLocalLastUpdate(time);
                EventAttendantLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });

        db.getAllDeletedSince(localLastUpdate, response -> {
            executor.execute(() -> {
                for (String id : response) {
                    localDB.attendantDao().deleteAttendantById(id);
                }
            });
        });
    }


    public void add(Attendant attendant, IListener<Void> listener) {
        db.add(attendant, (Void) -> {
            refreshAllAttendants();
            listener.onComplete(null);
        });
    }

    public void delete(String userId,String postId, IListener<Void> listener) {
        db.delete(userId,postId, data -> {
            refreshAllAttendants();
            listener.onComplete(null);
        });

    }


}
