package app.project.domain.usecase

import org.koin.dsl.module

val usecaseModule = module {

    single {
        initializeCommunicationUseCase(
            get()
        )
    }

    single {
        GetCurrentUserAccoountSyncUseCase(
            userAccountRepository = get(),
        )
    }

    single {
        SignUpUseCase(
            userAccountRepository = get(),
            postExecutionThread = get()
        )
    }

    single {
        SignInUseCase(
            userAccountRepository = get(),
            postExecutionThread = get(),
        )
    }

    single {
        SignOutUseCase(
            userAccountRepository = get(),
            postExecutionThread = get(),
        )
    }

    single {
        GetMessageUseCase(
            postExecutionThread = get(),
            messageRepository = get()
        )
    }

}