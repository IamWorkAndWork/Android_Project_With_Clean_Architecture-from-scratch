package app.project.domain.executer

import io.reactivex.Scheduler

interface PostExecutionThread {
    fun getScheduler(): Scheduler
}