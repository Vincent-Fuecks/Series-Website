package org.series.website.jakarta.services;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import org.series.website.jakarta.dao.SeriesRepository;
import org.series.website.jakarta.model.Series;

import java.util.List;

@Singleton
public class SeriesService {

    @Inject
    private SeriesRepository seriesRepository;
    
    public List<Series> list() {
        return seriesRepository.list();
    }

    public Long storeSeries(String name, int rating, String status) {
        return seriesRepository.storeSeries(name, rating, status);
    }

    public Series findById(Long id) {
        return seriesRepository.findById(id);
    }

}
