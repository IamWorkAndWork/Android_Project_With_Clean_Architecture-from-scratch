package app.project.ui

import app.project.domain.executer.PostExecutionThread
import app.project.ui.common.commonModule
import app.project.ui.mapper.mapperModule
import org.koin.dsl.module

const val TAG_APP_DEBUG = "app.debug"

val uiModule = listOf(
    module {
        single<PostExecutionThread> {
            UIThread()
        }
    },
    commonModule,
    mapperModule
)
