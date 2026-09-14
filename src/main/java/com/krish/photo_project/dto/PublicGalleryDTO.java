package com.krish.photo_project.dto;

import lombok.Data;

@Data
public class PublicGalleryDTO {

    private Long id;
    private String title;
    private String shareToken;
    private boolean published;

    public PublicGalleryDTO(
            Long id,
            String title,
            String shareToken,
            boolean published
    ) {
        this.id = id;
        this.title = title;
        this.shareToken = shareToken;
        this.published = published;
    }
}
