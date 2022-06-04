package com.kitchen.warehouse;

import com.kitchen.warehouse.logger.basic.BasicLogger;
import com.kitchen.warehouse.logger.database.DatabaseLogger;
import com.kitchen.warehouse.logger.user.UserLogger;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WarehouseApplicationTests {

	@Test
	void contextLoads() {
		Logger basic = BasicLogger.getLogger();
		Logger database = DatabaseLogger.getLogger();
		Logger user = UserLogger.getLogger();
	}

}
