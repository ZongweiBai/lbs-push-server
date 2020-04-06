package com.github.baymin.pushserver.service

import com.github.baymin.pushserver.entity.AppData

/**
 * AppData业务接口
 * @author Zongwei* @date 2020/4/5 20:43
 */
interface AppDataService {

    void addAppData(AppData appData)

    AppData getAppData(String paramString)

}
