package com.bcsd.project.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class OrderInfoVO {

    private String id;
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

    private String nickName; //患者名

    /**
     * 身份证号
     */
    private String identity;

    private String hospitalName; //医院和配送方名称

    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}