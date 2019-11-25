package com.xuecheng.framework.domain.media.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;

/**
 * Created by fanfan on 2019/11/22.
 */

@Data
public class QueryMediaFileRequest extends RequestData {

    private String fileOriginalName;
    private String processStatus;
    private String tag;
}
