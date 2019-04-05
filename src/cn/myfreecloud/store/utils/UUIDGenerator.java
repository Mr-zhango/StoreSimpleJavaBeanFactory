package cn.myfreecloud.store.utils;

import java.util.UUID;

/**
 * 生成无中划线的uuid
 */
public class UUIDGenerator {

    public static String  getUuId() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
