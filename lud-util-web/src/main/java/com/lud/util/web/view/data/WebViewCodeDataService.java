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
package com.lud.util.web.view.data;

import java.io.Serializable;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import com.lud.service.business.api.code.CodeService;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.view.ViewDataEntity;

/**
 * @author sunqinqiu
 * @date 2019-02-24 21:55
 */
@Service("com.lud.web.util.view.data.webviewcodedataservice")
public class WebViewCodeDataService implements IWebViewDataService {
    /**
     * REDIS服务
     */
    @Reference
    protected CodeService codeService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 14:59
     * @param data
     * @param request
     * @return
     */
    @Override
    public Serializable getData(ViewDataEntity data, ContextRequest request) {
        return (Serializable) codeService.getContent(data.getQuery());
    }
}
