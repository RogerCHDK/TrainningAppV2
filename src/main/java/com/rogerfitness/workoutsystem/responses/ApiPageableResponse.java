package com.rogerfitness.workoutsystem.responses;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiPageableResponse<T> extends ApiResponse<List<T>> {

    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Page {
        private Integer pageNumber;
        private Integer pageSize;
        private Integer totalPages;
        private Long totalElements;
        private ApiPageableResponse.Sort sort;
    }

    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sort {
        private Boolean isSorted;
        private Boolean isUnsorted;
    }

    private ApiPageableResponse.Page page;

    public ApiPageableResponse(org.springframework.data.domain.Page<T> page, HttpStatus status) {
        super(status.value(), page.getContent());

        Sort sort = Optional.ofNullable(page.getSort())
                .map(s -> Sort
                        .builder()
                        .isSorted(s.isSorted())
                        .isUnsorted(s.isUnsorted())
                        .build()
                ).orElse(new Sort());

        this.setPage(Page.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .sort(sort)
                .build()
        );

    }

    public ApiPageableResponse(org.springframework.data.domain.Page<T> page) {
        this(page, HttpStatus.OK);
    }
}
