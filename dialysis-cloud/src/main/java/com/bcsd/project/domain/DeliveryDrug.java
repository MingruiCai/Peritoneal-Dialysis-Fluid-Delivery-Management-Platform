package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配送方药品信息
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("delivery_drug")
public class DeliveryDrug extends BaseInfo {

    private String name;

    private String code;

    private Integer hospitalId;

    private String specifications;

    private String approvalNumber;

    private String manufacturer;

    private String packagingSpecifications;

    private String delFlag;
}