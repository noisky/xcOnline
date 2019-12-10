package com.xuecheng.search.service;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fanfan on 2019/12/09.
 */

@Service
public class EsCourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsCourseService.class);

    @Value("${xuecheng.elasticsearch.course.index}")
    private String es_index;
    @Value("${xuecheng.elasticsearch.course.type}")
    private String es_type;
    @Value("${xuecheng.elasticsearch.course.source_field}")
    private String source_field;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam) {
        //设置索引
        SearchRequest searchRequest = new SearchRequest(es_index);
        //设置类型
        searchRequest.types(es_type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //source字段过滤
        String[] source_fileds = source_field.split(",");
        searchSourceBuilder.fetchSource(source_fileds, new String[]{});
        //关键字
        if (StringUtils.isNotEmpty(courseSearchParam.getKeyword())) {
            //匹配关键字
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "teachplan", "description");
            //设置匹配度
            multiMatchQueryBuilder.minimumShouldMatch("70%");
            //提升name字段的boost权重值
            multiMatchQueryBuilder.field("name", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        //过滤
        if (StringUtils.isNotEmpty(courseSearchParam.getMt())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt", courseSearchParam.getMt()));
        }
        if (StringUtils.isNotEmpty(courseSearchParam.getSt())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("st", courseSearchParam.getSt()));
        }
        if (StringUtils.isNotEmpty(courseSearchParam.getGrade())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade", courseSearchParam.getGrade()));
        }
        //分页
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 20;
        }
        int start = (page - 1) * size;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(size);
        //布尔查询
        searchSourceBuilder.query(boolQueryBuilder);
        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        //设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        searchSourceBuilder.highlighter(highlightBuilder);
        //请求搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("xuecheng search error..{}", e.getMessage());
            return new QueryResponseResult(CommonCode.SUCCESS, new QueryResult<CoursePub>());
        }

        //结果集处理
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        //记录总数
        long totalHits = hits.getTotalHits();
        //数据列表
        List<CoursePub> list = new ArrayList<>();

        for (SearchHit hit : searchHits) {
            CoursePub coursePub = new CoursePub();
            //取出source
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            //取出名称
            String name = (String) sourceAsMap.get("name");
            //取出高亮字段内容
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields != null) {
                HighlightField nameField = highlightFields.get("name");
                if (nameField != null) {
                    Text[] fragments = nameField.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text str : fragments) {
                        stringBuffer.append(str.string());
                    }
                    name = stringBuffer.toString();
                }
            }
            coursePub.setName(name);
            //取出图片
            String pic = (String) sourceAsMap.get("pic");
            coursePub.setPic(pic);
            //价格
            Double price = null;
            try {
                if (sourceAsMap.get("price") != null) {
                    price = (Double)sourceAsMap.get("price");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //设置价格
            coursePub.setPrice(price);
            Double price_old = null;
            try {
                if (sourceAsMap.get("price_old") != null) {
                    price_old = (Double) sourceAsMap.get("price_old");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            coursePub.setPrice_old(price_old);
            list.add(coursePub);
        }
        QueryResult<CoursePub> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(totalHits);
        QueryResponseResult<CoursePub> coursePubQueryResponseResult = new QueryResponseResult<CoursePub>(CommonCode.SUCCESS, queryResult);
        return coursePubQueryResponseResult;
    }
}
