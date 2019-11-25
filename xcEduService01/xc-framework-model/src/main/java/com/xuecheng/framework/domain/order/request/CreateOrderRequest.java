package com.xuecheng.framework.domain.order.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by fanfan on 2019/11/22.
 */
@Data
@ToString
public class CreateOrderRequest extends RequestData {

    String courseId;

}
