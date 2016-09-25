package org.seckill.dao;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//高速junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	@Resource
	private SuccessKilledDao successKilledDao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsertSuccessKilled() {
		long id = 1001L;
		long phone = 15012424056L;
		int insertCount = successKilledDao.insertSuccessKilled(id, phone);
		System.out.println("insertCount = " + insertCount);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long id = 1001L;
		long phone = 15012424056L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
		System.out.println("SuccessKilled = " + successKilled);
		System.out.println("SuccessKilled.getSeckill = " + successKilled.getSeckill());
	}

}
