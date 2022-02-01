package com.xamk.werewolf.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {

    @Query("SELECT * from player_table")
    fun getAll(): MutableList<PlayerItem>

    @Insert
    fun insert(item: PlayerItem) : Long

    @Query("DELETE FROM player_table WHERE id = :itemId")
    fun delete(itemId: Int)

}