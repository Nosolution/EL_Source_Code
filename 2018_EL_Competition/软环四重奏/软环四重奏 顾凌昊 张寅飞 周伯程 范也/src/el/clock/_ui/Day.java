package el.clock._ui;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Day implements Serializable{

	private long startTime,endTime;
	ArrayList<Task> tasks = new ArrayList<Task>();
	
	public Day(Task task) {
		Date d = new Date(task.endTime);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY,0);
		startTime=c.getTimeInMillis();
		c.set(Calendar.HOUR_OF_DAY,24);
		endTime=c.getTimeInMillis();
		
		tasks.add(task);
		
	}
	
	public String getDate() {
		SimpleDateFormat time=new SimpleDateFormat("yyyy MM dd");
		Date date=new Date(startTime);
		return time.format(date);
	}
	
	public long getTotalActualTime() {
		long time=0;
		for(Task t:tasks) {
			if(t!=null) {
				time+=t.getActualTime();
			}
		}
		return time;
	}
	
	public long getTotalValidTime() {
		long time=0;
		for(Task t:tasks) {
			if(t!=null) {
				time+=t.getActualTime();
			}
		}
		return time;
	}
	
	public String efficiency() {
		double ef = (double)getTotalValidTime()/getTotalActualTime();
		DecimalFormat df = new DecimalFormat(".00");
		ef = Double.parseDouble(df.format(ef));
		return ef*100+"%";
	}
	
	public Day(long start,long end) {
		startTime=start;endTime=end;
	}
	
	public void addTask(Task t) {
		tasks.add(t);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}
	
	
}
