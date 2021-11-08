package com.fourthwall.android

import org.mockito.Mockito

/**
 * @author Santiago Carrillo
 * 8/11/21.
 */
object MockitoHelper {

    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}