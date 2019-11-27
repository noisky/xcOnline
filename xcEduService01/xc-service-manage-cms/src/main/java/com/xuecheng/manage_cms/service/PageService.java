package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * Created by fanfan on 2019/11/24.
 */
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * 页面列表分页查询
     * @param page 当前页码
     * @param size 页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        if (page <= 0) {
            page = 1;//从第一页开始查询
        }
        page = page - 1; //为了适应mongodb的接口将页码-1
        if (size <= 0) {
            size = 20;
        }
        //条件匹配器
        //页面名称/别名的模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());
        //条件值
        CmsPage cmsPage = new CmsPage();
        //站点ID
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //页面别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //页面名称
        if (StringUtils.isNotEmpty(queryPageRequest.getPageName())) {
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        //页面类型
        if (StringUtils.isNotEmpty(queryPageRequest.getPageType())) {
            cmsPage.setPageType(queryPageRequest.getPageType());
        }
        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        //分页对象
        Pageable pageable = new PageRequest(page, size);
        //分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<CmsPage>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
        //返回结果
        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    /**
     * 添加页面
     * @param cmsPage 添加页面属性
     * @return 添加结果
     */
    public CmsPageResult add(CmsPage cmsPage) {
        //添加页面前检查页面是否存在，根据页面名称、站点id、页面webPath查询
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 == null) {
            cmsPage.setPageId(null);//将主键id设置为null，使用数据库主键自增生成主键id
            cmsPageRepository.save(cmsPage);
            //返回操作成功结果
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        if (cmsPage1 != null) {
            //页面已经存在，抛出异常信息
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        //如果查询到数据，则代表页面已经存在，返回失败信息
        return new CmsPageResult(CommonCode.ADDPAGEFAIL, null);
    }

    /**
     * 根据id查询页面
     * @param id 查询的id
     * @return 查询结果
     */
    public CmsPage getById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        //如果未查到则返回空
        return optional.orElse(null);
    }

    /**
     * 更新页面信息
     * @param id 页面的id
     * @param cmsPage 页面信息
     * @return 运行结果
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {
        //根据id查询页面信息
        CmsPage page = this.getById(id);
        //如果不为空，则更新page信息
        if (page != null) {
            //更新模板id
            page.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            page.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            page.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            page.setPageName(cmsPage.getPageName());
            //更新访问路径
            page.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            page.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dateUrl
            page.setDataUrl(cmsPage.getDataUrl());
            //执行更新
            CmsPage save = cmsPageRepository.save(page);
            //更新成功
            return new CmsPageResult(CommonCode.SUCCESS, save);
        }
        //更新失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 根据id删除页面
     * @param id 删除页面的id
     * @return 删除结果
     */
    public ResponseResult delete(String id) {
        CmsPage page = this.getById(id);
        //判断页面是否存在
        if (page != null) {
            //删除页面
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        //删除失败
        return new ResponseResult(CommonCode.FAIL);
    }
}
