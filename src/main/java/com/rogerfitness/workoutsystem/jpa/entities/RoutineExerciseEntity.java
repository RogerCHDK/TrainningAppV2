package com.rogerfitness.workoutsystem.jpa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "routine_exercise", schema = "public")
public class RoutineExerciseEntity {
    @Id
    @SequenceGenerator(
            name = "routine_exercise_sequence",
            sequenceName = "routine_exercise_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "routine_exercise_sequence"
    )
    @Column(
            name = "routine_exercise_id_seq",
            updatable = false
    )
    private Integer routineExerciseIdSeq;

    @Column(name = "date_routine")
    private Timestamp dateRoutine;

    @Column(name = "observations")
    private String observations;

    @Column(name = "time_break")
    private Integer timeBreak;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID_SEQ")
    private UserEntity userEntity;

    @JsonManagedReference
    @OneToMany(mappedBy = "routineExerciseEntity",  fetch = FetchType.LAZY)
    private List<TrainingSetEntity> trainingSetEntityList;

    @JsonManagedReference
    @OneToMany(mappedBy = "routineExerciseEntity",  fetch = FetchType.LAZY)
    private List<CardioSetEntity> cardioSetEntityList;

    @Column(name = "effective_start_dttm")
    private Timestamp effectiveStartDttm;

    @Column(name = "effective_end_dttm")
    private Timestamp effectiveEndDttm;
}
