package com.service.beta.data.model;

import com.service.beta.data.enum_data.ArticleCategory;
import com.service.beta.data.enum_data.ArticleType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "db_article")
@Data
public class DbArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public DbUser user;

    @ManyToOne
    public DbComments comments;

    private String content;

    private String image;

    private String link;

    @Enumerated(EnumType.STRING)
    private ArticleCategory category;

    @Enumerated(EnumType.STRING)
    private ArticleType type;

}
