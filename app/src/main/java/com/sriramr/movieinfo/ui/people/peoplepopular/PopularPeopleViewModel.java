package com.sriramr.movieinfo.ui.people.peoplepopular;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sriramr.movieinfo.models.NetworkItems;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.ui.people.peoplepopular.models.PopularPeople;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.Status;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PopularPeopleViewModel extends AndroidViewModel {

    private String tag;
    private MutableLiveData<NetworkItems<PopularPeople>> popularPeople;
    private int page = 0;
    private CompositeDisposable compositeDisposable;
    private NetworkItems<PopularPeople> networkItems;

    public PopularPeopleViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
        this.popularPeople = new MutableLiveData<>();
        networkItems = new NetworkItems<>();
    }

    public void init(String tag) {
        this.tag = tag;
    }

    public void refreshData() {
        page++;
        compositeDisposable.add(
                NetworkRepository.getPopularPeople(AppConstants.API_KEY, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(popularPeopleResponse -> {
                            networkItems.setStatus(Status.SUCCESS);
                            networkItems.getItems().addAll(popularPeopleResponse.getResults());
                            popularPeople.setValue(networkItems);
                        }, throwable -> {
                            Timber.e(throwable);
                            networkItems.setStatus(Status.FAILURE);
                            popularPeople.setValue(networkItems);
                        })
        );
    }

    public void setPeople(ArrayList<PopularPeople> people) {
        NetworkItems<PopularPeople> peopleItem = new NetworkItems<>(Status.SUCCESS);
        peopleItem.setItems(people);
        popularPeople.setValue(peopleItem);
    }

    public MutableLiveData<NetworkItems<PopularPeople>> getPopularPeople() {

        if (popularPeople.getValue() == null && tag.equals(AppConstants.POPULAR_PEOPLE)) {
            refreshData();
        }

        return popularPeople;
    }

    public String getTag() {
        return tag;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
