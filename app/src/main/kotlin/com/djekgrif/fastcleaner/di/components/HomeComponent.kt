package com.djekgrif.fastcleaner.di.components

import com.djekgrif.fastcleaner.di.ActivityScope
import com.djekgrif.fastcleaner.ui.home.HomeActivity
import com.djekgrif.fastcleaner.ui.home.HomePresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import com.djekgrif.fastcleaner.ui.home.HomeContract
import com.djekgrif.fastcleaner.ui.home.HomeFragment

/**
 * Created by djek-grif on 5/22/17.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(HomeModule::class))
interface HomeComponent {
    fun inject(activity: HomeActivity)
    fun inject(fragment: HomeFragment)
}

@Module
class HomeModule(private val view: HomeContract.View) {
    @Provides fun provideView(): HomeContract.View  = view
    @Provides fun providePresenter() : HomeContract.Presenter = HomePresenter(view)
}