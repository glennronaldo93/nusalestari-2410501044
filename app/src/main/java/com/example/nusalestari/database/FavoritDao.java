package com.example.nusalestari.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.nusalestari.model.Endemik;
import com.example.nusalestari.model.Favorit;

import java.util.List;

@Dao
public interface FavoritDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorit(Favorit favorit);

    @Query("SELECT * FROM favorit")
    List<Favorit> getAllFavorit();

    @Query("SELECT * FROM favorit WHERE id = :id LIMIT 1")
    Favorit getFavoritById(String id);

    @Query("SELECT COUNT(*) FROM favorit WHERE id = :id")
    int checkFavorit(String id);

    @Query("DELETE FROM favorit WHERE id = :id")
    void deleteFavoritById(String id);

    @Query("DELETE FROM favorit")
    void deleteAllFavorit();

    @Query("SELECT * FROM endemik WHERE id IN (SELECT id FROM favorit) ORDER BY nama ASC")
    List<Endemik> getAllEndemikFavorit();
}