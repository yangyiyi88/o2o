package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.LocalAuthDao;
import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.exception.LocalAuthOperationException;
import com.imooc.o2o.service.LocalAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("localAuthServiceImpl")
public class LocalAuthServiceImpl implements LocalAuthService {
    @Resource
    private LocalAuthDao localAuthDao;

    public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(username, password);
    }

    public LocalAuth getLocalAuthByUserId(Long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
        //非空判断,传入的localAuth账号密码，用户信息特别是userId不能为空，否则直接返回错误
        if (localAuth == null || localAuth.getUsername() == null || localAuth.getPassword() == null
                || localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        } else {
            //查询此用户是否已绑定过平台账号
            LocalAuth tempLocalAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
            if (tempLocalAuth != null) {
                //如果绑定过直接退出，以保证平台账号的唯一性
                return  new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
            }
            try {
                //如果之前没有绑定过平台账号，则创建一个平台账号与该用户绑定
                localAuth.setCreateTime(new Date());
                localAuth.setLastEditTime(new Date());
                int effectedNum = localAuthDao.insertLocalAuth(localAuth);
                if (effectedNum <= 0) {
                    throw new LocalAuthOperationException("账号绑定失败");
                } else {
                    return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new LocalAuthOperationException("insertLocalAuth error:" + e.getMessage());
            }
        }
    }

    public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword) throws LocalAuthOperationException {
        //非空判断，判断传入的用户Id，账号，新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
        if (userId != null && username != null && password != null && newPassword != null && !password.equals(newPassword)) {
            try {
                int effectedNum = localAuthDao.updateLocalAuth(userId, username, password, newPassword, new Date());
                if (effectedNum <= 0) {
                    throw new LocalAuthOperationException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new LocalAuthOperationException("更新密码失败：" + e.getMessage());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
