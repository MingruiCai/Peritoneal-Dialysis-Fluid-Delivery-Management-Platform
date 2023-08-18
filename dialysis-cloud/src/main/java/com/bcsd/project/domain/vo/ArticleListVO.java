package com.bcsd.project.domain.vo;

import com.bcsd.common.core.domain.BaseInfo;
import lombok.Data;

@Data
public class ArticleListVO extends BaseInfo {

    private String title;

    private String status;

    private String articleType;

    private Integer readingNumber;

    private String delFlag;

    private String thumbnailUrl;


}