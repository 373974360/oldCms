package com.deya.project.dz_szb;

import java.util.List;
import java.util.Map;

/**
 * 电子报 RPC
 * Created by yangyan on 2016/12/6.
 */
public class SzbRPC {

    public List<SzbBean> getSzbList(Map<String, Object> params) {
        return SzbManager.getSzbList(params);
    }

    public String getSzbCount(Map<String, Object> params) {
        return SzbManager.getSzbCount(params);
    }

    public boolean addSzb(Map<String, Object> params) {
        return SzbManager.addSzb(params);
    }

    public SzbBean getSzb(Map<String, String> params) {
        return SzbManager.getSzb(params);
    }

    public boolean updateSzb(Map<String, Object> params) {
        return SzbManager.updateSzb(params);
    }

    public boolean updateSzbStatus(Map<String, Object> params) {
        return SzbManager.updateStatus(params);
    }

    public boolean deleteSzb(Map<String, Object> params) {
        return SzbManager.deleteSzb(params);
    }

    public boolean publishSzb(Map<String, Object> params) {
        return SzbManager.publishSzb(params);
    }
}
