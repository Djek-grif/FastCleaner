package com.djekgrif.fastcleaner

import android.app.Application
import com.djekgrif.fastcleaner.di.components.AppComponent
import com.djekgrif.fastcleaner.di.components.DaggerAppComponent
import com.djekgrif.fastcleaner.di.modules.AppModule
import com.djekgrif.fastcleaner.utils.DelegatesExt

/**
 * Created by djek-grif on 5/20/17.
 */
class App : Application(){

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue<App>()
    }

    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        appComponent.inject(this)
    }
}