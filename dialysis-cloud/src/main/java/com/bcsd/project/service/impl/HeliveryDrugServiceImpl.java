package com.bcsd.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bcsd.project.domain.HeliveryDrug;
import com.bcsd.project.domain.Hospital;
import com.bcsd.project.domain.vo.HeliveryDrugVO;
import com.bcsd.project.mapper.HeliveryDrugMapper;
import com.bcsd.project.mapper.HospitalMapper;
import com.bcsd.project.service.HeliveryDrugService;
import com.bcsd.project.service.HospitalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.bcsd.common.utils.SecurityUtils.getUsername;

/**
 * 医院药品信息实现类
 *
 * @author zhaofei
 * @date 2023-07-13 15:39:39
 */
@Slf4j
@Service
@AllArgsConstructor
public class HeliveryDrugServiceImpl extends ServiceImpl<HeliveryDrugMapper, HeliveryDrug> implements HeliveryDrugService {

	/**
	 * 列表
	 * @param hospital
	 * @return
	 */
	@Override
	public List<HeliveryDrugVO> list(HeliveryDrug hospital) {
		return this.baseMapper.selectList(hospital);
	}

	/**
	 * 修改查询
	 * @param id
	 * @return
	 */
	@Override
	public HeliveryDrug selectByPrimaryKey(Integer id) {
		return this.baseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 新增修改
	 * @param heliveryDrug
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdate(HeliveryDrug heliveryDrug) {
		if (heliveryDrug.getId()!=null) {
			heliveryDrug.setUpdateBy(getUsername());
			heliveryDrug.setUpdateTime(new Date());
			this.baseMapper.updateByPrimaryKeySelective(heliveryDrug);
		} else {
			heliveryDrug.setCreateBy(getUsername());
			heliveryDrug.setCreateTime(new Date());
			heliveryDrug.setDelFlag("0");
			this.baseMapper.insertSelective(heliveryDrug);
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
