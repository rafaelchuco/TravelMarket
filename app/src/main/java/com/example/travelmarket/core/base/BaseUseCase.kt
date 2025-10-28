package com.example.travelmarket.core.base

abstract class BaseUseCase<out Type> {
    abstract suspend operator fun invoke(): Type
}

abstract class BaseUseCaseWithParams<in Params, out Type> {
    abstract suspend operator fun invoke(params: Params): Type
}