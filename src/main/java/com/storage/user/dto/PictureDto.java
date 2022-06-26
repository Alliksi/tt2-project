package com.storage.user.dto;

import org.springframework.web.multipart.MultipartFile;

public class PictureDto {
    private MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }
    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
