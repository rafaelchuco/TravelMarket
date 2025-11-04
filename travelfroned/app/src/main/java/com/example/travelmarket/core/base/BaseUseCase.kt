package com.example.travelmarket.core.base

// UseCase sin parámetros
abstract class BaseUseCase<Out> {
    abstract suspend operator fun invoke(): Out
}
