package org.seckill.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(SeckillServiceTest.class);
	
	@Autowired
	private SeckillService seckillService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}

	@Test
	public void testGetById() {
		long id = 1000;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}", seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		long id = 1000;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		logger.info("exposer={}", exposer);
	}

	@Test
	public void testExecuteSeckill() {
		long id = 1000;
		long phone = 120L;
		String md5 = "21c1f4f69ce1ad71d5105e3d1ea77486";
		try{
			SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
			logger.info("result={}", seckillExecution);
		}catch(RepeatKillException e){
			logger.error(e.getMessage(), e);
		}catch(SeckillCloseException e){
			logger.error(e.getMessage(), e);
		}		
	}
	
	//集成测试代码完整逻辑，注意可重复执行
	@Test
	public void testSeckillLogic() {
		long id = 1000;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){ //如果秒杀开始
			logger.info("exposer={}", exposer);
			long phone = 119L;
			String md5 = exposer.getMd5();
			try{
				SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
				logger.info("result={}", seckillExecution);
			}catch(RepeatKillException e){
				logger.error(e.getMessage(), e);
			}catch(SeckillCloseException e){
				logger.error(e.getMessage(), e);
			}	
		}else{
			//秒杀未开启
			logger.warn("exposer={}", exposer);
		}
		
	}

}
