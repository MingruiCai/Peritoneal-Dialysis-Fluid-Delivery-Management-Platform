package com.bcsd.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bcsd.project.domain.OrderLogistics;
import com.bcsd.project.domain.dto.OrderLogisticsPageDTO;
import com.bcsd.project.domain.vo.OrderLogisticsVO;

import java.util.List;
import java.util.Map;

public interface OrderLogisticsMapper extends BaseMapper<OrderLogistics> {
    List<OrderLogisticsVO> selectList(OrderLogisticsPageDTO orderLogistics);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderLogistics record);

    OrderLogistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderLogistics record);

    int updateByPrimaryKey(OrderLogistics record);

    List<OrderLogistics> selectListByOrderId(Integer orderId);

    int getByOrderIdNotId(Map map);

}