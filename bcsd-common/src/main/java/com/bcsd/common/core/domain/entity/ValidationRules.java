package com.bcsd.common.core.domain.entity;

import lombok.Data;

/**
 * 验证规则 ly_validation_rules
 * 
 * @author bcsd
 */
@Data
public class ValidationRules
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String validation;//验证规则

    private String prompt;  //提示语

}
