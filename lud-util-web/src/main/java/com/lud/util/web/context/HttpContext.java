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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;

/**
 * @author sunqinqiu 
 * @date   2018-05-26 19:27
 */
public final class HttpContext {
    
    /**
     * 获取getContextPath
     * @author sunqinqiu 
     * @date   2018-05-13 14:20
     * @return
     */
    public static String getContextPath() {
        HttpServletRequest request = getRequest();
        if (request == null) { return CT.EMPTY; }
        return request.getContextPath();
    }
    
    /**
     * 获取DOMAIN
     * @author sunqinqiu 
     * @date   2018-05-13 14:20
     * @return
     */
    public static String getDomain() {
        HttpServletRequest request = getRequest();
        if (request == null) { return CT.EMPTY; }
        return request.getServerName();
    }
    
    /**
     * getParameters
     * @author sunqinqiu 
     * @date   2018-09-09 19:07
     * @return
     */
    public static Map<String, String> getParameters() {
        HttpServletRequest req = getRequest();
        if (req == null) { return null; }
        Map<String, String> result = new HashMap<>();
        for (Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            result.put(Convert.toString(entry.getKey()), Convert.toString(Content.joinString(CharacterContent.STRING_COMMA, entry.getValue())));
        }
        return result;
    }
    
    /**
     * 获取request
     * @author sunqinqiu 
     * @date   2018-05-13 14:20
     * @return
     */
    public static HttpServletRequest getRequest() {
        try {
            if (RequestContextHolder.currentRequestAttributes() == null) { return null; }
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (Exception ex) {
            return null;
        }
    }
    
    /**
     * 获取response
     * @author sunqinqiu 
     * @date   2018-05-13 14:20
     * @return
     */
    public static HttpServletResponse getResponse() {
        if (RequestContextHolder.currentRequestAttributes() == null) { return null; }
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
    
    /**
     * 
     */
    private HttpContext() {}
}
