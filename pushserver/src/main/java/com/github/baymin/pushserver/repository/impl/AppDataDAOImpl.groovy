package com.github.baymin.pushserver.repository.impl

import com.github.baymin.pushserver.entity.AppData
import com.github.baymin.pushserver.repository.AppDataDAO
import com.mongodb.BasicDBObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

/**
 * AppData数据库
 * @author Zongwei* @date 2020/4/5 20:46
 */
@Repository
class AppDataDAOImpl implements AppDataDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    void saveOrUpdate(AppData appData) {
        mongoTemplate.save(appData)
    }

    @Override
    AppData getAppData(String appId) {
        def query = Query.query(Criteria.where("appId").is(appId))
        def list = mongoTemplate.find(query, AppData.class)
        return list ? list.get(0) : null
    }
}
