package com.example.nusalestari.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.nusalestari.model.Endemik;

import java.util.List;

@Dao
public interface EndemikDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Endemik> endemikList);

    @Query("SELECT * FROM endemik")
    List<Endemik> getAllEndemik();

    @Query("SELECT * FROM endemik WHERE tipe = :tipe")
    List<Endemik> getEndemikByTipe(String tipe);

    @Query("SELECT * FROM endemik WHERE nama LIKE '%' || :keyword || '%' OR namaLatin LIKE '%' || :keyword || '%' ORDER BY nama ASC")
    List<Endemik> searchEndemik(String keyword);

    @Query("SELECT * FROM endemik WHERE id = :id LIMIT 1")
    Endemik getEndemikById(String id);

    @Query("SELECT COUNT(*) FROM endemik")
    int countEndemik();

    @Query("DELETE FROM endemik")
    void deleteAllEndemik();

    @Query("SELECT DISTINCT asal FROM endemik WHERE asal IS NOT NULL AND TRIM(asal) != '' ORDER BY asal ASC")
    List<String> getAllRegion();

    @Query("SELECT * FROM endemik " +
            "WHERE (nama LIKE '%' || :keyword || '%' " +
            "OR namaLatin LIKE '%' || :keyword || '%') " +
            "AND (:region = '' OR asal LIKE '%' || :region || '%' OR sebaran LIKE '%' || :region || '%') " +
            "ORDER BY nama ASC")
    List<Endemik> searchEndemikByRegion(String keyword, String region);
}