package com.guotion.pushserver.service.dao.impl;

import com.guotion.pushserver.service.dao.OfflineMessageDAO;
import com.guotion.pushserver.service.entity.OfflineMessage;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OfflineMessageDAOImpl implements OfflineMessageDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addOfflineMessage(OfflineMessage offlineMessage) {
        this.mongoTemplate.save(offlineMessage);
    }

    public List<OfflineMessage> getOfflineMessage(String alias) {
        BasicQuery query = new BasicQuery(new BasicDBObject("alias", alias));
        List list = this.mongoTemplate.find(query, OfflineMessage.class);
        return list;
    }

    public int deleteOfflineMessage(String alias) {
        BasicQuery query = new BasicQuery(new BasicDBObject("alias", alias));
        WriteResult writeResult = this.mongoTemplate.remove(query, OfflineMessage.class);
        return writeResult.getN();
    }
}