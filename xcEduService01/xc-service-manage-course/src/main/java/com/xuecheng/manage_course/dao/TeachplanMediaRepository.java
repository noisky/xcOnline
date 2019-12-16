package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by fanfan on 2019/12/14.
 */
public interface TeachplanMediaRepository extends JpaRepository<TeachplanMedia, String> {
    List<TeachplanMedia> findByCourseId(String courseId);
}
