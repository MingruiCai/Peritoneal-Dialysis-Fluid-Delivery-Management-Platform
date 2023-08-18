package com.bcsd.common.utils;

import com.bcsd.common.core.domain.BaseInfo;
import com.github.pagehelper.PageHelper;
import com.bcsd.common.core.page.PageDomain;
import com.bcsd.common.core.page.TableSupport;
import com.bcsd.common.utils.sql.SqlUtil;

/**
 * 分页工具类
 * 
 * @author bcsd
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 设置请求分页数据
     */
    public static void startPage(BaseInfo params){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = params.getPageNum()==null?1:params.getPageNum();
        Integer pageSize = params.getPageSize()==null?10:params.getPageSize();
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
