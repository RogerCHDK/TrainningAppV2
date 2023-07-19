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
@Table(name = "cardio_set", schema = "public")
public class CardioSetEntity {

    @Id
    @SequenceGenerator(
            name = "cardio_set_sequence",
            sequenceName = "cardio_set_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = SEQUENCE, generator = "cardio_set_sequence")
    @Column(name = "CARDIO_SET_ID_SEQ", updatable = false)
    private Integer cardioSetIdSeq;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cardio_machine_id", referencedColumnName = "cardio_machine_id_seq")
    private CardioMachineEntity cardioMachineEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "routine_exercise_id", referencedColumnName = "routine_exercise_id_seq")
    private RoutineExerciseEntity routineExerciseEntity;

    @Column(name = "effective_start_dttm")
    private Timestamp effectiveStartDttm;

    @Column(name = "effective_end_dttm")
    private Timestamp effectiveEndDttm;
}
