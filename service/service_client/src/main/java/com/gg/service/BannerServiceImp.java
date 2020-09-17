package com.gg.service;

import com.gg.beans.Banner;
import com.gg.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BannerServiceImp implements BannerService {
    @Autowired
    BannerMapper bannerMapper;
    @Override
//    @Cacheable(cacheNames = "banner")
    public List<Banner> getAllBanner() {
//        PageRequest pageRequest = PageRequest.of(Sort.by("create_time").ascending());
        return bannerMapper.findAll(Sort.by("createTime").ascending());

    }
}
