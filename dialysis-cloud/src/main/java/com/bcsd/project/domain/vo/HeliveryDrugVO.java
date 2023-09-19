package com.bcsd.project.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 医院药品信息
 */
@Data
public class HeliveryDrugVO{
    private Integer id;
    private String name;

    private String code;

    private Integer hospitalId;

    private String specifications;

    private String approvalNumber;

    private String manufacturer;

    private String packagingSpecifications;

    private Integer deliveryId;

    private String delFlag;
    private String createBy;
    private String createTime;
    private String deliveryName;
    private String deliveryCode;
    private BigDecimal price;

}