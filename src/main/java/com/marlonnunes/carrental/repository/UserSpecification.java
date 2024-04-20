package com.marlonnunes.carrental.repository;

import com.marlonnunes.carrental.model.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class UserSpecification {

    public static Specification<User> byCriteria(Collection<Long> ids, Collection<String> names, Collection<String> emails,
                                                 Collection<String> cpfs, LocalDate createdAtIni, LocalDate createdAtEnd, User authUser){

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(!CollectionUtils.isEmpty(ids)){
                predicates.add(root.get("id").in(ids));
            }

            if(!CollectionUtils.isEmpty(names)){
                Predicate namesPredicate = cb.or(names.stream()
                        .flatMap(name -> Stream.of(
                                cb.like(root.get("firstName"), "%" + name + "%"),
                                cb.like(root.get("lastName"), "%" + name + "%")
                        ))
                        .toArray(Predicate[]::new));

                predicates.add(namesPredicate);
            }

            if(!CollectionUtils.isEmpty(emails)){
                Predicate email1Predicate = cb.or(emails.stream()
                        .map(email -> cb.like(root.get("email"), "%" + email + "%"))
                        .toArray(Predicate[]::new));

                predicates.add(email1Predicate);
            }

            if(!CollectionUtils.isEmpty(cpfs)){
                Predicate cpfPredicate = cb.or(cpfs.stream()
                        .map(cpf -> cb.like(root.get("cpf"), "%" + cpf + "%"))
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
