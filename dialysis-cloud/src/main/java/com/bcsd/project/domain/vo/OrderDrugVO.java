package com.bcsd.project.domain.vo;

import lombok.Data;

@Data
public class OrderDrugVO{

    private Integer id;

    private Integer orderId;

    private Integer hospitalDrugId;

    private Integer orderNumber;

    private Integer deliveryNumber;  //交付数量

    private Integer completedNumber;  //已完成数量

    private String hospitalDrugName;

    private String hospitalDrugCode;

    private String deliveryDrugName;

    private String deliveryDrugCode;

    private String specifications;

    private String packagingSpecifications;



}