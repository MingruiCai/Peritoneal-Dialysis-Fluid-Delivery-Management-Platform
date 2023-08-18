package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单药品
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("order_drug")
public class OrderDrug  extends BaseInfo {

    private Integer orderId;

    private Integer hospitalDrugId;

    private Integer orderNumber;

    private Integer completedNumber;

}