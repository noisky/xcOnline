package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by fanfan on 2019/12/07.
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
