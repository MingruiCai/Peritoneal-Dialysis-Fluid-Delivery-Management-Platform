package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("order_logistics")
public class OrderLogistics extends BaseInfo {

    private String logisticsNumber;

    private Integer orderId;

    private String status;

    private String signatureUrl;

    private String boxUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date estimatedDate;

    private String remarks;

    /** 签收人 */
    private String qsBy;

    /** 签收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qsTime;
}