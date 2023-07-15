package com.rogerfitness.workoutsystem.jpa.repositories;

import com.rogerfitness.workoutsystem.jpa.entities.CardioMachineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardioMachineRepository extends JpaRepository<CardioMachineEntity, Integer> {
    Page<CardioMachineEntity> findCardioMachineEntitiesByIsExpired(boolean isExpired, Pageable pageable);
}
