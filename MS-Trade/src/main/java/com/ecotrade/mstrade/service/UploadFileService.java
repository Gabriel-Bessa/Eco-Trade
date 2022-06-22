package com.ecotrade.mstrade.service;

import com.ecotrade.mstrade.domain.UploadFile;
import com.ecotrade.mstrade.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadFileService {

    private final UploadFileRepository repository;

    @Transactional
    public UploadFile create(String url, String name) {
        UploadFile file = new UploadFile();
        file.setName(name);
        file.setUrl(url);
        return repository.save(file);
    }

}
