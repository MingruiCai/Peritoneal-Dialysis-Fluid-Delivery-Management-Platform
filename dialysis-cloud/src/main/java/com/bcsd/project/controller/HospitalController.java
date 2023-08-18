package com.bcsd.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.common.utils.StringUtils;
import com.bcsd.project.domain.Article;
import com.bcsd.project.domain.Hospital;
import com.bcsd.project.domain.vo.HospitalVO;
import com.bcsd.project.service.ArticleService;
import com.bcsd.project.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName 医院和配送单位管理 ArticlecController
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/11
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/hospital")
public class HospitalController extends BaseController {
    @Autowired
    private HospitalService hospitalService;

    /**
     * 列表分页
     * @param hospital
     * @return
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody Hospital hospital) {
        startPage(hospital);
        List<HospitalVO> list = hospitalService.list(hospital);
        return getDataTable(list);
    }

    /**
     * 新增修改
     * @param hospital
     * @return
     */
    @PostMapping("/addOrUpdate")
    public AjaxResult addOrUpdate(@RequestBody Hospital hospital) {
        if(StringUtils.isEmpty(hospital.getHospitalName())){
            AjaxResult.error("名称不能为空");
        }
        if(StringUtils.isEmpty(hospital.getType())){
            AjaxResult.error("类型不能为空");
        }
        return hospitalService.addOrUpdate(hospital);
    }

    /**
     * 根据id获取
     * @param jsonObject
     * @return
     */
    @PostMapping("getArticle")
    public AjaxResult getArticle(@RequestBody JSONObject jsonObject) {
        Hospital article = hospitalService.selectByPrimaryKey(jsonObject.getInteger("id"));
        return AjaxResult.success(article);
    }

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @PostMapping("deleteArticle")
    public AjaxResult deleteArticle(@RequestBody JSONObject jsonObject) {
        return hospitalService.deleteArticle(jsonObject.getInteger("id"));
    }



}
