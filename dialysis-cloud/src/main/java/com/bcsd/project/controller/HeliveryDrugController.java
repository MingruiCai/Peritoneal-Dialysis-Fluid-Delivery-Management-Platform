package com.bcsd.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.project.domain.HeliveryDrug;
import com.bcsd.project.domain.vo.HeliveryDrugVO;
import com.bcsd.project.service.HeliveryDrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName 医院药品管理 ArticlecController
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/11
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/hospitalDrug")
public class HeliveryDrugController extends BaseController {
    @Autowired
    private HeliveryDrugService heliveryDrugService;

    /**
     * 列表分页
     * @param hospital
     * @return
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody HeliveryDrug hospital) {
        startPage(hospital);
        List<HeliveryDrugVO> list = heliveryDrugService.list(hospital);
        return getDataTable(list);
    }

    /**
     * 新增修改
     * @param hospital
     * @return
     */
    @PostMapping("/addOrUpdate")
    public AjaxResult addOrUpdate(@RequestBody HeliveryDrug hospital) {
        heliveryDrugService.addOrUpdate(hospital);
        return AjaxResult.success();
    }

    /**
     * 根据id获取
     * @param jsonObject
     * @return
     */
    @PostMapping("getHeliveryDrugById")
    public AjaxResult getHeliveryDrugById(@RequestBody JSONObject jsonObject) {
        HeliveryDrug article = heliveryDrugService.selectByPrimaryKey(jsonObject.getInteger("id"));
        return AjaxResult.success(article);
    }

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @PostMapping("deleteByid")
    public AjaxResult deleteByid(@RequestBody JSONObject jsonObject) {
        heliveryDrugService.deleteArticle(jsonObject.getInteger("id"));
        return AjaxResult.success();
    }



}
