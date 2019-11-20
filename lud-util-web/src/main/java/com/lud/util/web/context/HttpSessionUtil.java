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
package com.lud.util.web.context;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;

/**
 * Session管理
 * @author sunqinqiu 
 * @date   2018-05-26 19:32
 */
public final class HttpSessionUtil {
    /**
     * 获取单个SESSION名称
     * @author sunqinqiu 
     * @date   2018-05-13 14:22
     * @param name
     * @return
     */
    public static String getCookie(String name) {
        Cookie[] cookies = getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie item : cookies) {
                item.getDomain();
                if (item.getName().equals(name)) { return item.getValue(); }
            }
        }
        return CT.EMPTY;
    }
    
    /**
     * 获取当前COOKIES
     * @author sunqinqiu 
     * @date   2018-05-13 14:21
     * @return
     */
    public static Cookie[] getCookies() {
        HttpServletRequest req = HttpContext.getRequest();
        if (req == null) { return new Cookie[0]; }
        return req.getCookies();
    }
    
    /**
     * 设置COOKIES
     * @author sunqinqiu 
     * @date   2018-05-13 14:21
     * @param name
     * @param value
     * @param num
     */
    public static void setCookie(String domain, String name, String value, int maxAge) {
        HttpServletResponse response = HttpContext.getResponse();
        if (response == null) { return; }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(CharacterContent.PATH_SPLITER_S);
        cookie.setDomain(domain);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    
    /**
     * 
     */
    private HttpSessionUtil() {}
    
}
