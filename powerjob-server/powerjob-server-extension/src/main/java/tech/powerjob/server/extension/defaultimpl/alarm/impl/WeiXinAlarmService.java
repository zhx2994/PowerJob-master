package tech.powerjob.server.extension.defaultimpl.alarm.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.powerjob.common.OmsConstant;
import tech.powerjob.server.extension.Alarmable;
import tech.powerjob.server.extension.defaultimpl.alarm.module.Alarm;
import tech.powerjob.server.persistence.remote.model.UserInfoDO;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @author Harrison
 * @date 2022年12月19日 14:39
 */
@Slf4j
@Service
public class WeiXinAlarmService implements Alarmable {

    private WeiXinUtils weiXinUtils;

    @Override
    public void onFailed(Alarm alarm, List<UserInfoDO> targetUserList) {
        if (weiXinUtils == null) {
            return;
        }
        String content = alarm.fetchContent().replaceAll(OmsConstant.LINE_SEPARATOR, OmsConstant.COMMA);
        targetUserList.forEach(user -> {
            String phone = user.getPhone();
            String webHook = user.getWebHook();
            if (StringUtils.isEmpty(webHook)) {
                return;
            }

            try {
                weiXinUtils.alterMarkdown(content, webHook, Collections.singletonList(phone));
            }catch (Exception e) {
                log.error("[WeiXinAlarmService] send weixin message failed, reason is {}", e.getMessage());
            }
        });
    }

    @PostConstruct
    public void init() {
        weiXinUtils = new WeiXinUtils();
        log.info("[WeiXinAlarmService] init WeiXinAlarmService successfully!");
    }

}
