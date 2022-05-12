package com.jo0oy.myblog.domain.uploadfile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "upload_files")
@Entity
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;                //예류.jpg

    @Column
    private String saveFileName;            //uuid예류.jpg

    @Column
    private String filePath;                // D:/image/uuid예류.jpg

    @Column
    private String contentType;             // image/jpeg

    private long size;                      // 4476873 (byte)

    private LocalDateTime registerDate;     // 2020-02-07 12:27:37.857

    @Builder
    public UploadFile(String fileName, String saveFileName, String filePath,
                      String contentType, long size, LocalDateTime registerDate) {
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.contentType = contentType;
        this.size = size;
        this.registerDate = registerDate;
    }
}


