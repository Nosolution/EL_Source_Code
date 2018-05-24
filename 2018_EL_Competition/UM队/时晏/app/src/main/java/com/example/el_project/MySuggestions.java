package com.example.el_project;
/**
 * 在任务完成界面提供建议
 * @author ns
 */
public class MySuggestions {
    public static String getSuggestion(int time,double focusRate,double breakRate){
        if(time<=3)
            return "时间有点短，下次试试能专注得久一点的任务吧XD";
        else{
            if(breakRate>=(1.0/3)){
                String[] temp={"切换应用有点频繁哦，下次努力专心一点吧。","切换应用过于频繁，请保持专心。","切换应用有点过于频繁，也许先处理完事情再做任务会更好。"};
                return temp[(int)(Math.random()*3)];
            }
            else if(breakRate<(1.0/3)&&breakRate>=0.2){
                String[] temp={"做得还可以，不过专注程度有待提高哦。","再努力一点，你可以做到更专注。"};
                return temp[(int)(Math.random()*2)];
            }
            else if(focusRate<=0.5)
                return "似乎心思不在任务上哦？处理好事情再专心做任务吧。";
            else if(breakRate<0.2&&breakRate>=(1.0/15)){
                String[] temp={"专注程度很不错，坚持，再更上一层楼。","继续加油，你的分数还可以再高一点。"};
                return temp[(int)(Math.random()*2)];
            }
            else if(focusRate>0.5&&focusRate<=0.7)
                return "也许休息的久了一点？下次调整好时间吧";
            else if(breakRate<(1.0/15)&&breakRate>=(1.0/30)){
                String[] temp={"做任务时非常专注，继续加油，精益求精。","非常好，要不要尝试挑战一下自己？"};
                return temp[(int)(Math.random()*2)];
            }
            else if(breakRate<(1.0/30)){
                String[] temp={"成绩很不错！请继续保持。","非常专注！坚持下去一定大有裨益。"};
                return temp[(int)(Math.random()*2)];
            }
            else
                return "继续加油哦";
        }
    }
}
