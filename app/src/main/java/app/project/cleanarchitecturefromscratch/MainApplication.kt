package app.project.cleanarchitecturefromscratch

import androidx.multidex.MultiDexApplication
import app.project.data.dataModule
import app.project.domain.domainModule
import app.project.domain.usecase.base.SynchronousUseCase
import app.project.domain.usecase.initializeCommunicationUseCase
import app.project.ui.uiModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger

class MainApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger(PrintLogger(Level.DEBUG))
            androidContext(this@MainApplication)
            modules(dataModule)
            modules(domainModule)
            modules(uiModule)
        }

        val initializeCommunicationUsecase: initializeCommunicationUseCase by inject()
        initializeCommunicationUsecase.execute(Unit, object : SynchronousUseCase.Callback<Unit> {
            override fun onSuccess(result: Unit) {

            }

            override fun onError(throwable: Throwable) {
            }
        })

    }
}