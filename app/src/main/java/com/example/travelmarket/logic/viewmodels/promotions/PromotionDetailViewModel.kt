package com.example.travelmarket.logic.viewmodels.promotions

import com.example.travelmarket.core.base.BaseViewModel
import com.example.travelmarket.logic.domain.models.Promotion
import com.example.travelmarket.logic.domain.usecases.promotions.GetPromotionDetailUseCase

class PromotionDetailViewModel(
    private val getPromotionDetailUseCase: GetPromotionDetailUseCase
) : BaseViewModel<Promotion>() {

    fun getPromotionDetail(promotionId: Int) {
        executeOperation {
            getPromotionDetailUseCase(promotionId)
        }
    }
}