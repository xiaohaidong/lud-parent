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
package com.lud.service.core.mongo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.api.config.ResourceService;
import com.lud.service.core.api.mongo.MongoService;
import com.lud.util.comm.util.Convert;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * @author sunqinqiu 
 * @date   2018-12-31 23:33
 */
@Service
public class MongoServiceImp implements MongoService {
    private final Logger       loger = LoggerFactory.getLogger(this.getClass());
    /**
     * 
     */
    @Autowired
    private MongoServerManager mongoServerManager;
    /**
     * 
     */
    @Autowired
    private ResourceService    resourceService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-07 21:25
     * @return
     */
    private Bson getBsonByMap(Map<String, Serializable> item) {
        String key = Convert.toString(item.get("k"));
        Serializable value = item.get("v");
        String lim = Convert.toString(item.get("l"));
        switch (lim) {
            case ">":
                return Filters.gt(key, value);
            case ">=":
                return Filters.gte(key, value);
            case "<":
                return Filters.lt(key, value);
            case "<=":
                return Filters.lte(key, value);
            default:
                return Filters.eq(key, value);
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-07 21:25
     * @param where
     * @return
     */
    private Bson getBsonByWhere(List<Map<String, Serializable>> wheres) {
        if (wheres != null) {
            Bson[] bsons = new Bson[wheres.size()];
            int i = 0;
            for (Map<String, Serializable> where : wheres) {
                bsons[i] = getBsonByMap(where);
                i++;
            }
            return Filters.and(bsons);
        } else {
            return null;
        }
    }
    
    /**
     * 获取Collection
     * @author sunqinqiu 
     * @date   2018-08-05 18:47
     * @param dbname
     * @param collection
     * @return 
     * @return
     */
    private MongoCollection<Document> getCollection(String dbName, String collectionName) {
        try {
            MongoDatabase mongodb = getDB(dbName);
            return mongodb.getCollection(collectionName, Document.class);
        } catch (Exception e) {
            loger.error("{}{}", resourceService.getError("1001.0006"), e);
            return null;
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-16 17:46
     * @param dbName
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getCollections(String dbName) {
        try {
            List<Map<String, Serializable>> result = new ArrayList<>();
            MongoDatabase db = getDB(dbName);
            for (String cname : db.listCollectionNames()) {
                MongoCollection<Document> coll = getCollection(dbName, cname);
                if (coll != null) {
                    Map<String, Serializable> item = new HashMap<>();
                    item.put("name", cname);
                    item.put("docs", coll.countDocuments());
                    result.add(item);
                }
            }
            return result;
        } catch (Exception e) {
            loger.error("{}{}", resourceService.getError("1001.0005"), e);
            return new ArrayList<>();
        }
    }
    
    /**
     * 获取Database列表
     * @author sunqinqiu 
     * @date   2018-12-31 23:40
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getDataBaseList() {
        List<Map<String, Serializable>> result = new ArrayList<>();
        try {
            MongoClient mongo = mongoServerManager.getMongoClient();
            for (String key : mongo.listDatabaseNames()) {
                Map<String, Serializable> item = new HashMap<>();
                MongoDatabase db = getDB(key);
                item.put("name", key);
                item.put("size", JSON.parseArray(JSON.toJSONString(db.listCollectionNames())).size());
                result.add(item);
            }
        } catch (Exception e) {
            loger.error("{}{}", resourceService.getError("1001.0001"), e);
        }
        return result;
    }
    
    /**
     * 获取DB
     * @author sunqinqiu 
     * @date   2018-12-31 23:36
     * @param dbName
     * @return
     */
    private MongoDatabase getDB(String dbName) {
        MongoClient mongo = mongoServerManager.getMongoClient();
        return mongo.getDatabase(dbName);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-01 00:32
     * @param dbName
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getDBInfo(String dbName) {
        try {
            List<Map<String, Serializable>> result = new ArrayList<>();
            MongoClient mongo = mongoServerManager.getMongoClient();
            MongoDatabase dataBase = mongo.getDatabase(dbName);
            dataBase.listCollections().iterator().forEachRemaining(item -> {
                Map<String, Serializable> map = new HashMap<>();
                map.put("name", Convert.toString(item.get("name")));
                map.put("type", Convert.toString(item.get("type")));
                map.put("content", JSON.toJSONString(item.get("info")));
                result.add(map);
            });
            return result;
        } catch (Exception e) {
            loger.error("{}{}", resourceService.getError("1001.0001"), e);
            return new ArrayList<>();
        }
    }
    
    @Override
    public Map<String, Serializable> getNewWhere(String key, String l, Serializable value) {
        Map<String, Serializable> map = new HashMap<>();
        map.put("k", key);
        map.put("v", value);
        map.put("l", l);
        return map;
    }
    
    /**
     * 插入列表数据
     * @author sunqinqiu 
     * @date   2018-08-08 13:19
     * @param dbName
     * @param collectionName
     * @param data
     * @return
     */
    @Override
    public boolean insertList(String dbName, String collectionName, List<Map<String, Serializable>> data) {
        if (data.isEmpty()) { return true; }
        try {
            MongoCollection<Document> collection = getCollection(dbName, collectionName);
            if (collection == null) { return false; }
            List<Document> documents = new ArrayList<>();
            data.forEach(item -> documents.add(mapToDoc(item)));
            collection.insertMany(documents);
            return true;
        } catch (Exception e) {
            loger.error("{}{}", resourceService.getError("1001.0003"), e);
            return false;
        }
    }
    
    /**
     * 插入一条数据
     * @author sunqinqiu 
     * @date   2018-08-05 19:15
     * @param dbName
     * @param collectionName
     * @param json
     * @return
     */
    @Override
    public boolean insertOne(String dbName, String collectionName, Map<String, Serializable> data) {
        if (data.isEmpty()) { return true; }
        try {
            MongoCollection<Document> collection = getCollection(dbName, collectionName);
            if (collection == null) { return false; }
            collection.insertOne(mapToDoc(data));
            return true;
        } catch (Exception e) {
            loger.error("{}{}", resourceService.getError("1001.0002"), e);
            return false;
        }
    }
    
    /**
     * 把MAP转化为DOC
     * @author sunqinqiu 
     * @date   2018-08-08 13:17
     * @param data
     * @return
     */
    private Document mapToDoc(Map<String, Serializable> data) {
        Document doc = new Document();
        doc.putAll(data);
        return doc;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-07 20:53
     * @param dbName
     * @param collectionName
     * @param filed
     * @param where
     * @return
     */
    @Override
    public List<Map<String, Serializable>> select(String dbName, String collectionName, String filed, List<Map<String, Serializable>> where, String sort, int sortMethod, int begin, int size) {
        List<Map<String, Serializable>> list = new ArrayList<>();
        try {
            MongoCollection<Document> collection = getCollection(dbName, collectionName);
            if (collection == null) { return list; }
            Bson bson = getBsonByWhere(where);
            FindIterable<Document> iterable = bson == null ? collection.find() : collection.find(bson);
            if (!sort.isEmpty()) {
                iterable.sort(new BasicDBObject(sort, sortMethod));
            }
            if (size > 0) {
                iterable.skip(begin).limit(size);
            }
            String[] fileds = filed.split(",");
            for (Document document : iterable) {
                Map<String, Serializable> item = new HashMap<>();
                for (String _filed : fileds) {
                    Object docitem = document.get(_filed);
                    switch (docitem.getClass().getName()) {
                        case "org.bson.Document":
                            item.put(_filed, JSON.parseObject(JSON.toJSONString(document.get(_filed)), HashMap.class));
                            break;
                        case "java.util.Date":
                            item.put(_filed, (Date) document.get(_filed));
                            break;
                        default:
                            item.put(_filed, (Serializable) document.get(_filed));
                            break;
                    }
                }
                list.add(item);
            }
        } catch (Exception e) {
            loger.error("{}{}", resourceService.getError("1001.0004"), e);
            loger.error(e.toString());
        }
        return list;
    }
}
