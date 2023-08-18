package com.bcsd.project.controller;

import com.bcsd.common.annotation.Anonymous;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.BaseInfo;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.project.domain.Patient;
import com.bcsd.project.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName PatientController
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/6
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/patient")
public class PatientController extends BaseController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/list")
    @Anonymous
    public TableDataInfo list(@RequestBody Patient patient){
        startPage(patient);
        List<Patient> list = patientService.getPatientList(patient);
        return getDataTable(list);
    }


}
