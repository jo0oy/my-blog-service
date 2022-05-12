package com.jo0oy.myblog.application.service.images;

import com.jo0oy.myblog.domain.uploadfile.UploadFile;
import com.jo0oy.myblog.domain.uploadfile.UploadFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final UploadFileRepository uploadFileRepository;

    private final Path rootLocation; // d:/image/

    public ImageServiceImpl(UploadFileRepository uploadFileRepository, @Value("${custom.file.upload-dir}") String uploadPath) {
        this.uploadFileRepository = uploadFileRepository;
        this.rootLocation = Paths.get(uploadPath);
        log.info("rootLocation={}", rootLocation);
    }

    @Override
    public UploadFile store(MultipartFile file) throws Exception {
        log.info("imageService store 로직 실행");
        //		 fileName : 예류2.jpg
        //		 filePath : d:/images/uuid-예류2.jpg
        //		 saveFileName : uuid-예류2.png
        //		 contentType : image/jpeg
        //		 size : 4994942
        //		 registerDate : 2020-02-06 22:29:57.748
        try {
            if(file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
            }

            String saveFileName = fileSave(rootLocation.toString(), file);
            UploadFile saveFile = UploadFile.builder()
                    .fileName(file.getOriginalFilename())
                    .saveFileName(saveFileName)
                    .contentType(file.getContentType())
                    .size(file.getResource().contentLength())
                    .registerDate(LocalDateTime.now())
                    .filePath(rootLocation.toString().replace(File.separatorChar, '/') +'/' + saveFileName)
                    .build();

            return uploadFileRepository.save(saveFile);

        } catch(IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UploadFile load(Long fileId) {
        log.info("imageService load 로직 실행");
        return uploadFileRepository.findById(fileId).orElseThrow(() -> {
            log.error("존재하지 않는 파일입니다. fileId={}", fileId);
            throw new IllegalArgumentException("존재하지 않는 파일입니다. fileId=" + fileId);
        });
    }

    @Override
    public String fileSave(String rootLocation, MultipartFile file) throws IOException {
        log.info("imageService fileSave 로직 실행");
        File uploadDir = new File(rootLocation);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // saveFileName 생성
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid + file.getOriginalFilename();
        File saveFile = new File(rootLocation, saveFileName);
        FileCopyUtils.copy(file.getBytes(), saveFile);

        return saveFileName;
    }

    @Override
    @Transactional
    public void delete(Long imgId) {
        log.info("imageService delete 로직 실행");
        UploadFile file = uploadFileRepository.findById(imgId).orElseThrow(() -> {
            log.error("존재하지 않는 파일입니다. fileId={}", imgId);
            throw new IllegalArgumentException("존재하지 않는 파일입니다. fileId=" + imgId);
        });

        uploadFileRepository.delete(file);

        log.info("imageService delete 로직 완료");
    }
}
