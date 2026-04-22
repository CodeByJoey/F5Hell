package com.f5hell.common.util;

import com.f5hell.common.entity.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    // 파일경로를 불러오는 기능
    private String getFullPath(String filename) {
        return fileDir + filename;
    }

    // 파일을 저장하는 기능
    public UploadFile saveFile(MultipartFile multipartFile) {
        if(multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        try {
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
        } catch(IOException e) {
            log.error("파일 저장하다 오류낫쏭 {}", e.getMessage());
        }

        return new UploadFile(originalFileName, storeFileName);
    }

    // 파일명 변환
    private String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

    // 파일을 삭제하는 기능
}
