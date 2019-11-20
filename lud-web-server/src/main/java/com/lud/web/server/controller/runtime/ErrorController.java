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
package com.lud.web.server.controller.runtime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 错误页面
 * @author sunqinqiu 
 * @date   2019-02-23 14:39
 */
@Controller
@RequestMapping("/runtime/error")
public class ErrorController {
    
    /**
     * 404错误页面-json
     * @author sunqinqiu 
     * @date   2019-02-23 14:56
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/json-404")
    public String json404(ModelMap map) {
        return "";
    }
    
    /**
     * 404错误页面
     * @author sunqinqiu 
     * @date   2019-02-23 14:49
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/page-404")
    public String page404(ModelMap map) {
        return "";
    }
}
