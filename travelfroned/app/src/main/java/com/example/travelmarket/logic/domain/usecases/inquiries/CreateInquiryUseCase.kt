package com.example.travelmarket.logic.domain.usecases.inquiries

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.inquiries.CreateInquiryRequest
import com.example.travelmarket.logic.domain.models.Inquiry
import com.example.travelmarket.logic.domain.repositories.InquiriesRepository
import javax.inject.Inject

class CreateInquiryUseCase @Inject constructor(
    private val repository: InquiriesRepository
) {
    suspend operator fun invoke(request: CreateInquiryRequest): NetworkResult<Inquiry> {
        return repository.createInquiry(request)
    }
}
