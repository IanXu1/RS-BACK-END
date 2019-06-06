package com.teee.referencestation.common.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;


/**
 * @author zhanglei
 */
@Data
public class IdListCache implements Serializable {
    int total;
    Map<Integer, long[]> idLists;
}
