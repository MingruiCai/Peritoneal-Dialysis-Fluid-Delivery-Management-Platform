package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医院药品信息
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("hospital_drug")
public class HeliveryDrug extends BaseInfo {
    private String name;

    private String code;

    private Integer hospitalId;

    private String specifications;

    private String approvalNumber;

    private String manufacturer;

    private String packagingSpecifications;

    private Integer deliveryId;

    private String delFlag;

    @TableField(exist = false)
    private String createTimeStart;

    @TableField(exist = false)
    private String createTimeEnd;

}