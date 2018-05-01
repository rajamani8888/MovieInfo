package com.sriramr.movieinfo.utils;

import java.util.concurrent.Executor;

public class AppExecutors {

    private Executor diskIO, networkIO;

    public AppExecutors(Executor diskIO, Executor networkIO) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public void setDiskIO(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public void setNetworkIO(Executor networkIO) {
        this.networkIO = networkIO;
    }
}
