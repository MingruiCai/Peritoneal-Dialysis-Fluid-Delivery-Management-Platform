/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 */
package com.bcsd.web.controller.weixin;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.common.utils.StringUtils;
import com.bcsd.project.domain.*;
import com.bcsd.project.domain.dto.*;
import com.bcsd.project.domain.vo.*;
import com.bcsd.project.service.*;
import com.bcsd.project.utils.IdentityUtils;
import com.bcsd.project.utils.ThirdSessionHolder;
import com.bcsd.project.utils.WxMaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.bcsd.common.utils.PageUtils.startPage;

/**
 * 患者微信用户
 *
 * @author www.joolun.com
 * @date 2019-08-25 15:39:39
 */

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/api/ma/wxuser")
@Api(value = "wxuser", tags = "小程序用户API")
public class WxUserApi extends BaseController {

	private final WxUserService wxUserService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private OrderLogisticsService orderLogisticsService;
	@Autowired
	private ArticleService articleService;

	/**
	 * 患者列表分页
	 * @param wxUser
	 * @return
	 */
	@PostMapping("/list")
	public TableDataInfo list(@RequestBody WxUser wxUser) {
		startPage();
		List<WxUser> list = wxUserService.list(wxUser);
		return getDataTable(list);
	}

	@ApiOperation(value = "小程序用户登录")
	@PostMapping("/isLoginExist")
	public AjaxResult isLoginExist(HttpServletRequest request, @RequestBody LoginMaDTO loginMaDTO){
		try {
			return AjaxResult.success("操作成功",wxUserService.isLoginExist(WxMaUtil.getAppId(request),loginMaDTO.getJsCode()));
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 小程序用户登录
	 * @param request
	 * @param loginMaDTO
	 * @return
	 */
	@ApiOperation(value = "小程序用户登录")
	@PostMapping("/login")
	public AjaxResult login(HttpServletRequest request, @RequestBody LoginMaDTO loginMaDTO){
		try {
			WxUser wxUser = wxUserService.loginMa(WxMaUtil.getAppId(request),loginMaDTO.getJsCode());
			return AjaxResult.success(wxUser);
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 获取用户信息
	 * @param
	 * @return
	 */
	@ApiOperation(value = "获取用户信息")
	@GetMapping
	public AjaxResult get(){
		String id = ThirdSessionHolder.getThirdSession().getWxUserId();
		return AjaxResult.success(wxUserService.getById(id));
	}

	/**
	 * 保存用户信息
	 * @param wxOpenDataDTO
	 * @return
	 */
	@ApiOperation(value = "保存用户信息")
	@PostMapping
	public AjaxResult saveOrUptateWxUser(@RequestBody WxOpenDataDTO wxOpenDataDTO){
		wxOpenDataDTO.setAppId(ThirdSessionHolder.getThirdSession().getAppId());
		wxOpenDataDTO.setUserId(ThirdSessionHolder.getThirdSession().getWxUserId());
		wxOpenDataDTO.setSessionKey(ThirdSessionHolder.getThirdSession().getSessionKey());
		WxUser wxUser = wxUserService.saveOrUptateWxUser(wxOpenDataDTO);
		return AjaxResult.success(wxUser);
	}


	/**
	 * 退出登录
	 * @param
	 * @return
	 */
	@ApiOperation(value = "退出登录")
	@PostMapping("/logout")
	public AjaxResult logout(HttpServletRequest request){
		wxUserService.logout(request);
		return AjaxResult.success();
	}

	/**
	 * 个人信息修改查询
	 * @param
	 * @return
	 */
	@ApiOperation(value = "个人信息修改查询")
	@PostMapping("/getWxUserById")
	public AjaxResult getWxUserById(@RequestBody JSONObject jsonObject){
		String id = jsonObject.getString("id");
		if(StringUtils.isEmpty(id)){
			return AjaxResult.error("id不能为空");
		}
		return AjaxResult.success(wxUserService.getWxUserById(id));
	}

	/**
	 * 个人信息修改
	 * @param
	 * @return
	 */
	@ApiOperation(value = "个人信息修改")
	@PostMapping("/uptateWxUser")
	public AjaxResult uptateWxUser(@RequestBody @Valid WxUserDTO wxUserDTO){
		boolean idNumber = IdentityUtils.isIDNumber(wxUserDTO.getIdentity());
		if(!idNumber){
			return AjaxResult.error("请正确输入身份证号");
		}
		return wxUserService.uptateWxUser(wxUserDTO);
	}

	/**
	 * 订单签收（患者签收）
	 *
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/confirmReceipt")
	public AjaxResult confirmReceipt(@RequestBody JSONObject jsonObject) {
		Integer id = jsonObject.getInteger("id");
		String signatureUrl = jsonObject.getString("signatureUrl");
		String boxUrl = jsonObject.getString("boxUrl");
		String remarks = jsonObject.getString("remarks");
		if (id == null) {
			return AjaxResult.error("id不能为空");
		}
		try {
			return wxUserService.confirmReceipt(id,signatureUrl,remarks,boxUrl);
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 患者小程序端 订单关闭
	 *
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/orderClose")
	public AjaxResult orderClose(@RequestBody JSONObject jsonObject) {
		Integer id = jsonObject.getInteger("id");
		String remarks = jsonObject.getString("remarks");
		if (id == null) {
			return AjaxResult.error("id不能为空");
		}
		try {
			orderInfoService.orderClose(id,remarks);
			return AjaxResult.success();
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 订单新增修改
	 *
	 * @param orderInfo
	 * @return
	 */
	@PostMapping("/orderInfoAdd")
	public AjaxResult orderInfoAdd(@RequestBody OrderInfo orderInfo) {
		try {
			return orderInfoService.addOrUpdate(orderInfo);
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 医院列表分页
	 * @param hospital
	 * @return
	 */
	@PostMapping("/hospitalList")
	public TableDataInfo list(@RequestBody Hospital hospital) {
		startPage(hospital);
		List<HospitalVO> list = hospitalService.list(hospital);
		return getDataTable(list);
	}

	/**
	 * 订单列表分页
	 *
	 * @param orderInfo
	 * @return
	 */
	@PostMapping("/orderInfoList")
	public TableDataInfo list(@RequestBody OrderInfoDTO orderInfo) {
		startPage(orderInfo);
		List<OrderInfoVO> list = orderInfoService.list(orderInfo);
		return getDataTable(list);
	}

	/**
	 * 配送单列表
	 * @param logistics
	 * @return
	 */
	@PostMapping("/logisticsList")
	public TableDataInfo list(@RequestBody OrderLogisticsPageDTO logistics) {
		startPage(logistics);
		List<OrderLogisticsVO> list = orderLogisticsService.list(logistics);
		return getDataTable(list);
	}

	/**
	 * 根据id获取订单详情
	 *
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/getOrderById")
	public AjaxResult getOrderById(@RequestBody JSONObject jsonObject) {
		Integer id = jsonObject.getInteger("id");
		if (id == null) {
			return AjaxResult.error("id不能为空");
		}
		return orderInfoService.getOrderById(id);
	}

	/**
	 * 根据物流id获取 物流信息
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/getLogisticsById")
	public AjaxResult getLogisticsById(@RequestBody JSONObject jsonObject) {
		return orderLogisticsService.selectByPrimaryKey(jsonObject.getInteger("id"));
	}

	/**
	 * 文章列表分页
	 * @param article
	 * @return
	 */
	@PostMapping("/articleList")
	public TableDataInfo articleList(@RequestBody Article article) {
		startPage(article);
		List<ArticleListVO> list = articleService.list(article);
		return getDataTable(list);
	}

	/**
	 * 文章阅读量
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/readingNumberAdd")
	public AjaxResult readingNumberAdd(@RequestBody JSONObject jsonObject) {
		articleService.readingNumberAdd(jsonObject.getInteger("id"));
		return AjaxResult.success();
	}

	/**
	 * 根据id获取文章
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/getArticle")
	public AjaxResult getArticle(@RequestBody JSONObject jsonObject) {
		Article article = articleService.selectByPrimaryKey(jsonObject.getInteger("id"));
		return AjaxResult.success(article);
	}




	/*********************************用药咨询***************************************/
	@Autowired
	private ConsultationService consultationService;
	/**
	 * 患者新增用药咨询
	 * @param consultation
	 * @return
	 */
	@ApiOperation("患者新增用药咨询")
	@PostMapping({"/addConsultation"})
	public AjaxResult addConsultation(@RequestBody Consultation consultation) {
		if(StringUtils.isEmpty(consultation.getHospitalName())){
			AjaxResult.error("医院名称不能为空");
		}
		if(StringUtils.isEmpty(consultation.getContent())){
			AjaxResult.error("咨询内容不能为空");
		}
		return consultationService.addConsultation(consultation);
	}
	/**
	 * 患者更新用药咨询
	 * @param consultation
	 * @return
	 */
	@ApiOperation("患者更改用药咨询内容")
	@PostMapping({"/updateConsultation"})
	public AjaxResult updateConsultation(@RequestBody Consultation consultation) {
		if(StringUtils.isEmpty(consultation.getContent())){
			AjaxResult.error("咨询内容不能为空");
		}
		return consultationService.updateConsultation(consultation);
	}
	/**
	 * 删除咨询
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation("删除用药咨询")
	@PostMapping({"/deleteConsultation"})
	public AjaxResult deleteConsultation(@RequestBody JSONObject jsonObject) {
		return consultationService.deleteConsultation(jsonObject.getLong("id"));
	}
	/**
	 * 根据id查询用药咨询
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation("根据id查询用药咨询内容")
	@PostMapping({"/getConsultationById"})
	public AjaxResult getConsultationById(@RequestBody JSONObject jsonObject) {
		Consultation consultation=consultationService.selectByPrimaryKey(jsonObject.getLong("id"));
		return AjaxResult.success(consultation);
	}
	/**
	 * 获取患者本人咨询
	 * @param consultation
	 * @return
	 */
	@ApiOperation("患者查看用药咨询分页")
	@PostMapping("/patientConsultationList")
	public TableDataInfo getPatientConsultationList(@RequestBody Consultation consultation) {
		startPage(consultation);
		List<Consultation> patientConsultationList = consultationService.getPatientConsultationList(consultation);
		return getDataTable(patientConsultationList);
	}
	/**
	 * 获取精华咨询
	 * @param consultation
	 * @return
	 */
	@ApiOperation("精华咨询分页")
	@PostMapping("/highlightConsultationList")
	public TableDataInfo highlightConsultationList(@RequestBody Consultation consultation) {
		startPage(consultation);
		List<Consultation> highlightConsultationList = consultationService.highlightConsultationList(consultation);
		return getDataTable(highlightConsultationList);
	}
}
