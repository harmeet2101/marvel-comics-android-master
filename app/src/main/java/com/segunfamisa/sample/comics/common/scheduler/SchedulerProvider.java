package com.segunfamisa.sample.comics.common.scheduler;


import java.util.concurrent.Executor;

import io.reactivex.Scheduler;

/**
 * Interface defining the RxJava Scheduler provider.
 */
public interface SchedulerProvider {

    Scheduler io();

    Scheduler mainThread();

    Scheduler computation();

    Scheduler trampoline();

    Scheduler from(Executor executor);

}
