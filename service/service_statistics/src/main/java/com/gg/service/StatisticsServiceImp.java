package com.gg.service;

import com.gg.beans.Statistics;
import com.gg.feign.UserFeign;
import com.gg.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StatisticsServiceImp implements StatisticsService {
    @Autowired
    UserFeign userFeign;
    @Autowired
    StatisticsMapper statisticsMapper;
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Statistics getRegisterCountByDate(String date) {

        Integer registerCountByDate = userFeign.getRegisterCountByDate(date);
        Statistics statistics = new Statistics();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
           throw new RuntimeException("date format err");
        }
        statisticsMapper.deleteByCalculatedDate(date1);
        statistics.setCalculatedDate(date1);
        statistics.setRegisterNum(registerCountByDate.longValue());
       return statisticsMapper.save(statistics);
    }

    @Override
    public List/*<Map<String, String>>*/ getStatisticsForChart(/*String factor,*/ String min, String max) {

//        StringBuilder sql = new StringBuilder();
//        sql.append(" select "+factor+",calculated_date from statistics ");
//        Query query = entityManager.createNativeQuery(sql.toString());
//        List resultList = query.getResultList();
//        System.out.println(resultList);
        List resultList= statisticsMapper.getStatisticsForChart(min,max);
        return resultList;
    }
}
