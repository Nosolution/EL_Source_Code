package com.example.el_project;

/**
* Task类，存有Task的各项数据
 * 实现可比较接口，可进行排序
 * @author ns
 */

public class Task implements Comparable<Task>{
	private int id;
	private String name;
	private String assumedTime;
	private String deadline;
	private int isDailyTask;
	private String comments;
	private int timeUsed;
	private int hourRequired;
	private int minuteRequired;
	private String remark;
	private int backgroundId;
	private int selectedBackgroundId;
	private int defaultBackground;
	//以下是为了实现排序，与Task功能无关
	private Integer eDgree;
	private Integer dateDiffWeight;
	private Integer weight;

	public Task(int id, String name, String assumedTime, String deadline, int eDgree,int isDailyTask, String comments, int timeUsed){
		this.id=id;
		this.name=name;
		this.assumedTime=assumedTime;
		this.deadline=deadline;
		this.eDgree=eDgree;
		this.isDailyTask=isDailyTask;
		this.comments=comments;
		this.timeUsed=timeUsed;

		this.backgroundId=R.drawable.task_bar;
		this.selectedBackgroundId=R.drawable.taskbar_chosen;
		this.defaultBackground=R.drawable.task_bar;

		String[] temp= this.assumedTime.split(":");
		hourRequired=Integer.parseInt(temp[0]);
		minuteRequired=Integer.parseInt(temp[1]);

		if(isDailyTask==0)
			remark=MyAlgorithm.getTaskRestDays(deadline);
		else if(isDailyTask==1)
			remark="每日";
		else
			remark="";

		this.dateDiffWeight=MyAlgorithm.calcDateDiffWeight(deadline);
		weight=isDailyTask==0? 3*this.eDgree+4*this.dateDiffWeight:18;

	}


	public int compareTo(Task arg0){
		return arg0.getWeight().compareTo(this.getWeight());
	}

	public int getId(){return id;}
	public String getName(){return name;}
	public String getRemark(){return remark;}
	public int getHourRequired() {return hourRequired;}
	public int getMinuteRequired() {return minuteRequired;}
	public String getDeadline(){return deadline;}
	public int getEmergencyDegree(){return eDgree;}
	public int getIsDailyTask(){return isDailyTask;}
	public String getComments(){return comments;}
	public int getTimeUsed(){return timeUsed;}
	public Integer getWeight(){
		return this.weight;
	}
	public int getBackgroundId(){
		return backgroundId;
	}

	public void setName(String name){this.name=name;}

	public int getSelectedBackgroundId(){return selectedBackgroundId;}

	public void switchBackground(){
		int temp=backgroundId;
		backgroundId= selectedBackgroundId;
		selectedBackgroundId =temp;
	}

	public void initBackground(){
		this.backgroundId=defaultBackground;
	}

	public String[] getThisTaskInfo(){
		String[] result={String.valueOf(id),name,assumedTime,deadline,String.valueOf(eDgree),String.valueOf(isDailyTask),comments,String.valueOf(timeUsed)};
		return result;
	}
}

