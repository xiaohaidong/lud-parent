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
package com.lud.util.spring.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.*;

/**
 * @author sunqinqiu
 * @date 2019-10-25 15:43
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Preload {
    /**
     * 执行顺序
     * @author sunqinqiu 
     * @date   2019-10-25 17:22
     * @return
     */
    int seq() default 0;
    
    /**
     * 是否刷新资源刷新
     * @author sunqinqiu 
     * @date   2019-10-25 17:22
     * @return
     */
    boolean fresh() default true;
    
    /**
     * 需要执行的方法
     * @author sunqinqiu 
     * @date   2019-10-25 17:23
     * @return
     */
    String exe() default "run";
}
