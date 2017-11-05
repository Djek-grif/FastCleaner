package com.djekgrif.fastcleaner.di.modules

import android.content.Context
import com.djekgrif.fastcleaner.App
import com.djekgrif.fastcleaner.di.AppContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by djek-grif on 5/22/17.
 */
@Module
class AppModule(private val app : App) {

    @Provides
    @Singleton
    fun provideApplication(): App = app

    @Provides
    @AppContext
    internal fun provideContext(): Context = app
}