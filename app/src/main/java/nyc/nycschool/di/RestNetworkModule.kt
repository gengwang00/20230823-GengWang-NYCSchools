package nyc.nycschool.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import nyc.nycschool.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import nyc.nycschool.network.api.SchoolApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestNetworkModule {

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder();
        okHttpClient.callTimeout(60, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.readTimeout(60, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(interceptor)
        return okHttpClient.build();
    }

    @Singleton
    @Provides
    fun provideRestAdapter(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retrofit = Retrofit.Builder().baseUrl("https://data.cityofnewyork.us").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): SchoolApiService {
        return retrofit.create(SchoolApiService::class.java)
    }

    @Provides
    fun provideIDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}