package com.djekgrif.fastcleaner.ui.base

/**
 * Created by djek-grif on 5/18/17.
 */
abstract class BasePresenterImpl<out V> (val view : V): BasePresenter {
    override fun subscribe(){}
    override fun unSubscribe(){}
}