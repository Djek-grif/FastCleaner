package com.djekgrif.fastcleaner.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.djekgrif.fastcleaner.utils.Logger

/**
 * Created by djek-grif on 5/19/17.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("CREATE activity:" + this.localClassName, Logger.LIFECYCLE)
    }

}