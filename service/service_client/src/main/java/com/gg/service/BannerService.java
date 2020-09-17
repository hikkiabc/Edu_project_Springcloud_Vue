package com.gg.service;

import com.gg.beans.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerService {
    List<Banner> getAllBanner();
}
