package com.segunfamisa.sample.comics.util;


import android.content.Context;

import com.segunfamisa.sample.comics.R;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.HttpException;

/**
 * Maps throwable errors into strings.
 */
public class ErrorStringMapper {

    private final Context context;

    @Inject
    public ErrorStringMapper(Context context) {
        this.context = context;
    }

    /**
     * Resolves throwable error into likely human readable strings.
     * @param throwable - throwable
     * @return - the mapped string error
     */
    public String getErrorMessage(Throwable throwable) {
        if (throwable instanceof HttpException) {
            // We had non-2XX http error
            return context.getString(R.string.error_msg_server);
        } else if (throwable instanceof IOException) {
            // A network or conversion error happened
            return context.getString(R.string.error_msg_network);
        } else {
            // Generic error handling
            return context.getString(R.string.error_msg_network_generic);
        }
    }

}
