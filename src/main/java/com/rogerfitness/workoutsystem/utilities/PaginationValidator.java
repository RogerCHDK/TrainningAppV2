package com.rogerfitness.workoutsystem.utilities;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort.Direction;

@UtilityClass
public class PaginationValidator {
    private static final int DEFAULT_MAX_SIZE = 100;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 50;

    public static Integer sanitizePageIsValid(Integer page){
        if (null == page || page < 0){
            page = DEFAULT_PAGE;
        }
        return page;
    }

    public static Integer sanitizePageSizeIsValid(Integer size){
        if (null == size || size < 1){
            size = DEFAULT_SIZE;
        }else if(size > DEFAULT_MAX_SIZE){
            size = DEFAULT_MAX_SIZE;
        }
        return size;
    }

    public static Direction sanitizeSortOrder(String sortOrder, Direction defaultDirection){
        Direction direction;
        if ("ASC".equalsIgnoreCase(sortOrder)){
            direction = Direction.ASC;
        }else if("DESC".equalsIgnoreCase(sortOrder)){
            direction = Direction.DESC;
        }else {
            direction = defaultDirection;
        }
        return direction;
    }
}
