package mx.diossa.cashbackapp.domain.usecases

import mx.diossa.cashbackapp.data.remote.dto.EmployeeCarryData
import mx.diossa.cashbackapp.data.repository.CashbackRepository
import javax.inject.Inject

class CarryUseCase @Inject constructor(
    private val repository: CashbackRepository
) {
    suspend operator fun invoke(employeeId: Int, quantity: Int): Result<EmployeeCarryData>{
        return repository.postCarryData(employeeId, quantity)
    }
}