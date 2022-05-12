package com.jo0oy.myblog.web;

import com.jo0oy.myblog.application.service.images.ImageService;
import com.jo0oy.myblog.common.response.ResultResponse;
import com.jo0oy.myblog.domain.uploadfile.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    private final ResourceLoader resourceLoader;

    // 이미지 업로드
    @PostMapping("/images")
    public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile file) {
        try {
            UploadFile uploadFile = imageService.store(file);
            return ResponseEntity.ok().body("/image/" + uploadFile.getId());
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // 이미지 조회
    @GetMapping("/images/{fileId}")
    public ResponseEntity<?> serveFile(@PathVariable Long fileId){
        try {
            UploadFile uploadFile = imageService.load(fileId);
            Resource resource = resourceLoader.getResource("file:" + uploadFile.getFilePath());
            return ResponseEntity.ok().body(resource);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // 이미지 삭제
    @DeleteMapping("/images/{imgId}")
    public ResponseEntity<?> deleteFile(@PathVariable(name = "imgId") Long imgId) {
        log.info("imgId={}", imgId);
        imageService.delete(imgId);

        return ResponseEntity.ok(ResultResponse.success("이미지 삭제 성공했습니다.", imgId));
    }
}
