package com.mirea.diplom.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import static com.mirea.diplom.utils.Constants.EXTENSION_DELIMITER;
import static com.mirea.diplom.utils.Constants.PATH_DELIMITER;

@UtilityClass
public class ServiceUtils {
    public static String getClassNameFromPath(String path) {
        String className = StringUtils.substringAfterLast(path, PATH_DELIMITER);
        className = StringUtils.substringBefore(className, EXTENSION_DELIMITER);
        return className;
    }
}
