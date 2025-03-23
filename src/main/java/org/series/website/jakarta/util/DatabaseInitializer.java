package org.series.website.jakarta.util;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.series.website.jakarta.dao.SerieRepository;

@Singleton
@Startup
public class DatabaseInitializer {

    @Inject
    private SerieRepository serieRepository;

    @PostConstruct
    public void init() {
        if (serieRepository.list().isEmpty()) {
            serieRepository.register("Dark", 9, "Abgeschlossen");
            serieRepository.register("Stranger Things", 8, "Abgeschlossen");
            serieRepository.register("Breaking Bad", 10, "Abgeschlossen");
            System.out.println("Beispielserien wurden zur Datenbank hinzugefügt!");
        }
        System.out.println("DatabaseInitializer init() executed.");
    }
}

