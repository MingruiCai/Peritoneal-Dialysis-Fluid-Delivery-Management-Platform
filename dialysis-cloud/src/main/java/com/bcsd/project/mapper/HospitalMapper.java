package com.bcsd.project.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bcsd.project.domain.Hospital;
import com.bcsd.project.domain.vo.HospitalVO;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface HospitalMapper extends BaseMapper<Hospital> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Hospital record);

    Hospital selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hospital record);

    int updateByPrimaryKey(Hospital record);

    List<HospitalVO> selectList(Hospital record);

    @Update("update hospital set del_flag = '1' where id = #{id}")
    int updateById(Integer id);

    int selNameById(Map map);
}