package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by fanfan on 2019/11/30.
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
}
