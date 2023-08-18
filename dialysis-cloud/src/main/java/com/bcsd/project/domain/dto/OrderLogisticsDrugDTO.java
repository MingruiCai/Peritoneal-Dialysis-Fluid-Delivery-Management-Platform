package com.bcsd.project.domain.dto;

import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;

@Data
public class OrderLogisticsDrugDTO {
    private Integer orderId;

    private Integer hospitalDrugId;

    private Integer logisticsId;

    private Integer deliveryNumber;//本次配送数量

    private Integer orderNumber; //订单数量
}