package com.segunfamisa.sample.comics.util;

import android.content.Context;

import com.segunfamisa.sample.comics.R;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ErrorStringMapperTest {

    @Mock
    private Context context;
    private ErrorStringMapper errorMapper;

    /**
     * Set Up.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        errorMapper = new ErrorStringMapper(context);
    }

    @Test
    public void getErrorMessage_HttpException() {
        // given that we have an httpexception
        Throwable throwable = mock(HttpException.class);

        // given that our context returns the appropriate string message
        when(context.getString(R.string.error_msg_server)).thenReturn("Server");

        // when we try to get the error message
        String errorMessage = errorMapper.getErrorMessage(throwable);

        // then assert that the error message is "Server" as expected
        assertEquals("Server", errorMessage);
    }

    @Test
    public void getErrorMessage_IoException() {
        // given that we have an io exception
        Throwable throwable = mock(IOException.class);

        // given that context returns appropriate string message
        when(context.getString(R.string.error_msg_network)).thenReturn("Network");

        // when we try to ge the error message
        String errorMessage = errorMapper.getErrorMessage(throwable);

        // then assert that the error message is "Network" as expected
        assertEquals("Network", errorMessage);
    }

    @Test
    public void getErrorMessage_UnknownHostException() {
        // given that we have an unknown host exception
        Throwable throwable = mock(UnknownHostException.class);

        // given that context returns appropriate string message
        when(context.getString(R.string.error_msg_network)).thenReturn("Network");

        // when we try to ge the error message
        String errorMessage = errorMapper.getErrorMessage(throwable);

        // then assert that the error message is "Network" as expected
        assertEquals("Network", errorMessage);
    }

    @Test
    public void getErrorMessage_OtherException() {
        // given that we have any other exception (say RuntimeException)
        Throwable throwable = mock(RuntimeException.class);

        // given that context returns the appropriate string message
        when(context.getString(R.string.error_msg_network_generic)).thenReturn("Random error");

        // when we try to get the error message
        String errorMesssage = errorMapper.getErrorMessage(throwable);

        // then assert that the error message is "Random error" as expceted
        assertEquals("Random error", errorMesssage);
    }

}