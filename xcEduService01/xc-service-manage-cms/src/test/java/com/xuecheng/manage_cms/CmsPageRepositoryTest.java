package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by fanfan on 2019/11/24.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    //注入dao
    @Autowired
    private CmsPageRepository cmsPageRepository;

    //分页查询测试
    @Test
    public void testFindPage() {
        int page = 0; //从0页开始
        int size = 10; //每页10条数据
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //添加
    @Test
    public void testInsert() {
        //定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s10");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }

    //删除
    @Test
    public void testDelete() {
        cmsPageRepository.deleteById("5dda706253320a0c58bd3243");
    }

    //修改
    @Test
    public void testUpdate() {
        //Optional是一个容器对象，可以提醒你非空判断。并对象非空检测标准化。
        Optional<CmsPage> optional = cmsPageRepository.findById("5dda719e53320a1690c7fca1");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面01");
            cmsPageRepository.save(cmsPage);
        }
    }

    @Test
    public void testFindAllByExample() {
        //分页参数
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //查询id为xxx站点的页面
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        //自定义Example
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);

    }

    //自定义条件查询测试
    @Test
    public void testFindAll() {
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        //条件值
        CmsPage cmsPage = new CmsPage();
        //站点id
        //cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //模板id
        //cmsPage.setTemplateId("5a962bf8b00ffc514038fafa");
        //别名
        cmsPage.setPageAliase("轮播");
        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Pageable pageable = new PageRequest(0, 10);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        System.out.println(all);
    }
}
