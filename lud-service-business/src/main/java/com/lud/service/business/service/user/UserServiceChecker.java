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
package com.lud.service.business.service.user;

import org.springframework.stereotype.Service;

import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.content.redis.node.UserNode;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.task.Task;

/**
 * @author sunqinqiu 
 * @date   2019-10-28 01:42
 */
@Task(time = 10)
@Service
public class UserServiceChecker extends CoreService {
    /**
     * 每10分钟检查一次Session
     * @author sunqinqiu 
     * @date   2019-10-28 01:43
     */
    public void run() {
        if (this.redisService.exists(UserNode.USER_SESSION_REDIS_EXPRE)) {
            this.redisService.hmgetAll(UserNode.USER_SESSION_REDIS_EXPRE).forEach((key, value) -> {
                long cur = DateUtil.getCurrentTimestamp();
                long lastUpdate = Convert.toLong(value);
                if (cur - lastUpdate > 60 * 60) {
                    this.redisService.hdel(UserNode.USER_SESSION_REDIS_EXPRE, key);
                    this.redisService.hdel(UserNode.USER_SESSION_REDIS, key);
                }
            });
        }
    }
}
