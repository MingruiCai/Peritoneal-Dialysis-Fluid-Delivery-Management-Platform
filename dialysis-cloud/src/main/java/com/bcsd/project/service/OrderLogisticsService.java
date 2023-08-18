package com.bcsd.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.project.domain.HeliveryDrug;
import com.bcsd.project.domain.OrderLogistics;
import com.bcsd.project.domain.dto.OrderLogisticsPageDTO;
import com.bcsd.project.domain.vo.HeliveryDrugVO;
import com.bcsd.project.domain.vo.OrderLogisticsVO;

import java.util.List;

/**
 * 订单物流管理
 *
 */
public interface OrderLogisticsService extends IService<OrderLogistics> {

	List<OrderLogisticsVO> list(OrderLogisticsPageDTO dto);

	AjaxResult selectByPrimaryKey(Integer id);

	AjaxResult outbound(Integer id);
}
