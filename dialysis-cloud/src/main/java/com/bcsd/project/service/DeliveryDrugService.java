package com.bcsd.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bcsd.project.domain.DeliveryDrug;
import com.bcsd.project.domain.Hospital;

import java.util.List;

/**
 * 配送方药品信息
 *
 */
public interface DeliveryDrugService extends IService<DeliveryDrug> {

	List<DeliveryDrug> list(DeliveryDrug hospital);

	void addOrUpdate(DeliveryDrug hospital);

	DeliveryDrug selectByPrimaryKey(Integer id);

	void deleteArticle(Integer id);
}
