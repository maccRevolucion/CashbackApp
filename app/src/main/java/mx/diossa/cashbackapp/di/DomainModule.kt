package mx.diossa.cashbackapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mx.diossa.cashbackapp.data.repository.UserRepository
import mx.diossa.cashbackapp.domain.usecases.ValidateLoginUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideValidateLoginUseCase(repository: UserRepository): ValidateLoginUseCase {
        return ValidateLoginUseCase(repository)
    }
}