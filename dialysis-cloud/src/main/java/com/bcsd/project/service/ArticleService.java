package com.bcsd.project.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bcsd.project.domain.Article;
import com.bcsd.project.domain.vo.ArticleListVO;

import java.util.List;

public interface ArticleService extends IService<Article> {


    List<ArticleListVO> list(Article article);

    void addOrUpdate(Article article);

    void readingNumberAdd(Integer id);

    Article selectByPrimaryKey(Integer id);

    void deleteArticle(Integer id);

    void updateStatus(JSONObject jsonObject);

}
