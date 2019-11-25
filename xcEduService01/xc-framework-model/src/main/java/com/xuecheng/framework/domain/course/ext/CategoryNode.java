package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.Category;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by fanfan on 2019/11/22.
 */
@Data
@ToString
public class CategoryNode extends Category {

    List<CategoryNode> children;

}
