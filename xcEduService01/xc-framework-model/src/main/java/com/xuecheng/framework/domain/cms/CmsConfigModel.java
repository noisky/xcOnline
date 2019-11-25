package com.xuecheng.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * Created by fanfan on 2019/11/22.
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue;
    private String value;

}
