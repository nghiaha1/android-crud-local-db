package com.example.crud_demo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.crud_demo.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(UserEntity userEntity);

    @Update
    void updateUser(UserEntity userEntity);

    @Delete
    void deleteUser(UserEntity userEntity);

    @Query("SELECT * FROM users")
    List<UserEntity> getAllUser();

    @Query("SELECT * FROM users WHERE id=:id")
    UserEntity getUser(int id);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT * FROM users WHERE username=:username")
    UserEntity getUserByUsername(String username);
}
