package com.marlonnunes.carrental.repository;

import com.marlonnunes.carrental.model.Vehicle;
import com.marlonnunes.carrental.model.enums.Color;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification {

    public static Specification<Vehicle> byCriteria(List<Long> ids, List<String> numberPlates, List<Color> colors,
                                                    List<String> makes, List<String> models){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(!CollectionUtils.isEmpty(ids)){
                predicates.add(root.get("id").in(ids));
            }

            if(!CollectionUtils.isEmpty(numberPlates)){
                Predicate numberPlatePredicate = cb.or(numberPlates.stream()
                        .map(plate -> cb.like(root.get("numberPlate"), "%" + plate + "%"))
                        .toArray(Predicate[]::new));

                predicates.add(numberPlatePredicate);
            }

            if(!CollectionUtils.isEmpty(colors)){
                predicates.add(root.get("color").in(colors));
            }

            if(!CollectionUtils.isEmpty(makes)){
                Predicate makePredicate = cb.or(makes.stream()
                        .map(make -> cb.like(root.get("make"), "%" + make + "%"))
                        .toArray(Predicate[]::new));

                predicates.add(makePredicate);
            }

            if(!CollectionUtils.isEmpty(models)){
                Predicate modelPredicate = cb.or(models.stream()
                        .map(model -> cb.like(root.get("model"), "%" + model + "%"))
                        .toArray(Predicate[]::new));

                predicates.add(modelPredicate);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
