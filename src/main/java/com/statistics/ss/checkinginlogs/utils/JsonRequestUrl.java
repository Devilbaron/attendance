package com.statistics.ss.checkinginlogs.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.statistics.ss.checkingin.entity.Attendances;
import com.statistics.ss.checkingin.entity.AttendancesData;
import com.statistics.ss.checkingin.entity.PicturePath;
import com.statistics.ss.checkingin.entity.RequestData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonRequestUrl {
    Attendances attendances = new Attendances();

    static public List<PicturePath> getJsonArrayPp(String jsonString, int n) {
        List<PicturePath> list = new ArrayList<PicturePath>();
        JSONArray jsonArray = (JSONArray) JSONArray.parseObject(jsonString)
                .getJSONObject("data")
                .getJSONArray("data");

        Iterator<String> iterator = null;

//        if (n <0 ){
//            for (int i = 0; i < jsonArray.size(); i++) {
//
//                JSONObject item = (JSONObject) jsonArray.get(i);
//                PicturePath pp = new PicturePath();
//                pp.setSeqid(item.getInteger("Seqid"));
//                pp.setPicturePath(item.getString("picturePath"));
//                pp.setRecordId(item.getInteger("recordId"));
//                pp.setPassTime(item.getString("passTime"));
//                pp.setImageIndex(item.getInteger("imageIndex"));
//                pp.setFaceId(item.getInteger("faceId"));
//                pp.setImageIndex(item.getInteger("imageIndex"));
//                pp.setImageType(item.getInteger("imageType"));
//                pp.setImagedata(item.getString("imagedata"));
//                pp.setImageFormat(item.getString("imageFormat"));
//                pp.setImageurl(item.getString("imageurl"));
//                list.add(pp);
//            }
//        }else{

        JSONObject item = jsonArray.getJSONObject(n);
        JSONArray columnValues = item.getJSONArray("PicturePath");
        for (int i = 0; i < columnValues.size(); i++) {
            JSONObject item1 = (JSONObject) jsonArray.get(i);
            PicturePath pp = new PicturePath();
            pp.setSeqid(item1.getInteger("Seqid"));
            pp.setPicturePath(item1.getString("picturePath"));
            pp.setRecordId(item1.getInteger("recordId"));
            pp.setPassTime(item1.getString("passTime"));
            pp.setImageIndex(item1.getInteger("imageIndex"));
            pp.setFaceId(item1.getInteger("faceId"));
            pp.setImageIndex(item1.getInteger("imageIndex"));
            pp.setImageType(item1.getInteger("imageType"));
            pp.setImagedata(item1.getString("imagedata"));
            pp.setImageFormat(item1.getString("imageFormat"));
            pp.setImageurl(item1.getString("imageurl"));
            list.add(pp);
        }

        return list;
    }

    static public List<AttendancesData> getJsonArrayData(String jsonString) {
        List<AttendancesData> list = new ArrayList<AttendancesData>();
        JSONArray jsonArray = (JSONArray) JSONArray.parseObject(jsonString)
                .getJSONObject("data")
                .getJSONArray("data");
        Iterator<String> iterator = null;

        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject item = (JSONObject) jsonArray.get(i);
            AttendancesData pp = new AttendancesData();
            pp.setRecordId(item.getInteger("RecordId"));
            pp.setRecordDate(item.getString("RecordDate"));
            pp.setRecordTime(item.getString("RecordTime"));

            pp.setPersonCode(item.getInteger("PersonCode"));
            pp.setPersonName(item.getString("PersonName"));

            pp.setDepartmentName(item.getString("DepartmentName"));
            pp.setDepartmentCode(item.getInteger("DepartmentCode"));
//            pp.setInTime(item.getString("InTime"));
//            pp.setOutTime(item.getString("OutTime"));
//            pp.setDuration(item.getString("Duration"));
            pp.setPassTime(item.getString("PassTime"));
            pp.setInorout(item.getString("Inorout"));
//            pp.setDeviceCode(item.getInteger("DeviceCode"));
//            pp.setDeviceName(item.getString("DeviceName"));
//            List<PicturePath> p = getJsonArrayPp(jsonString, i);
//            pp.setPicturePath(p);
//            pp.setSeqid(item.getInteger("Seqid"));
//            pp.setPersonPicturePaths(item.getString("PersonPicturePaths"));
            list.add(pp);
        }
        return list;
    }

    static public RequestData getJsonData(String jsonString) {
        List<AttendancesData> list = new ArrayList<AttendancesData>();
        JSONObject jsonObject = JSONObject.parseObject(jsonString)
                .getJSONObject("data");
        RequestData result = new RequestData();
        result.setErrCode((Integer) jsonObject.get("ErrCode"));
        result.setErrMsg((String) jsonObject.get("ErrMsg"));
        return result;
    }


    static public RequestData getJsonString(String jsonString) {

        RequestData result = new RequestData();
        result.setData(getJsonArrayData(jsonString));
        ;
        result.setErrCode(getJsonData(jsonString).getErrCode());
        result.setErrMsg(getJsonData(jsonString).getErrMsg());
        return result;
    }
}
