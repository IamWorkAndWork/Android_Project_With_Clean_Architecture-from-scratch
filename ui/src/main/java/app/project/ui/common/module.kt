package app.project.ui.common

import app.project.ui.navigation.ScreenNavigator
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {

    single {
        FragmentNavigator()
    } bind ScreenNavigator::class

    factory {
        TabHistory()
    }

}