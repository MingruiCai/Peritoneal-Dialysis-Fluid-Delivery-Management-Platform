package com.bcsd.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.project.domain.HeliveryDrug;
import com.bcsd.project.domain.OrderLogistics;
import com.bcsd.project.domain.dto.OrderLogisticsPageDTO;
import com.bcsd.project.domain.vo.HeliveryDrugVO;
import com.bcsd.project.domain.vo.OrderLogisticsVO;
import com.bcsd.project.service.HeliveryDrugService;
import com.bcsd.project.service.OrderLogisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName 订单物流管理 OrderLogisticsController
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/11
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/orderLogistics")
public class OrderLogisticsController extends BaseController {
    @Autowired
    private OrderLogisticsService orderLogisticsService;

    /**
     * 列表分页
     * @param logistics
     * @return
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody OrderLogisticsPageDTO logistics) {
        startPage(logistics);
        List<OrderLogisticsVO> list = orderLogisticsService.list(logistics);
        return getDataTable(list);
    }


    /**
     * 根据id获取
     * @param jsonObject
     * @return
     */
    @PostMapping("getLogisticsById")
    public AjaxResult getLogisticsById(@RequestBody JSONObject jsonObject) {
        return orderLogisticsService.selectByPrimaryKey(jsonObject.getInteger("id"));
    }

    /**
     * 物流单明细分页
     * @param jsonObject
     * @return
     */
    @PostMapping("/logisticsList")
    public TableDataInfo logisticsList(@RequestBody JSONObject jsonObject) {
        startPage(jsonObject);
        List<Map<String,Object>> list = orderLogisticsService.logisticsList(jsonObject);
        return getDataTable(list);
    }


}
