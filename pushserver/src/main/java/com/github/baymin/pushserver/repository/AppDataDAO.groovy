package com.github.baymin.pushserver.repository

import com.github.baymin.pushserver.entity.AppData

/**
 * TODO 这里描述class的作用
 * @author Zongwei* @date 2020/4/5 20:45
 */
interface AppDataDAO {

    void saveOrUpdate(AppData appData);

    AppData getAppData(String appId);

}