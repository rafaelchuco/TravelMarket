package com.example.travelmarket.logic.domain.usecases.inquiries

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Inquiry
import com.example.travelmarket.logic.domain.repositories.InquiriesRepository
import javax.inject.Inject

class GetInquiryByIdUseCase @Inject constructor(
    private val repository: InquiriesRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Inquiry> {
        return repository.getInquiryById(id)
    }
}
