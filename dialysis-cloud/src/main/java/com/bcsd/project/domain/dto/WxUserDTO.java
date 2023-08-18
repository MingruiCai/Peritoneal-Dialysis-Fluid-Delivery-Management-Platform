package com.bcsd.project.domain.dto;

import com.bcsd.common.sensitive.Sensitive;
import com.bcsd.common.sensitive.SensitiveTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 微信用户
 *
 */
@Data
public class WxUserDTO{
private static final long serialVersionUID = 1L;

    /**
   * 主键
   */
    @NotEmpty(message="id不能为空")
    private String id;
    /**
   * 备注信息
   */
    private String remark;
    /**
   * 昵称
   */
    @NotEmpty(message="姓名不能为空")
    private String nickName;
    /**
   * 性别（0：男，1：女，2：未知）
   */
    @NotEmpty(message="性别不能为空")
    private String sex;
    /**
   * 手机号码
   */
    @Sensitive(type = SensitiveTypeEnum.MOBILE_PHONE)
    @NotEmpty(message="手机号不能为空")
    private String phone;
    /**
   * 头像
   */
    private String headimgUrl;

    /**
     * 与就诊人关系（0：本人，1：家属，2：其他）
     */
    @NotEmpty(message="与就诊人关系不能为空")
    private String relationship;

    /**
     * 身份证号
     */
    @NotEmpty(message="身份证号不能为空")
    private String identity;

    /**
     * 身高
     */
    @NotEmpty(message="身高不能为空")
    private String height;
    private String heightVal;
    private String weightVal;

    /**
     * 体重
     */
    @NotEmpty(message="体重不能为空")
    private String weight;

    /**
     * 出生日期
     */
    @NotEmpty(message="出生日期不能为空")
    private String birthday;

    /**
     * 注册人姓名
     */
    private String registrantName;
}
