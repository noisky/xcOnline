package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by fanfan on 2019/12/04.
 */
public interface FileSystemRepository extends MongoRepository<FileSystem, String> {
}
