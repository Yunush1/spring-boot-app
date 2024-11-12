package com.service.beta.services.business_logic;

import com.service.beta.data.dao.DaoArticle;
import com.service.beta.data.dao.DaoUser;
import com.service.beta.data.enum_data.ArticleCategory;
import com.service.beta.data.enum_data.ArticleType;
import com.service.beta.data.model.DbArticle;
import com.service.beta.data.model.DbUser;
import com.service.beta.data.response.Response;
import com.service.beta.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    public ResponseEntity<Response> savedArticle(Long userId,DaoArticle daoArticle){
        try {
            DbUser user = userService.findByDbUserId(userId).orElseThrow(()->new IllegalArgumentException("User not found"));
            DbArticle dbArticle = modelMapper.map(daoArticle, DbArticle.class);
            dbArticle.setUser(user);
            dbArticle.setCategory(ArticleCategory.valueOf(daoArticle.getArticleCategory().toUpperCase()));
            dbArticle.setType(ArticleType.valueOf(daoArticle.getArticleType().toUpperCase()));
            DbArticle savedArticle = articleRepository.save(dbArticle);
            return new ResponseEntity<>(new Response("Article save","200",savedArticle),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response("Error: "+e.getMessage(),"500",null), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Response> updateArticle(Long articleId,DaoArticle daoArticle){
        try {
            DbArticle dbArticle = articleRepository.findById(articleId).orElseThrow(()->new IllegalArgumentException("Article not found"));

            if (daoArticle.getTitle() != null) {
                dbArticle.setTitle(daoArticle.getTitle());
            }
            if (daoArticle.getContent() != null) {
                dbArticle.setContent(daoArticle.getContent());
            }

            if (daoArticle.getArticleCategory() != null && daoArticle.getArticleCategory().isEmpty()) {
                dbArticle.setCategory(ArticleCategory.valueOf(daoArticle.getArticleCategory().toUpperCase()));
            }

            if (daoArticle.getArticleType() != null && daoArticle.getArticleType().isEmpty()) {
                dbArticle.setType(ArticleType.valueOf(daoArticle.getArticleType().toUpperCase()));
            }

            if (daoArticle.getDescription() != null && daoArticle.getDescription().isEmpty()) {
                dbArticle.setDescription(daoArticle.getDescription());
            }

            if (daoArticle.getLink() != null && daoArticle.getLink().isEmpty()) {
                dbArticle.setLink(daoArticle.getLink());
            }
            if (daoArticle.getImage() != null && daoArticle.getImage().isEmpty()) {
                dbArticle.setImage(daoArticle.getImage());
            }

            DbArticle savedArticle = articleRepository.save(dbArticle);
            return new ResponseEntity<>(new Response("Article update","200",savedArticle),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response("Error: "+e.getMessage(),"500",null), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Response> deleteArticle(Long articleId){
        try {
            articleRepository.deleteById(articleId);
            return new ResponseEntity<>(new Response("Deleted success","200",null),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response("Error: "+e.getMessage(),"500",null), HttpStatus.CREATED);
        }
    }
    public ResponseEntity<Response> getArticle(Long articleId){
        try {
            return new ResponseEntity<>(new Response("Article get","200",articleRepository.findById(articleId).get()),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response("Error: "+e.getMessage(),"500",null), HttpStatus.CREATED);
        }
    }

//    public ResponseEntity<Response> searchArticle(String keyword){
//        return new ResponseEntity<>(new Response())
//    }
}
