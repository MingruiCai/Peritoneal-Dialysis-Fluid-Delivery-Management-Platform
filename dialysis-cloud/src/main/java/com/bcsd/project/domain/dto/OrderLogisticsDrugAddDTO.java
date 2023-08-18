package com.bcsd.project.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderLogisticsDrugAddDTO {
    private List<OrderLogisticsDrugDTO> orderDrugList;
    private String remarks;
}