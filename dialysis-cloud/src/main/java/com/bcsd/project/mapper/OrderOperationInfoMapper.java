package com.bcsd.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bcsd.project.domain.OrderOperationInfo;

import java.util.List;

public interface OrderOperationInfoMapper extends BaseMapper<OrderOperationInfo> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderOperationInfo record);

    OrderOperationInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderOperationInfo record);

    List<OrderOperationInfo> selectByOrderId(Integer orderId);
}