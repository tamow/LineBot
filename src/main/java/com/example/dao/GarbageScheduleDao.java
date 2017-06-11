package com.example.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface GarbageScheduleDao {

	@Select
	List<String> selectTypes(Integer dayOfTheWeek);
}