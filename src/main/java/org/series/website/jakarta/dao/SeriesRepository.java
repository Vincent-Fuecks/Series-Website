package org.series.website.jakarta.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.series.website.jakarta.model.Series;

import java.util.List;

@Stateless
@Transactional
public class SeriesRepository {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    public Long storeSeries(String name, int rating, String status){
        Series series = new Series();
        series.setSeries(name, rating, status);
        em.persist(series);
        return series.id;
    }

    public  List<Series> list() {
        CriteriaQuery<Series> criteria = em.getCriteriaBuilder().createQuery(Series.class);
        Root<Series> root = criteria.from(Series.class);
        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }

    public Series findById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Series> criteria = builder.createQuery(Series.class);
        Root<Series> root = criteria.from(Series.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), id));
        return em.createQuery(criteria)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
