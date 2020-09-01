package pl.nojkir.roomdatabase.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.nojkir.roomdatabase.data.ExerciseDataBase

import pl.nojkir.roomdatabase.other.Constants
import pl.nojkir.roomdatabase.other.Constants.EXERCISE_DATABASE_NAME

import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)

object AppModule {

    @Singleton
    @Provides

    fun provideExerciseDataBase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ExerciseDataBase::class.java,
        EXERCISE_DATABASE_NAME
    )   .fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun providesExerciseDao(db: ExerciseDataBase) = db.getExerciseDao()


}


