package com.bcsd.project.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.utils.StringUtils;
import com.bcsd.common.utils.bean.BeanUtils;
import com.bcsd.project.config.WxMaConfiguration;
import com.bcsd.project.constants.ConfigConstant;
import com.bcsd.project.constants.WxMaConstants;
import com.bcsd.project.domain.OrderInfo;
import com.bcsd.project.domain.OrderLogistics;
import com.bcsd.project.domain.OrderOperationInfo;
import com.bcsd.project.domain.dto.WxUserDTO;
import com.bcsd.project.domain.vo.ThirdSession;
import com.bcsd.project.domain.dto.WxOpenDataDTO;
import com.bcsd.project.domain.WxUser;
import com.bcsd.project.domain.vo.WxUserVO;
import com.bcsd.project.enums.OrderStatusEnum;
import com.bcsd.project.handler.SubscribeHandler;
import com.bcsd.project.mapper.OrderInfoMapper;
import com.bcsd.project.mapper.OrderLogisticsMapper;
import com.bcsd.project.mapper.OrderOperationInfoMapper;
import com.bcsd.project.mapper.WxUserMapper;
import com.bcsd.project.service.WxUserService;
import com.bcsd.project.utils.ThirdSessionHolder;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.api.WxMpUserTagService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.bcsd.common.utils.SecurityUtils.getUserId;
import static com.bcsd.common.utils.SecurityUtils.getUsername;

/**
 * 微信用户
 *
 * @author www.joolun.com
 * @date 2019-03-25 15:39:39
 */
