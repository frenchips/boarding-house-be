package com.adk.kost.service;

import com.adk.kost.constant.Constants;
import com.adk.kost.entity.BaseEntity;

import java.sql.Timestamp;

public class BaseServiceImpl implements BaseService {

    protected BaseEntity createAudit(BaseEntity entity, String username){
        entity.setCreateBy(username);
        entity.setCreateAt(new Timestamp(System.currentTimeMillis()));
        entity.setRecordFlag(Constants.N);
        return entity;
    }

    protected BaseEntity updateAudit(BaseEntity entity, String username){
        entity.setUpdateBy(username);
        entity.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        entity.setRecordFlag(Constants.U);
        return entity;
    }

    protected void setDeleteAudit(BaseEntity entity, String username){
        entity.setUpdateBy(username);
        entity.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        entity.setRecordFlag(Constants.D);
    }
}
