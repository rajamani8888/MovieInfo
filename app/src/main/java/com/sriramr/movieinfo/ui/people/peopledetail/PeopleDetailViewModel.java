package com.sriramr.movieinfo.ui.people.peopledetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sriramr.movieinfo.models.NetworkItem;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.ui.people.peopledetail.models.PeopleDetailResponse;
import com.sriramr.movieinfo.utils.Status;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PeopleDetailViewModel extends AndroidViewModel {

    private String id;
    private MutableLiveData<NetworkItem<PeopleDetailResponse>> peopleDetailItem;
    private CompositeDisposable compositeDisposable;

    public PeopleDetailViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
        peopleDetailItem = new MutableLiveData<>();
    }

    public void init(String id) {
        this.id = id;
    }

    private void refreshData() {
        compositeDisposable.add(
                NetworkRepository.getPeopleDetail(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(peopleDetailResponse -> {
                            NetworkItem<PeopleDetailResponse> peopleResponse = new NetworkItem<>(Status.SUCCESS);
                            peopleResponse.setItem(peopleDetailResponse);
                            peopleDetailItem.setValue(peopleResponse);
                        }, throwable -> {
                            Timber.e(throwable);
                            NetworkItem<PeopleDetailResponse> peopleResponse = new NetworkItem<>(Status.FAILURE);
                            peopleDetailItem.setValue(peopleResponse);
                        })
        );
    }

    public MutableLiveData<NetworkItem<PeopleDetailResponse>> getPeopleDetailItem() {

        if (peopleDetailItem.getValue() == null) {
            refreshData();
        }

        return peopleDetailItem;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
