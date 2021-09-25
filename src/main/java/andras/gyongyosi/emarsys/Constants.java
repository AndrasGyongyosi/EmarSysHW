package andras.gyongyosi.emarsys;

import com.google.common.collect.Lists;

import java.time.DayOfWeek;
import java.util.List;

public class Constants {
    public static final List<DayOfWeek> WEEK_END_DAYS = Lists.newArrayList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    public static final Integer WORKING_HOURS_START = 9;
    public static final Integer WORKING_HOURS_END = 17;

    public static final Integer WORKING_HOURS_PER_DAY = WORKING_HOURS_END - WORKING_HOURS_START;

}
