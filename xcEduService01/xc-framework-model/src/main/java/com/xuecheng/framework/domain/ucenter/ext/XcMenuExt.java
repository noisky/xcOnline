package com.xuecheng.framework.domain.ucenter.ext;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by fanfan on 2019/11/22.
 */
@Data
@ToString
public class XcMenuExt extends XcMenu {

    List<CategoryNode> children;
}
