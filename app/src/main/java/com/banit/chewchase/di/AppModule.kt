package com.banit.chewchase.di

import android.content.Context
import androidx.room.Room
import com.banit.chewchase.data.AppDatabase
import com.banit.chewchase.data.AppInitializer
import com.banit.chewchase.data.dao.MenuDAO
import com.banit.chewchase.data.dao.OrderDAO
import com.banit.chewchase.data.dao.OrderFoodsDAO
import com.banit.chewchase.data.dao.UserDAO
import com.banit.chewchase.data.repository.OrderRepository
import com.banit.chewchase.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideAppInitializer(
        appDatabase: AppDatabase,
        @ApplicationContext context: Context
    ): AppInitializer {
        return AppInitializer(appDatabase, context, Dispatchers.IO)
    }

    @Provides
    fun provideMenuDAO(database: AppDatabase): MenuDAO {
        return database.menuDAO()
    }

    @Provides
    @Singleton
    fun provideUserDAO(database: AppDatabase): UserDAO {
        return database.userDAO()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDAO: UserDAO): UserRepository {
        return UserRepository(userDAO)
    }

    @Provides
    @Singleton
    fun provideOrderDAO(database: AppDatabase): OrderDAO = database.orderDAO()

    @Provides
    @Singleton
    fun provideOrderFoodsDAO(database: AppDatabase): OrderFoodsDAO = database.orderFoodsDAO()

    @Provides
    @Singleton
    fun provideOrderRepository(
        orderDAO: OrderDAO, menuDAO: MenuDAO, orderFoodsDAO: OrderFoodsDAO
    ): OrderRepository = OrderRepository(orderDAO, menuDAO, orderFoodsDAO)

}