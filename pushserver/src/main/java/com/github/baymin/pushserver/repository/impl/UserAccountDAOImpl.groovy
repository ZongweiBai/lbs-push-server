package com.github.baymin.pushserver.repository.impl

import com.github.baymin.pushserver.constant.DeviceType
import com.github.baymin.pushserver.entity.UserAccount
import com.github.baymin.pushserver.model.Location
import com.github.baymin.pushserver.repository.UserAccountDAO
import com.mongodb.BasicDBObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

/**
 * UserAccount数据库交互
 * @author Zongwei* @date 2020/4/2 23:23
 */
@Repository
class UserAccountDAOImpl implements UserAccountDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    UserAccount findByAlias(String alias) {
        def basicDBObject = new BasicDBObject("alias", alias)
        def query = new BasicQuery(basicDBObject.toString())
        def list = mongoTemplate.find(query, UserAccount.class)
        return list.size() > 0 ? (UserAccount) list.get(0) : null
    }

    @Override
    void addAliasAccount(UserAccount aliasAccount) {
        mongoTemplate.save(aliasAccount)
    }

    @Override
    void updateLocation(String alias, Location location) {
        def query = new BasicQuery(new BasicDBObject("alias", alias).toString())
        def basicDBObject = new BasicDBObject("longitude", Double.valueOf(location.getLongitude()))
        basicDBObject.append("latitude", Double.valueOf(location.getLatitude()))
        def update = new Update().set("location", basicDBObject)
        mongoTemplate.updateFirst(query, update, UserAccount.class)
    }

    @Override
    void updateClientAndDevice(String alias, String clientId, DeviceType device) {
        def query = new BasicQuery(new BasicDBObject().put("alias", alias).toString())
        def update = new Update().set("clientId", clientId).set("bindDevice", device)
        mongoTemplate.updateFirst(query, update, UserAccount.class)
    }

    @Override
    List<UserAccount> getAround(String[] aliasArray, Location location, double radius) {
        def locationArr = [location.getLongitude(), location.getLatitude()]
        def centerSphereArr = [locationArr, Double.valueOf(radius / 6371.0D)]
        BasicDBObject queryObject = new BasicDBObject("location", new BasicDBObject("\$geoWithin", new BasicDBObject("\$centerSphere", centerSphereArr)))
        queryObject.append("alias", new BasicDBObject("\$in", aliasArray))
        def query = new BasicQuery(queryObject.toString())
        mongoTemplate.find(query, UserAccount.class)
    }

    @Override
    List<UserAccount> getAround(Location location, double radius) {
        def locationArr = [location.getLongitude(), location.getLatitude()]
        def centerSphereArr = [locationArr, radius / 6371.0D]
        def queryObject = new BasicDBObject("location", new BasicDBObject("\$geoWithin", new BasicDBObject("\$centerSphere", centerSphereArr)))
        def query = new BasicQuery(queryObject.toString())
        mongoTemplate.find(query, UserAccount.class)
    }
}
