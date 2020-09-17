package com.gg.service;

import com.gg.beans.Statistics;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    Statistics getRegisterCountByDate(String date);

    List<Map<String, String>> getStatisticsForChart(/*String factor,*/ String min, String max);
}
