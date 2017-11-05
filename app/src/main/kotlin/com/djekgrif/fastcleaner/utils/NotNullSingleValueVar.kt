package com.djekgrif.fastcleaner.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by djek-grif on 5/22/17.
 */
class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("${property.name} " + "not initialized")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("${property.name} already initialized")
    }

}