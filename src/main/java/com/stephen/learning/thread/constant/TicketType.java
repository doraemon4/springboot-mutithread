package com.stephen.learning.thread.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: jack
 * @Date: 2018/9/19 08:22
 * @Description: 票的类型
 */
@Getter
@AllArgsConstructor
public enum TicketType {
    PLANE("plane"), TRAIN("train"), STEAMER("steamer");
    private String type;
}
