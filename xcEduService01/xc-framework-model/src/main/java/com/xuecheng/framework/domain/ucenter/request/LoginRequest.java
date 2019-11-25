package com.xuecheng.framework.domain.ucenter.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by fanfan on 2019/11/22.
 */
@Data
@ToString
public class LoginRequest extends RequestData {

    String username;
    String password;
    String verifycode;

}
