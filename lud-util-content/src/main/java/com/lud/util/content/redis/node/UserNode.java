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
package com.lud.util.content.redis.node;

/**
 * @author sunqinqiu 
 * @date   2019-10-28 01:44
 */
public class UserNode {
    private UserNode() {}
    
    public static final String USER_SESSION_REDIS       = "com.lud.web.user.session";
    public static final String USER_SESSION_REDIS_EXPRE = "com.lud.web.user.session.expre";
}
