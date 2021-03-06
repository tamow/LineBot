package com.example.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface GarbageScheduleDao {

	@Select
	List<String> selectItems(int dayOfWeek);
}