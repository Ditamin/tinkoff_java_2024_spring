package edu.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class LinkUpdaterScheduler {

    private final static Logger LOGGER = LogManager.getLogger();

    @Scheduled(fixedDelayString = "${app.scheduler.interval")
    void update() {
        LOGGER.info("Обновляю данные...");
    }
}
