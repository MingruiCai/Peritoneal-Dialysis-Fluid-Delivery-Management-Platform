package com.bcsd.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.common.utils.StringUtils;
import com.bcsd.project.domain.DeliveryDrug;
import com.bcsd.project.service.DeliveryDrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName 配送方药品管理 ArticlecController
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/11
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/deliverylDrug")
public class DeliveryDrugController extends BaseController {
    @Autowired
    private DeliveryDrugService deliveryDrugService;

    /**
     * 列表分页
     * @param hospital
     * @return
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody DeliveryDrug hospital) {
        startPage(hospital);
        List<DeliveryDrug> list = deliveryDrugService.list(hospital);
        return getDataTable(list);
    }

    /**
     * 新增修改
     * @param hospital
     * @return
     */
    @PostMapping("/addOrUpdate")
    public AjaxResult addOrUpdate(@RequestBody DeliveryDrug hospital) {
        if(StringUtils.isEmpty(hospital.getName())){
            AjaxResult.error("药品名称不能为空");
        }
        if(StringUtils.isEmpty(hospital.getCode())){
            AjaxResult.error("药品编码不能为空");
        }
        deliveryDrugService.addOrUpdate(hospital);
        return AjaxResult.success();
    }

    /**
     * 根据id获取
     * @param jsonObject
     * @return
     */
    @PostMapping("getDeliveryDrugById")
    public AjaxResult getDeliveryDrugByid(@RequestBody JSONObject jsonObject) {
        DeliveryDrug article = deliveryDrugService.selectByPrimaryKey(jsonObject.getInteger("id"));
        return AjaxResult.success(article);
    }

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @PostMapping("deleteByid")
    public AjaxResult deleteByid(@RequestBody JSONObject jsonObject) {
        deliveryDrugService.deleteArticle(jsonObject.getInteger("id"));
        return AjaxResult.success();
    }



}
