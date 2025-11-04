package com.example.travelmarket.core.base

// UseCase con parámetros
abstract class BaseUseCaseWithParams<in P, Out> {
    abstract suspend operator fun invoke(params: P): Out
}
