package com.timife.movies.data.pagination

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface RemoteKeyDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertOrReplace(remoteKey: RemoteKey)

  @Query("SELECT * FROM remote_keys")
  fun getRemoteKey(): Single<RemoteKey>

  @Query("DELETE FROM remote_keys")
  fun deleteRemoteKey()
}