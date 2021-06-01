package com.loizenai.jwtauthentication.service;

import com.loizenai.jwtauthentication.model.ImageModel;
import com.loizenai.jwtauthentication.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageService {
    @Autowired
    private final ImageRepository repository;

    public ImageService(ImageRepository companyRepository) {
        this.repository = companyRepository;
    }

    public void save(ImageModel img)
    {
        repository.save(img);
    }

    public List<ImageModel> getAllBy(String title, String description, String tags, String fromS, String toS)
    {
        System.out.println("------------------------------------------------------");
        System.out.println(title+description+tags+fromS+toS);
        List<ImageModel> imageModels = repository.findAll();
        System.out.println(imageModels.size());
        List<ImageModel> imageModelsTitle;
        List<ImageModel> imageModelsDescription;
        List<ImageModel> imageModelsTags;
        List<ImageModel> imageModelsFrom;
        List<ImageModel> imageModelsTo;
        System.out.println(fromS);
        System.out.println(toS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if(title != null && !title.equals("undefined"))
        {
            imageModelsTitle = repository.findByTitleContaining(title);
            System.out.println(imageModelsTitle.size());
            imageModels.retainAll(imageModelsTitle);
            System.out.println(imageModels.size());
        }
        System.out.println(imageModels.size());
        if(description != null && !description.equals("undefined"))
        {
            imageModelsDescription = repository.findByDescriptionContaining(description);
            imageModels.retainAll(imageModelsDescription);
        }
        System.out.println(imageModels.size());
        if(tags != null && !tags.equals("undefined"))
        {
            imageModelsTags = repository.findByTagsContaining(tags);
            imageModels.retainAll(imageModelsTags);
        }
        System.out.println(imageModels.size());
        if(fromS != null && !fromS.equals("undefined"))
        {
            LocalDateTime from = LocalDateTime.parse(fromS, formatter);
            imageModelsFrom = repository.findByDateGreaterThan(from);
            imageModels.retainAll(imageModelsFrom);
        }
        System.out.println(imageModels.size());
        if(toS != null && !toS.equals("undefined"))
        {
            System.out.println("ELO");
            LocalDateTime to = LocalDateTime.parse(toS, formatter);
            imageModelsTo = repository.findByDateLessThan(to);
            imageModels.retainAll(imageModelsTo);
        }
        System.out.println(imageModels.size());

        for (ImageModel imageModel : imageModels) {
            imageModel.setPicByte(decompressBytes(imageModel.getPicByte()));
        }
        System.out.println(imageModels.size());

        return imageModels;
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException ignored) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ignored) {
        }
        return outputStream.toByteArray();
    }
}
