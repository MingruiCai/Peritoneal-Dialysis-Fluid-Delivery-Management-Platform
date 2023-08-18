package com.bcsd.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bcsd.project.domain.OrderLogisticsDrug;
import com.bcsd.project.domain.vo.OrderLogisticsDrugVO;

import java.util.List;

public interface OrderLogisticsDrugMapper extends BaseMapper<OrderLogisticsDrug> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderLogisticsDrug record);

    int insertSelective(OrderLogisticsDrug record);

    OrderLogisticsDrug selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderLogisticsDrug record);

    int updateByPrimaryKey(OrderLogisticsDrug record);

    List<OrderLogisticsDrug> getByOrderId(Integer orderId);

    List<OrderLogisticsDrugVO> getByLogisticsId(Integer logisticsId);
}