package com.github.baymin.pushserver.entity

import com.github.baymin.pushserver.constant.MessageType
import org.springframework.data.annotation.Id

/**
 * 离线消息
 * @author Zongwei* @date 2020/4/2 22:47
 */
class OfflineMessage {

    @Id
    String id

    String alias

    MessageType messageType

    String content

}
