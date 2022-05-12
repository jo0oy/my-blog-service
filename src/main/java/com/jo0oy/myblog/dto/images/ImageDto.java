package com.jo0oy.myblog.dto.images;

import lombok.Builder;
import lombok.Getter;

public class ImageDto {

    @Getter
    @Builder
    public static class DeleteReq {
        private String src;
    }
}
