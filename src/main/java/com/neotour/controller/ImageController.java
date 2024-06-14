package com.neotour.controller;

import com.cloudinary.utils.ObjectUtils;
import com.neotour.dto.ImageDto;
import com.neotour.entity.Image;
import com.neotour.service.ImageService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Operation(summary = "Upload Image to Cloudinary", description = "Uploads an image file to Cloudinary and saves it in the database.")
    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(
            @Parameter(description = "Image file to upload", required = true) @RequestParam("file") MultipartFile file) {
        Image imageDto = imageService.uploadImage(file);
        return ResponseEntity.ok(imageDto);
    }

    @Operation(summary = "Download Images from Cloudinary",
            description = "Downloads images from the specified folder in Cloudinary.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved image URLs"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/downloadFromCloudinary")
    public ResponseEntity<List<ImageDto>> downloadImagesFromCloudinary(
        @Parameter(description = "Path of the folder in Cloudinary to download images from") @RequestParam("folder") String folder){
        List<ImageDto> imageDtos = imageService.downloadImagesFromCloudinary(folder);
        return ResponseEntity.ok(imageDtos);
    }

    @Hidden
    @GetMapping("")
    public ResponseEntity<List<ImageDto>> getAllImages() {
        List<ImageDto> imageDtos = imageService.getAllImages();
        return ResponseEntity.ok(imageDtos);
    }
}
