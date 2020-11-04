package com.leyou.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import java.util.UUID;

/**
 * @author xuqiangsheng
 */
public class CodecUtils {
    /**
     * 对数据进行加密
     * @author XuQiangsheng
     * @date 2020/11/4 15:30
     * @param data 需要加密的数据
     * @param salt 盐
     * @return java.lang.String
    */
    public static String md5Hex(String data,String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

    public static String shaHex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.sha512Hex(salt + DigestUtils.sha512Hex(data));
    }

    public static String generateSalt(){
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
