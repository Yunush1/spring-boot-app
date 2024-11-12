package com.service.beta.repository;

import com.service.beta.data.model.DbArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories(basePackageClasses = ArticleRepository.class)
public interface ArticleRepository extends JpaRepository<DbArticle,Long> {
    List<DbArticle> findDbArticleByUserId(Long userId);
    List<DbArticle> findDbArticleByTitle(String title);
    List<DbArticle> findDbArticleByTitleLike(String title);
    List<DbArticle> findDbArticleByTitleContaining(String title);
    List<DbArticle> findDbArticleByTitleStartingWith(String title);
    List<DbArticle> findDbArticleByTitleEndingWith(String title);
    List<DbArticle> findDbArticleByTitleContainingIgnoreCase(String title);
}
