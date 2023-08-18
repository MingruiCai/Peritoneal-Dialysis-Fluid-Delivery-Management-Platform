package com.bcsd.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.common.utils.StringUtils;
import com.bcsd.project.domain.Consultation;
import com.bcsd.project.service.ConsultationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ConsultationController
 * @Description: TODO
 * @Author Mingrui
 * @Date 2023/7/13
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/consultation")
public class ConsultationController extends BaseController {

    @Autowired
    private ConsultationService consultationService;
    /**
     * 根据id查询用药咨询
     * @param jsonObject
     * @return
     */
    @PostMapping({"/getConsultationById"})
    public AjaxResult getConsultationById(@RequestBody JSONObject jsonObject) {
        Consultation consultation=consultationService.selectByPrimaryKey(jsonObject.getLong("id"));
        return AjaxResult.success(consultation);
    }
    /**
     * 新增医生回复
     * @param jsonObject
     * @return
     */
    @PostMapping("/replyConsultation")
    public AjaxResult replyConsultation(@RequestBody JSONObject jsonObject) {
        Long id =jsonObject.getLong("id");
        String reply=jsonObject.getString("reply");
        if(StringUtils.isEmpty(reply)){
            AjaxResult.error("回复内容不能为空");
        }
        return consultationService.replyConsultation(id,reply);
    }
    /**
     * 精华普通帖切换
     * 根据咨询记录ID切换帖子分类
     * @param jsonObject
     * @return
     */
    @PostMapping("/switchNormalHighlight")
    public AjaxResult switchNormalHighlight(@RequestBody JSONObject jsonObject) {
        return consultationService.switchNormalHighlight(jsonObject.getLong("id"));
    }
    /**
     * 获取医生所在医院的咨询列表
     * @param consultation
     * @return
     */
    @PostMapping("/hospitalConsultationList")
    public TableDataInfo getHospitalConsultationList(@RequestBody Consultation consultation){
        startPage(consultation);
        List<Consultation> hospitalConsultationList=consultationService.getHospitalConsultationList(consultation);
        return getDataTable(hospitalConsultationList);
    }
    /**
     * 咨询管理员获取全部咨询列表分页
     * @param consultation
     * @return
     */
    @PostMapping("/getConsultationList")
    public TableDataInfo getConsultationList(@RequestBody Consultation consultation) {
        startPage(consultation);
        List<Consultation> consultationList = consultationService.getConsultationList(consultation);
        return getDataTable(consultationList);
    }

}

