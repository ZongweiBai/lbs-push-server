package com.github.baymin.pushserver.endpoint

import com.github.baymin.pushserver.entity.UserAccount
import com.github.baymin.pushserver.model.Location
import com.github.baymin.pushserver.model.LocationInfo
import com.github.baymin.pushserver.service.UserAccountService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 地理位置信息
 * @author Zongwei* @date 2020/4/2 22:49
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/location")
class LocationEndpoint {

    @Autowired
    private UserAccountService aliasAccountService

    /**
     * 更新指定alias的经纬度信息
     * @param locationInfo
     */
    @PutMapping
    void updateLocation(@RequestBody LocationInfo locationInfo) {
        if (!(locationInfo.getAlias() && locationInfo.getLocation()
                && locationInfo.getLocation().getLatitude() && locationInfo.getLocation().getLongitude())) {
            log.info("请求参数有误")
            return
        }
        aliasAccountService.updateLocation(locationInfo.getAlias(), locationInfo.getLocation())
    }

    /**
     * 查找指定坐标半径附近的Location信息
     * @param radius 指定半径，单位米
     * @param longitude 经度
     * @param latitude 维度
     * @return
     */
    @GetMapping("/nearby/{radius}")
    def getAroundPoints(@PathVariable Double radius, @RequestParam Double longitude, @RequestParam Double latitude) {
        def location = new Location("longitude": longitude, "latitude": latitude)
        def aliasAccountList = this.aliasAccountService.getNearbyAccounts(location, radius.doubleValue());
        def locationList = new LinkedList();
        for (UserAccount aliasAccount : aliasAccountList) {
            locationList.add(new LocationInfo("location": aliasAccount.getLocation(), "alias": aliasAccount.getAlias()));
        }
        return locationList
    }

}
