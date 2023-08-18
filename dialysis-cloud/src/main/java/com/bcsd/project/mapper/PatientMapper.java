package com.bcsd.project.mapper;


import com.bcsd.project.domain.Patient;

import java.util.List;

public interface PatientMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Patient record);

    int insertSelective(Patient record);

    Patient selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Patient record);

    int updateByPrimaryKey(Patient record);

    List<Patient> getPatientList(Patient record);
}