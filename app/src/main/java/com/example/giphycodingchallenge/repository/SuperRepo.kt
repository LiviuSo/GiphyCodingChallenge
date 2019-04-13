package com.example.giphycodingchallenge.repository

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base class for all repositories providing common functionality such as Disposable management.
 */
open class SuperRepo {
    private val compositeDisposable = CompositeDisposable()

    /**
     * Used by subtypes to add a disposable to be managed by this instance.
     */
    protected fun add(disposable: Disposable) = compositeDisposable.add(disposable)

    /**
     * Closes all resources associated with this instance. After invoking this function the
     * instance should be considered unusable. The base implementation simply clears all managed
     * disposables. Subtypes can override to provide additional functionality.
     */
    @CallSuper
    open fun close() = compositeDisposable.clear()
}