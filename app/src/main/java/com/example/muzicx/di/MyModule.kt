package com.example.muzicx.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.muzicx.DataRepo
import com.example.muzicx.api.ApiServices
import com.example.muzicx.room.RoomDb
import com.example.muzicx.viewmodel.ParentViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyModule{

//    @Provides
//    @Singleton
//    fun provideParentViewModel(@ApplicationContext cxt: Context) : ParentViewModel{
//        return ViewModelProvider(cxt
//        ).get(ParentViewModel::class.java)
//    }

    @Provides
    @Singleton
    fun provideRoomInstance(
        @ApplicationContext context: Context,
        populateDataCallback: RoomDb.PopulateDataClass
    ) : RoomDb{
        return Room.databaseBuilder(context, RoomDb::class.java, "muzicx")
            .fallbackToDestructiveMigration()
            .addCallback(populateDataCallback)
            .build()
    }

    @Provides
    fun provideDaoInstance(roomDb: RoomDb) = roomDb.getDao()


    @Provides
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob())

    @Provides
    fun provideHttpClient(): OkHttpClient {
        // declaring our interceptor to be used with the client
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        // declaring our client , useful to show log in logcat
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Provides
    fun provideRetrofitInstance(
        httpClient: OkHttpClient
    ) : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.deezer.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    fun provideApiServices(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)

    @Provides
    fun provideRepo(services: ApiServices) = DataRepo(services)
}