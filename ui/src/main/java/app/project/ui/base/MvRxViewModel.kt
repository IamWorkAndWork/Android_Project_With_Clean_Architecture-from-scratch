package app.project.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import app.project.ui.TAG_APP_DEBUG
import app.project.ui.navigation.ScreenNavigator
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState

abstract class MvRxViewModel<State : MvRxState>(
    initialState: State,
    val navigator: ScreenNavigator
) : BaseMvRxViewModel<State>(initialState, debugMode = true) {

    val errorMutableLiveData: MutableLiveData<Throwable> = MutableLiveData()

    init {
        Log.d(TAG_APP_DEBUG, "Initializing $this")
        logStateChanges()
    }

    protected fun handleError(throwable: Throwable){
        errorMutableLiveData.value = throwable
    }



}