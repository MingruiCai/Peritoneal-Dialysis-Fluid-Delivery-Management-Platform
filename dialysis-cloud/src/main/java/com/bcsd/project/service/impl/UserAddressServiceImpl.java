package com.bcsd.project.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bcsd.project.config.CommonConstants;
import com.bcsd.project.domain.UserAddress;
import com.bcsd.project.mapper.UserAddressMapper;
import com.bcsd.project.service.UserAddressService;
import org.springframework.stereotype.Service;

/**
 * 用户收货地址
 *
 * @author JL
 * @date 2019-09-11 14:28:59
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

	@Override
	public boolean save(UserAddress entity) {
		setIsDefault(entity);
		return super.save(entity);
	}

	@Override
	public boolean updateById(UserAddress entity) {
		setIsDefault(entity);
		return super.updateById(entity);
	}

	/**
	 * 修改默认地址
	 * @param entity
	 */
	void setIsDefault(UserAddress entity){
		if(CommonConstants.YES.equals(entity.getIsDefault())){
			UserAddress userAddress = new UserAddress();
			userAddress.setIsDefault(CommonConstants.NO);
			super.update(userAddress, Wrappers.<UserAddress>lambdaQuery()
					.eq(UserAddress::getUserId,entity.getUserId())
					.eq(UserAddress::getIsDefault,CommonConstants.YES));
		}
	}
}
