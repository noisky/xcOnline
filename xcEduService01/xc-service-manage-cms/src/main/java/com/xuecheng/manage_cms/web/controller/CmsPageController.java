package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fanfan on 2019/11/24.
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    PageService pageService;

    /**
     * 分页条件查询
     * @param page 当前页
     * @param size 每页查询数据
     * @param pageRequest 查询条件
     * @return 查询结果
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest pageRequest) {
        /*//暂时采用测试数据，测试接口是否能够正常运行
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(2);
        //静态数据列表
        List list = new ArrayList();
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试页面");
        list.add(cmsPage);
        queryResult.setList(list);
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);*/
        return pageService.findList(page, size, pageRequest);
    }

    /**
     * 添加页面
     *
     * @param cmsPage 添加页面信息
     * @return 添加结果
     */
    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    /**
     * 根据id查询
     * @param id 查询的id
     * @return 查询结果
     */
    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return pageService.getById(id);
    }

    /**
     * 根据id更新页面
     * @param id 更新的页面id
     * @param cmsPage 更新的页面信息
     * @return 更新结果
     */
    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return pageService.update(id, cmsPage);
    }

    /**
     * 根据id删除页面
     * @param id 删除页面的id
     * @return 删除结果
     */
    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return pageService.delete(id);
    }
}
