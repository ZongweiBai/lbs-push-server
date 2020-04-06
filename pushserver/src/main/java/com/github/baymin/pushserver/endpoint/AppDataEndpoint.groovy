package com.github.baymin.pushserver.endpoint

import com.github.baymin.pushserver.entity.AppData
import com.github.baymin.pushserver.service.AppDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * AppData相关restful接口
 * @author Zongwei* @date 2020/4/5 20:25
 */
@RestController
@RequestMapping("/api/v1/appdata")
class AppDataEndpoint {

    @Autowired
    private AppDataService appDataService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    void addAppData(AppData appData) {
        appDataService.addAppData(appData);
    }

}
