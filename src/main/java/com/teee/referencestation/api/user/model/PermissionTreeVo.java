package com.teee.referencestation.api.user.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglei
 */
@Data
public class PermissionTreeVo {
    private long id;
    private String label;
    private List<PermissionTreeVo> children = new ArrayList<>();
}
