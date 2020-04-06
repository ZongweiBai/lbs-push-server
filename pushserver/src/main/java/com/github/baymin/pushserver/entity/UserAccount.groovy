package com.github.baymin.pushserver.entity

import com.github.baymin.pushserver.constant.DeviceType
import com.github.baymin.pushserver.model.Location
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.index.Indexed

/**
 * 用户信息表
 * @author Zongwei* @date 2020/4/2 22:43
 */
class UserAccount {

    @Id
    String id

    AppData appData

    String clientId

    @Indexed(unique = true)
    String alias

    DeviceType bindDevice

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
    Location location

}
