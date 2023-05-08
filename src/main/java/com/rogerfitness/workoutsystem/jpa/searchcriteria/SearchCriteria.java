package com.rogerfitness.workoutsystem.jpa.searchcriteria;

import com.rogerfitness.workoutsystem.utilities.PaginationValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class SearchCriteria {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortOrder;

    public Pageable createPageable(){
        Integer pageInput = PaginationValidator.sanitizePageIsValid(page);
        Integer sizeInput = PaginationValidator.sanitizePageSizeIsValid(size);
        return PageRequest.of(pageInput, sizeInput, createSort());
    }

    public Sort createSort(){
        Sort.Direction direction = PaginationValidator.sanitizeSortOrder(sortOrder, defaultSortDirection());
        String propertyPathForSort = findPropertyPathForSort(sortBy);
        return Sort.by(direction, propertyPathForSort);
    }

    abstract Sort.Direction defaultSortDirection();
    protected abstract  String defaultSortProperty();

    String findPropertyPathForSort(String property){
        if (StringUtils.isBlank(property)){
            return defaultSortProperty();
        }else {
            return property;
        }
    }

    public void useDefaultSort(){setSortBy(defaultSortProperty());}
}
