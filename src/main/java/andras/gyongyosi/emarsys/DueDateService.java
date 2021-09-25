package andras.gyongyosi.emarsys;

import andras.gyongyosi.emarsys.Exception.NonWorkingTimeException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DueDateService {

    public LocalDateTime calculateDueDate(LocalDateTime submitTime, Integer periodHours) throws NonWorkingTimeException {
        validateSubmitTime(submitTime);
        Integer plusWorkingDays = (toWorkingHour(submitTime.getHour()) + periodHours) / Constants.WORKING_HOURS_PER_DAY;
        Integer dueDateWorkingHour = (toWorkingHour(submitTime.getHour()) + periodHours) % Constants.WORKING_HOURS_PER_DAY;
        submitTime = addWorkingDaysAndHour(submitTime, plusWorkingDays, dueDateWorkingHour);
        return submitTime;
    }

    private void validateSubmitTime(LocalDateTime submitTime) throws NonWorkingTimeException{
        if (submitTime == null || isWeekEnd(submitTime) || submitTime.getHour()<Constants.WORKING_HOURS_START || submitTime.getHour()>=Constants.WORKING_HOURS_END){
            throw new NonWorkingTimeException("Submit date is not valid. It needs to be in work time.");
        }
    }

    private boolean isWeekEnd(LocalDateTime time){
        return Constants.WEEK_END_DAYS.contains(time.getDayOfWeek());
    }

    private Integer toWorkingHour(Integer hour) throws NonWorkingTimeException {
        if(hour<Constants.WORKING_HOURS_START || hour>=Constants.WORKING_HOURS_END){
            throw new NonWorkingTimeException("This date cannot be converted into working hour, because its not in worktime.");
        }
        return hour - Constants.WORKING_HOURS_START;
    }

    private LocalDateTime addWorkingDaysAndHour(LocalDateTime dateTime, Integer plusWorkingDays, Integer workingHour) throws NonWorkingTimeException{
        while (plusWorkingDays != 0){
            dateTime = dateTime.plusDays(1);
            if(!isWeekEnd(dateTime)){
                plusWorkingDays--;
            }
        }
        return dateTime.withHour(toRegularHour(workingHour));
}

    private Integer toRegularHour(Integer workingHour) throws NonWorkingTimeException {
        if(workingHour>Constants.WORKING_HOURS_PER_DAY || workingHour<0){
            throw new NonWorkingTimeException("Working hour must be positive and must be lower than the daily expectation.");
        }
        return workingHour + Constants.WORKING_HOURS_START;
    }
}
