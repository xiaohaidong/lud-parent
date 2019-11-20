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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lud.util.content.util.text.CT;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.view.ViewDataEntity;

/**
 * @author sunqinqiu 
 * @date   2019-03-06 10:55
 */
@Service("com.lud.web.util.view.data.webviewnulldataservice")
public class WebViewDataNullService implements IWebViewDataService {
    
    @Override
    public Serializable getData(ViewDataEntity data, ContextRequest request) {
        String resultType = data.getConfig().get(CT.STRING_RESULT);
        return resultType.equals(CT.STRING_LIST) ? new ArrayList<Map<String, Serializable>>() : new HashMap<String, Serializable>();
    }
}
