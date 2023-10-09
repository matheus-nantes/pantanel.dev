package com.example.B3_Social.controllers;

import com.example.B3_Social.dtos.UserRecordDTO;
import com.example.B3_Social.models.UserModel;
import com.example.B3_Social.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/User")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRecordDTO userRecordDTO){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDTO, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
    }

    @GetMapping("/User")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        List<UserModel> ongList = userRepository.findAll();
        if(!ongList.isEmpty()){
            for(UserModel ong : ongList){
                UUID id = ong.getId();
                ong.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(ongList);
    }

    @GetMapping("/User/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value="id") UUID id) {
        Optional<UserModel> userO = userRepository.findById(id);
        if(userO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userO.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("Users List"));
        return ResponseEntity.status(HttpStatus.OK).body(userO.get());
    }

    @PutMapping("/User/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value="id") UUID id, @RequestBody @Valid UserRecordDTO userRecordDTO){
        Optional<UserModel> userO = userRepository.findById(id);
        if(userO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var userModel = userO.get();
        BeanUtils.copyProperties(userRecordDTO, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
    }

    @DeleteMapping("/User/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value="id") UUID id){
        Optional<UserModel> userO = userRepository.findById(id);
        if(userO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userRepository.delete(userO.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted sucessfully");
    }

}
