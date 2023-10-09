package com.example.B3_Social.controllers;

import com.example.B3_Social.dtos.ONGRecordDTO;
import com.example.B3_Social.models.ONGModel;
import com.example.B3_Social.repositories.ONGRepository;
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
public class ONGController {

    @Autowired
    ONGRepository ongRepository;

    @PostMapping("/ONG")
    public ResponseEntity<ONGModel> saveONG(@RequestBody @Valid ONGRecordDTO ongRecordDTO){
        var ongModel = new ONGModel();
        BeanUtils.copyProperties(ongRecordDTO, ongModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(ongRepository.save(ongModel));
    }

    @GetMapping("/ONG")
    public ResponseEntity<List<ONGModel>> getAllONGs(){
        List<ONGModel> ongList = ongRepository.findAll();
        if(!ongList.isEmpty()){
            for(ONGModel ong : ongList){
                UUID id = ong.getId();
                ong.add(linkTo(methodOn(ONGController.class).getONG(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(ongList);
    }

    @GetMapping("/ONG/{id}")
    public ResponseEntity<Object> getONG(@PathVariable(value="id") UUID id) {
        Optional<ONGModel> ongO = ongRepository.findById(id);
        if(ongO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ONG not found.");
        }
        ongO.get().add(linkTo(methodOn(ONGController.class).getAllONGs()).withRel("ONGs List"));
        return ResponseEntity.status(HttpStatus.OK).body(ongO.get());
    }

    @PutMapping("/ONG/{id}")
    public ResponseEntity<Object> updateONG(@PathVariable(value="id") UUID id, @RequestBody @Valid ONGRecordDTO ongRecordDTO){
        Optional<ONGModel> ongO = ongRepository.findById(id);
        if(ongO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ONG not found");
        }

        var ongModel = ongO.get();
        BeanUtils.copyProperties(ongRecordDTO, ongModel);
        return ResponseEntity.status(HttpStatus.OK).body(ongRepository.save(ongModel));
    }

    @DeleteMapping("/ONG/{id}")
    public ResponseEntity<Object> deleteONG(@PathVariable(value="id") UUID id){
        Optional<ONGModel> ongO = ongRepository.findById(id);
        if(ongO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ONG not found");
        }
        ongRepository.delete(ongO.get());
        return ResponseEntity.status(HttpStatus.OK).body("ONG deleted sucessfully");
    }

}
