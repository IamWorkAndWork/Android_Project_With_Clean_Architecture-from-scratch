package app.project.ui.base

import android.os.Bundle
import android.util.Log
import android.view.View
import app.project.ui.R
import app.project.ui.TAG_APP_DEBUG
import app.project.ui.common.HiddenBottomBar
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRxState
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseFragment : BaseMvRxFragment() {

    abstract val viewModel: MvRxViewModel<out MvRxState>

    private var bottomBar: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_APP_DEBUG, "onCreate() $this")
        bottomBar = activity?.findViewById(R.id.navBottomBar)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG_APP_DEBUG, "onStart() $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG_APP_DEBUG, "onResume() $this")

        setBottomBarVisibility()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG_APP_DEBUG, "onPause() $this")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG_APP_DEBUG, "onPause() $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_APP_DEBUG, "onDestroy() $this")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG_APP_DEBUG, "onDetach() $this")
    }

    private fun setBottomBarVisibility() {
        if (this is HiddenBottomBar) bottomBar?.visibility = View.GONE
        else bottomBar?.visibility = View.VISIBLE
    }

}