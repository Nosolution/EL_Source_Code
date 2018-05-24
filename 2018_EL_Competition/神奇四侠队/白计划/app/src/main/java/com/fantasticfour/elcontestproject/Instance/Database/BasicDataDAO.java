package com.fantasticfour.elcontestproject.Instance.Database;

import com.fantasticfour.elcontestproject.Instance.Instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BasicDataDAO<T> {
    public Map<Long, BasicDataNode<T> > m_DataMap = new TreeMap<>();
    public BasicDataNode<T> m_FirstData, m_VirtualData;

    private static final String m_WhereID = BasicDataNode.s_col_ID+"=?";
    private String[] WhereArgsID(BasicDataNode<T> data){
        String[] res=new String[1];
        res[0]=String.valueOf(data.m_ID);
        return res;
    }

    private void LinkedData_Insert(BasicDataNode<T> src, BasicDataNode<T> next){
        BasicDataNode<T> pre=next.m_Previous;
        next.m_PreviousID=src.m_ID;next.m_Previous=src;
        pre.m_NextID=src.m_ID;pre.m_Next=src;
        src.m_NextID=next.m_ID;src.m_Next=next;
        src.m_PreviousID=pre.m_ID;src.m_Previous=pre;
    }
    private void LinkedData_Remove(BasicDataNode<T> src){
        BasicDataNode<T> next=src.m_Next;
        BasicDataNode<T> pre=src.m_Previous;
        next.m_PreviousID=pre.m_ID;next.m_Previous=pre;
        pre.m_NextID=next.m_ID;pre.m_Next=next;
        m_DataMap.remove(src.m_ID);
        m_FirstData = m_VirtualData.m_Next;
    }
    protected void LinkedData_Link(BasicDataNode<T> pre, BasicDataNode<T> p){
        pre.m_NextID = p.m_ID;pre.m_Next = p;
        p.m_PreviousID = pre.m_ID;p.m_Previous = pre;
    }
    private void LinkedData_SwapAdjacent(BasicDataNode<T> src1, BasicDataNode<T> src2){
        if(src2.m_Next==src1){
            BasicDataNode<T> temp = src1;
            src1 = src2;
            src2 = temp;
        }
        if(src1 == m_FirstData)
            m_FirstData = src2;
        BasicDataNode<T> next = src2.m_Next;
        BasicDataNode<T> pre = src1.m_Previous;
        pre.m_Next = src2;pre.m_NextID = src2.m_ID;
        next.m_Previous = src1;next.m_PreviousID = src1.m_ID;
        src1.m_Next = next;src1.m_NextID = next.m_ID;
        src1.m_Previous = src2;src1.m_PreviousID = src2.m_ID;
        src2.m_Next = src1;src2.m_NextID = src1.m_ID;
        src2.m_Previous = pre;src2.m_PreviousID = pre.m_ID;
    }
    private void Data_Update(BasicDataNode<T> data){
        if(!data.BeVirtual())
            Instance.s_CommonDAO.m_Database.update(data.GetTableName(),data.GetContentValues(),m_WhereID,WhereArgsID(data));
    }
    private void Data_Insert(BasicDataNode<T> data){
        data.m_ID=Instance.s_CommonDAO.m_Database.insert(data.GetTableName(),null,data.GetContentValues());
    }
    private void Data_Delete(BasicDataNode<T> data){
        Instance.s_CommonDAO.m_Database.delete(data.GetTableName(),m_WhereID,WhereArgsID(data));
    }

    public BasicDataDAO(BasicDataNode<T> virtualData){
        m_FirstData = m_VirtualData = virtualData;
        m_VirtualData.SetVirtual();
        m_DataMap.put(m_VirtualData.m_ID, m_VirtualData);
    }

    /**
     * 插入一个数据到第一位
     * 并且为该数据分配ID
     * @param data
     * 要插入的data
     */
    public long InsertData(BasicDataNode<T> data){
        Data_Insert(data);
        m_DataMap.put(data.m_ID,data);
        LinkedData_Insert(data,m_FirstData);
        Data_Update(m_FirstData);
        Data_Update(data);
        m_FirstData=data;
        return data.m_ID;
    }

    /**
     * 更新data到数据库
     * @param data
     * 要更新的data
     */
    public void UpdateData(BasicDataNode<T> data){
        Data_Update(data);
    }

    /**
     * 删除ID为id的数据
     * @param id
     * 它的ID
     */
    public void DeleteData(long id){
        BasicDataNode<T> data=m_DataMap.get(id);
        BasicDataNode<T> pre=data.m_Previous;
        BasicDataNode<T> next=data.m_Next;
        LinkedData_Remove(data);
        Data_Delete(data);
        Data_Update(pre);
        Data_Update(next);
    }

    /**
     * 交换两个相邻元素src1,src2的位置
     * 注意是相邻的！
     * @param src1ID
     * src1的ID
     * @param src2ID
     * src2的ID
     */
    public void SwapAdjacentData(long src1ID, long src2ID){
        BasicDataNode<T> src1 = m_DataMap.get(src1ID);
        BasicDataNode<T> src2 = m_DataMap.get(src2ID);
        LinkedData_SwapAdjacent(src1, src2);
        Data_Update(src1);
        Data_Update(src2);

        BasicDataNode<T> next, pre;
        if(src1.m_Next == src2){
            next = src2.m_Next;
            pre = src1.m_Previous;
        }
        else{
            next = src1.m_Next;
            pre = src2.m_Previous;
        }
        Data_Update(next);
        Data_Update(pre);
    }

    /**
     * 移动src到next之前，若移动到最后，nextID为-1
     * @param srcID
     * src的ID
     * @param nextID
     * target的ID
     */
    public void MoveData(long srcID,long nextID){
        if(srcID == nextID)return;
        BasicDataNode<T> src=m_DataMap.get(srcID);
        BasicDataNode<T> next=m_DataMap.get(nextID);
        LinkedData_Remove(src);
        Data_Update(m_DataMap.get(src.m_NextID));
        Data_Update(m_DataMap.get(src.m_PreviousID));
        LinkedData_Insert(src,next);
        Data_Update(src);
        Data_Update(m_DataMap.get(src.m_NextID));
        Data_Update(m_DataMap.get(src.m_PreviousID));
    }

    /**
     * 得到计划的列表，有序
     * @return
     * ArrayList
     */
    public List<T> GetDataList(){
        List<T> res=new ArrayList<>();
        for(BasicDataNode<T> p=m_FirstData;!p.BeVirtual();p=p.m_Next)
            res.add(p.m_Data);
        return res;
    }

    public Map<Long, T> GetDataMap(){
        Map<Long, T> res = new HashMap<>();
        for(Map.Entry<Long, BasicDataNode<T>> entry : m_DataMap.entrySet())
            if(!entry.getValue().BeVirtual())
                res.put(entry.getKey(),entry.getValue().m_Data);
        return res;
    }
    public Map<Long, BasicDataNode<T>> GetRawDataMap(){
        return m_DataMap;
    }

    /**
     * 得到ID为id的数据
     * @param id
     * 数据的id
     * @return
     * 对应id的数据
     */
    public T GetData(long id){
        return m_DataMap.get(id).m_Data;
    }

    public int GetDataNum(){
        return m_DataMap.size()-1;
    }
}
