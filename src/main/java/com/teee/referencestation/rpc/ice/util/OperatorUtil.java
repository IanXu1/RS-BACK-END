package com.teee.referencestation.rpc.ice.util;

import com.teee.referencestation.rpc.ice.teee.WhereCond;
import com.teee.referencestation.rpc.ice.teee.WhereCondType;

/**
 * @author zhanglei
 */
public enum OperatorUtil {

    EQ("eq"),
    NEQ("neq"),
    GT("gt"),
    LT("lt"),
    GTE("gte"),
    LTE("let"),
    LIKE("like"),
    IN("in");

    private String operator;

    OperatorUtil(String operator) {
        this.operator = operator;
    }

    public String valueOf() {
        return this.operator;
    }

    public static WhereCondType getOperator(String operator) {
        if (EQ.valueOf().equalsIgnoreCase(operator)) {
            return WhereCondType.E_COND_EQ;
        } else if (NEQ.valueOf().equalsIgnoreCase(operator)) {
            return WhereCondType.E_COND_NEQ;
        } else if (GT.valueOf().equalsIgnoreCase(operator)) {
            return WhereCondType.E_COND_GT;
        } else if (LT.valueOf().equalsIgnoreCase(operator)) {
            return WhereCondType.E_COND_LT;
        } else if (GTE.valueOf().equalsIgnoreCase(operator)) {
            return WhereCondType.E_COND_GT;
        } else if (LTE.valueOf().equalsIgnoreCase(operator)) {
            return WhereCondType.E_COND_LTE;
        } else if (LIKE.valueOf().equalsIgnoreCase(operator)) {
            return WhereCondType.E_COND_LIKE;
        } else if (IN.valueOf().equalsIgnoreCase(operator)) {
            return WhereCondType.E_COND_IN;
        }
        return WhereCondType.E_COND_ERROR;
    }
}
