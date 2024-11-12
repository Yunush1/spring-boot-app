package com.service.beta.controller;

import com.service.beta.data.dao.DaoArticle;
import com.service.beta.data.response.Response;
import com.service.beta.services.business_logic.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/")
    public ResponseEntity<Response> savedArticle(@RequestParam("userId") Long userId,@RequestBody DaoArticle daoArticle) {
        return articleService.savedArticle(userId,daoArticle);
    }

//    @GetMapping("/")
//    public ResponseEntity<Response> getAllArticles() {
//        return
//    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateArticle(@RequestParam("articleId") Long articleId,@RequestBody DaoArticle daoArticle) {
        return articleService.updateArticle(articleId,daoArticle);
    }


}
