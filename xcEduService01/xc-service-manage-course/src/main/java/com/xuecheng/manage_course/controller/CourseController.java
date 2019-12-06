package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fanfan on 2019/12/02.
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;

    /**
     * 根据课程id获得课程计划
     *
     * @param courseId 课程id
     * @return 课程计划
     */
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    /**
     * 添加课程计划
     *
     * @param teachplan 课程计划
     * @return 执行结果
     */
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    /**
     * 查询我的课程
     *
     * @param page              查询页面
     * @param size              每页个数
     * @param courseListRequest 查询条件
     * @return 查询结果
     */
    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable("page") Integer page, @PathVariable("size") Integer size, CourseListRequest courseListRequest) {
        return courseService.findCourseList(page, size, courseListRequest);
    }

    /**
     * 添加课程
     *
     * @param courseBase 添加的课程信息
     * @return 添加结果
     */
    @Override
    @PostMapping("/coursebase/add")
    public AddCourseResult addCourseBase(@RequestBody CourseBase courseBase) {
        return courseService.addCourseBase(courseBase);
    }

    /**
     * 根据id获取课程信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    @Override
    @GetMapping("/coursebase/get/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable("courseId") String courseId) {
        return courseService.getCoursebaseById(courseId);
    }

    /**
     * 更新课程信息
     *
     * @param id         课程id
     * @param courseBase 课程信息
     * @return 更新结果
     */
    @Override
    @PutMapping("/coursebase/update/{id}")
    public ResponseResult updateCourseBase(@PathVariable("id") String id, @RequestBody CourseBase courseBase) {
        return courseService.updateCoursebase(id, courseBase);
    }

    /**
     * 根据课程id获取课程营销信息
     *
     * @param courseId 课程id
     * @return 营销信息
     */
    @Override
    @GetMapping("/coursemarket/get/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseService.getCourseMarketById(courseId);
    }

    /**
     * 更新课程营销信息
     *
     * @param id           课程id
     * @param courseMarket 营销信息
     * @return 结果
     */
    @Override
    @PostMapping("/coursemarket/update/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id") String id, @RequestBody CourseMarket courseMarket) {
        CourseMarket courseMarket_u = courseService.updateCourseMarket(id, courseMarket);
        if (courseMarket_u != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        } else {
            return new ResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 保存课程图片信息
     * @param courseId 课程id
     * @param pic 图片信息
     * @return 执行结果
     */
    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {
        //保存课程图片
        return courseService.saveCoursePic(courseId, pic);
    }

    /**
     * 根据课程id查询课程图片信息
     * @param courseId 课程id
     * @return 课程图片信息
     */
    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursepic(courseId);
    }

    /**
     * 删除课程图片
     * @param courseId 课程id
     * @return 删除结果
     */
    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return courseService.deleteCoursePic(courseId);
    }

    /**
     * 根据课程id查询课程view信息
     * @param id 课程id
     * @return 查询结果
     */
    @Override
    @GetMapping("/courseview/{id}")
    public CourseView courseview(@PathVariable("id") String id) {
        return courseService.getCourseView(id);
    }

    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult preview(@PathVariable("id") String id) {
        return courseService.preview(id);
    }
}
