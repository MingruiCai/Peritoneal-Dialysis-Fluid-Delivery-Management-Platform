package com.bcsd.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bcsd.common.core.domain.BaseInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用药咨询信息
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("consultation")
public class Consultation extends BaseInfo {
    /**
     * 患者编号
     */
    private Long patientId;
    /**
     * 医院编号
     */
    private Integer hospitalId;

    /**
     * 医生编号
     */
    private Long doctorId;
    /**
     * 咨询内容
     */
    private String content;
    /**
     * 咨询内容上传图片
     */
    private String fileUrl;
    /**
     * 咨询回复
     */
    private String reply;
    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;
    /**
     * 咨询状态（是否回复）
     * 0表示未回复，1表示已回复
     */
    private String status;
    /**
     * 咨询分类（普通/精华）
     * 0表示普通，1表示精华
     */
    private String category;
    /**********不在consultation表里的字段*************/
    /**
     * 医院名字
     */
    private String hospitalName;
    /**
     * 医生名字
     */
    private String doctorName;

    @TableField(exist = false)
    private String createTimeStart;

    @TableField(exist = false)
    private String createTimeEnd;
}
