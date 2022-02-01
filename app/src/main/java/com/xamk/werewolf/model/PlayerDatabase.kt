package com.xamk.werewolf.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlayerItem::class], version = 1)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
}