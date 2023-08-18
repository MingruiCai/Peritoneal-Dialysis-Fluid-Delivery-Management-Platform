package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  订单过程信息
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("order_operation_info")
public class OrderOperationInfo extends BaseInfo {

    private Long orderId;

    private String remarks;

    private String createId;

    private String type;
}