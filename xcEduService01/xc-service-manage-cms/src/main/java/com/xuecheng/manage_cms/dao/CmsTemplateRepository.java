package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by fanfan on 2019/11/28.
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate, String> {
}
