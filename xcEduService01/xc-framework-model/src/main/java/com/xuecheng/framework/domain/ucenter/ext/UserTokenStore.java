package com.xuecheng.framework.domain.ucenter.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by fanfan on 2019/11/22.
 */
@Data
@ToString
@NoArgsConstructor
public class UserTokenStore extends AuthToken {
    String userId;//用户id
    String utype;//用户类型
    String companyId;//用户所属企业信息
}
