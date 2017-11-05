package com.djekgrif.fastcleaner.ui.home

import com.djekgrif.fastcleaner.App
import com.djekgrif.fastcleaner.di.components.HomeModule
import com.djekgrif.fastcleaner.ui.base.SingleFragmentActivity
import com.djekgrif.fastcleaner.ui.home.HomeContract
import com.djekgrif.fastcleaner.ui.home.HomeFragment

/**
 * Created by djek-grif on 5/19/17.
 */
class HomeActivity : SingleFragmentActivity<HomeContract.View>() {

    override fun onCreateFragment(): HomeFragment {
        return HomeFragment.newInstance()
    }

}