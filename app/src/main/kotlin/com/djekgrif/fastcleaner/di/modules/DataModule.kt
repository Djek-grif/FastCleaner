package com.djekgrif.fastcleaner.di.modules

import com.djekgrif.fastcleaner.data.DataRepository
import com.djekgrif.fastcleaner.data.DataRepositoryImpl
import dagger.Module
import dagger.Provides

/**
 * Created by djek-grif on 5/22/17.
 */
@Module(includes = arrayOf(AppModule::class))
class DataModule{

    @Provides
    fun provideDataRepository(): DataRepository = DataRepositoryImpl()
}