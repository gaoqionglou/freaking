package com.kotlin.freak_ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "user_profile")
class UserProfile {
    @Id
    private Long userId = 0L;
    private String name = null;
    private String gender = null;

    @Generated(hash = 80676340)
    public UserProfile(Long userId, String name, String gender) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
    }

    @Generated(hash = 968487393)
    public UserProfile() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}