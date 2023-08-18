package com.bcsd.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bcsd.common.core.controller.BaseController;
import com.bcsd.common.core.domain.AjaxResult;
import com.bcsd.common.core.domain.model.RegisterBody;
import com.bcsd.framework.web.service.SysRegisterService;

/**
 * 注册验证
 * @author bcsd
 */
@RestController
public class SysRegisterController extends BaseController {

    @Autowired
    private SysRegisterService registerService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user) {
        //String msg = registerService.register(user);
        //return StringUtils.isEmpty(msg) ? success() : error(msg);
        return error("当前系统没有开启注册功能！");
    }
}
