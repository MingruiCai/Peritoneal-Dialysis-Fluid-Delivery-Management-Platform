package com.bcsd.project.mapper;

import com.bcsd.project.domain.Consultation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ConsultationMapper
 * @Description: TODO
 * @Author Mingrui
 * @Date 2023/7/13
 * @Version V1.0
 **/
public interface ConsultationMapper {
    int insertSelective(Consultation record);

    Consultation selectByPrimaryKey(Long id);

    List<Consultation> getConsultationList(Consultation record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Consultation record);

    List<Consultation> getPatientConsultationList(Consultation record);

    List<Consultation> highlightConsultationList(Consultation record);

    List<Consultation> getHospitalConsultationList(Consultation record);

    String getHospitalNameById(Integer hospitalId);

}

