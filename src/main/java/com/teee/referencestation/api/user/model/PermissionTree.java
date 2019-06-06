package com.teee.referencestation.api.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
public class PermissionTree {
    @JsonProperty("id")
    private long id;
    @JsonProperty("pId")
    private long pId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("checked")
    private boolean checked;
}
