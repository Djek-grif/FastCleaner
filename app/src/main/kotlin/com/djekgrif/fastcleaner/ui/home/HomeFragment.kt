package com.djekgrif.fastcleaner.ui.home

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.djekgrif.fastcleaner.App
import com.djekgrif.fastcleaner.R
import com.djekgrif.fastcleaner.di.components.HomeModule
import com.djekgrif.fastcleaner.ui.base.BaseActivity
import com.djekgrif.fastcleaner.ui.base.BasePresenterFragment
import com.djekgrif.fastcleaner.ui.widgets.CustomProgress

/**
 * Created by djek-grif on 5/19/17.
 */
class HomeFragment : BasePresenterFragment<HomeContract.Presenter>(), HomeContract.View{

    private lateinit var storageProgress : CustomProgress
    private lateinit var ramProgress : CustomProgress
    private lateinit var speedUpBtn : View
    private lateinit var cleanBtn : View
    private lateinit var appsBtn : View
    private lateinit var bootBtn : View
    private lateinit var toolBar: Toolbar

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun injectComponent() {
        App.instance.appComponent.plus(HomeModule(this)).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_home, null)
        toolBar = view.findViewById(R.id.toolbar) as Toolbar
        speedUpBtn = view.findViewById(R.id.speed_up_btn)
        cleanBtn = view.findViewById(R.id.clean_btn)
        appsBtn = view.findViewById(R.id.apps_btn)
        bootBtn = view.findViewById(R.id.boot_btn)
        storageProgress = view.findViewById(R.id.storage_progress) as CustomProgress
        ramProgress = view.findViewById(R.id.ram_progress) as CustomProgress
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).setSupportActionBar(toolBar)
        speedUpBtn.setOnClickListener { presenter.onClickSpeedUpButton() }
        cleanBtn.setOnClickListener { presenter.onClickCleanButton() }
        bootBtn.setOnClickListener { presenter.onClickBootButton() }
        appsBtn.setOnClickListener { presenter.onClickAppsButton() }
    }

    override fun updateStorageProgress(from: Int, to: Int) {
        storageProgress.setProgressWithAnimation(from, to)
    }

    override fun updateMemoryProgress(from: Int, to: Int) {
        ramProgress.setProgressWithAnimation(from, to)
    }

    override fun isHandleBackPressed(): Boolean = false
}