@Slf4j
@Service
@AllArgsConstructor
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

	private final WxMpService wxService;
	private final RedisTemplate redisTemplate;
	@Autowired
	private OrderLogisticsMapper orderLogisticsMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private OrderOperationInfoMapper operationInfoMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateRemark(WxUser entity) throws WxErrorException {
		String id = entity.getId();
		String remark = entity.getRemark();
		String openId = entity.getOpenId();
		entity = new WxUser();
		entity.setId(id);
		entity.setRemark(remark);
		super.updateById(entity);
		WxMpUserService wxMpUserService = wxService.getUserService();
		wxMpUserService.userUpdateRemark(openId,remark);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void tagging(String taggingType,Long tagId,String[] openIds) throws WxErrorException {
		WxMpUserTagService wxMpUserTagService = wxService.getUserTagService();
		WxUser wxUser;
		if("tagging".equals(taggingType)){
			for(String openId : openIds){
				wxUser = baseMapper.selectOne(Wrappers.<WxUser>lambdaQuery()
						.eq(WxUser::getOpenId,openId));
				Long[] tagidList = wxUser.getTagidList();
				List<Long> list = Arrays.asList(tagidList);
				list = new ArrayList<>(list);
				if(!list.contains(tagId)){
					list.add(tagId);
					tagidList = list.toArray(new Long[list.size()]);
					wxUser.setTagidList(tagidList);
					this.updateById(wxUser);
				}
			}
			wxMpUserTagService.batchTagging(tagId,openIds);
		}
		if("unTagging".equals(taggingType)){
			for(String openId : openIds){
				wxUser = baseMapper.selectOne(Wrappers.<WxUser>lambdaQuery()
						.eq(WxUser::getOpenId,openId));
				Long[] tagidList = wxUser.getTagidList();
				List<Long> list = Arrays.asList(tagidList);
				list = new ArrayList<>(list);
				if(list.contains(tagId)){
					list.remove(tagId);
					tagidList = list.toArray(new Long[list.size()]);
					wxUser.setTagidList(tagidList);
					this.updateById(wxUser);
				}
			}
			wxMpUserTagService.batchUntagging(tagId,openIds);
		}
	}

	@Override
	public List<WxUser> list(WxUser wxUser) {
		LambdaQueryWrapper<WxUser> wxUserLambdaQueryWrapper = Wrappers.<WxUser>lambdaQuery().eq(WxUser::getDelFlag,"0").eq(WxUser::getIsPerfect,"Y");
		if(StringUtils.isNotEmpty(wxUser.getNickName())){
			wxUserLambdaQueryWrapper.like(WxUser::getNickName,wxUser.getNickName());
		}
		if(StringUtils.isNotEmpty(wxUser.getPhone())){
			wxUserLambdaQueryWrapper.like(WxUser::getPhone,wxUser.getPhone());
		}
		if(StringUtils.isNotEmpty(wxUser.getIdentity())){
			wxUserLambdaQueryWrapper.like(WxUser::getIdentity,wxUser.getIdentity());
		}
		return this.baseMapper.selectList(wxUserLambdaQueryWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void synchroWxUser() throws WxErrorException {
		//先将已关注的用户取关
		WxUser wxUser = new WxUser();
		wxUser.setSubscribe(ConfigConstant.SUBSCRIBE_TYPE_NO);
		this.baseMapper.update(wxUser, Wrappers.<WxUser>lambdaQuery()
				.eq(WxUser::getSubscribe, ConfigConstant.SUBSCRIBE_TYPE_YES));
		WxMpUserService wxMpUserService = wxService.getUserService();
		this.recursionGet(wxMpUserService,null);
	}

	/**
	 * 递归获取
	 * @param nextOpenid
	 */
	void recursionGet(WxMpUserService wxMpUserService,String nextOpenid) throws WxErrorException {
		WxMpUserList userList = wxMpUserService.userList(nextOpenid);
		List<WxUser> listWxUser = new ArrayList<>();
		List<WxMpUser> listWxMpUser = getWxMpUserList(wxMpUserService,userList.getOpenids());
		listWxMpUser.forEach(wxMpUser -> {
			WxUser wxUser = baseMapper.selectOne(Wrappers.<WxUser>lambdaQuery()
					.eq(WxUser::getOpenId,wxMpUser.getOpenId()));
			if(wxUser == null){//用户未存在
				wxUser = new WxUser();
				wxUser.setSubscribeNum(1);
			}
			SubscribeHandler.setWxUserValue(wxUser,wxMpUser);
			listWxUser.add(wxUser);
		});
		this.saveOrUpdateBatch(listWxUser);
		if(userList.getCount() >= 10000){
			this.recursionGet(wxMpUserService,userList.getNextOpenid());
		}
	}

	/**
	 * 分批次获取微信粉丝信息 每批100条
	 * @param wxMpUserService
	 * @param openidsList
	 * @return
	 * @throws WxErrorException
	 * @author
	 */
	private List<WxMpUser> getWxMpUserList(WxMpUserService wxMpUserService, List<String> openidsList) throws WxErrorException {
		// 粉丝openid数量
		int count = openidsList.size();
		if (count <= 0) {
			return new ArrayList<>();
		}
		List<WxMpUser> list = Lists.newArrayList();
		List<WxMpUser> followersInfoList;
		int a = count % 100 > 0 ? count / 100 + 1 : count / 100;
		for (int i = 0; i < a; i++) {
			if (i + 1 < a) {
				log.debug("i:{},from:{},to:{}", i, i * 100, (i + 1) * 100);
				followersInfoList = wxMpUserService.userInfoList(openidsList.subList(i * 100, ((i + 1) * 100)));
				if (null != followersInfoList && !followersInfoList.isEmpty()) {
					list.addAll(followersInfoList);
				}
			}
			else {
				log.debug("i:{},from:{},to:{}", i, i * 100, count - i * 100);
				followersInfoList = wxMpUserService.userInfoList(openidsList.subList(i * 100, count));
				if (null != followersInfoList && !followersInfoList.isEmpty()) {
					list.addAll(followersInfoList);
				}
			}
		}
		log.debug("本批次获取微信粉丝数：",list.size());
		return list;
	}

	@Override
	public WxUser getByOpenId(String openId){
		return this.baseMapper.selectOne(Wrappers.<WxUser>lambdaQuery()
				.eq(WxUser::getOpenId,openId));
	}

	@Override
	public Object isLoginExist(String appId, String jsCode) throws WxErrorException{
		WxMaJscode2SessionResult jscode2session = WxMaConfiguration.getMaService(appId).jsCode2SessionInfo(jsCode);
		String key = WxMaConstants.THIRD_SESSION_BEGIN + ":" + jscode2session.getOpenid();
		Object o = redisTemplate.opsForValue().get(key);
		if(o == null){
			return null;
		}
		return o;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public WxUser loginMa(String appId, String jsCode) throws WxErrorException {
		WxMaJscode2SessionResult jscode2session = WxMaConfiguration.getMaService(appId).jsCode2SessionInfo(jsCode);
		WxUser wxUser = this.getByOpenId(jscode2session.getOpenid());
		if(wxUser==null) {
			//新增微信用户
			wxUser = new WxUser();
			wxUser.setAppType(ConfigConstant.WX_APP_TYPE_1);
			wxUser.setOpenId(jscode2session.getOpenid());
			wxUser.setSessionKey(jscode2session.getSessionKey());
			wxUser.setUnionId(jscode2session.getUnionid());
			wxUser.setIsPerfect("N");
			try{
				this.save(wxUser);
			}catch (DuplicateKeyException e){
				if(e.getMessage().contains("uk_appid_openid")){
					wxUser = this.getByOpenId(wxUser.getOpenId());
				}
			}
		}else {
			//更新SessionKey
			wxUser.setAppType(ConfigConstant.WX_APP_TYPE_1);
			wxUser.setOpenId(jscode2session.getOpenid());
			wxUser.setSessionKey(jscode2session.getSessionKey());
			wxUser.setUnionId(jscode2session.getUnionid());
			this.updateById(wxUser);
		}

		//String thirdSessionKey = UUID.randomUUID().toString();
		ThirdSession thirdSession = new ThirdSession();
		thirdSession.setAppId(appId);
		thirdSession.setSessionKey(wxUser.getSessionKey());
		thirdSession.setWxUserId(wxUser.getId());
		thirdSession.setOpenId(wxUser.getOpenId());
		thirdSession.setIsPerfect(wxUser.getIsPerfect());
		thirdSession.setWxUserName(wxUser.getNickName());
		//将3rd_session和用户信息存入redis，并设置过期时间
		String key = WxMaConstants.THIRD_SESSION_BEGIN + ":" + jscode2session.getOpenid();
		redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(thirdSession) , WxMaConstants.TIME_OUT_SESSION, TimeUnit.DAYS);
		wxUser.setSessionKey(jscode2session.getOpenid());
		return wxUser;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public WxUser saveOrUptateWxUser(WxOpenDataDTO wxOpenDataDTO) {
		WxMaUserService wxMaUserService = WxMaConfiguration.getMaService(wxOpenDataDTO.getAppId()).getUserService();
		WxMaUserInfo wxMaUserInfo = wxMaUserService.getUserInfo(wxOpenDataDTO.getSessionKey(), wxOpenDataDTO.getEncryptedData(), wxOpenDataDTO.getIv());
		WxUser wxUser = new WxUser();
		BeanUtil.copyProperties(wxMaUserInfo,wxUser);
		wxUser.setId(wxOpenDataDTO.getUserId());
		wxUser.setSex(wxMaUserInfo.getGender());
		wxUser.setHeadimgUrl(wxMaUserInfo.getAvatarUrl());
		baseMapper.updateById(wxUser);
		wxUser = baseMapper.selectById(wxUser.getId());
		return wxUser;
	}

	@Override
	public void logout(HttpServletRequest request) {
		String thirdSessionHeader = request.getHeader(ConfigConstant.HEADER_THIRDSESSION);
		if(StrUtil.isNotBlank(thirdSessionHeader)){
			//获取缓存中的ThirdSession
			String key = WxMaConstants.THIRD_SESSION_BEGIN  + ":" + thirdSessionHeader;
			redisTemplate.delete(key);
		}
	}

	@Override
	public WxUserVO getWxUserById(String id) {
		WxUser wxUser = baseMapper.selectById(id);
		WxUserVO vo = new WxUserVO();
		BeanUtil.copyProperties(wxUser,vo);
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AjaxResult uptateWxUser(WxUserDTO wxUserDTO) {
		if(!wxUserDTO.getRelationship().equals("0") && StringUtils.isEmpty(wxUserDTO.getRegistrantName())){
			return AjaxResult.error("注册人姓名不能为空");
		}
		Integer count = baseMapper.selectCount(Wrappers.<WxUser>lambdaQuery().eq(WxUser::getIdentity, wxUserDTO.getIdentity()).ne(WxUser::getId,wxUserDTO.getId()));
		if(count > 0){
			return AjaxResult.error("身份证号已存在");
		}
		WxUser wxUser = baseMapper.selectById(wxUserDTO.getId());
		if(ObjectUtils.isEmpty(wxUser)){
			return AjaxResult.error("用户不存在");
		}
		BeanUtil.copyProperties(wxUserDTO,wxUser);
		wxUser.setIsPerfect("Y");
		baseMapper.updateById(wxUser);
		return AjaxResult.success();
	}

	/**
	 * 患者签收
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AjaxResult confirmReceipt(Integer id,String signatureUrl,String remarks,String boxUrl) {
		OrderLogistics orderLogistics = orderLogisticsMapper.selectByPrimaryKey(id);
		if(ObjectUtils.isEmpty(orderLogistics)){
			return AjaxResult.error("物流信息不存在!");
		}
		if(!orderLogistics.getStatus().equals(OrderStatusEnum.ZERO.getType())){
			return AjaxResult.error("已签收，请勿重复签收!");
		}
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderLogistics.getOrderId());
		if(ObjectUtils.isEmpty(orderInfo)){
			return AjaxResult.error("订单不存在!");
		}
		Map map2 = new HashMap();
		OrderOperationInfo operationInfo = new OrderOperationInfo();
		map2.put("id",id);
		map2.put("orderId",orderLogistics.getOrderId());
		int byOrderIdNotId = orderLogisticsMapper.getByOrderIdNotId(map2);
		if(orderInfo.getStatus().equals(OrderStatusEnum.FOUR.getType()) && byOrderIdNotId <= 0){
			Map map = new HashMap();
			map.put("id", orderLogistics.getOrderId());
			map.put("status", OrderStatusEnum.ZERO.getType());
			this.orderInfoMapper.updateStatusById(map);
			operationInfo.setRemarks("订单已签收");
			operationInfo.setType(OrderStatusEnum.ZERO.getText());
		}else {
			operationInfo.setRemarks("订单已签收");
			operationInfo.setType("订单已签收");
		}
		ThirdSession thirdSession = ThirdSessionHolder.getThirdSession();
		orderLogistics.setStatus(OrderStatusEnum.ONE.getType());
		orderLogistics.setSignatureUrl(signatureUrl);
		orderLogistics.setBoxUrl(boxUrl);
		orderLogistics.setRemarks(remarks);
		orderLogistics.setUpdateBy(thirdSession.getWxUserName());
		orderLogistics.setUpdateTime(new Date());
		orderLogistics.setQsBy(thirdSession.getWxUserName());
		orderLogistics.setQsTime(new Date());
		orderLogisticsMapper.updateByPrimaryKeySelective(orderLogistics);
		operationInfo.setOrderId(Long.valueOf(id));
		operationInfo.setCreateId(thirdSession.getWxUserId());
		operationInfo.setCreateBy(thirdSession.getWxUserName());
		operationInfo.setCreateTime(new Date());
		/*remarks = StringUtils.isEmpty(remarks) ? "" : remarks;
		operationInfo.setRemarks("订单已完成，备注信息：" + remarks);*/
		operationInfo.setType(OrderStatusEnum.ZERO.getText());
		operationInfoMapper.insertSelective(operationInfo);
		return AjaxResult.success();
	}
}
