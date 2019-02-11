package com.eleme.dao;

import com.eleme.constant.ReceivingStatus;
import com.eleme.entity.Receiving;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;


public interface ReceivingDao extends CrudRepository<Receiving, Long> {

    Receiving findByIdAndUserId(long id, long userId);

    Receiving findByStatusAndUserId(ReceivingStatus status,
                                                  String userId);

    Receiving findByUrlKeyAndStatusNot(String urlKey,ReceivingStatus status);

    Slice<Receiving> findByUserId(String userId, Pageable pageable);

    List<Receiving> findByStatus(ReceivingStatus status);



}
