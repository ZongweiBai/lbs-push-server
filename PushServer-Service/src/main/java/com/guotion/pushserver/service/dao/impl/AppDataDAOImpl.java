package com.guotion.pushserver.service.dao.impl;

import com.guotion.pushserver.service.dao.AppDataDAO;
import com.guotion.pushserver.service.entity.AppData;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDataDAOImpl implements AppDataDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    public AppData getAppData(String appId) {
        BasicQuery query = new BasicQuery(new BasicDBObject("appId", appId));
        List list = this.mongoTemplate.find(query, AppData.class);
        return list.size() > 0 ? (AppData) list.get(0) : null;
    }

    public void add(AppData appData) {
        this.mongoTemplate.save(appData);
    }
}