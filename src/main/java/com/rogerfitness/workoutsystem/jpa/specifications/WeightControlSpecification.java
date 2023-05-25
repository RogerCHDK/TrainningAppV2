package com.rogerfitness.workoutsystem.jpa.specifications;

import com.rogerfitness.workoutsystem.jpa.entities.WeightControlEntity;
import org.springframework.data.jpa.domain.Specification;

public class WeightControlSpecification {
    public static Specification<WeightControlEntity> weightGreaterThan(Double weight) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("weight"), weight);
    }
}
