package com.example.travelmarket.logic.domain.usecases.inquiries

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Inquiry
import com.example.travelmarket.logic.domain.repositories.InquiriesRepository
import javax.inject.Inject

class GetInquiriesUseCase @Inject constructor(
    private val repository: InquiriesRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Inquiry>> {
        return repository.getInquiries()
    }
}
