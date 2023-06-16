package com.timife.movies.data.pagination

import androidx.room.Entity

@Entity(tableName = "remote_keys")
data class RemoteKey(val label: String, val loadKey: Int?)