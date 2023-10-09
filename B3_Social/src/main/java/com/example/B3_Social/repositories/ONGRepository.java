package com.example.B3_Social.repositories;

import com.example.B3_Social.models.ONGModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ONGRepository extends JpaRepository<ONGModel, UUID> {


}
