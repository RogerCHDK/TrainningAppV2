package com.rogerfitness.workoutsystem.jpa.specifications;

import com.rogerfitness.workoutsystem.jpa.entities.UserEntity;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Slf4j
@UtilityClass
public class UserSpecification {
    private static final String NAME = "name";
    private static final String USER_ID_SEQ = "userIdSeq";
    private static final String EMAIL = "email";

    public static Specification<UserEntity> filterByName(String name) {
        Specification<UserEntity> specs = Specification.where(null);
        if (StringUtils.isNotBlank(name)) {
            log.info("Filtering by name: {}", name);
            specs = (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
                    -> criteriaBuilder.equal(criteriaBuilder.lower(root.get(NAME)), name.toLowerCase());
        }
        return specs;
    }

    public static Specification<UserEntity> filterByUserId(Integer userIdSeq) {
      return (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
          if (null == userIdSeq){
              return criteriaBuilder.conjunction();
          }
          log.info("Filtering by userIdSeq: {}", userIdSeq);
          return criteriaBuilder.equal(root.get(USER_ID_SEQ),userIdSeq);
      };
    }
    public static Specification<UserEntity> filterByEmail(String email) {
        return (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (StringUtils.isBlank(email)){
                return criteriaBuilder.conjunction();
            }
            log.info("Filtering by email: {}", email);
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get(EMAIL)), email.toLowerCase());
        };
    }
}
