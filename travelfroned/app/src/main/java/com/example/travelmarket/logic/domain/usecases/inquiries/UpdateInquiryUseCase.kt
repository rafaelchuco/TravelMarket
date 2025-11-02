package com.example.travelmarket.logic.domain.usecases.inquiries

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.inquiries.UpdateInquiryRequest
import com.example.travelmarket.logic.domain.models.Inquiry
import com.example.travelmarket.logic.domain.repositories.InquiriesRepository
import javax.inject.Inject

class UpdateInquiryUseCase @Inject constructor(
    private val repository: InquiriesRepository
) {
    suspend operator fun invoke(id: Long, request: UpdateInquiryRequest): NetworkResult<Inquiry> {
        return repository.updateInquiry(id, request)
    }
}
