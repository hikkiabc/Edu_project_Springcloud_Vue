package com.gg.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Statistics {
    @Id
    @GeneratedValue
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date calculatedDate;
    private Long registerNum;
}
