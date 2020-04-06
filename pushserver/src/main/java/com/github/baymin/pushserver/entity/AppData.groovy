package com.github.baymin.pushserver.entity

import org.springframework.data.annotation.Id

/**
 * 记录个推的appId等信息
 * @author Zongwei* @date 2020/4/2 22:34
 */
class AppData {

    /**
     * 主键ID
     */
    @Id
    String id

    String appId

    String appKey

    String master

}
