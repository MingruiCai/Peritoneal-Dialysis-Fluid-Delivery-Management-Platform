package com.bcsd.project.domain.vo;

import lombok.Data;

@Data
public class HospitalVO{
    private Integer id;
    private String hospitalName;
    private String hospitalAddress;
    private String contacts;
    private String telephone;
    private String type;
    private String delFlag;
    private String createBy;
    private String createTime;
}