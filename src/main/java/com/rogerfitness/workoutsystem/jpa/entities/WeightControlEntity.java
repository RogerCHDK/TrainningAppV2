package com.rogerfitness.workoutsystem.jpa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "weight_control", schema = "public")
public class WeightControlEntity {
    @Id
    @SequenceGenerator(
            name = "weight_control_sequence",
            sequenceName = "weight_control_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "weight_control_sequence"
    )
    @Column(
            name = "weight_control_id_seq",
            updatable = false
    )
    private Integer weightControlIdSeq;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "date_weight")
    private Date date_weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID_SEQ")
    private UserEntity user;
}
