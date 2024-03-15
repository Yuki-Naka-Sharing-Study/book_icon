package com.example.book_icon


import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BookIconModule {

    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext appContext: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContext)
    }

}