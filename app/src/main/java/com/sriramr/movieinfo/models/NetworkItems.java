package com.sriramr.movieinfo.models;

import com.sriramr.movieinfo.utils.Status;

import java.util.ArrayList;
import java.util.List;

public class NetworkItems<T> {

    private Status status;
    private List<T> listItems;

    public NetworkItems() {
        this.status = Status.FAILURE;
        this.listItems = new ArrayList<>();
    }

    public NetworkItems(Status status, List<T> listItems) {
        this.status = status;
        this.listItems = listItems;
    }

    public NetworkItems(Status status) {
        this.status = status;
        this.listItems = new ArrayList<>();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<T> getListItems() {
        return listItems;
    }

    public void setListItems(List<T> listItems) {
        this.listItems = listItems;
    }
}
