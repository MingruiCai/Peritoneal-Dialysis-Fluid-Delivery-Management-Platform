package com.bcsd.project.service.impl;

import com.bcsd.project.domain.Patient;
import com.bcsd.project.mapper.PatientMapper;
import com.bcsd.project.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PatientServiceImpl
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/6
 * @Version V1.0
 **/
@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public List<Patient> getPatientList(Patient patient) {
        return patientMapper.getPatientList(patient);
    }
}
