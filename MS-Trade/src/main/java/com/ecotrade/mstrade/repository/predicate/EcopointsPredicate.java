package com.ecotrade.mstrade.repository.predicate;

import com.ecotrade.mstrade.domain.Advertisement;
import com.ecotrade.mstrade.domain.Advertisement_;
import com.ecotrade.mstrade.domain.Donation;
import com.ecotrade.mstrade.domain.Donation_;
import com.ecotrade.mstrade.domain.Offer;
import com.ecotrade.mstrade.domain.Offer_;
import com.ecotrade.mstrade.domain.User_;
import com.ecotrade.mstrade.domain.enuns.AdvertisementStatus;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@RequiredArgsConstructor
public class EcopointsPredicate {
    private final EntityManager em;

    public Object getEcopoints(Long userId, double value) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery(Object.class);
        Root<Advertisement> root = query.from(Advertisement.class);
        Join<Advertisement, Offer> offerJoin = root.join(Advertisement_.ACCEPTED_OFFER);

        Subquery<Double> subQuery = getSubQuery(userId, cb, query);
        query.select(cb.diff(cb.prod(cb.count(root.get(Advertisement_.id)), value), subQuery));
        query.where(cb.equal(root.get(Advertisement_.STATUS), AdvertisementStatus.RECIVED),
                cb.or(
                        cb.equal(root.get(Advertisement_.ADVERTISER).get(User_.ID), userId),
                        cb.equal(offerJoin.get(Offer_.OFFERER).get(User_.ID), userId)
                )
        );
        return em.createQuery(query).getSingleResult();
    }

    private Subquery<Double> getSubQuery(Long userId, CriteriaBuilder cb, CriteriaQuery<Object> query) {
        Subquery<Double> subquery = query.subquery(Double.class);
        Root<Donation> from = subquery.from(Donation.class);
        return subquery.select(
                    cb.coalesce(cb.sum(from.get(Donation_.QUANTITY)), 0D)
                ).where(
                cb.equal(from.get(Donation_.user).get(User_.ID), userId)
        );
    }
}
