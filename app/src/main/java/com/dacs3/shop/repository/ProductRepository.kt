package com.dacs3.shop.repository

import com.dacs3.shop.model.Product
import com.dacs3.shop.model.ProductDto
import com.dacs3.shop.network.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productService: ProductService) {
    suspend fun getAllProducts() = productService.getAllProducts()
    suspend fun getProductById(id: Int) = productService.getProductById(id)
    suspend fun searchProduct(query: String) = productService.searchProduct(query)
    suspend fun getProductByCategory(id: Int) = productService.getProductByCategory(id)
    suspend fun addProduct(productDto: ProductDto) = productService.addProduct(productDto)
    suspend fun updateProduct(id: Int, product: Product) = productService.updateProduct(id, product)
    suspend fun deleteProduct(id: Int) = productService.deleteProduct(id)
}