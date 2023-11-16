package com.bcsd.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.domain.BaseInfo;
import com.bcsd.common.core.domain.entity.SysRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bcsd.common.constant.HttpStatus;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.domain.model.LoginUser;
import com.bcsd.common.core.page.PageDomain;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.common.core.page.TableSupport;
import com.bcsd.common.utils.DateUtils;
import com.bcsd.common.utils.PageUtils;
import com.bcsd.common.utils.SecurityUtils;
import com.bcsd.common.utils.StringUtils;
import com.bcsd.common.utils.sql.SqlUtil;

/**
 * web层通用数据处理
 * 
 * @author bcsd
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageUtils.startPage();
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage(BaseInfo params)
    {
        PageUtils.startPage(params);
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage(JSONObject params)
    {
        PageUtils.startPage2(params);
    }

    /**
     * 设置请求排序数据
     */
    protected void startOrderBy()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy()))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 清理分页的线程变量
     */
    protected void clearPage()
    {
        PageUtils.clearPage();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应请求分页数据（是否需要编号）
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list,boolean isNo)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        PageInfo page = new PageInfo(list);
        rspData.setTotal(page.getTotal());
        if (isNo){
            for (int i = 0; i < list.size(); i++) {
                BaseInfo baseInfo = (BaseInfo) list.get(i);
                baseInfo.setNo((page.getPageNum()-1)* page.getPageSize()+i+1);
            }
        }
        return rspData;
    }

    /**
     * 返回成功
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(Object data)
    {
        return AjaxResult.success(data);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 响应返回结果
     * 
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * 获取用户缓存信息
     */
    public LoginUser getLoginUser()
    {
        return SecurityUtils.getLoginUser();
    }

    /**
     * 获取登录用户id
     */
    public Long getUserId()
    {
        return getLoginUser().getUserId();
    }

    /**
     * 获取登录部门id
     */
    public Long getDeptId()
    {
        return getLoginUser().getDeptId();
    }

    /**
     * 获取用户名
     */
    public String getUsername()
    {
        return getLoginUser().getUser().getNickName();
    }

    /**
     * 获取所有角色
     * @return
     */
    public List<SysRole> getRoles(){
        return getLoginUser().getUser().getRoles();
    }

    /**
     * 获取所有角色标识
     * @return
     */
    public Set<String> getRoleKeys(){
        return getRoles().stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
    }

    /**
     * 获取第一个角色标识
     * @return
     */
    public String getOneRoleKey(){
        return getRoles().get(0).getRoleKey();
    }
}
