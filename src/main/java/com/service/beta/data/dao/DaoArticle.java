package com.service.beta.data.dao;

import lombok.Data;

@Data
public class DaoArticle {
    String title;
    Long userId;
    String content;
    String description;
    String articleType;
    String articleCategory;
    String link;
    String image;
}
