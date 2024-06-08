package com.dacs3.shop.di

import com.dacs3.shop.interceptor.AuthInterceptor
import com.dacs3.shop.network.AuthService
import com.dacs3.shop.network.CategoryService
import com.dacs3.shop.network.ColorService
import com.dacs3.shop.network.ProductService
import com.dacs3.shop.network.SizeService
import com.dacs3.shop.network.UserService
import com.dacs3.shop.network.VariantService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.80.210:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideVariantService(retrofit: Retrofit): VariantService {
        return retrofit.create(VariantService::class.java)
    }

    @Provides
    @Singleton
    fun provideColorService(retrofit: Retrofit): ColorService {
        return retrofit.create(ColorService::class.java)
    }

    @Provides
    @Singleton
    fun provideSizeService(retrofit: Retrofit): SizeService {
        return retrofit.create(SizeService::class.java)
    }
}