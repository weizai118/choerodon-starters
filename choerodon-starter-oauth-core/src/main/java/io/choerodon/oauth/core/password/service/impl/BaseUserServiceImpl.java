package io.choerodon.oauth.core.password.service.impl;

import org.springframework.stereotype.Service;

import java.util.Date;

import io.choerodon.core.exception.CommonException;
import io.choerodon.oauth.core.password.domain.BaseUserDO;
import io.choerodon.oauth.core.password.mapper.BaseUserMapper;
import io.choerodon.oauth.core.password.service.BaseUserService;

/**
 * @author wuguokai
 */
@Service
public class BaseUserServiceImpl implements BaseUserService{
    private BaseUserMapper baseUserMapper;

    public BaseUserServiceImpl(BaseUserMapper baseUserMapper) {
        this.baseUserMapper = baseUserMapper;
    }

    @Override
    public BaseUserDO lockUser(Long userId, long lockExpireTime) {
        BaseUserDO user = baseUserMapper.selectByPrimaryKey(userId);
        user.setLocked(true);
        user.setLockedUntilAt(new Date(System.currentTimeMillis() + lockExpireTime * 1000));
        if (baseUserMapper.updateByPrimaryKeySelective(user) != 1) {
            throw new CommonException("error.user.lock");
        }
        return baseUserMapper.selectByPrimaryKey(userId);
    }
}
