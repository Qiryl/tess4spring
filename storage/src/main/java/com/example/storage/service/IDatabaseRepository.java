package com.example.storage.service;

import com.example.storage.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDatabaseRepository extends JpaRepository<File, UUID> {
    @Modifying
    @Query("update File f set f.status = true, f.url = :url where f.id = :id")
    void updateStatusById(@Param("url") String url, @Param("id") UUID id);
}