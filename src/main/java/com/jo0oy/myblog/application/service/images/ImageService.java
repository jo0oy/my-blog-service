package com.jo0oy.myblog.application.service.images;

import com.jo0oy.myblog.domain.uploadfile.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    UploadFile store(MultipartFile file) throws Exception;

    UploadFile load(Long fileId);

    String fileSave(String rootLocation, MultipartFile file) throws IOException;

    void delete(Long imgId);
}
