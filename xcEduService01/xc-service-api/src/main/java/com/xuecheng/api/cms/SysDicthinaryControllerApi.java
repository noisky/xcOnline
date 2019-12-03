package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by fanfan on 2019/12/03.
 */


@Api(value = "数据字典接口", description = "提供数据字典接口的管理、查询功能")
public interface SysDicthinaryControllerApi {
    //数据字典查询接口
    @ApiOperation(value = "数据字典查询接口")
    public SysDictionary getByType(String type);
}
