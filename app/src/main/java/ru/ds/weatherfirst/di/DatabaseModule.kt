package ru.ds.weatherfirst.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ds.weatherfirst.data.db.HistoryDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        HistoryDatabase::class.java,
        "new_db"
    ).createFromAsset("database/new.db").build()

    @Singleton
    @Provides
    fun provideDao(database: HistoryDatabase) = database.historyDao()
}