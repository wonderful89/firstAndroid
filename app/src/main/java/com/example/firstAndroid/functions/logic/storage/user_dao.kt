package com.example.firstAndroid.functions.logic.storage

import android.database.Cursor
import androidx.room.*
import io.reactivex.Flowable

@Dao
interface UserDao {
    @get:Query("SELECT * FROM user")
    val all: List<EntityUser?>?

    @get:Query("SELECT * FROM user")
    val flowAll: Flowable<List<EntityUser?>>

    @get:Query("SELECT * FROM user WHERE uid > 10 LIMIT 5")
    val getMinUid: Cursor

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): List<EntityUser?>?

    @Query(
        "SELECT * FROM user WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    fun findByName(first: String?, last: String?): EntityUser?

    @Query("SELECT * FROM user WHERE uid = :uid LIMIT 1")
    fun flowFindById(uid: Long): Flowable<EntityUser>

    @Insert
    fun insertAll(vararg users: EntityUser?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: EntityUser?)

    @Delete
    fun delete(user: EntityUser?)
}