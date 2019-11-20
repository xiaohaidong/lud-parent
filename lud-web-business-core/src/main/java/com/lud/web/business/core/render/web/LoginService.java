/*
 * Copyright 2018-2019 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lud.web.business.core.render.web;

import java.io.Serializable;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.service.business.api.user.UserService;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.context.ContextResult;
import com.lud.util.web.core.WebUserService;
import com.lud.util.web.util.view.BWebViewRender;
import com.lud.util.web.util.view.IWebViewRender;

/**
 * @author sunqinqiu 
 * @date   2019-01-11 02:34
 */
@Service("com.lud.web.business.core.render.web.login.main")
public class LoginService extends BWebViewRender implements IWebViewRender {
    @Autowired
    private WebUserService webUserService;
    @Reference
    private UserService    userService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-04 04:10
     * @param map
     */
    private void setUserSession(Map<String, Serializable> map) {
        String userid = Convert.toString(map.get("userid"));
        String usergroup = Convert.toString(map.get("usergroup"));
        String project = Convert.toString(map.get("project"));
        String token = userService.getUserToken(project, userid, usergroup);
        userService.setUserSession(token, map);
        webUserService.setUserToken(token);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-04 00:56
     * @param map
     * @param request
     * @param result
     */
    public void userLogin(Map<String, Serializable> map, ContextRequest request, ContextResult result) {
        String code = "";
        boolean res = false;
        if (map.isEmpty()) {
            code = "2000.0001";
        } else {
            String userpwd = DEncrypt.getSHAEncode(request.getData().get("userpwd"));
            if (userpwd.equals(map.get("userpwd"))) {
                code = "2000.0003";
                setUserSession(map);
                res = true;
            } else {
                code = "2000.0002";
            }
        }
        result.sets(res, code, resourceService.getMessage(code));
    }
}
