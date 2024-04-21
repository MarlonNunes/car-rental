package com.marlonnunes.carrental.repository;

import com.marlonnunes.carrental.model.Store;
import com.marlonnunes.carrental.model.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class StoreSpecification {

    public static Specification<Store> byCriteria(Collection<Long> ids, Collection<String> names, Collection<String> cnpjs,
                                                  LocalDate createdAtIni, LocalDate createdAtEnd, User authUser){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(!CollectionUtils.isEmpty(ids)){
                predicates.add(root.get("id").in(ids));
            }

            if(!CollectionUtils.isEmpty(names)){
                Predicate namesPredicate = cb.or(names.stream()
                        .map(name -> cb.like(root.get("legalName"), "%" + name + "%"))
                        .toArray(Predicate[]::new));
                predicates.add(namesPredicate);
            }

            if(!CollectionUtils.isEmpty(cnpjs)){
                Predicate cpfPredicate = cb.or(cnpjs.stream()
                        .map(cnpj -> cb.like(root.get("cnpj"), "%" + cnpj + "%"))
                        .toArray(Predicate[]::new));

                predicates.add(cpfPredicate);
            }

            if(Objects.nonNull(createdAtIni) || Objects.nonNull(createdAtEnd)){
                if(Objects.nonNull(createdAtIni) && Objects.nonNull(createdAtEnd)){
                    predicates.add(cb.between(root.get("createdAt"), createdAtIni, createdAtEnd));
                } else if (Objects.nonNull(createdAtIni)) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), createdAtIni));
                } else if (Objects.nonNull(createdAtEnd)) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), createdAtEnd));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
