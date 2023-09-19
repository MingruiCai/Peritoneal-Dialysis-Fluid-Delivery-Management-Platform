package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("article")
public class Article extends BaseInfo {

    private String title;

    private String status;

    private String content;

    private String contentBase64;

    private String articleType;

    private Integer readingNumber;

    private String delFlag;

    private String thumbnailUrl;

    @TableField(exist = false)
    private String createTimeStart;

    @TableField(exist = false)
    private String createTimeEnd;

}