package com.guotion.pushserver.service.dao.impl;

import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.dao.AliasAccountDAO;
import com.guotion.pushserver.service.entity.AliasAccount;
import com.guotion.pushserver.service.enums.DeviceType;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AliasAccountDAOImpl implements AliasAccountDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    public AliasAccount findByAlias(String alias) {
        BasicDBObject basicDBObject = new BasicDBObject("alias", alias);
        BasicQuery query = new BasicQuery(basicDBObject);
        List list = this.mongoTemplate.find(query, AliasAccount.class);
        return list.size() > 0 ? (AliasAccount) list.get(0) : null;
    }

    public void addAliasAccount(AliasAccount aliasAccount) {
        this.mongoTemplate.save(aliasAccount);
    }

    public void updateLocation(String alias, Location location) {
        BasicQuery query = new BasicQuery(new BasicDBObject("alias", alias));
        BasicDBObject basicDBObject = new BasicDBObject("longitude", Double.valueOf(location.getLongitude()));
        basicDBObject.append("latitude", Double.valueOf(location.getLatitude()));
        Update update = new Update().set("location", basicDBObject);
        this.mongoTemplate.updateFirst(query, update, AliasAccount.class);
    }

    public void updateClientAndDevice(String clientId, String alias, DeviceType device) {
        BasicQuery query = new BasicQuery(new BasicDBObject("alias", alias));
        Update update = new Update().set("clientId", clientId).set("bindDevice", device);
        this.mongoTemplate.updateFirst(query, update, AliasAccount.class);
    }

    public List<AliasAccount> getAround(String[] aliasArray, Location location, double radius) {
        Object[] locationArr = new Object[]{location.getLongitude(), location.getLatitude()};
        Object[] centerSphereArr = new Object[]{locationArr, Double.valueOf(radius / 6371.0D)};
//        BasicDBObject queryObject = new BasicDBObject("location", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", new Object[]{[location.getLongitude(), location.getLatitude()], Double.valueOf(radius / 6371.0D)})));
        BasicDBObject queryObject = new BasicDBObject("location", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", centerSphereArr)));
        queryObject.append("alias", new BasicDBObject("$in", aliasArray));
        BasicQuery query = new BasicQuery(queryObject);
        return this.mongoTemplate.find(query, AliasAccount.class);
    }

    public List<AliasAccount> getAround(Location location, double radius) {
        Object[] locationArr = new Object[]{location.getLongitude(), location.getLatitude()};
        Object[] centerSphereArr = new Object[]{locationArr, Double.valueOf(radius / 6371.0D)};
//        BasicDBObject queryObject = new BasicDBObject("location", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", new Object[]{[location.getLongitude(), location.getLatitude()], Double.valueOf(radius / 6371.0D)})));
        BasicDBObject queryObject = new BasicDBObject("location", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", centerSphereArr)));
        BasicQuery query = new BasicQuery(queryObject);
        return this.mongoTemplate.find(query, AliasAccount.class);
    }
}