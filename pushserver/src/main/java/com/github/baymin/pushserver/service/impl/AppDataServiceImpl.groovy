package com.github.baymin.pushserver.service.impl

import com.github.baymin.pushserver.entity.AppData
import com.github.baymin.pushserver.repository.AppDataDAO
import com.github.baymin.pushserver.service.AppDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * TODO 这里描述class的作用
 * @author Zongwei* @date 2020/4/5 20:44
 */
@Service
class AppDataServiceImpl implements AppDataService {

    @Autowired
    private AppDataDAO appDataDAO

    @Override
    void addAppData(AppData appData) {
        AppData appDataInDb = getAppData(appData.getAppId())
        if (appDataInDb) {
            appData.setId(appDataInDb.getId())
        }
        appDataDAO.saveOrUpdate(appData)
    }

    @Override
    AppData getAppData(String appId) {
        appDataDAO.getAppData(appId)
    }
}
