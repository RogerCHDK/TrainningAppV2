package com.rogerfitness.workoutsystem.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "cardio_machine", schema = "public")
public class CardioMachineEntity {
    @Id
    @SequenceGenerator(
            name = "cardio_machine_sequence",
            sequenceName = "cardio_machine_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "cardio_machine_sequence"
    )
    @Column(
            name = "cardio_machine_id_seq",
            updatable = false
    )
    private Integer cardioMachineIdSeq;
    @Column(name = "name")
    private String name;
    @Column(name = "image_workout")
    private String imageWorkout;
    @Column(name = "description")
    private String description;
    @Column(name = "is_expired")
    private boolean isExpired;
}
