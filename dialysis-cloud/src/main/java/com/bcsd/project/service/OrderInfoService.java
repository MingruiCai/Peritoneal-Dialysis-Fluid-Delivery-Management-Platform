package com.bcsd.project.service;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bcsd.common.core.domain.AjaxResult;
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

import java.util.List;

/**
 * 订单信息
 *
 */
public interface OrderInfoService extends IService<OrderInfo> {

	List<OrderInfoVO> list(OrderInfoDTO hospital);

	AjaxResult addOrUpdate(OrderInfo hospital);

	AjaxResult getOrderById(Integer id);

	void orderClose(Integer id,String remarks);

	void orderClosePC(Integer id,String remarks);

	AjaxResult orderConfirm(OrderDrugDTO orderDrugList);

	AjaxResult orderExamine(Integer id,String remarks);

	AjaxResult orderExamineBatch(JSONArray ids, String remarks);

	AjaxResult orderReject(Integer id,String remarks);

	AjaxResult orderDelivery(OrderLogisticsDrugAddDTO orderDrugList);

	AjaxResult confirmReceiptPC(Integer id,String signatureUrl,String remarks,String boxUrl);

}
