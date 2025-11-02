package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.packages.CreateCategoryRequest
import com.example.travelmarket.logic.data.models.request.packages.CreatePackageRequest
import com.example.travelmarket.logic.domain.models.Package
import com.example.travelmarket.logic.domain.models.PackageCategory

interface PackagesRepository {
    suspend fun getPackages(): NetworkResult<List<Package>>
    suspend fun getPackageById(id: Long): NetworkResult<Package>
    suspend fun createPackage(request: CreatePackageRequest): NetworkResult<Package>
    suspend fun updatePackage(id: Long, request: CreatePackageRequest): NetworkResult<Package>
    suspend fun deletePackage(id: Long): NetworkResult<Unit>
    suspend fun getCategories(): NetworkResult<List<PackageCategory>>
    suspend fun createCategory(request: CreateCategoryRequest): NetworkResult<PackageCategory>
    suspend fun deleteCategory(id: Long): NetworkResult<Unit>
}


/**
 * Repositorio de paquetes turísticos.
 *
 * USO EN APP MÓVIL USUARIO:
 *   - getPackages()        // ✅ Listar paquetes turísticos (GET)
 *   - getPackageById()     // ✅ Detalle de paquete (GET)
 *   - getCategories()      // ✅ Listar categorías de paquetes (GET)
 *
 * SOLO ADMIN PANEL (NO USAR EN VISTAS USUARIO):
 *   - createPackage()/updatePackage()/deletePackage()
 *   - createCategory()/deleteCategory()
 *
 * Estos métodos existen por arquitectura, sólo los usaría un panel de administración.
 */