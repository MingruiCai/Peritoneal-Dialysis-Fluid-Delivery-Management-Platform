package com.bcsd.project.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class OrderLogisticsDrugVO {
    private Long id;
    private Integer orderId;

    private Integer hospitalDrugId;

    private Long logisticsId;

    private Integer deliveryNumber;

    private String hospitalDrugName;
    private String hospitalDrugCode;
    private String specifications;
    private String packagingSpecifications;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}