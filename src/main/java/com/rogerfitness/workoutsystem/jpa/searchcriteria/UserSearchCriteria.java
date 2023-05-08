package com.rogerfitness.workoutsystem.jpa.searchcriteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@SuperBuilder
@Data
@NoArgsConstructor
public class UserSearchCriteria extends SearchCriteria{
    private Integer userIdSeq;
    private String name;
    private String email;
    @Override
    Sort.Direction defaultSortDirection() {
        return Sort.Direction.DESC;
    }

    @Override
    protected String defaultSortProperty() {
        return "userIdSeq";
    }
}
