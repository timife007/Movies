package com.timife.movies.data.remote.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.timife.movies.BuildConfig
import com.timife.movies.data.remote.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiService(app: Application): MoviesApi {
        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Auth-Token", BuildConfig.API_KEY)
                .build()
            chain.proceed(request)
        }
        val chuckerInterceptor = ChuckerInterceptor.Builder(app)
            .collector(
                ChuckerCollector(
                    app,
                    showNotification = true,
                    retentionPeriod = RetentionManager.Period.ONE_WEEK
                )
            )
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(chuckerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

}