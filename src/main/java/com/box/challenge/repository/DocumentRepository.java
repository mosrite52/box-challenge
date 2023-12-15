package com.box.challenge.repository;

import com.box.challenge.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByFilename(String filename);
    @Query("SELECT d FROM Document d " +
            "WHERE (:hashType = 'SHA-256' AND d.hashSha256 = :hash) OR " +
            "(:hashType = 'SHA-512' AND d.hashSha512 = :hash)")
    Optional<Document> findByHash(@Param("hashType") String hashType,
                                  @Param("hash") String hash);
}