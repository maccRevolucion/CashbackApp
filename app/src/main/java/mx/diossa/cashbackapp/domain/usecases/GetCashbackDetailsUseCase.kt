package mx.diossa.cashbackapp.domain.usecases

import mx.diossa.cashbackapp.data.repository.CashbackRepository
import javax.inject.Inject

class GetCashbackDetailsUseCase @Inject constructor(
    private val cashbackRepository: CashbackRepository
) {
    suspend operator fun invoke(id: Int) = cashbackRepository.getCashbackDetails(id)
}