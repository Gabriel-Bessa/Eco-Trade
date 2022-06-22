package com.ecotrade.mstrade.repository;

import com.ecotrade.mstrade.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UploadFileRepository extends JpaRepository<UploadFile, UUID> {
}
