package com.diiser.network.config

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://deezerdevs-deezer.p.rapidapi.com"
private const val DEEZER_BASE_URL = "https://api.deezer.com/"

class NetworkConfig(private val context: Context, baseUrl: String = BASE_URL) {

    val apiService: Api by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(ChuckInterceptor(context))
            .addInterceptor { chain ->
                val request = chain.request()
                chain.proceed(
                    request.newBuilder()
                        .addHeader(
                            "x-rapidapi-host",
                            "deezerdevs-deezer.p.rapidapi.com"
                        ).addHeader(
                            "x-rapidapi-key", "e39d59c328mshc259ec0bca8a2c0p114221jsn712cd6596548"
                        ).build()
                )
            }.build()
    }
}

class DeezerNetworkConfig(private val context: Context, baseUrl: String = DEEZER_BASE_URL) {

    val apiService: DeezerApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(DeezerApi::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(ChuckInterceptor(context)).build()
    }
}
