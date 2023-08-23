package nyc.nycschool.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import nyc.nycschool.network.api.NycSchoolApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
        return retrofit;
    }

    @Singleton
    @Provides
    fun provideSchoolApiService(retrofit: Retrofit): NycSchoolApiService {
        return retrofit.create(NycSchoolApiService::class.java)
    }

    @Provides
    @IoScheduler
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @MainScheduler
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()
}