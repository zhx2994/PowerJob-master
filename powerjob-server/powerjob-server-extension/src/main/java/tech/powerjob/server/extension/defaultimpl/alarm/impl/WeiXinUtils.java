package tech.powerjob.server.extension.defaultimpl.alarm.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author Harrison
 * @date 2022年12月19日 14:40
 */
@Slf4j
public class WeiXinUtils {


    /**
     * 发送文本信息报警
     *
     * @param url   机器人WebHook地址
     * @param content  报警消息
     * @param mobileList    手机号列表
     * @return  发送结果
     */
    public String alterText(String content, String url, List<String> mobileList){
        mobileList = (mobileList == null ? ListUtil.empty() : mobileList);

        Map<Object, Object> contentMap = MapUtil.builder()
                .put("content", content)
                .put("mentioned_mobile_list", mobileList)
                .map();
        Map<Object, Object> paramMap = MapUtil.builder()
                .put("msgtype", "text")
                .put("text", contentMap)
                .map();
        log.info("QyWeiXin WebHook Alter Text：{}", content);
        String response = HttpUtil.post(url, JSONObject.toJSONString(paramMap));
        log.info("QyWeiXin WebHook Alter Text Response：{}", response);
        return response;
    }

    public String alterMarkdown(String content, String url, List<String> mobileList){
        mobileList = (mobileList == null ? ListUtil.empty() : mobileList);

        Map<Object, Object> contentMap = MapUtil.builder()
                .put("content", content)
                .put("mentioned_mobile_list", mobileList)
                .map();
        Map<Object, Object> paramMap = MapUtil.builder()
                .put("msgtype", "markdown")
                .put("markdown", contentMap)
                .map();
        log.info("QyWeiXin WebHook Alter MarkDown：{}", content);
        String response = HttpUtil.post(url, JSONObject.toJSONString(paramMap));
        log.info("QyWeiXin WebHook Alter MarkDown Response：{}", response);
        return response;
    }
}
