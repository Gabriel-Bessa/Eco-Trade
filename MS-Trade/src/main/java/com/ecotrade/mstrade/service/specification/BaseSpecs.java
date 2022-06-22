package com.ecotrade.mstrade.service.specification;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.StringJoiner;

public interface BaseSpecs<T> {

    @NonNull
    default Specification<T> buildSpecAnd(Specification<T> pSpec1) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.and(lPredicate1);
        };
    }

    @NonNull
    default Specification<T> buildSpecOr(Specification<T> pSpec1) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.and(lPredicate1);
        };
    }

    @NonNull
    default Specification<T> buildSpecAnd(Specification<T> pSpec1, Specification<T> pSpec2) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            Predicate lPredicate2 = pSpec2.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.and(lPredicate1, lPredicate2);
        };
    }

    @NonNull
    default Specification<T> buildSpecOr(Specification<T> pSpec1, Specification<T> pSpec2) {
        return (root, query, criteriaBuilder) -> {
            Predicate lPredicate1 = pSpec1.toPredicate(root, query, criteriaBuilder);
            Predicate lPredicate2 = pSpec2.toPredicate(root, query, criteriaBuilder);
            return criteriaBuilder.or(lPredicate1, lPredicate2);
        };
    }

    @NonNull
    default String concatLike(String field) {
        return new StringJoiner("", "%", "%")
                .add(field.toUpperCase())
                .toString();
    }

    @NonNull
    default Specification<T> byLike(SingularAttribute<T, String> attribute, String data) {
        return (root, query, cb) -> {
            if (StringUtils.isNotEmpty(data)) {
                return cb.like(cb.upper(root.get(attribute)), concatLike(data));
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> byEquals(SingularAttribute<T, D> attribute, D data) {
        return (root, query, cb) -> {
            if ((data instanceof Boolean && BooleanUtils.isTrue((Boolean) data))
                    || (data instanceof String && StringUtils.isNotEmpty((String) data))
                    || (data != null)) {
                return cb.equal(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <D> Specification<T> byIn(SingularAttribute<T, D> attribute, List<D> data) {
        return (root, query, cb) -> {
            if (data != null && !CollectionUtils.isEmpty(data)) {
                return root.get(attribute).in(data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <A, N, D extends A> Specification<T> byAbstractEqualsJoin(SingularAttribute<T, D> attributeJoin, SingularAttribute<A, N> attribute, N data) {
        return (root, query, cb) -> {
            Join<T, D> join = root.join(attributeJoin);
            if ((data instanceof Boolean && BooleanUtils.isTrue((Boolean) data))
                    || (data instanceof String && StringUtils.isNotEmpty((String) data))
                    || (data != null)) {
                return cb.equal(join.get(attribute), data);
            }
            return cb.and();
        };
    }
                
    default <D> Specification<T> byNotEquals(SingularAttribute<T, D> attribute, D data) {
        return (root, query, cb) -> {
            if ((data instanceof Boolean && BooleanUtils.isTrue((Boolean) data))
                    || (data instanceof String && StringUtils.isNotEmpty((String) data))
                    || (data != null)) {
                return cb.notEqual(root.get(attribute), data);
            }
            return cb.and();
        };
    }

    default <A, N> Specification<T> byEqualsSetJoin(SetAttribute<T, A> attributeJoin, SingularAttribute<A, N> attribute, N data) {
        return (root, query, cb) -> {
            Join<T, A> join = root.join(attributeJoin);
            if ((data instanceof Boolean && BooleanUtils.isTrue((Boolean) data))
                    || (data instanceof String && StringUtils.isNotEmpty((String) data))
                    || (data != null)) {
                return cb.equal(join.get(attribute), data);
            }
            return cb.and();
        };
    }

    @NonNull
    default <A, N> Specification<T> byEqualsJoinSpec(SingularAttribute<T, A> attributeJoin, SingularAttribute<A, N> attribute, N data) {
        return (root, query, cb) -> {
            Join<T, A> join = root.join(attributeJoin);
            if ((data instanceof Boolean && BooleanUtils.isTrue((Boolean) data))
                    || (data instanceof String && StringUtils.isNotEmpty((String) data))
                    || (data != null)) {
                return cb.equal(join.get(attribute), data);
            }
            return cb.and();
        };
    }
}
