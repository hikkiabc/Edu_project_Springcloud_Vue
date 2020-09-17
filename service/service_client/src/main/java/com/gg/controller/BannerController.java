package com.gg.controller;

import com.gg.beans.Banner;
import com.gg.mapper.BannerMapper;
import com.gg.service.BannerService;
import com.gg.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/front")
public class BannerController {
    @Autowired
    BannerService bannerService;
    @Autowired
    BannerMapper bannerMapper;
    @GetMapping("/banner")

    public CommonResult getAllBanners(){
        List<Banner> list=bannerService.getAllBanner();
        CommonResult commonResult = new CommonResult();
        commonResult.setData(list);
        return commonResult;
    }


}
