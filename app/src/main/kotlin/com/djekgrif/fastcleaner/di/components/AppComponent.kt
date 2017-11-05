package com.djekgrif.fastcleaner.di.components

import com.djekgrif.fastcleaner.App
import com.djekgrif.fastcleaner.di.modules.AppModule
import com.djekgrif.fastcleaner.di.modules.DataModule
import com.djekgrif.fastcleaner.ui.home.HomePresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by djek-grif on 5/22/17.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class))
interface AppComponent {
    fun inject(app: App)
    fun inject(homePresenter: HomePresenter)

    fun plus(module: HomeModule): HomeComponent
}