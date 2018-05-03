package com.sriramr.movieinfo.utils;

import android.support.v7.util.DiffUtil;

import com.sriramr.movieinfo.ui.movies.movielist.models.Movie;

import java.util.List;

public class DiffUtilsCallback<T> extends DiffUtil.Callback {

    private List<T> newList;
    private List<T> oldList;

    public DiffUtilsCallback(List<T> newList, List<T> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T newObject = newList.get(newItemPosition);
        T oldObject = oldList.get(oldItemPosition);

        if (newObject instanceof Movie && oldObject instanceof Movie) {
            Movie newMovie = (Movie) newObject;
            Movie oldMovie = (Movie) oldObject;
            return newMovie.getId() == oldMovie.getId();
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T newObject = newList.get(newItemPosition);
        T oldObject = oldList.get(oldItemPosition);

        if (newObject instanceof Movie && oldObject instanceof Movie) {
            Movie newMovie = (Movie) newObject;
            Movie oldMovie = (Movie) oldObject;
            return newMovie.getId() == oldMovie.getId();
        }
        return false;
    }
}
