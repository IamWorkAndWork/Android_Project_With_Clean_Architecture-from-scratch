package app.project.ui.common

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import app.project.ui.R
import app.project.ui.navigation.ScreenNavigator
import com.airbnb.mvrx.MvRx

class FragmentNavigator : ScreenNavigator {

    lateinit var tabManager: TabManager

    private fun navigateTo(
        @IdRes actionId: Int,
        arg: Parcelable? = null,
        allowSeveralInstances: Boolean = false
    ) {
        if (!allowSeveralInstances &&
            tabManager.currentController.popBackStack(actionId, false)
        ) {
            return
        }

        val bundle = arg?.let {
            Bundle().apply {
                putParcelable(MvRx.KEY_ARG, it)
            }
        }
        tabManager.currentController.navigate(actionId, bundle)
    }

    private fun setNavRoot(@IdRes actionId: Int, arg: Parcelable? = null) {
        val bundle = arg?.let {
            Bundle().apply {
                putParcelable(MvRx.KEY_ARG, it)
            }
        }
        val graph = tabManager.currentController.navInflater.inflate(R.navigation.graph_nav_main)

        graph.startDestination = actionId

        tabManager.currentController.setGraph(graph, bundle)
    }

    override fun goBackScreen() {
        tabManager.onBackPress()
    }

    fun supportNavigationUpTo() {
        tabManager.supportNavigationUpTo()
    }

    fun switchTab(tabId: Int, addToHistory: Boolean = true) {
        tabManager.switchTab(tabId, addToHistory)
    }

    override fun resetMainScreen() {
        tabManager.resetMainActivity()
    }

    override fun displayNewsFeedAsNavRoot() {
        setNavRoot(R.id.fragmentNewsFeed)
    }

    override fun navigateToSignIn() {
        navigateTo(R.id.fragmentSignIn)
    }

    override fun navigateToSignUp() {
        navigateTo(R.id.fragmentSignUp)
    }

    override fun displayGettingStartedAsNavRoot() {
        setNavRoot(R.id.fragmentIntroduction)
    }

    override fun displayIntroductionAsNavRoot() {
        setNavRoot(R.id.fragmentGettingStarted)
    }

}