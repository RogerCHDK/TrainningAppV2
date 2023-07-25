package com.rogerfitness.workoutsystem.jpa.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "USER", schema = "public")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
            name = "USER_ID_SEQ",
            updatable = false
    )
    private Integer userIdSeq;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @JsonManagedReference
    @OneToMany(mappedBy = "user",  fetch = FetchType.LAZY)
    private List<WeightControlEntity> weightControlEntity;
}
