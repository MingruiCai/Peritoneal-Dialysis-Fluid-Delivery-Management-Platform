package com.bcsd.project.mapper;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bcsd.project.domain.Article;
import com.bcsd.project.domain.vo.ArticleListVO;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleListVO> selectArticleList(Article article);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    @Update("update article set reading_number = reading_number + 1 where id = #{id}")
    int readingNumberAdd(Integer id);

    @Update("update article set del_flag = '1' where id = #{id}")
    int updateById(Integer id);

    @Update("update article set status = #{status} where id = #{id}")
    int updateStatus(JSONObject jsonObject);
}