package com.segunfamisa.sample.comics.util;


import com.segunfamisa.sample.comics.common.scheduler.SchedulerProvider;

import java.util.concurrent.Executor;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Test scheduler provider that returns trampoline scheduler.
 */
public class TestSchedulerProvider implements SchedulerProvider {

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler mainThread() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler trampoline() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler from(Executor executor) {
        return Schedulers.trampoline();
    }
}
