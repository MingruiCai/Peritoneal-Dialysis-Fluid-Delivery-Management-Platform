package com.bcsd.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.project.domain.Article;
import com.bcsd.project.domain.dto.WxOpenDataDTO;
import com.bcsd.project.domain.WxUser;
import com.bcsd.project.domain.dto.WxUserDTO;
import com.bcsd.project.domain.vo.WxUserVO;
import me.chanjar.weixin.common.error.WxErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 微信用户
 *
 * @author www.joolun.com
 * @date 2019-03-25 15:39:39
 */
public interface WxUserService extends IService<WxUser> {

	List<WxUser> list(WxUser wxUser);

	/**
	 * 同步微信用户
	 */
	void synchroWxUser() throws WxErrorException;

	/**
	 * 修改用户备注
	 * @param entity
	 * @return
	 */
	boolean updateRemark(WxUser entity) throws WxErrorException;

	/**
	 * 认识标签
	 * @param taggingType
	 * @param tagId
	 * @param openIds
	 * @throws WxErrorException
	 */
	void tagging(String taggingType, Long tagId, String[] openIds) throws WxErrorException;

	/**
	 * 根据openId获取用户
	 * @param openId
	 * @return
	 */
	WxUser getByOpenId(String openId);

	Object isLoginExist(String appId, String jsCode) throws WxErrorException;

	/**
	 * 小程序登录
	 * @param appId
	 * @param jsCode
	 * @return
	 */
	WxUser loginMa(String appId, String jsCode) throws WxErrorException;

	/**
	 * 新增、更新微信用户
	 * @param wxOpenDataDTO
	 * @return
	 */
	WxUser saveOrUptateWxUser(WxOpenDataDTO wxOpenDataDTO);

    /**
     * 退出登录
     * @param request
     */
	void logout(HttpServletRequest request);

	WxUserVO getWxUserById(String id);

    AjaxResult uptateWxUser(WxUserDTO wxUserDTO);

	AjaxResult confirmReceipt(Integer id,String signatureUrl,String remarks,String boxUrl);
}
