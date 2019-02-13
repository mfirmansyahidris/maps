package com.dev.fi.maps

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 ****************************************
created by -manca-
.::manca.fi@gmail.com ::.
 ****************************************
 */

class MapsPresenter(private val view: MapsView,  private var mCompositeDisposable: CompositeDisposable? = null) {

    fun getRoutes(map: LinkedHashMap<String, String?>) {
        view.onProcess()
        val postService = RetrofitBuilder.create()
        mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable?.add(
                postService.getRoutes(map)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ it ->
                            if (it.body()?.status == "OK") {
                                view.getResult(it.body()!!)
                            } else {
                                view.getError(it.body()?.status)
                            }
                        }, {
                            view.getError(it.toString())
                        })
        )
    }
}