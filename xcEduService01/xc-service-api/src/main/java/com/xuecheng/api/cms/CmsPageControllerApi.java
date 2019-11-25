package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * Created by fanfan on 2019/11/23.
 */
public interface CmsPageControllerApi {

    public QueryResponseResult findList(int page, int size, QueryPageRequest pageRequest);

}
