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
package com.lud.iot.business.core.server.runtime;

import org.apache.dubbo.config.annotation.Service;

import com.lud.util.dubbo.util.PreloaderAbstractService;
import com.lud.util.dubbo.util.PreloaderService;

/**
 * @author sunqinqiu 
 * @date   2019-10-27 02:03
 */
@Service(group = "iotserver", version = "0.1.0")
public class PreloaderServiceImp extends PreloaderAbstractService implements PreloaderService {}
