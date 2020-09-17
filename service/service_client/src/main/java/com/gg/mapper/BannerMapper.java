package com.gg.mapper;

import com.gg.beans.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerMapper  extends JpaRepository<Banner,String> {
}
