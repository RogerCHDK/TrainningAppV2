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
@Table(name = "cardio_workout", schema = "public")
public class CardioWorkoutEntity {
    @Id
    @SequenceGenerator(
            name = "cardio_workout_sequence",
            sequenceName = "cardio_workout_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "cardio_workout_sequence"
    )
    @Column(
            name = "cardio_workout_id_seq",
            updatable = false
    )
    private Integer cardioWorkoutIdSeq;

    @Column(name = "calories")
    private Double calories;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "velocity_average")
    private Double velocityAverage;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cardio_set_id", referencedColumnName = "cardio_set_id_seq")
    private CardioSetEntity cardioSetEntity;
}
