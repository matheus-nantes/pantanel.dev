package com.example.B3_Social.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.B3_Social.models.UserModel;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
}
