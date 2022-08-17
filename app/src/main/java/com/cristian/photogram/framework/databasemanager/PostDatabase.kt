package com.cristian.photogram.framework.databasemanager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cristian.photogram.framework.databasemanager.daos.PostDao
import com.cristian.photogram.framework.databasemanager.entities.PostEntity

@Database(entities = [PostEntity::class], version = 1)
abstract class PostDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao
}