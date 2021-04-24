package app.project.ui.base

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController

class MvRxEpoxyController(
    val buildMdodelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {

    override fun buildModels() {
        buildMdodelsCallback()
    }

}