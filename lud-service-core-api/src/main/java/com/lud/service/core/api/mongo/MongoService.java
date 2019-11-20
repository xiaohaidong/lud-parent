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
package com.lud.service.core.api.mongo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2018-12-31 23:32
 */
public interface MongoService {
    
    /**
     * 获取列表
     * @author sunqinqiu 
     * @date   2019-09-11 13:53
     * @param dbName
     * @return
     */
    List<Map<String, Serializable>> getCollections(String dbName);
    
    /**
     * 获取数据库列表
     * @author sunqinqiu 
     * @date   2019-09-11 13:52
     * @return
     */
    List<Map<String, Serializable>> getDataBaseList();
    
    /**
     * 获取DB信息
     * @author sunqinqiu 
     * @date   2019-09-11 13:53
     * @param dbName
     * @return
     */
    List<Map<String, Serializable>> getDBInfo(String dbName);
    
    /**
     * 获取新条件
     * @author sunqinqiu 
     * @date   2019-09-11 13:56
     * @param key
     * @param l
     * @param value
     * @return
     */
    Map<String, Serializable> getNewWhere(String key, String l, Serializable value);
    
    /**
     * 插入列表
     * @author sunqinqiu 
     * @date   2019-09-11 13:55
     * @param dbName
     * @param collectionName
     * @param data
     * @return
     */
    boolean insertList(String dbName, String collectionName, List<Map<String, Serializable>> data);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-11 13:53
     * @param dbName
     * @param collectionName
     * @param data
     * @return
     */
    boolean insertOne(String dbName, String collectionName, Map<String, Serializable> data);
    
    /**
     * 选择数据
     * @author sunqinqiu 
     * @date   2019-09-11 13:56
     * @param dbName
     * @param collectionName
     * @param filed
     * @param where
     * @param sort
     * @param sortMethod
     * @param begin
     * @param size
     * @return
     */
    List<Map<String, Serializable>> select(String dbName, String collectionName, String filed, List<Map<String, Serializable>> where, String sort, int sortMethod, int begin, int size);
    
}
