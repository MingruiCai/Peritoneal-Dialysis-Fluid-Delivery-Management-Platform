package com.bcsd.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.project.domain.Article;
import com.bcsd.project.domain.Hospital;
import com.bcsd.project.domain.vo.HospitalVO;
import com.bcsd.project.mapper.HeliveryDrugMapper;
import com.bcsd.project.mapper.HospitalMapper;
import com.bcsd.project.mapper.OrderDrugMapper;
import com.bcsd.project.mapper.OrderInfoMapper;
import com.bcsd.project.service.HospitalService;
import com.bcsd.system.mapper.SysUserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.bcsd.common.utils.SecurityUtils.getUsername;

/**
 * 医院信息实现类
 *
 */
@Slf4j
@Service
@AllArgsConstructor
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements HospitalService {

	@Autowired
	private HeliveryDrugMapper heliveryDrugMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private SysUserMapper sysUserMapper;


	/**
	 * 列表
	 * @param hospital
	 * @return
	 */
	@Override
	public List<HospitalVO> list(Hospital hospital) {
		List<HospitalVO> hospitals = this.baseMapper.selectList(hospital);
		return hospitals;
	}

	/**
	 * 修改查询
	 * @param id
	 * @return
	 */
	@Override
	public Hospital selectByPrimaryKey(Integer id) {
		return this.baseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 新增修改
	 * @param hospital
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AjaxResult addOrUpdate(Hospital hospital) {
		Map map = new HashMap();
		map.put("hospitalName",hospital.getHospitalName());
		map.put("type",hospital.getType());
		int nameById = 0;
		if (hospital.getId()!=null) {
			map.put("id",hospital.getId());
			nameById = this.baseMapper.selNameById(map);
			if(nameById > 0){
				return AjaxResult.error("名称已存在，不可重复");
			}
			hospital.setUpdateBy(getUsername());
			hospital.setUpdateTime(new Date());
			this.baseMapper.updateByPrimaryKeySelective(hospital);
		} else {
			nameById = this.baseMapper.selNameById(map);
			if(nameById > 0){
				return AjaxResult.error("名称已存在，不可重复");
			}
			hospital.setCreateBy(getUsername());
			hospital.setCreateTime(new Date());
			hospital.setDelFlag("0");
			this.baseMapper.insertSelective(hospital);
		}
		return AjaxResult.success();
	}

	/**
	 * 删除
	 * @param id
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AjaxResult deleteArticle(Integer id) {
		int checkUser = sysUserMapper.checkHospitalId(id);
		if(checkUser > 0){
			return AjaxResult.error("已存在人员，不可删除");
		}
		int checkHospitalId = heliveryDrugMapper.checkHospitalId(id);
		if(checkHospitalId > 0){
			return AjaxResult.error("已存在药品，不可删除");
		}
		this.baseMapper.updateById(id);
		return AjaxResult.success();
	}
}
