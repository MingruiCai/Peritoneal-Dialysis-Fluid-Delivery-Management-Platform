package com.bcsd.project.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.project.domain.HeliveryDrug;
import com.bcsd.project.domain.OrderLogistics;
import com.bcsd.project.domain.dto.OrderLogisticsPageDTO;
import com.bcsd.project.domain.vo.HeliveryDrugVO;
import com.bcsd.project.domain.vo.OrderLogisticsVO;
import com.bcsd.project.enums.OrderStatusEnum;
import com.bcsd.project.mapper.HeliveryDrugMapper;
import com.bcsd.project.mapper.OrderInfoMapper;
import com.bcsd.project.mapper.OrderLogisticsDrugMapper;
import com.bcsd.project.mapper.OrderLogisticsMapper;
import com.bcsd.project.service.HeliveryDrugService;
import com.bcsd.project.service.OrderLogisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcsd.common.utils.SecurityUtils.getUsername;

/**
 * 订单物流管理信息实现类
 *
 * @author zhaofei
 * @date 2023-07-13 15:39:39
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements OrderLogisticsService {

	@Autowired
	private OrderLogisticsDrugMapper orderLogisticsDrugMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;

	/**
	 * 列表
	 * @param orderLogistics
	 * @return
	 */
	@Override
	public List<OrderLogisticsVO> list(OrderLogisticsPageDTO orderLogistics) {
		return this.baseMapper.selectList(orderLogistics);
	}

	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	@Override
	public AjaxResult selectByPrimaryKey(Integer id) {
		Map<String, Object> res = new HashMap<>();
		OrderLogistics logistics = this.baseMapper.selectByPrimaryKey(id);
		//订单物流信息
		res.put("logistics", logistics);
		//订单物流药品信息
		res.put("orderLogistics", orderLogisticsDrugMapper.getByLogisticsId(id));
		//订单信息
		res.put("orderInfo", orderInfoMapper.selectInfoById(logistics.getOrderId()));
		return AjaxResult.success(res);
	}

	/**
	 * 修改查询
	 * @param id
	 * @return
	 */
	@Override
	public AjaxResult outbound(Integer id) {
		OrderLogistics orderLogistics = this.baseMapper.selectByPrimaryKey(id);
		if(ObjectUtils.isEmpty(orderLogistics)){
			return AjaxResult.error("物流不存在");
		}
		orderLogistics.setStatus(OrderStatusEnum.ONE.getType());
		this.baseMapper.updateByPrimaryKeySelective(orderLogistics);
		return AjaxResult.success();
	}

	@Override
	public List<Map<String, Object>> logisticsList(JSONObject jsonObject) {
		return baseMapper.logisticsList(jsonObject);
	}

}
