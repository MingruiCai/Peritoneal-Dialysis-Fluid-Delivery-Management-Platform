package com.bcsd.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bcsd.project.domain.Article;
import com.bcsd.project.domain.DeliveryDrug;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DeliveryDrugMapper extends BaseMapper<DeliveryDrug> {

    List<DeliveryDrug> selectList(DeliveryDrug deliveryDrug);

    int deleteByPrimaryKey(Integer id);

    int insert(DeliveryDrug record);

    int insertSelective(DeliveryDrug record);

    DeliveryDrug selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeliveryDrug record);

    int updateByPrimaryKey(DeliveryDrug record);

    @Update("update delivery_drug set del_flag = '1' where id = #{id}")
    int updateById(Integer id);
}