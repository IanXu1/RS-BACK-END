package com.teee.referencestation.api.sysmanage.service;

import com.teee.referencestation.api.sysmanage.model.DictionaryItem;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@Service
public class DictionaryService extends BaseService {

    public RestResponse loadItemByCode(Map request) {
        List<DictionaryItem> items = session.selectList("dictionary.selectItems", request);
        return ObjUtil.isNotEmpty(items) ? RestResponse.success(items) : RestResponse.error("加载下拉列表数据错误");
    }

    public String loadItemNameByDicCodeAndItemCode(int dicCode, int itemCode){
        DictionaryItem item = new DictionaryItem();
        item.setDicCode(dicCode);
        item.setItemCode(itemCode);
        Map request = JsonUtil.json2map(JsonUtil.toJSONString(item));
        item = session.selectOne("dictionary.selectItems", request);
        return ObjUtil.isEmpty(item) ? "" : item.getItemName();
    }
}
