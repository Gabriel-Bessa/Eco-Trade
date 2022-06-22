package com.ecotrade.mstrade.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ecotrade.mstrade.config.errors.BusinessException;
import com.ecotrade.mstrade.domain.UploadFile;
import com.ecotrade.mstrade.service.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BucketService {

    private final UploadFileService uploadFileService;
    private final AmazonS3 bucket;
    private final String BUCKET_NAME = "eco-trade";
    private final String BUCKET_DIR = "bucket";
    private final String BUCKET_URL = "https://eco-trade.s3.amazonaws.com/"+BUCKET_DIR+"/";

    @Transactional
    public UploadFile upload(MultipartFile file) {
        String fileName = generateFileName(file);
        try {
            File fileToUpload = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(fileToUpload);
            outputStream.write(file.getBytes());
            outputStream.close();
            bucket.putObject(new PutObjectRequest(BUCKET_NAME + "/" + BUCKET_DIR, fileName, fileToUpload));
            fileToUpload.delete();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("file.exception", "file.unexpected");
        }
        return uploadFileService.create(String.format("%s%s", BUCKET_URL, fileName), file.getOriginalFilename());
    }

    private String generateFileName(MultipartFile file) {
        return String.format("%s.%s", UUID.randomUUID(), FileUtils.getFileExtention(file));
    }
}
