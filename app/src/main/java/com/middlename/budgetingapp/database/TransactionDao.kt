package com.middlename.budgetingapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    fun getTransaction(id: Int): Flow<Transaction>

    @Query("SELECT * FROM `transaction` ORDER BY date DESC")
    fun getTransactions(): Flow<List<Transaction>>
}