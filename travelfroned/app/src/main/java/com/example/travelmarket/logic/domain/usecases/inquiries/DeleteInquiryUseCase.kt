package com.example.travelmarket.logic.domain.usecases.inquiries

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.repositories.InquiriesRepository
import javax.inject.Inject

class DeleteInquiryUseCase @Inject constructor(
    private val repository: InquiriesRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Unit> {
        return repository.deleteInquiry(id)
    }
}
