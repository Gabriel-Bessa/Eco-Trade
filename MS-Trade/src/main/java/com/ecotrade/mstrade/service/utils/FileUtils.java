package com.ecotrade.mstrade.service.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class FileUtils {

    private static final String[] valideImageExtentions = {"SVG", "PNG", "JPEG", "JPG"};

    public static boolean haveImageExtentions(MultipartFile file) {
        String ext = getFileExtention(file);
        return Arrays.stream(valideImageExtentions).anyMatch(it -> !it.equalsIgnoreCase(ext));
    }

    public static String getFileExtention(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename());
    }
}
