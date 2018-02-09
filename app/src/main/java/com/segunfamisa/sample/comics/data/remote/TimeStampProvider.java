package com.segunfamisa.sample.comics.data.remote;


import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Wrapper to provide the current time stamp.
 */
@Singleton
public class TimeStampProvider {

    @Inject
    public TimeStampProvider() {
    }

    /**
     * Get current time stamp
     *
     * @return - string value of the current time stamp.
     */
    public String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

}
