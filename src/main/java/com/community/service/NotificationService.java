package com.community.service;

import com.community.dto.NotificationDTO;
import com.community.enums.NotificationStatusEnum;
import com.community.enums.NotificationTypeEnum;
import com.community.exception.CustomErrorCodeEnum;
import com.community.exception.CustomErrorCodeEnumImp;
import com.community.exception.CustomException;
import com.community.mapper.NotificationMapper;
import com.community.mapper.UserMapper;
import com.community.model.Notification;
import com.community.model.NotificationExample;
import com.community.model.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 舒先亮 on 2019/9/22.
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PageInfo<NotificationDTO> findMyList(Integer pageNum, Integer pageSize, Long userId) {
//        判断参数是否为空
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExample(notificationExample);

//        将查出来的po类型集合转成DTO类型的
        PageInfo<Notification> pageInfo = new PageInfo<>(notificationList);
        Page<NotificationDTO> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        for (Notification notification : pageInfo.getList()) {
            NotificationDTO notificationDTO = null;
            try {
                notificationDTO = NotificationDTO.class.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(notification, notificationDTO);
            page.add(notificationDTO);
        }
        PageInfo<NotificationDTO> notificationDTOPageInfo = new PageInfo<>(page);

        if (notificationList.size() == 0) {
            return notificationDTOPageInfo;
        }
        /*Set<Long> distinctUserId = notificationList.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
        ArrayList<Long> userIdList = new ArrayList<>(distinctUserId);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIdList);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));*/

        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        notificationDTOPageInfo.setList(notificationDTOS);

        return notificationDTOPageInfo;
    }

    public Long unReadCount(Long userId) {

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(0);
        long unReadCount = notificationMapper.countByExample(notificationExample);
        return unReadCount;
    }

    public NotificationDTO readNotification(Long id, User userFindByToken) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomException(CustomErrorCodeEnumImp.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), userFindByToken.getId())) {
            throw new CustomException(CustomErrorCodeEnumImp.READ_NOTIFICATION_FAIL);
        }
//        当通知查出来没有问题了的时候，可以标记为已读
        notification.setStatus(NotificationStatusEnum.READ_STATUS_TRUE.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;

    }
}
