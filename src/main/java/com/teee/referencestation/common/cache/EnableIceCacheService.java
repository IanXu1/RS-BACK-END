package com.teee.referencestation.common.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EnableIceCacheService {

    int expire() default 15;

    CacheOperation cacheOperation();

    enum CacheOperation {
        QUERY,
        ADD,
        MODIFY,
        DELETE
    }
}
