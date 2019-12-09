package com.xuecheng.framework.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * Created by fanfan on 2019/11/22.
 */

@Data
@ToString
public class QueryResponseResult<T> extends ResponseResult {

    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode,QueryResult queryResult){
        super(resultCode);
       this.queryResult = queryResult;
    }

}
