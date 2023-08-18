package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("order_info")
public class OrderInfo  extends BaseInfo {

    private String orderNumber;

    private String status;

    private String userId;

    private Integer hospitalId;

    private String userName;

    private String provinceName;

    private String cityName;

    private String countyName;

    private String detailInfo;

    private String telNum;

    private String remarks;

    private String fileUrl;

    private String isPay;
}