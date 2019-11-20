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
package com.lud.util.web.view.entity.render;

import org.springframework.stereotype.Service;

import com.lud.util.web.api.view.IViewCompService;

/**
 * 最常用的渲染模式，直接把config和Detail数据加到网页中
 * 所有数据必须配置在Data中，渲染时禁止放入任何数据
 * @author sunqinqiu 
 * @date   2019-01-10 12:14
 */
@Service("com.lud.web.util.view.entity.render.viewcompservice.normal")
public class ViewCompServiceForNormal extends AbstractViewCompService implements IViewCompService {
    
}
