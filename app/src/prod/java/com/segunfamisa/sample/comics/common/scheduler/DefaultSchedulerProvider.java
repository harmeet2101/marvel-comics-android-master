package com.segunfamisa.sample.comics.common.scheduler;


import java.util.concurrent.Executor;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Default implementation of {@link SchedulerProvider}.
 */
public class DefaultSchedulerProvider implements SchedulerProvider {

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler trampoline() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler from(Executor executor) {
        return Schedulers.from(executor);
    }
}
