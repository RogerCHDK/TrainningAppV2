package com.rogerfitness.workoutsystem.jpa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "training_set", schema = "public")
public class TrainingSetEntity {
    @Id
    @SequenceGenerator(
            name = "training_set_sequence",
            sequenceName = "training_set_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "training_set_sequence"
    )
    @Column(
            name = "training_set_id_seq",
            updatable = false
    )
    private Integer trainingSetIdSeq;

    @Column(name = "number_reps")
    private Integer numberReps;

    @Column(name = "time_average")
    private Integer timeAverage;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "training_exercise_id", referencedColumnName = "training_exercise_id_seq")
    private TrainingExerciseEntity trainingExerciseEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "routine_exercise_id", referencedColumnName = "routine_exercise_id_seq")
    private RoutineExerciseEntity routineExerciseEntity;

    @Column(name = "effective_start_dttm")
    private Timestamp effectiveStartDttm;

    @Column(name = "effective_end_dttm")
    private Timestamp effectiveEndDttm;
}
