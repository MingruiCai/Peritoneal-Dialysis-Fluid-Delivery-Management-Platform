package com.bcsd.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.project.domain.Article;
import com.bcsd.project.domain.Hospital;
import com.bcsd.project.domain.vo.HospitalVO;

import java.util.List;

/**
 * 医院信息
 *
 */
public interface HospitalService extends IService<Hospital> {

	List<HospitalVO> list(Hospital hospital);

	AjaxResult addOrUpdate(Hospital hospital);

	Hospital selectByPrimaryKey(Integer id);

	AjaxResult deleteArticle(Integer id);
}
