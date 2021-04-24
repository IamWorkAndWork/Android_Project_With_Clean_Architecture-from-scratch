package app.project.ui.common

import android.content.Intent
import android.view.View
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import app.project.ui.MainActivity
import app.project.ui.R
import org.koin.core.KoinComponent

class TabManager(
    private val tabHistory: TabHistory,
    private val mainActivity: MainActivity
) : KoinComponent {

    private val startDestinations = mapOf(
        R.id.menu_news_feed to R.id.fragmentNewsFeed,
        R.id.menu_connection to R.id.fragmentConnection,
        R.id.menu_post to R.id.fragmentPost,
        R.id.menu_notification to R.id.fragmentNotification,
        R.id.menu_profile to R.id.fragmentProfile
    )

    private val navNewsFeedController by lazy {
        mainActivity.findNavController(R.id.newsFeedTab).apply {
            graph = navInflater.inflate(R.navigation.graph_nav_main).apply {
                startDestination = startDestinations.getValue(R.id.menu_news_feed)
            }
        }
    }

    private val navConnectionController by lazy {
        mainActivity.findNavController(R.id.connectionTab).apply {
            graph = navInflater.inflate(R.navigation.graph_nav_main).apply {
                startDestination = startDestinations.getValue(R.id.menu_connection)
            }
        }
    }

    private val navPostFeedController by lazy {
        mainActivity.findNavController(R.id.postTab).apply {
            graph = navInflater.inflate(R.navigation.graph_nav_main).apply {
                startDestination = startDestinations.getValue(R.id.menu_post)
            }
        }
    }

    private val navNotificationController by lazy {
        mainActivity.findNavController(R.id.notificationTab).apply {
            graph = navInflater.inflate(R.navigation.graph_nav_main).apply {
                startDestination = startDestinations.getValue(R.id.menu_notification)
            }
        }
    }

    private val navProfileController by lazy {
        mainActivity.findNavController(R.id.profileTab).apply {
            graph = navInflater.inflate(R.navigation.graph_nav_main).apply {
                startDestination = startDestinations.getValue(R.id.menu_profile)
            }
        }
    }

    private val newsFeedTabContainer by lazy {
        mainActivity.binding.newsFeedTabContainer
    }
    private val connectionTabContainer by lazy {
        mainActivity.binding.connectionTabContainer
    }
    private val postTabContainer by lazy {
        mainActivity.binding.postTabContainer
    }
    private val notificationTabContainer by lazy {
        mainActivity.binding.notificationTabContainer
    }
    private val profileTabContainer by lazy {
        mainActivity.binding.profileTabContainer
    }

    private val navBottomBar by lazy {
        mainActivity.binding.navBottomBar
    }

    var currentTabId: Int = R.id.menu_news_feed

    var currentController: NavController = navNewsFeedController

    init {
        tabHistory.apply {
            add(currentTabId)
        }
    }

    fun supportNavigationUpTo() {
        currentController.navigateUp()
    }

    fun onBackPress() {
        currentController.let {
            if (it.currentDestination == null ||
                it.currentDestination?.id == startDestinations.getValue(currentTabId)
            ) {
                if (tabHistory.size > 1) {
                    val tabId = tabHistory.removePrevious()
                    //switchTab
                    switchTab(tabId, false)
                    navBottomBar.menu.findItem(tabId).isChecked = true
                } else {
                    mainActivity.finish()
                }
            }

            it.popBackStack()
        }
    }

    fun switchTab(tabId: Int, addToHistory: Boolean = true) {
        currentTabId = tabId

        when (tabId) {
            R.id.menu_news_feed -> {
                currentController = navNewsFeedController
                invisibleTabContainer(newsFeedTabContainer)
            }
            R.id.menu_connection -> {
                currentController = navConnectionController
                invisibleTabContainer(connectionTabContainer)
            }
            R.id.menu_post -> {
                currentController = navPostFeedController
                invisibleTabContainer(postTabContainer)
            }
            R.id.menu_notification -> {
                currentController = navNotificationController
                invisibleTabContainer(notificationTabContainer)
            }
            R.id.menu_profile -> {
                currentController = navProfileController
                invisibleTabContainer(profileTabContainer)
            }
        }

        if (addToHistory) {
            if (tabHistory.contains(tabId) && tabId != R.id.menu_news_feed) {
                tabHistory.remove(tabId)
            }
            tabHistory.add(tabId)
        }
    }

    fun resetMainActivity() {
        mainActivity.finish()
        mainActivity.startActivity(Intent(mainActivity, MainActivity::class.java))
    }

    private fun invisibleTabContainer(container: View?) {
        newsFeedTabContainer.isInvisible = true
        connectionTabContainer.isInvisible = true
        postTabContainer.isInvisible = true
        notificationTabContainer.isInvisible = true
        profileTabContainer.isInvisible = true

        container?.isInvisible = false
    }


}