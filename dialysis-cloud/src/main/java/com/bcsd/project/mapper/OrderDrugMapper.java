package com.bcsd.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bcsd.project.domain.Hospital;
import com.bcsd.project.domain.OrderDrug;
import com.bcsd.project.domain.vo.OrderDrugVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDrugMapper extends BaseMapper<OrderDrug> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderDrug record);

    int insertOrderDrugList(@Param("records") List<OrderDrug> records);

    OrderDrug selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderDrug record);

    List<OrderDrug> selectByOrderId(Integer orderId);

    List<OrderDrugVO> selectListByOrderId(Integer orderId);

    int deleteByOrderId(Integer orderId);
}