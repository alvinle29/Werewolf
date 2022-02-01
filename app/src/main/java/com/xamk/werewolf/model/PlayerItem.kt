package com.xamk.werewolf.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class PlayerItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var isSelected: Boolean = false,
)