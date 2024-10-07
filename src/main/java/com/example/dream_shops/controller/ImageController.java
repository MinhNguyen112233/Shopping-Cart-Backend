package com.example.dream_shops.controller;

import com.example.dream_shops.dto.ImageDto;
import com.example.dream_shops.exception.ResourceNotFoundException;
import com.example.dream_shops.model.Image;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,@RequestParam Long productId) {
       try{
           List<ImageDto> imageDtos = imageService.saveImage(files, productId);
           return ResponseEntity.ok(ApiResponse.builder()
                   .message("Images saved successfully!")
                   .data(imageDtos).build());
       }catch(Exception e){
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                   .message(e.getMessage())
                   .build());
       }
    }

    @GetMapping("image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@RequestParam Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .contentType(org.springframework.http.MediaType.parseMediaType(image.getFileType()))
                .body(resource);
    }


    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try{
            Image image = imageService.getImageById(imageId);
            if(image == null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(ApiResponse.builder()
                        .message("Image updated successfully!")
                        .build());
            }
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                .message("Image not found!")
                .build());
    }


    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@RequestParam Long imageId) {
        try{
            Image image = imageService.getImageById(imageId);
            if(image == null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(ApiResponse.builder()
                        .message("Image deleted successfully!")
                        .build());
            }
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                .message("Image not found!")
                .build());
    }
}
