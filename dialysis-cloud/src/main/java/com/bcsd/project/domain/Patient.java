package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("patient")
public class Patient extends BaseInfo {
    private Long id;

    private String patientName;

    private String identity;

    private String phoneNumber;

    private String birthday;

    private String sex;

    private String remarks;

}