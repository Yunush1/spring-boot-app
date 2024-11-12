package com.service.beta.services.business_logic;

import com.service.beta.data.dao.DaoUser;
import com.service.beta.data.model.DbUser;
import com.service.beta.data.response.Response;
import com.service.beta.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<Response> saveUser(DaoUser daoUser) {
        try {
            DbUser user = modelMapper.map(daoUser, DbUser.class);
            return new ResponseEntity<>(new Response("Registration success","200",userRepository.save(user)), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(new Response("Error: "+e.getMessage(),"",null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<DbUser> findByDbUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public List<DbUser> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<Response> deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return new ResponseEntity<>(new Response("User deleted success", "200",null),HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new Response("Error: "+e.getMessage(),"500",null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Response> updateUser(Long userId, DaoUser daoUser) {
        try {
            DbUser user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
            DbUser dbUser =  modelMapper.map(daoUser,DbUser.class);
            dbUser.setId(userId);
            dbUser.setAddress(dbUser.getAddress());
            userRepository.save(dbUser);
            return new ResponseEntity<>(new Response("User updated success", "200",dbUser),HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new Response("Error: "+e.getMessage(),"500",null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private DbUser mapToDb(Object object) {
        return modelMapper.map(object, DbUser.class);
    }
}
