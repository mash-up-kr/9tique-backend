package kr.co.mash_up_9tique.util;

import kr.co.mash_up_9tique.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public class ParameterUtil {

    public static boolean isEmpty(Object... args) {
        if (args == null) {
            return true;
        }

        for (Object arg : args) {
            if (arg == null) {
                return true;
            } else if (arg instanceof String && StringUtils.isBlank((String) arg)) {
                return true;
            } else if (arg instanceof Integer && (Integer) arg == 0) {
                return true;
            } else if (arg instanceof Long && (Long) arg == 0) {
                return true;
            } else if (arg instanceof MultipartFile && ((MultipartFile) arg).isEmpty()) {
                return true;
            } else if (arg instanceof Collection && CollectionUtils.isEmpty((Collection<?>) arg)) {
                return true;
            }
        }
        return false;
    }

    public static void checkParameterEmpty(Object... args) {
        if (isEmpty(args)) {
            throw new InvalidParameterException();
        }
    }
}
