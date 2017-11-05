package com.djekgrif.fastcleaner.utils

import com.djekgrif.fastcleaner.utils.NotNullSingleValueVar
import kotlin.properties.ReadWriteProperty

/**
 * Created by djek-grif on 5/22/17.
 */

object DelegatesExt {
    fun <T>notNullSingleValue(): ReadWriteProperty<Any?, T> = NotNullSingleValueVar()
}