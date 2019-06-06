package com.teee.referencestation.api.upgrade.service;

import com.teee.referencestation.api.basestation.service.BaseStationService;
import com.teee.referencestation.api.upgrade.model.UpgradeFile;
import com.teee.referencestation.api.upgrade.model.UpgradeResult;
import com.teee.referencestation.api.upgrade.model.UpgradeVersion;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.BaseStationInfo;
import com.teee.referencestation.utils.AESUtil;
import com.teee.referencestation.utils.FileUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.util.*;

/**
 * @author zhanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UpgradeService extends BaseService {

    @Autowired
    private UpgradeVersionService versionService;
    @Autowired
    private UpgradeFileService fileService;
    @Autowired
    private UpgradeResultService resultService;
    @Autowired
    private BaseStationService stationService;

    /**
     * @desc 获取指定文件
     * @param request
     * @return
     * @throws Exception
     */
    public RestResponse getUpgradeFile(Map<String, Object> request) throws Exception {
        String fileKey = request.get("fileKey").toString();
        byte[] fileKeyBytes = Base64.getDecoder().decode(fileKey);
        String md5 = Hex.encodeHexString(FileUtil.subBytes(fileKeyBytes, 0, 16));
        UpgradeFile file = fileService.findUpgradeFileByMd5(md5);
        if (ObjUtil.isEmpty(file)) {
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "the upgrade file not exists");
        }
        // 判断当前拉取文件是否是期望文件
        long id = Long.valueOf(request.get("uuid").toString());
        UpgradeFile expectFile = getFileByTerminalId(id);
        if (!file.getMd5().equalsIgnoreCase(expectFile.getMd5())) {
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "the fileKey not belong to expect version");
        }

        Map response = new HashMap(8);
        response.put("fileKey", fileKey);
        response.put("fileName", file.getFileName());
        response.put("fileSize", file.getFileSize());
        String filePath = AESUtil.getInstance().decrypt(file.getFilePath());
        response.put("fileContent", Base64.getEncoder().encodeToString(IOUtils.toByteArray(new FileInputStream(filePath))));
        return RestResponse.success(response);
    }

    /**
     * @desc 合并两个数组
     * @param array1
     * @param array2
     * @return
     */
    private byte[] mergeArray(byte[] array1, byte[] array2) {
        int length1 = array1.length;
        int length2 = array2.length;
        //数组扩容
        array1 = Arrays.copyOf(array1, length1 + length2);
        System.arraycopy(array2, 0, array1, length1, length2);
        return array1;
    }

    /**
     * @desc 获取当前版本的所有文件key
     * @return
     * @throws DecoderException
     */
    public RestResponse getAllResources(Map<String, Object> request) throws DecoderException {
        long id = Long.valueOf(request.get("uuid").toString());
        UpgradeFile file = getFileByTerminalId(id);
        List<String> resources = new ArrayList<>();
        byte[] md5Bytes = Hex.decodeHex(file.getMd5());
        byte[] fileTypeBytes = Hex.decodeHex(file.getFileType());
        byte[] fileKeyBytes = mergeArray(md5Bytes, fileTypeBytes);
        resources.add(Base64.getEncoder().encodeToString(fileKeyBytes));
        return RestResponse.success(resources);
    }

    public UpgradeFile getFileByTerminalId(long id) {
        UpgradeResult result = resultService.findLatestResultByTerminalId(id);
        UpgradeFile file;
        if (ObjUtil.isNotEmpty(result)) {
            UpgradeVersion currVersion = versionService.findUpgradeVersionById(result.getAfterVersionId());
            file = fileService.findUpgradeFileById(currVersion.getFileId());
        } else {
            UpgradeVersion initVersion = versionService.findInitVersion();
            file = fileService.findUpgradeFileById(initVersion.getFileId());
        }
        return file;
    }

    /**
     * @desc 上报升级结果
     * @param request
     * @return
     */
    public RestResponse reportResult(Map<String, Object> request) {
        long stationId = Long.valueOf(request.get("uuid").toString());
        String fileKey = request.get("fileKey").toString();
        byte[] fileKeyBytes = Base64.getDecoder().decode(fileKey);
        String md5 = Hex.encodeHexString(FileUtil.subBytes(fileKeyBytes, 0, 16));
        UpgradeFile file = fileService.findUpgradeFileByMd5(md5);
        if (ObjUtil.isEmpty(file)) {
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "invalid fileKey");
        }
        UpgradeVersion version = versionService.findLatestVersionByFileId(file.getId());
        if (ObjUtil.isEmpty(version)) {
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "cannot find the upgrade version");
        }
        UpgradeResult result = resultService.findResultByTerminalIdAndVersionId(stationId, version.getId());
        if (ObjUtil.isEmpty(result)) {
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "cannot find the upgrade result");
        }
        result.setState(Constant.UPGRADE_RESULT_STATE.UP_FAIL);
        result.setErrorCode(Integer.valueOf(request.get("errCode").toString()));
        resultService.modifyResultOnly(result);
        return RestResponse.success();
    }

    /**
     * @desc
     * @param request
     * @return
     */
    public RestResponse reportVersion(Map<String, Object> request) {
        long stationId = Long.valueOf(request.get("uuid").toString());
        String verBase64 = request.get("ver").toString();
        byte[] verBytes = Base64.getDecoder().decode(verBase64.replaceAll("\\[", "").replaceAll("]", "").trim());
        verBytes = FileUtil.subBytes(verBytes, 0, 16);
        UpgradeResult result = resultService.findLatestResultByTerminalId(stationId);
        if (ObjUtil.isEmpty(result)) {
            return RestResponse.success();
        }
        UpgradeFile file = fileService.findUpgradeFileByMd5(Hex.encodeHexString(verBytes));
        if (ObjUtil.isEmpty(file)) {
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "cannot find the upgrade file");
        }
        UpgradeVersion version = versionService.findLatestVersionByFileId(file.getId());
        if (ObjUtil.isEmpty(version)) {
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "cannot find the upgrade version");
        }
        // 升级成功
        if (result.getAfterVersionId() == version.getId()) {
            result.setState(Constant.UPGRADE_RESULT_STATE.UP_SUCCESS);
            resultService.modifyResultOnly(result);
        }
        return RestResponse.success();
    }

    public RestResponse sendVersion2AllStation() {
        List<BaseStationInfo> baseStationInfoList = stationService.loadAvailableStation();
        for (BaseStationInfo baseStationInfo : baseStationInfoList) {
            super.forward2terminal(baseStationInfo.getBaseStationInfoId());
        }
        return RestResponse.success();
    }
}
