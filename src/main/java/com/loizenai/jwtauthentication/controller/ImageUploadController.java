package com.loizenai.jwtauthentication.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import com.loizenai.jwtauthentication.model.ImageModel;
import com.loizenai.jwtauthentication.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "image")
public class ImageUploadController {
    @Autowired
    ImageService imageService;

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity uploadImage(@RequestParam("imageFile") MultipartFile file, @RequestParam("title") String title,
                                      @RequestParam("author") String author, @RequestParam("description") String description,
                                      @RequestParam("tags") String tags) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
                ImageService.compressBytes(file.getBytes()), title, author, description, tags, LocalDateTime.now());
        imageService.save(img);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ImageModel>> getAllByQuery(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "from", required = false) String fromS,
            @RequestParam(value = "to", required = false) String toS
    ) {
        List<ImageModel> imageModels = imageService.getAllBy(title, description, tags, fromS, toS);

        return new ResponseEntity<>(imageModels, HttpStatus.OK);
    }
}