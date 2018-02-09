package com.segunfamisa.sample.comics.data.di;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Scope for each screen of the app.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ScreenScoped {
}
