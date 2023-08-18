package com.bcsd.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.project.domain.HeliveryDrug;
import com.bcsd.project.domain.OrderDrug;
import com.bcsd.project.domain.OrderInfo;
import com.bcsd.project.domain.OrderLogisticsDrug;
import com.bcsd.project.domain.dto.OrderDrugDTO;
import com.bcsd.project.domain.dto.OrderInfoDTO;
import com.bcsd.project.domain.dto.OrderLogisticsDrugAddDTO;
import com.bcsd.project.domain.dto.OrderLogisticsDrugDTO;
import com.bcsd.project.domain.vo.HeliveryDrugVO;
import com.bcsd.project.domain.vo.OrderInfoVO;
import com.bcsd.project.service.HeliveryDrugService;
import com.bcsd.project.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName 订单管理 ArticlecController
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/11
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderInfoController extends BaseController {
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 列表分页
     *
     * @param orderInfo
     * @return
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody OrderInfoDTO orderInfo) {
        startPage(orderInfo);
        List<OrderInfoVO> list = orderInfoService.list(orderInfo);
        return getDataTable(list);
    }



    /**
     * 根据id获取订单详情
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("getOrderById")
    public AjaxResult getOrderById(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        if (id == null) {
            return AjaxResult.error("id不能为空");
        }
        return orderInfoService.getOrderById(id);
    }



    /**
     * PC端 订单关闭
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("orderClosePC")
    public AjaxResult orderClosePC(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        String remarks = jsonObject.getString("remarks");
        if (id == null) {
            return AjaxResult.error("id不能为空");
        }
        try {
            orderInfoService.orderClosePC(id,remarks);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 订单确认（护士确认订单）
     *
     * @param orderDrugList
     * @return
     */
    @PostMapping("orderConfirm")
    public AjaxResult orderConfirm(@RequestBody OrderDrugDTO orderDrugList) {
        return orderInfoService.orderConfirm(orderDrugList);
    }

    /**
     * 订单审核（医师审核）
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("orderExamine")
    public AjaxResult orderExamine(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        String remarks = jsonObject.getString("remarks");
        if (id == null) {
            return AjaxResult.error("id不能为空");
        }
        try {
            return orderInfoService.orderExamine(id,remarks);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 订单审核（医师驳回）
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("orderReject")
    public AjaxResult orderReject(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        String remarks = jsonObject.getString("remarks");
        if (id == null) {
            return AjaxResult.error("id不能为空");
        }
        try {
            return orderInfoService.orderReject(id,remarks);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }


    /**
     * 订单配送（配送方配送药品）
     *
     * @param orderDrugList
     * @return
     */
    @PostMapping("orderDelivery")
    public AjaxResult orderDelivery(@RequestBody OrderLogisticsDrugAddDTO orderDrugList) {
        return orderInfoService.orderDelivery(orderDrugList);
    }

    /**
     * 订单签收（配送方签收）
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("confirmReceiptPC")
    public AjaxResult confirmReceiptPC(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        String signatureUrl = jsonObject.getString("signatureUrl");
        String boxUrl = jsonObject.getString("boxUrl");
        String remarks = jsonObject.getString("remarks");
        if (id == null) {
            return AjaxResult.error("id不能为空");
        }
        try {
            return orderInfoService.confirmReceiptPC(id,signatureUrl,remarks,boxUrl);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }


}
