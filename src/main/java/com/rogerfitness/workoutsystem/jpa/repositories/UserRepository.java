package com.rogerfitness.workoutsystem.jpa.repositories;

import com.rogerfitness.workoutsystem.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
