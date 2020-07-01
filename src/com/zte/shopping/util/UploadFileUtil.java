package com.zte.shopping.util;

import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Created by Eytins
 */

public class UploadFileUtil {

    public static String renameUploadFileName(String orifileName) {

        if (StringUtils.isEmpty(orifileName)) {
            return "";
        }

        String suffix = orifileName.substring(orifileName.lastIndexOf("."));
        String prefixName = orifileName.substring(0, orifileName.lastIndexOf("."));

        String uuid = UUID.randomUUID().toString();

        return uuid + prefixName + suffix;
    }

}
