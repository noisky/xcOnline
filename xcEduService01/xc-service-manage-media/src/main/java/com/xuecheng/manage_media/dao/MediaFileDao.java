package com.xuecheng.manage_media.dao;

import com.xuecheng.framework.domain.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by fanfan on 2019/12/14.
 */
public interface MediaFileDao extends MongoRepository<MediaFile, String> {
}
