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

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.user.UserEntity;
import com.lud.service.business.api.user.UserService;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.content.redis.node.UserNode;
import com.lud.util.dubbo.util.CoreService;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 20:49
 */
@Service
public class UserServiceImp extends CoreService implements UserService {
    
    /**
     * 获取用户Token
     * @author sunqinqiu 
     * @date   2019-03-04 05:03
     * @param project
     * @param userid
     * @param usergroup
     * @return
     */
    @Override
    public String getUserToken(String project, String userid, String usergroup) {
        return DEncrypt.getSHAEncode(MessageFormat.format("{0}.{1}.{2}", project, userid, usergroup));
    }
    
    /**
     * 该方法为测试方法，做好登录之后请修改
     * @author sunqinqiu 
     * @date   2019-01-08 19:54
     * @return
     */
    @Override
    public UserEntity getUserInfoByToken(String token) {
        if (token.isEmpty()) return new UserEntity();
        return JSON.parseObject(this.redisService.hget(UserNode.USER_SESSION_REDIS, token), UserEntity.class);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-01 21:25
     */
    @Override
    public void logout(String token) {
        this.redisService.hdel(UserNode.USER_SESSION_REDIS, token);
        this.redisService.hdel(UserNode.USER_SESSION_REDIS_EXPRE, token);
    }
    
    /**
     * 设置SESSION
     * @author sunqinqiu 
     * @date   2019-03-04 03:59
     * @param project
     * @param userid
     * @param usergroup
     */
    
    @Override
    public void setUserSession(String token, Map<String, Serializable> map) {
        UserEntity user = new UserEntity();
        user.setUserid(Convert.toString(map.get("userid")));
        user.setGroup(Convert.toString(map.get("usergroup")));
        user.setGroupName(Convert.toString(map.get("usergroup_groupname")));
        user.setGroupKey(Convert.toString(map.get("usergroupkey")));
        user.setDepartment(Convert.toString(map.get("userdepartment")));
        user.setDepartmentName(Convert.toString(map.get("userdepartment_shortname")));
        user.setOffice(Convert.toString(map.get("office")));
        user.setRole(Convert.toString(map.get("userrole")));
        user.setName(Convert.toString(map.get("name")));
        user.setToken(token);
        user.setProject(Convert.toString(map.get("project")));
        user.setLast(new Date());
        this.redisService.hset(UserNode.USER_SESSION_REDIS, token, JSON.toJSONString(user));
        this.redisService.hset(UserNode.USER_SESSION_REDIS_EXPRE, token, DateUtil.getCurrentTimestamp() + "");
    }
    
    /**
     * 更新用户SESSION
     * @author sunqinqiu 
     * @date   2019-10-28 01:21
     * @param token
     */
    @Override
    public boolean updateUserSession(String token) {
        if (this.redisService.exists(UserNode.USER_SESSION_REDIS_EXPRE)) {
            boolean hexists = this.redisService.hexists(UserNode.USER_SESSION_REDIS_EXPRE, token);
            if (hexists) this.redisService.hset(UserNode.USER_SESSION_REDIS_EXPRE, token, DateUtil.getCurrentTimestamp() + "");
            return hexists;
        }
        return false;
    }
}
