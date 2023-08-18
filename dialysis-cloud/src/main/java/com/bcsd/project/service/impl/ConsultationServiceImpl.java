package com.bcsd.project.service.impl;

import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.domain.model.LoginUser;
import com.bcsd.project.domain.Consultation;
import com.bcsd.project.domain.vo.ThirdSession;
import com.bcsd.project.mapper.ConsultationMapper;
import com.bcsd.project.service.ConsultationService;
import com.bcsd.project.utils.ThirdSessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bcsd.common.utils.SecurityUtils.*;

/**
 * 用药咨询
 * @ClassName ConsultationServiceImpl
 * @Description: TODO
 * @Author Mingrui
 * @Date 2023/7/13
 **/
@Slf4j
@Service
public class ConsultationServiceImpl implements ConsultationService {
    @Autowired
    private ConsultationMapper consultationMapper;
    /**
     * 列表
     * @param consultation
     * @return
     */
    @Override
    public List<Consultation> getConsultationList(Consultation consultation) {

        return consultationMapper.getConsultationList(consultation);

    }
    /**
     * 新增咨询
     * @param consultation
     * @return
     */
    @Override
    public AjaxResult addConsultation(Consultation consultation) {
        consultation.setPatientId(Long.valueOf(ThirdSessionHolder.getWxUserId()));
        consultation.setHospitalName(consultationMapper.getHospitalNameById(consultation.getHospitalId()));
        consultation.setCreateBy(ThirdSessionHolder.getWxUserName());
        consultation.setUpdateBy(consultation.getCreateBy());
        consultation.setCreateTime(new Date());
        consultation.setUpdateTime(consultation.getCreateTime());
        consultation.setStatus("0");//未回复表示为0
        consultation.setCategory("0");
        consultationMapper.insertSelective(consultation);
        return AjaxResult.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public AjaxResult deleteConsultation(Long id) {
        consultationMapper.deleteByPrimaryKey(id);
        return AjaxResult.success();
    }
    /**
     * 患者更新咨询
     * @param consultation
     * @return
     */
    public AjaxResult updateConsultation(Consultation consultation){
        consultation.setUpdateTime(new Date());
        consultation.setUpdateBy(ThirdSessionHolder.getWxUserId());
        consultationMapper.updateByPrimaryKeySelective(consultation);
        return AjaxResult.success();
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public Consultation selectByPrimaryKey(Long id) {
        return consultationMapper.selectByPrimaryKey(id);

    }
    /**
     * 获取患者咨询（本人咨询）
     * @param consultation
     * @return
     */
    @Override
    public List<Consultation> getPatientConsultationList(Consultation consultation) {
        consultation.setPatientId(Long.valueOf(ThirdSessionHolder.getWxUserId()));
        return consultationMapper.getPatientConsultationList(consultation);
    }
    /**
     * 获取精华咨询
     * @param consultation
     * @return
     */
    public List<Consultation> highlightConsultationList(Consultation consultation){
        return consultationMapper.highlightConsultationList(consultation);
    }
    /**
     * 获取本医院咨询
     * @param consultation
     * @return
     */
    @Override
    public List<Consultation> getHospitalConsultationList(Consultation consultation) {
//        LoginUser loginUser = getLoginUser();
//        loginUser.getUser().getHospitalId();
        Integer hospitalId=consultation.getHospitalId();
        if (hospitalId==null){
            return consultationMapper.getConsultationList(consultation);
        }else{
            return consultationMapper.getHospitalConsultationList(consultation);
        }

    }
    /**
     * 新增医生回复
     * @param id
     * @param reply
     * @return
     */
    @Override
    public AjaxResult replyConsultation(Long id, String reply){
        Consultation consultation = selectByPrimaryKey(id);
        consultation.setDoctorId(getUserId());
        consultation.setUpdateBy(getUsername());
        consultation.setReply(reply);
        consultation.setStatus("1");//1表示已回复
        consultation.setCategory("0");//0表示普通
        consultation.setUpdateTime(new Date());
        consultation.setReplyTime(consultation.getUpdateTime());
        consultationMapper.updateByPrimaryKeySelective(consultation);
        return AjaxResult.success();
    }
    /**
     * 精华普通帖切换
     * 根据咨询记录ID切换帖子分类
     * @param id
     * @return
     */
    @Override
    public AjaxResult switchNormalHighlight(Long id){
        Consultation consultation= selectByPrimaryKey(id);
        //0表示普通，1表示精华
        if (consultation.getCategory().equals("0") ) {
            consultation.setCategory("1");
        } else  {
            consultation.setCategory("0");
        }
        consultation.setUpdateBy(getUsername());
        consultationMapper.updateByPrimaryKeySelective(consultation);
        return AjaxResult.success();
    }
}
