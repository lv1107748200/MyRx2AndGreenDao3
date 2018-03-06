package com.hr.myrx2andgreendao3.entiy;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 吕 on 2018/3/6.
 */
@Entity
public class Dog {
    @Id(autoincrement = true)
    private Long id;
    private long tagId;
    private String name;

    @Generated(hash = 103448141)
    public Dog(Long id, long tagId, String name) {
        this.id = id;
        this.tagId = tagId;
        this.name = name;
    }

    @Generated(hash = 2001499333)
    public Dog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTagId() {
        return this.tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}
