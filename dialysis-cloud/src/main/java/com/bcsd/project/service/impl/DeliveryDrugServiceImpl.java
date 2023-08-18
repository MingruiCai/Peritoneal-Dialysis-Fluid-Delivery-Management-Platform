package com.bcsd.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bcsd.project.domain.DeliveryDrug;
import com.bcsd.project.mapper.DeliveryDrugMapper;
import com.bcsd.project.service.DeliveryDrugService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.bcsd.common.utils.SecurityUtils.getUsername;

/**
 * 配送方药品信息实现类
 *
 * @author zhaofei
 * @date 2023-07-13 15:39:39
 */
@Slf4j
@Service
@AllArgsConstructor
public class DeliveryDrugServiceImpl extends ServiceImpl<DeliveryDrugMapper, DeliveryDrug> implements DeliveryDrugService {

	/**
	 * 列表
	 * @param hospital
	 * @return
	 */
	@Override
	public List<DeliveryDrug> list(DeliveryDrug hospital) {
		return this.baseMapper.selectList(hospital);
	}

	/**
	 * 修改查询
	 * @param id
	 * @return
	 */
	@Override
	public DeliveryDrug selectByPrimaryKey(Integer id) {
		return this.baseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 新增修改
	 * @param hospital
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdate(DeliveryDrug hospital) {
		if (hospital.getId()!=null) {
			hospital.setUpdateBy(getUsername());
			hospital.setUpdateTime(new Date());
			this.baseMapper.updateByPrimaryKeySelective(hospital);
		} else {
			hospital.setCreateBy(getUsername());
			hospital.setCreateTime(new Date());
			hospital.setDelFlag("0");
			this.baseMapper.insertSelective(hospital);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteArticle(Integer id) {
		this.baseMapper.updateById(id);
	}
}
