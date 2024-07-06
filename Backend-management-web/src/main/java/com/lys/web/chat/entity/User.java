package com.lys.web.chat.entity;

import lombok.Data;

/**
 * 接收前台传来的发送人和接收人
 */
@Data
public class User {
    private String fromUser;
    private String toUser;
}
