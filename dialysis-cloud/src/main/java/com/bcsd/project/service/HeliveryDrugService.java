package com.bcsd.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bcsd.project.domain.HeliveryDrug;
import com.bcsd.project.domain.vo.HeliveryDrugVO;

import java.util.List;

/**
 * 医院药品信息
 *
 */
public interface HeliveryDrugService extends IService<HeliveryDrug> {

	List<HeliveryDrugVO> list(HeliveryDrug hospital);

	void addOrUpdate(HeliveryDrug hospital);

	HeliveryDrug selectByPrimaryKey(Integer id);

	void deleteArticle(Integer id);
}
