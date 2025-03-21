package org.series.website.jakarta;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
@Singleton
public class SerieRepository {
    @PersistenceContext(unitName = "PostgresPU")
    EntityManager em;

    public Serie register(String name, int rating, String status) {
        Serie serie = new Serie();
        serie.setName(name);
        serie.setRating(rating);
        serie.setStatus(status);
        em.persist(serie);
        return serie;
    }

    public List<Serie> list() {
        CriteriaQuery<Serie> criteria = em.getCriteriaBuilder().createQuery(Serie.class);
        Root<Serie> root = criteria.from(Serie.class);
        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }

    public Serie findById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Serie> criteria = builder.createQuery(Serie.class);
        Root<Serie> root = criteria.from(Serie.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), id));
        return em.createQuery(criteria)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
