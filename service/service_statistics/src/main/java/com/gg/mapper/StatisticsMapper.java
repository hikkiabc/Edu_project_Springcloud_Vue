package com.gg.mapper;

import com.gg.beans.Statistics;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticsMapper extends JpaRepository<Statistics,Integer> {
    Integer deleteByCalculatedDate(Date date);
@Query(value="select * from statistics where calculated_date between :min and :max order by calculated_date ",nativeQuery=true)
    List<Map<String, String>> getStatisticsForChart(
        @Param("min") String min, @Param("max") String max);
}
