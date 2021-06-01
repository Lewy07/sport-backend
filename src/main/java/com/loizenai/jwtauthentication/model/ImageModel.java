package com.loizenai.jwtauthentication.model;

import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "image_table")
public class ImageModel {
    public ImageModel() {
        super();
    }
    public ImageModel(String name, String type, byte[] picByte) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    public ImageModel(String name, String type, byte[] picByte, String title, String author, String description,
                      String tags, LocalDateTime date) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
        this.title = title;
        this.author = author;
        this.description = description;
        this.tags = tags;
        this.date = date;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "pic_byte")
    private byte[] picByte;
    private String title;
    private String author;
    private String description;
    private String tags;
    private LocalDateTime date;

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public byte[] getPicByte() {
        return picByte;
    }
    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
