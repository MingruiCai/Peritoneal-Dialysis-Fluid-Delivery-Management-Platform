package com.bcsd.project.domain.dto;

import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;

import java.util.List;

@Data
public class OrderInfoDTO  extends BaseInfo {

    //private String id;
    private String orderNumber;

    private List<String> status;

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
}