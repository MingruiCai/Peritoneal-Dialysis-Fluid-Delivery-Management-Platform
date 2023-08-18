package com.bcsd.project.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.domain.entity.SysUser;
import com.bcsd.common.core.page.TableDataInfo;
import com.bcsd.common.utils.StringUtils;
import com.bcsd.project.domain.Article;
import com.bcsd.project.domain.vo.ArticleListVO;
import com.bcsd.project.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName 文章内容 ArticlecController
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/11
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;

    /**
     * 列表分页
     * @param article
     * @return
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody Article article) {
        startPage(article);
        List<ArticleListVO> list = articleService.list(article);
        return getDataTable(list);
    }

    /**
     * 新增修改
     * @param article
     * @return
     */
    @PostMapping("/addOrUpdate")
    public AjaxResult addOrUpdate(@RequestBody Article article) {
        articleService.addOrUpdate(article);
        return AjaxResult.success();
    }

    /**
     * 根据id获取文章
     * @param jsonObject
     * @return
     */
    @PostMapping("getArticle")
    public AjaxResult getArticle(@RequestBody JSONObject jsonObject) {
        Article article = articleService.selectByPrimaryKey(jsonObject.getInteger("id"));
        return AjaxResult.success(article);
    }

    /**
     * 删除
     * @param jsonObject
     * @return
     */
    @PostMapping("deleteArticle")
    public AjaxResult deleteArticle(@RequestBody JSONObject jsonObject) {
        articleService.deleteArticle(jsonObject.getInteger("id"));
        return AjaxResult.success();
    }



    /**
     * 发布
     * @param jsonObject
     * @return
     */
    @PostMapping("updateStatus")
    public AjaxResult updateStatus(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        String status = jsonObject.getString("status");
        if(id == null){
            AjaxResult.error("id不能为空");
        }
        if(StringUtils.isEmpty(status)){
            AjaxResult.error("状态不能为空");
        }
        articleService.updateStatus(jsonObject);
        return AjaxResult.success();
    }

}
