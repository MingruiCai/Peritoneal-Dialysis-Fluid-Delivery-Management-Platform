//
//package com.bcsd.web.controller.weixin;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.bcsd.common.core.controller.BaseController;
//import com.bcsd.common.core.domain.AjaxResult;
//import com.bcsd.project.service.WxMenuService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.common.error.WxErrorException;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 自定义菜单
// *
// * @author www.joolun.com
// * @date 2019-03-27 16:52:10
// */
//@Slf4j
//@RestController
//@AllArgsConstructor
//@RequestMapping("/wxmenu")
//public class WxMenuController extends BaseController {
//
//	private final WxMenuService wxMenuService;
//
//	/**
//	 * 通过appId查询自定义菜单
//	 *
//	 * @return R
//	 */
//	@GetMapping("/list")
//	@PreAuthorize("@ss.hasPermi('wxmp:wxmenu:get')")
//	public AjaxResult getWxMenuButton() {
//		return AjaxResult.success(wxMenuService.getWxMenuButton());
//	}
//
//	/**
//	 * 保存并发布菜单
//	 *
//	 * @param
//	 * @return R
//	 */
//	@PostMapping("/release")
//	@PreAuthorize("@ss.hasPermi('wxmp:wxmenu:add')")
//	public AjaxResult saveAndRelease(@RequestBody String data) {
//		JSONObject jSONObject = JSONObject.parse(data);
//		String strWxMenu = jSONObject.getString("strWxMenu");
//		String appId = jSONObject.getString("appId");
//		try {
//			wxMenuService.saveAndRelease(strWxMenu);
//			return AjaxResult.success();
//		} catch (WxErrorException e) {
//			e.printStackTrace();
//			log.error("发布自定义菜单失败appID:" + appId + ":" + e.getMessage());
//			return AjaxResult.error(e.getMessage());
//		}
//	}
//
//}
