package com.bcsd.project.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bcsd.project.domain.Article;
import com.bcsd.project.domain.Hospital;
import com.bcsd.project.domain.vo.ArticleListVO;
import com.bcsd.project.mapper.ArticleMapper;
import com.bcsd.project.mapper.HospitalMapper;
import com.bcsd.project.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.bcsd.common.utils.SecurityUtils.getUsername;

/**
 * @ClassName ArticleServiceImpl
 * @Description: TODO
 * @Author zhaofei
 * @Date 2023/7/11
 * @Version V1.0
 **/
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public List<ArticleListVO> list(Article article) {
        return articleMapper.selectArticleList(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdate(Article article) {
        if (article.getId()!=null) {
            articleMapper.updateByPrimaryKeySelective(article);
        } else {
            article.setCreateBy(getUsername());
            article.setCreateTime(new Date());
            article.setDelFlag("0");
            article.setReadingNumber(0);
            article.setStatus("0");
            articleMapper.insertSelective(article);
        }
    }

    /**
     * 修改查询
     * @param id
     * @return
     */
    @Override
    public Article selectByPrimaryKey(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    /**
     * 阅读量
     * @param id
     * @return
     */
    @Override
    public void readingNumberAdd(Integer id) {
        articleMapper.readingNumberAdd(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(Integer id) {
        articleMapper.updateById(id);
    }

    @Override
    public void updateStatus(JSONObject jsonObject) {
        articleMapper.updateStatus(jsonObject);
    }
}
