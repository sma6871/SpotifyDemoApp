package com.spotify.demo.ui.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    val bag = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }
}