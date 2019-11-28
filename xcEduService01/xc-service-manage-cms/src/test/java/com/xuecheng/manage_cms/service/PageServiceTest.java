package com.xuecheng.manage_cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by fanfan on 2019/11/28.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {

    @Autowired
    PageService pageService;

    @Test
    public void test1() {
        String pageHtml = pageService.getPageHtml("5ddfc6741bfe324648ee7480");
        System.out.println(pageHtml);
    }

}