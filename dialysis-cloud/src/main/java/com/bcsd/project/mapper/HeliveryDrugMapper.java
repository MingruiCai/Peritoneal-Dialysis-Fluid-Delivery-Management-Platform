package com.bcsd.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bcsd.project.domain.HeliveryDrug;
import com.bcsd.project.domain.vo.HeliveryDrugVO;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface HeliveryDrugMapper extends BaseMapper<HeliveryDrug> {

    List<HeliveryDrugVO> selectList(HeliveryDrug deliveryDrug);

    int deleteByPrimaryKey(Integer id);

    int insert(HeliveryDrug record);

    int insertSelective(HeliveryDrug record);

    HeliveryDrug selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HeliveryDrug record);

    int updateByPrimaryKey(HeliveryDrug record);

    @Update("update hospital_drug set del_flag = '1' where id = #{id}")
    int updateById(Integer id);

    int checkHospitalId(Integer hospitalId);
}