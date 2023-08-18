package com.bcsd.project.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderLogisticsVO{

    private String logisticsNumber;

    private String orderNumber;

    private Long id;

    private Integer orderId;

    private String status;

    private String signatureUrl;

    private String boxUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date estimatedDate;

    private String remarks;

    private String userName;

    private String provinceName;

    private String cityName;

    private String countyName;

    private String detailInfo;

    private String telNum;

    private String fileUrl;

    private String isPay;

    private String nickName; //患者名

    /**
     * 身份证号
     */
    private String identity;

    private String hospitalName; //医院和配送方名称

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}