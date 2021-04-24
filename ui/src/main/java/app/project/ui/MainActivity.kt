package app.project.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import app.project.ui.common.FragmentNavigator
import app.project.ui.common.TabHistory
import app.project.ui.common.TabManager
import app.project.ui.databinding.ActivityMainBinding
import com.airbnb.mvrx.BaseMvRxActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class MainActivity : BaseMvRxActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    private val navigator: FragmentNavigator by inject()
    private val tabHistory: TabHistory by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBottomBar.setOnNavigationItemSelectedListener(this)
        navigator.tabManager = TabManager(
            tabHistory, this
        )

        navigator.displayIntroductionAsNavRoot()
    }

    override fun onBackPressed() {
        navigator.goBackScreen()
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        navigator.supportNavigationUpTo()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigator.switchTab(item.itemId)
        return true
    }

}