package com.djekgrif.fastcleaner.ui.home

import com.djekgrif.fastcleaner.ui.base.BasePresenter
import com.djekgrif.fastcleaner.ui.base.BaseView

/**
 * Created by djek-grif on 5/19/17.
 */
interface HomeContract {

    interface View : BaseView{
        fun updateStorageProgress(from: Int, to: Int)
        fun updateMemoryProgress(from: Int, to:Int)
    }

    interface Presenter : BasePresenter {
        fun onClickSpeedUpButton()
        fun onClickCleanButton()
        fun onClickAppsButton()
        fun onClickBootButton()
    }
}