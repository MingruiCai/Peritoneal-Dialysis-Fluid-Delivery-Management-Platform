package com.bcsd.project.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ThirdSession implements Serializable {
	/**
	 * 微信用户ID
	 */
	private String wxUserId;

	private String wxUserName;
	/**
	 * 配置项ID
	 */
	private String appId;
	/**
	 * 微信sessionKey
	 */
	private String sessionKey;
	/**
	 * 用户标识
	 */
	private String openId;

	/**
	 * 是否个人信息是否完善（Y：完善，N：未完善）
	 */
	private String isPerfect;
}
