package com.zw.myproject.dao;

import com.zw.myproject.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);

    //自定义查询语句
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
