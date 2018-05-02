package com.sriramr.movieinfo.models;

import com.sriramr.movieinfo.utils.Status;

import java.util.ArrayList;
import java.util.List;

public class NetworkItems<T> {

    private Status status;
    private List<T> items;

    public NetworkItems() {
        this.status = Status.FAILURE;
        items = new ArrayList<>();
    }

    public NetworkItems(Status status, List<T> listItems) {
        this.status = status;
        this.items = listItems;
    }

    public NetworkItems(Status status) {
        this.status = status;
        items = new ArrayList<>();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
