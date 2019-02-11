package com.eleme.schedule;

import com.eleme.dao.AltDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledService {

    @Autowired
    private AltDao altDao;


    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void sheduled(){

        altDao.resetUseNum();
        log.info("使用次数清零");
    }
}
