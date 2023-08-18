package com.bcsd.project.service;

import com.bcsd.project.domain.Patient;

import java.util.List;

/**
 * @ClassName PatientService
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/6
 * @Version V1.0
 **/
public interface PatientService {

    List<Patient> getPatientList(Patient patient);
}
