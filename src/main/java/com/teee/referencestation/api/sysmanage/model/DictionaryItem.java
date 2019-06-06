package com.teee.referencestation.api.sysmanage.model;

import lombok.Data;

/**
 * @author zhanglei
 */
@Data
public class DictionaryItem {
    private long id;
    private int dicCode;
    private int itemCode;
    private String itemName;
}
