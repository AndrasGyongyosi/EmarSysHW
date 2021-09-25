package andras.gyongyosi.emarsys;

import andras.gyongyosi.emarsys.Exception.NonWorkingTimeException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class DueDateServiceTests {

	@Autowired
	DueDateService service;

	LocalDateTime normalInput;

	@BeforeEach
	void init(){
		normalInput = LocalDateTime.of(2021,9,23,10,30,30);
	}

	@Test
	void nonWorkingTimeInputSaturday() {
		Assert.assertThrows(NonWorkingTimeException.class, () -> {
			LocalDateTime input = LocalDateTime.of(2021,9,25,12,0,0);
			service.calculateDueDate(input,10);
		});
	}
	@Test
	void nonWorkingTimeInputSunday() {
		Assert.assertThrows(NonWorkingTimeException.class, () -> {
			LocalDateTime input = LocalDateTime.of(2021,9,26,12,0,0);
			service.calculateDueDate(input,10);
		});
	}
	@Test
	void nonWorkingTimeInputBeforeWork() {
		Assert.assertThrows(NonWorkingTimeException.class, () -> {
			LocalDateTime input = LocalDateTime.of(2021,9,23,2,0,0);
			service.calculateDueDate(input,10);
		});
	}
	@Test
	void nonWorkingTimeInputAfterWork() {
		Assert.assertThrows(NonWorkingTimeException.class, () -> {
			LocalDateTime input = LocalDateTime.of(2021,9,23,18,30,0);
			service.calculateDueDate(input,10);
		});
	}
	@Test
	void sameDayDueDate() throws Exception {
		LocalDateTime result = service.calculateDueDate(normalInput,6);
		Assert.assertEquals(LocalDateTime.of(2021,9,23,16,30,30), result);
	}
	@Test
	void nextDayDueDate() throws Exception {
		LocalDateTime result = service.calculateDueDate(normalInput,10);
		Assert.assertEquals(LocalDateTime.of(2021,9,24,12,30,30), result);
	}
	@Test
	void nextWeekDueDate() throws Exception {
		LocalDateTime result = service.calculateDueDate(normalInput, 23);
		Assert.assertEquals(LocalDateTime.of(2021,9,28,9,30,30), result);
	}

}
