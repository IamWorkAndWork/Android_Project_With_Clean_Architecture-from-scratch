package app.project.data

import app.project.data.mapper.mapperModule
import app.project.data.repository.repositoryModule

val dataModule = listOf(
    mapperModule,
    repositoryModule
)