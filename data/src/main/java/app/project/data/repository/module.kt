package app.project.data.repository

import app.project.domain.repository.CommunicationRepository
import app.project.domain.repository.MessageRepository
import app.project.domain.repository.UserAccountRepository
import org.koin.dsl.bind
import org.koin.dsl.module

const val TAG = "dataLayer"

val repositoryModule = module {

    single<CommunicationRepository> {
        CommunicationRepositoryImpl(
            context = get()
        )
    }

    single {
        UserAccountRepositoryImpl(
            context = get(),
            parseUserMapper = get()
        )
    } bind UserAccountRepository::class

    single {
        MessageRepositoryImpl(
            context = get(),
            messageParseObjectMapper = get()
        )
    } bind MessageRepository::class

}