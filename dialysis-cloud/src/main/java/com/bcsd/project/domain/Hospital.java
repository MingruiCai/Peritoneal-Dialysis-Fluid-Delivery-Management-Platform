package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医院和配送方信息
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("article")
public class Hospital extends BaseInfo {

    private String hospitalName;

    private String hospitalAddress;
    private String contacts;
    private String telephone;
    private String type;
    private String delFlag;

    @TableField(exist = false)
    private String createTimeStart;

    @TableField(exist = false)
    private String createTimeEnd;

}