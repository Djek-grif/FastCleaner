package com.djekgrif.fastcleaner.ui.home

import com.djekgrif.fastcleaner.App
import com.djekgrif.fastcleaner.common.MemoryAnalyzer
import com.djekgrif.fastcleaner.ui.base.BasePresenterImpl
import javax.inject.Inject

/**
 * Created by djek-grif on 5/18/17.
 */
class HomePresenter(view: HomeContract.View) : BasePresenterImpl<HomeContract.View>(view), HomeContract.Presenter {

    @Inject lateinit var app: App
    private val memoryAnalyzer: MemoryAnalyzer

    init {
        App.instance.appComponent.inject(this)
        memoryAnalyzer = MemoryAnalyzer(app)
    }

    override fun onResume() {
        view.updateMemoryProgress(0, calculateMemory().toInt())
        view.updateStorageProgress(0, calculateStorage().toInt())
    }

    private fun calculateMemory(): Double {
        val availableMemory = memoryAnalyzer.getAvailableMemory()
        val totalMemory = memoryAnalyzer.getTotalMemory()
        return (totalMemory - availableMemory) / totalMemory.toDouble() * 100
    }

    private fun calculateStorage(): Long {
        val systemInfo = memoryAnalyzer.getSystemSpaceInfo()

        var availableBlocks: Long = systemInfo.first
        var totalBlocks: Long = systemInfo.second
        if (memoryAnalyzer.isSDCardInfoAvailable()) {
            val mSDCardInfo = memoryAnalyzer.getSDCardInfo()
            availableBlocks += mSDCardInfo.first
            totalBlocks += mSDCardInfo.second
        }
        return (((totalBlocks - availableBlocks) / totalBlocks.toDouble()) * 100).toLong()
    }

    override fun onClickSpeedUpButton() {
    }

    override fun onClickCleanButton() {
    }

    override fun onClickAppsButton() {
    }

    override fun onClickBootButton() {
    }
}