package com.bcsd.project.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bcsd.project.constants.ConfigConstant;
import com.bcsd.project.domain.WxUser;
import com.bcsd.project.mapper.WxUserMapper;
import com.bcsd.project.service.WxMsgService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author www.joolun.com
 */
@Slf4j
@Component
@AllArgsConstructor
public class UnsubscribeHandler extends AbstractHandler {

    private final WxMsgService wxMsgService;
    private final WxUserMapper wxUserMapper;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String openId = wxMessage.getFromUser();
        log.info("取消关注用户 OPENID: " + openId);
        WxUser wxUser = wxUserMapper.selectOne(Wrappers.<WxUser>lambdaQuery()
                .eq(WxUser::getOpenId,openId));
        if(wxUser!=null){
            wxUser.setSubscribe(ConfigConstant.SUBSCRIBE_TYPE_NO);
            wxUser.setCancelSubscribeTime(LocalDateTime.now().toString());
            wxUserMapper.updateById(wxUser);
            //消息记录
            MsgHandler.getWxMpXmlOutMessage(wxMessage,null,wxUser,wxMsgService);
        }
        return null;
    }

}
