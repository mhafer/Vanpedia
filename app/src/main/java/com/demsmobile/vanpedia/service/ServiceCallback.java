package com.demsmobile.vanpedia.service;

import com.demsmobile.vanpedia.data.Channel;

/**
 * Created by Redlive on 2016-02-07.
 */
public interface ServiceCallback<T> {
    void serviceSuccess(T response);
    void serviceFailure(Exception exception);
}
