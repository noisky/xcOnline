package com.xuecheng.framework.domain.course.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by fanfan on 2019/11/22.
 */
@Data
@ToString
public class CourseListRequest extends RequestData {
    //公司Id
    private String companyId;
}
