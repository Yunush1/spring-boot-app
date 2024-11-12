package com.service.beta.controller;

import com.service.beta.data.dao.DaoUser;
import com.service.beta.data.model.DbUser;
import com.service.beta.data.response.Response;
import com.service.beta.services.UploadFileService;
import com.service.beta.services.business_logic.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/")
    public ResponseEntity<Response> savedUser(@RequestBody DaoUser daoUser){
        return userService.saveUser(daoUser);
    }

    @GetMapping("file")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
       URL fileUrl=  uploadFileService.uploadFile(file);
        return new ResponseEntity<>("File uploaded successfully: " + fileUrl.toString(), HttpStatus.OK);

    }
    @GetMapping("file/url")
    public ResponseEntity<String> getUrl(@RequestPart String bucketName, @RequestPart String name) throws IOException {
        System.out.println(name);
        return new ResponseEntity<>(uploadFileService.preSignedUrl(bucketName),HttpStatus.OK);
    }

    @GetMapping("/")
    public List<DbUser> getUser(){
        return userService.getAllUsers();
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateUser(@RequestParam("id") Long id, @RequestBody DaoUser daoUser){
        return userService.updateUser(id, daoUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteUser(@RequestParam("id") Long id){
        return userService.deleteUser(id);
    }
}
