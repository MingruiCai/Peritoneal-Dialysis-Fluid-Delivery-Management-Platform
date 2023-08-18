package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("order_logistics_drug")
public class OrderLogisticsDrug extends BaseInfo {
    private Integer orderId;

    private Integer hospitalDrugId;

    private Long logisticsId;

    private Integer deliveryNumber;

}