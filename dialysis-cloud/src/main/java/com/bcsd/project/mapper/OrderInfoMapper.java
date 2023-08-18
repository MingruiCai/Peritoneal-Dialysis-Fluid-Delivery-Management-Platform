package com.bcsd.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bcsd.project.domain.OrderInfo;
import com.bcsd.project.domain.dto.OrderInfoDTO;
import com.bcsd.project.domain.vo.OrderInfoVO;

import java.util.List;
import java.util.Map;

public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    OrderInfoVO selectInfoById(Integer id);

    List<OrderInfoVO> selectOrderList(OrderInfoDTO orderInfo);

    int selectOrderCount(String userId);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateStatusById(Map map);

    int checkHospitalId(Integer hospitalId);
}