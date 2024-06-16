package com.dacs3.shop.di

import com.dacs3.shop.repository.AddressRepository
import com.dacs3.shop.repository.AuthRepository
import com.dacs3.shop.repository.CartRepository
import com.dacs3.shop.repository.CategoryRepository
import com.dacs3.shop.repository.ColorRepository
import com.dacs3.shop.repository.CommentRepository
import com.dacs3.shop.repository.DataStoreRepository
import com.dacs3.shop.repository.OrderRepository
import com.dacs3.shop.repository.ProductRepository
import com.dacs3.shop.repository.SizeRepository
import com.dacs3.shop.repository.UploadImageRepository
import com.dacs3.shop.repository.UserRepository
import com.dacs3.shop.repository.VariantRepository
import com.dacs3.shop.ui.screens.account.AccountViewModel
import com.dacs3.shop.ui.screens.address.create.AddressCreateViewModel
import com.dacs3.shop.ui.screens.address.management.AddressManageViewModel
import com.dacs3.shop.ui.screens.address.update.AddressUpdateViewModel
import com.dacs3.shop.ui.screens.cart.CartViewModel
import com.dacs3.shop.ui.screens.category.CategoryViewModel
import com.dacs3.shop.ui.screens.category.details.CategoryDetailsViewModel
import com.dacs3.shop.ui.screens.category.management.CategoryManageViewModel
import com.dacs3.shop.ui.screens.category.update.CategoryUpdateViewModel
import com.dacs3.shop.ui.screens.checkout.CheckoutViewModel
import com.dacs3.shop.ui.screens.home.HomeViewModel
import com.dacs3.shop.ui.screens.login.LoginViewModel
import com.dacs3.shop.ui.screens.order.OrderViewModel
import com.dacs3.shop.ui.screens.product.create.CreateProductViewModel
import com.dacs3.shop.ui.screens.product.details.ProductDetailsViewModel
import com.dacs3.shop.ui.screens.product.management.ProductManageViewModel
import com.dacs3.shop.ui.screens.product.newproduct.ProductNewViewModel
import com.dacs3.shop.ui.screens.product.saleproduct.ProductSaleViewModel
import com.dacs3.shop.ui.screens.product.search.SearchDetailsViewModel
import com.dacs3.shop.ui.screens.register.SignUpViewModel
import com.dacs3.shop.ui.screens.search.SearchViewModel
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
        productRepository: ProductRepository,
        dataStoreRepository: DataStoreRepository,
        authRepository: AuthRepository,
        cartRepository: CartRepository
    ): HomeViewModel {
        return HomeViewModel(categoryRepository, productRepository, dataStoreRepository, authRepository, cartRepository)
    }

    @Provides
    fun provideProductDetailsViewModel(
        productRepository: ProductRepository,
        commentRepository: CommentRepository,
        authRepository: AuthRepository,
        userRepository: UserRepository,
        cartRepository: CartRepository
    ): ProductDetailsViewModel {
        return ProductDetailsViewModel(productRepository, commentRepository, authRepository, userRepository, cartRepository)
    }

    @Provides
    fun provideProductManageViewModel(
        productRepository: ProductRepository,
        variantRepository: VariantRepository
    ): ProductManageViewModel {
        return ProductManageViewModel(productRepository, variantRepository)
    }

    @Provides
    fun provideProductSaleViewModel(
        productRepository: ProductRepository
    ): ProductSaleViewModel {
        return ProductSaleViewModel(productRepository)
    }

    @Provides
    fun provideProductNewViewModel(
        productRepository: ProductRepository
    ): ProductNewViewModel {
        return ProductNewViewModel(productRepository)
    }

    @Provides
    fun provideCreateProductViewModel(
        productRepository: ProductRepository,
        colorRepository: ColorRepository,
        sizeRepository: SizeRepository,
        categoryRepository: CategoryRepository,
        uploadImageRepository: UploadImageRepository
    ): CreateProductViewModel {
        return CreateProductViewModel(
            productRepository, sizeRepository, colorRepository, categoryRepository, uploadImageRepository
        )
    }

    @Provides
    fun provideSearchDetailsViewModel(
        productRepository: ProductRepository
    ): SearchDetailsViewModel {
        return SearchDetailsViewModel(productRepository)
    }

    @Provides
    fun provideSearchViewModel() : SearchViewModel {
        return SearchViewModel()
    }

    @Provides
    fun provideCategoryManageViewModel(
        categoryRepository: CategoryRepository
    ) : CategoryManageViewModel {
        return CategoryManageViewModel(categoryRepository)
    }

    @Provides
    fun provideCategoryUpdateViewModel(
        categoryRepository: CategoryRepository,
        uploadImageRepository: UploadImageRepository
    ) : CategoryUpdateViewModel {
        return CategoryUpdateViewModel(categoryRepository, uploadImageRepository)
    }

    @Provides
    fun provideCartViewModel(
        cartRepository: CartRepository,
        authRepository: AuthRepository
    ) : CartViewModel {
        return CartViewModel(cartRepository, authRepository)
    }

    @Provides
    fun provideAddressManageViewModel(
        addressRepository: AddressRepository
    ) : AddressManageViewModel {
        return AddressManageViewModel(addressRepository)
    }

    @Provides
    fun provideAddressCreateViewModel(
        addressRepository: AddressRepository
    ) : AddressCreateViewModel {
        return AddressCreateViewModel(addressRepository)
    }

    @Provides
    fun provideAddressUpdateViewModel(
        addressRepository: AddressRepository
    ) : AddressUpdateViewModel {
        return AddressUpdateViewModel(addressRepository)
    }

    @Provides
    fun provideCheckoutViewModel(
        cartRepository: CartRepository,
        addressRepository: AddressRepository
    ) : CheckoutViewModel {
        return CheckoutViewModel(cartRepository, addressRepository)
    }

    @Provides
    fun provideOrderViewModel(
        orderRepository: OrderRepository,
        authRepository: AuthRepository
    ) : OrderViewModel {
        return OrderViewModel(orderRepository, authRepository)
    }

}