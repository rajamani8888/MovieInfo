package com.sriramr.movieinfo.models;

import com.sriramr.movieinfo.utils.Status;

public class NetworkItem<T> {

    private Status status;
    private T item;

    public NetworkItem(Status status) {
        this.status = status;
    }

    public NetworkItem(Status status, T item) {
        this.status = status;
        this.item = item;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
