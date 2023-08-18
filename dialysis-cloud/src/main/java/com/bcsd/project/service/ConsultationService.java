package com.bcsd.project.service;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.framework.log.Jackson;
import com.bcsd.project.domain.Consultation;

import java.util.List;

/**
 * @ClassName ConsultationService
 * @Description: TODO
 * @Author Mingrui
 * @Date 2023/7/13
 * @Version V1.0
 **/
public interface ConsultationService {
    /**
     * 咨询管理员获取全部咨询列表
     * @param consultation
     */
    List<Consultation> getConsultationList(Consultation consultation);//获取咨询列表
    /**
     * 新增咨询
     * @param consultation
     */
    AjaxResult addConsultation(Consultation consultation);
    /**
     * 删除咨询
     * @param id
     */
    AjaxResult deleteConsultation(Long id);
    /**
     * 更新咨询
     * @param consultation
     */
    AjaxResult updateConsultation(Consultation consultation);
    /**
     * 根据id获取咨询信息
     * @param id
     */
    Consultation selectByPrimaryKey(Long id);
    /**
     * 获取患者咨询（本人和精华咨询）
     * @param consultation
     */
    List<Consultation> getPatientConsultationList(Consultation consultation);
    /**
     * 获取本医院咨询
     * @param consultation
     */
    List<Consultation> getHospitalConsultationList(Consultation consultation);
    /**
     * 获取精华咨询
     * @param consultation
     */
    List<Consultation> highlightConsultationList(Consultation consultation);
    /**
     * 根据id添加咨询回复
     * @param id
     */
    AjaxResult replyConsultation(Long id, String reply);
    /**
     * 根据id切换咨询分类（普通/精华）
     * @param id
     */
    AjaxResult switchNormalHighlight(Long id);
}

