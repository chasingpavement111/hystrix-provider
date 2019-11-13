package com.arges.web.hessian;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 通知
 *
 * @author zhangjie
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
class NotificationDTO implements Serializable {

    private static final long serialVersionUID = 1L;
//
//    /**
//     * 客户端应用系统类型（Android，IOS，...）
//     */
//    private Integer systemType;
//
//    /**
//     * 客户端的业务类型（教师端，家长端，...）
//     */
//    private Integer endpointType;

    private String clientId;

    private String subject;

    private String content;
}
