package com.bcsd.project.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import com.bcsd.project.domain.OrderDrug;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 订单药品
 */
@Data
public class OrderDrugDTO {

    private List<OrderDrug> orderDrugList;

    private String remarks;


}