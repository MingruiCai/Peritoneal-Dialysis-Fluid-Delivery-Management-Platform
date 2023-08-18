package com.bcsd.project.domain.vo;

import com.bcsd.common.sensitive.Sensitive;
import com.bcsd.common.sensitive.SensitiveTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 微信用户
 *
 */
@Data
public class WxUserVO {
private static final long serialVersionUID = 1L;

    /**
   * 主键
   */
    private String id;
    /**
   * 备注信息
   */
    private String remark;
    /**
   * 昵称
   */
    private String nickName;
    /**
   * 性别（0：男，1：女，2：未知）
   */
    private String sex;
    /**
   * 手机号码
   */
    private String phone;
    /**
   * 头像
   */
    private String headimgUrl;

    /**
     * 与就诊人关系（0：本人，1：家属，2：其他）
     */
    private String relationship;

    /**
     * 身份证号
     */
    private String identity;

    /**
     * 身高
     */
    private String height;
    private String heightVal;
    private String weightVal;

    /**
     * 体重
     */
    private String weight;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 注册人姓名
     */
    private String registrantName;
}
