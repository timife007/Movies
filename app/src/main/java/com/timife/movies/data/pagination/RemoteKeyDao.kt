package com.timife.movies.data.pagination

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrReplace(remoteKey: RemoteKey)

  @Query("SELECT * FROM remote_keys")
  suspend fun getRemoteKey(): RemoteKey

  @Query("DELETE FROM remote_keys")
  suspend fun deleteRemoteKey()
}