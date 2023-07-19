package com.rogerfitness.workoutsystem.jpa.entities;

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
@Table(name = "training_exercise", schema = "public")
public class TrainingExerciseEntity {
    @Id
    @SequenceGenerator(
            name = "training_exercise_sequence",
            sequenceName = "training_exercise_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "training_exercise_sequence"
    )
    @Column(
            name = "training_exercise_id_seq",
            updatable = false
    )
    private Integer trainingExerciseIdSeq;

    @Column(name = "name")
    private String name;

    @Column(name = "body_part")
    private String bodyPart;

    @Column(name = "description")
    private String description;

    @Column(name = "image_exercise")
    private String imageExercise;

    @Column(name = "effective_start_dttm")
    private Timestamp effectiveStartDttm;

    @Column(name = "effective_end_dttm")
    private Timestamp effectiveEndDttm;
}
