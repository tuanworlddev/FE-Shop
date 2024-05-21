package com.dacs3.shop.repository

import com.dacs3.shop.model.Category
import com.dacs3.shop.network.CategoryService
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryService: CategoryService) {
    suspend fun getAllCategories() = categoryService.getAllCategories()
    suspend fun getCategoryById(id: Int) = categoryService.getCategoryById(id)
    suspend fun addCategory(category: Category) = categoryService.addCategory(category)
    suspend fun updateCategory(id: Int, category: Category) = categoryService.updateCategory(id, category)
    suspend fun deleteCategory(id: Int) = categoryService.deleteCategory(id)
}