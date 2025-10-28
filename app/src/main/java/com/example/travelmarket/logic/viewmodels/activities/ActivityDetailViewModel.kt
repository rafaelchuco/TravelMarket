package com.example.travelmarket.logic.viewmodels.activities

import com.example.travelmarket.core.base.BaseViewModel
import com.example.travelmarket.logic.domain.models.Activity
import com.example.travelmarket.logic.domain.usecases.activities.GetActivityDetailUseCase

class ActivityDetailViewModel(
    private val getActivityDetailUseCase: GetActivityDetailUseCase
) : BaseViewModel<Activity>() {

    fun getActivityDetail(activityId: Int) {
        executeOperation {
            getActivityDetailUseCase(activityId)
        }
    }
}