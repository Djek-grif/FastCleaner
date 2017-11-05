package com.djekgrif.fastcleaner.ui.base

/**
 * Created by djek-grif on 5/19/17.
 */
interface BasePresenter {
    fun onResume()
    fun subscribe()
    fun unSubscribe()
}