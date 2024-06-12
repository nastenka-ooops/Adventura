package com.neotour.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.neotour.dto.ImageDto;
import com.neotour.entity.Image;
import com.neotour.mapper.ImageMapper;
import com.neotour.mapper.TourListMapper;
import com.neotour.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final Cloudinary cloudinary;

    @Autowired
    public ImageService(ImageRepository imageRepository, Cloudinary cloudinary) {
        this.imageRepository = imageRepository;
        this.cloudinary = cloudinary;
    }

    public ImageDto uploadImage(MultipartFile file) throws IOException {
        Map params = ObjectUtils.asMap(
                "folder", "NeoTour"
        );
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setUrl(uploadResult.get("url").toString());

        Image savedImage = imageRepository.save(image);

        return new ImageDto(
                savedImage.getId(),
                savedImage.getName(),
                savedImage.getUrl()
        );
    }

    public List<ImageDto> getAllImages() {
        return imageRepository.findAll().stream()
                .map(ImageMapper::mapToImageDto).collect(Collectors.toList());

    }

    public List<ImageDto> downloadImagesFromCloudinary(String folder) {
        try {
            Map options = ObjectUtils.asMap(
                     "type", "upload",
                    "prefix", folder,
                    "max_results", 100
            );

            Map response = cloudinary.api().resources(options);

            List<String> imageUrls = new ArrayList<>();

            List<Map<String, Object>> resources = (List<Map<String, Object>>) response.get("resources");
            for (Map<String, Object> resource : resources) {
                String imageUrl = (String) resource.get("secure_url");
                imageUrls.add(imageUrl);
            }

            List<ImageDto> imageDTOs = new ArrayList<>();
            for (String url : imageUrls) {
                Optional<Image> existingImage = imageRepository.findByUrl(url);
                if (existingImage.isEmpty()) {
                    Image image = new Image();
                    image.setName(extractFileNameFromUrl(url));
                    image.setUrl(url);

                    Image savedImage = imageRepository.save(image);
                    imageDTOs.add(ImageMapper.mapToImageDto(savedImage));
                }
            }
            return imageDTOs;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
