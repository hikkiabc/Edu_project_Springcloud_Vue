package com.gg.schedual;

import com.gg.service.StatisticsService;
import com.gg.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduleTask {
    @Autowired
    StatisticsService statisticsService;

    @Scheduled(cron = "0 0 1 * * ? ")
    public void generateStatistics() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(DateUtil.getDayFromToday(new Date(), -1));
        statisticsService.getRegisterCountByDate(dateStr);
    }

}
