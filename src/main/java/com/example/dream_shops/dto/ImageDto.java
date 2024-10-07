package com.example.dream_shops.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private Long imageId;
    private String imageName;
    private String downloadUrl;
}
