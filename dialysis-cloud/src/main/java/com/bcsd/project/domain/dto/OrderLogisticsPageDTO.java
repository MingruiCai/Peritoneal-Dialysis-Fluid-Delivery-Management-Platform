package com.bcsd.project.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;

@Data
public class OrderLogisticsPageDTO extends BaseInfo {
    private String userId;
    private String logisticsNumber;
    private String status;
    private String orderNumber;
    @TableField(exist = false)
    private String createTimeStart;

    @TableField(exist = false)
    private String createTimeEnd;
}