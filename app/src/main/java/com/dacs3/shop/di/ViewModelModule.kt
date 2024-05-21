package com.dacs3.shop.di

import com.dacs3.shop.repository.AuthRepository
import com.dacs3.shop.repository.CategoryRepository
import com.dacs3.shop.repository.DataStoreRepository
import com.dacs3.shop.repository.ProductRepository
import com.dacs3.shop.repository.UserRepository
import com.dacs3.shop.ui.screens.account.AccountViewModel
import com.dacs3.shop.ui.screens.category.CategoryViewModel
import com.dacs3.shop.ui.screens.category.details.CategoryDetailsViewModel
import com.dacs3.shop.ui.screens.home.HomeViewModel
import com.dacs3.shop.ui.screens.login.LoginViewModel
import com.dacs3.shop.ui.screens.product.details.ProductDetailsViewModel
import com.dacs3.shop.ui.screens.product.management.ProductManageViewModel
import com.dacs3.shop.ui.screens.register.SignUpViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideLoginViewModel(
        authRepository: AuthRepository,
        dataStoreRepository: DataStoreRepository
    ): LoginViewModel {
        return LoginViewModel(authRepository, dataStoreRepository)
    }

    @Provides
    fun provideSignUpViewModel(
        userRepository: UserRepository
    ): SignUpViewModel {
        return SignUpViewModel(userRepository)
    }

    @Provides
    fun provideAccountViewModel(
        authRepository: AuthRepository,
        dataStoreRepository: DataStoreRepository
    ): AccountViewModel {
        return AccountViewModel(authRepository, dataStoreRepository)
    }

    @Provides
    fun provideCategoryViewModel(
        categoryRepository: CategoryRepository
    ): CategoryViewModel {
        return CategoryViewModel(categoryRepository)
    }

    @Provides
    fun provideCategoryDetailsViewModel(
        categoryRepository: CategoryRepository,
        productRepository: ProductRepository
    ): CategoryDetailsViewModel {
        return CategoryDetailsViewModel(categoryRepository = categoryRepository, productRepository = productRepository)
    }

    @Provides
    fun provideHomeViewModel(
        categoryRepository: CategoryRepository,
        productRepository: ProductRepository
    ): HomeViewModel {
        return HomeViewModel(categoryRepository, productRepository)
    }

    @Provides
    fun provideProductDetailsViewModel(
        productRepository: ProductRepository
    ): ProductDetailsViewModel {
        return ProductDetailsViewModel(productRepository)
    }

    @Provides
    fun provideProductManageViewModel(
        productRepository: ProductRepository
    ): ProductManageViewModel {
        return ProductManageViewModel(productRepository)
    }
}