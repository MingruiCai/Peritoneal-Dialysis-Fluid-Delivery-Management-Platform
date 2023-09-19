package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
    private String payUrl;

    /** 护士确认人 */
    private String hsqrBy;

    /** 护士确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hsqrTime;

    /** 药师审核人 */
    private String ysshBy;

    /** 药师审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ysshTime;
}