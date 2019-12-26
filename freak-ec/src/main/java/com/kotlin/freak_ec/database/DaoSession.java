package com.kotlin.freak_ec.database;

import com.kotlin.freak_ec.database.bean.Test;
import com.kotlin.freak_ec.database.bean.UserProfile;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig testDaoConfig;
    private final DaoConfig userProfileDaoConfig;

    private final TestDao testDao;
    private final UserProfileDao userProfileDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        testDaoConfig = daoConfigMap.get(TestDao.class).clone();
        testDaoConfig.initIdentityScope(type);

        userProfileDaoConfig = daoConfigMap.get(UserProfileDao.class).clone();
        userProfileDaoConfig.initIdentityScope(type);

        testDao = new TestDao(testDaoConfig, this);
        userProfileDao = new UserProfileDao(userProfileDaoConfig, this);

        registerDao(Test.class, testDao);
        registerDao(UserProfile.class, userProfileDao);
    }

    public void clear() {
        testDaoConfig.clearIdentityScope();
        userProfileDaoConfig.clearIdentityScope();
    }

    public TestDao getTestDao() {
        return testDao;
    }

    public UserProfileDao getUserProfileDao() {
        return userProfileDao;
    }

}