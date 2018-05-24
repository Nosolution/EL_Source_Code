package com.fantasticfour.elcontestproject.Instance.Ins_WNoise;

/**
 * 音量取值为 0~100
 *
 */

import android.content.Context;

import com.fantasticfour.elcontestproject.Instance.Database.BasicDataNode;

import java.util.List;
import java.util.Map;

public class WNoiseController {
    private WNoiseDataDAO m_WNoiseDAO;
    private WNoisePresetDataDAO m_WNoisePresetDAO;
    private WNoisePlayer m_WNoisePlayer;

    public WNoiseController(Context context){
        m_WNoiseDAO=new WNoiseDataDAO(context);
        m_WNoisePresetDAO=new WNoisePresetDataDAO(context, m_WNoiseDAO);
        m_WNoisePlayer=new WNoisePlayer(context,m_WNoiseDAO.GetRawDataMap());
    }

    /**
     * 得到当前白噪音列表
     * @return
     * 一个List
     */
    public List<WNoise> GetWNoiseList(){
        return m_WNoiseDAO.GetDataList();
    }

    /**
     * 添加新的白噪音前
     * 必须调用此方法来申请白噪音
     * 数据为其自动分配ID
     * 必须修改FileName,Name
     * 随后必须调用SetWNoiseNameAndFile方法设定该白噪音的数据
     * @return
     * 返回一个WNoise实例
     */
    public WNoise GenerateNewWNoise(){
        WNoise wNoise = new WNoise();
        wNoise.m_Name = "";
        wNoise.m_BeBuiltIn = 0;
        wNoise.m_FileName = "";
        m_WNoiseDAO.InsertData(wNoise);
        return wNoise;
    }

    /**
     * 设定新添加的白噪音的Name与FileName
     * @param wNoise
     * 该新增的白噪音
     */
    public void SetWNoiseNameAndFile(WNoise wNoise){
        m_WNoiseDAO.UpdateData(wNoise);
        m_WNoisePlayer.AddWNoise(wNoise);
    }

    /**
     * 删除一个白噪音
     * @param id
     * 该白噪音的ID
     */
    public void RemoveWNoise(long id){
        m_WNoiseDAO.DeleteData(id);
        m_WNoisePlayer.DeleteWNoise(id);
    }

    /**
     * 交换相邻的两个白噪音src1,src2位置
     * 注意必须是相邻的！
     * @param src1ID
     * src1的ID
     * @param src2ID
     * src2的ID
     */
    public void SwapAdjacentWNoise(long src1ID, long src2ID) {
        m_WNoiseDAO.SwapAdjacentData(src1ID, src2ID);
    }

    /**
     * 设置ID为id的白噪音音量为volume
     * @param id
     * 白噪音的id
     * @param volume
     * 要设置的音量
     */
    public void SetWNoiseVolume(long id, int volume) {
        m_WNoiseDAO.SetWNoiseVolume(id, volume);
        m_WNoisePlayer.SetVolume(id, volume);
    }

    public void SetWNoiseName(long id, String name) {
        m_WNoiseDAO.SetWNoiseName(id, name);
    }

    /**
     * 静音，但各白噪音的音量值不变
     * @param beSilent
     * true为静音，false为非静音
     */
    public void SetSilent(boolean beSilent){
        m_WNoisePlayer.SetSilent(beSilent);
    }

    /**
     * 获得当前是否为静音
     * @return
     * boolean型，true:静音
     */
    public boolean GetSilent(){
        return m_WNoisePlayer.GetSilent();
    }

    /**
     * 把所有音量重置为0
     */
    public void ResetWNoiseVolume(){
        m_WNoiseDAO.ResetWNoiseVolume();
    }

    /**
     * 随机设置每个白噪音的音量
     */
    public void RandomSetWNoiseVolume(){
        Map<Long, BasicDataNode<WNoise>> wNoiseMap = m_WNoiseDAO.GetRawDataMap();
        for(BasicDataNode<WNoise> wNoise : wNoiseMap.values()){
            wNoise.m_Data.m_Volume = (int)Math.round(Math.random()*100.0);
            m_WNoiseDAO.UpdateData(wNoise);
        }
    }

    /**
     * 加载ID为id的预设
     * @param id
     * 该预设的id
     */
    public void LoadPreset(long id){
        List<Long> iDOrder = m_WNoisePresetDAO.GetWNoisePreset(id).GetWNoiseIDList();
        Map<Long, Integer> volumeMap = m_WNoisePresetDAO.GetWNoisePreset(id).GetWNoiseVolumeMap();
        m_WNoiseDAO.SetOrder(iDOrder);
        m_WNoiseDAO.SetWNoiseVolume(volumeMap);
        m_WNoisePresetDAO.SetCurrentPreset(id);
        m_WNoisePlayer.SetVolume(m_WNoiseDAO.GetRawDataMap());
    }

    /**
     * 临时加载ID为id的预设
     * @param id
     * 该预设的ID
     */
    public void TryPreset(long id){
        m_WNoisePlayer.SetVolumeByVolumeMap(m_WNoisePresetDAO.GetWNoisePreset(id).GetWNoiseVolumeMap());
    }
    /**
     * 临时加载之后恢复回白噪音控制台的状态
     */
    public void TryPresetRecover(){
        m_WNoisePlayer.SetVolume(m_WNoiseDAO.GetRawDataMap());
    }

    /**
     * 获得ID为id的预设
     * @param id
     * 该预设的id
     * @return
     * 返回这个预设
     */
    public WNoisePreset GetPreset(long id){
        return m_WNoisePresetDAO.GetWNoisePreset(id);
    }

    /**
     * 获得所有预设
     * @return
     * 一个List
     */
    public List<WNoisePreset> GetPresetList(){
        return m_WNoisePresetDAO.GetWNoisePresetList();
    }

    /**
     * /**
     * 把当前的音量状态保存为新的预设，其名称为name
     * 并为其分配ID
     * @param name
     * 新预设的名称
     * @return
     * 返回为该预设分配的ID
     */
    public long SaveAsNewPreset(String name){
        return m_WNoisePresetDAO.SaveAsNewPreset(m_WNoiseDAO.GetDataList(), name);
    }

    /**
     * 覆盖保存当前所在的预设
     */
    public void SaveCurrentPreset(){
        m_WNoisePresetDAO.SaveCurrentPreset(m_WNoiseDAO.GetDataList());
    }

    /**
     * 移除ID为id的预设
     * @param id
     * 该预设的id
     */
    public void RemovePreset(long id){
        m_WNoisePresetDAO.DeleteData(id);
    }

    /**
     * 交换相邻的两个预设src1,src2位置
     * 注意必须是相邻的！
     * @param src1ID
     * src1的ID
     * @param src2ID
     * src2的ID
     */
    public void SwapAdjacentPreset(long src1ID, long src2ID){
        m_WNoisePresetDAO.SwapAdjacentData(src1ID, src2ID);
    }

    /**
     * 获取当前预设
     * @return
     * 当前预设
     */
    public WNoisePreset GetCurrentPreset(){
        return m_WNoisePresetDAO.GetCurrentWNoisePreset();
    }
}

