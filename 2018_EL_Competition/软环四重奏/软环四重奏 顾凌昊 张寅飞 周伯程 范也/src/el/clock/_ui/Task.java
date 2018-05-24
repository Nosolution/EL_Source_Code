package el.clock._ui;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable{
	
	private String name;
	long startTime,endTime,actualTime,validTime,planTime;
	//ArrayList<Long> pausingTimes; 
	//ArrayList<Long> continueTimes;
	ArrayList<Pause> pauses=new ArrayList<Pause>();
	
	public Task(long start, long plan) {
		this.startTime = start;
		this.planTime = plan;
	}
	
	public void addPause(long start,long end) {
		pauses.add(new Pause(start,end));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public long getActualTime() {
		return actualTime;
	}

	public void setActualTime(long actualTime) {
		this.actualTime = actualTime;
	}

	public long getValidTime() {
		return validTime;
	}

	public void setValidTime(long validTime) {
		this.validTime = validTime;
	}

	public long getPlanTime() {
		return planTime;
	}

	public void setPlanTime(long planTime) {
		this.planTime = planTime;
	}
	
	private class Pause implements Serializable{
		long start,end;
		
		public Pause(long start,long end) {
			this.start=start;
			this.end=end;
		}

		public long getStart() {
			return start;
		}

		public void setStart(long start) {
			this.start = start;
		}

		public long getEnd() {
			return end;
		}

		public void setEnd(long end) {
			this.end = end;
		}
		
		
	}
	
	
}
