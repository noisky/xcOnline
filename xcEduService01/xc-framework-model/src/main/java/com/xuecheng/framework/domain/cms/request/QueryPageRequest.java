package com.xuecheng.framework.domain.cms.request;

import lombok.Data;

/**
 * Created by fanfan on 2019/11/23.
 */

@Data
public class QueryPageRequest {
    //站点id
    private String siteId;
    //页面ID
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //模版id
    private String templateId;
}
