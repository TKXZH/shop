package shop.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerControl {
	Timer timer;
	public TimerControl() {
		timer = new Timer();
		timer.schedule(new NotifyTask("该吃中饭了，请提醒组员订餐"),this.getTimeAfternoon(),24 * 60 * 60 * 1000);
		timer.schedule(new NotifyTask("该吃晚餐了，请提醒组员订餐"),this.getTimeNight(),24 * 60 * 60 * 1000);
	}
	private Date getTimeAfternoon() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 33);
		Date date = calendar.getTime();
		if(date.before(new Date())) {
			date = this.addDay(date, 1);
		}
		return date;
	}
//	private Date getTimeMorning() {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, 07);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		Date date = calendar.getTime();
//		if(date.before(new Date())) {
//			date = this.addDay(date, 1);
//		}
//		return date;
//	}
	private Date getTimeNight( ) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 17);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 15);
		Date date = calendar.getTime();
		if(date.before(new Date())) {
			date = this.addDay(date, 1);
		}
		return date;
	}
	public Date addDay(Date date, int num) {  
        Calendar startDT = Calendar.getInstance();  
        startDT.setTime(date);  
        startDT.add(Calendar.DAY_OF_MONTH, num);  
        return startDT.getTime();  
    } 
}
