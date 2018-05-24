Attribute VB_Name = "BY_ContainerMod"
Public Type BY_Container
hdc As Long
pControlID As Long '指向第一个执行事件的控件ID
pControlDrawID As Long '指向第一个绘制的控件ID
pControlType As Long '指向第一个执行事件的控件类型
pControlDrawType As Long '指向第一个绘制的控件类型
MPX As Single
MPY As Single
MouseDlbClick As Boolean
MouseDown As Boolean
MouseRightDown As Boolean
MouseLock As Boolean '鼠标锁定在某一个控件
MouseLockType As Long
MouseLockID As Long
MouseLockResize As Boolean
MouseLockResizeWi As Long
MouseLockResizeHi As Long
NoDraw As Boolean
MouseLockX As Single
MouseLockY As Single
Wi As Long
Hi As Long
BY_FSize As Long
BY_FWeight As Long
BY_FColor As Long
DrawLock As Boolean
ReDrawAll As Boolean
Resizing As Boolean
DrawCount As Long
Disabled As Boolean
End Type
'ControlType 1 按钮
Public Type BY_COUNT
Button As Long
TextBox As Long
ListBox As Long
SCROLL As Long
End Type
Public BY_Containers(2) As BY_Container
Public IsPopShow As Boolean
Public IsNotActMouse As Boolean
Public bkColor As Long
Public oldTaskName As String

Public Sub iniBY_Container(ID As Long)
Select Case ID
Case 1
    BY_BUTTON_MAIN.Start = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "专注", True, 0, True
    BY_BUTTONS(BY_BUTTON_MAIN.Start).NoBack = True
    
    BY_BUTTON_MAIN.Work = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "继续专注", True, 0, True
    BY_BUTTONS(BY_BUTTON_MAIN.Work).NoBack = True
    
    BY_BUTTON_MAIN.Giveup = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "放弃", True, 0, True
    BY_BUTTONS(BY_BUTTON_MAIN.Giveup).NoBack = True
    
    BY_BUTTON_MAIN.PreMusic = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 6, False
    BY_BUTTONS(BY_BUTTON_MAIN.PreMusic).NoBack = True
    
    BY_BUTTON_MAIN.NextMusic = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 7, False
    BY_BUTTONS(BY_BUTTON_MAIN.NextMusic).NoBack = True
    
    BY_LISTBOX_MAIN.List = BY_LISTBOX_MAIN.num + 1
    iniBY_LISTBOX ID, 0, 0, 30, 30, 1
    BY_LISTBOXS(BY_LISTBOX_MAIN.List).Invisible = True
    
    BY_SCROLL_MAIN.List = BY_SCROLL_MAIN.num + 1
    iniBY_SCROLL ID, 0, 0, 30, 30, 3, BY_LISTBOX_MAIN.List
    BY_SCROLLS(BY_SCROLL_MAIN.List).Invisible = True
    
    BY_BUTTON_MAIN.LoopMusic = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 32, 32, "", False, 8, True
    BY_BUTTONS(BY_BUTTON_MAIN.LoopMusic).NoBack = True
    BY_BUTTONS(BY_BUTTON_MAIN.LoopMusic).IsCheck = ClockData.LoopMusic
    
    BY_BUTTON_MAIN.Exit = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 0, False
    BY_BUTTONS(BY_BUTTON_MAIN.Exit).NoBack = True
    
    'BY_BUTTON_MAIN.max = BY_BUTTON_MAIN.num + 1
    'iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 0, False
    'BY_BUTTONS(BY_BUTTON_MAIN.max).NoBack = True
    
    BY_BUTTON_MAIN.min = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 0, False
    BY_BUTTONS(BY_BUTTON_MAIN.min).NoBack = True
    
    BY_BUTTON_MAIN.Show = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 5, True
    BY_BUTTONS(BY_BUTTON_MAIN.Show).NoBack = True
    
    BY_BUTTON_MAIN.List = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 4, True
    BY_BUTTONS(BY_BUTTON_MAIN.List).NoBack = True
    
    BY_BUTTON_MAIN.music = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 2, True
    BY_BUTTONS(BY_BUTTON_MAIN.music).NoBack = True
    
    BY_BUTTON_MAIN.timer = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 1, True
    BY_BUTTONS(BY_BUTTON_MAIN.timer).NoBack = True
    
    'BY_BUTTON_MAIN.setting = BY_BUTTON_MAIN.num + 1
    'iniBY_BUTTON ID, 0, 0, 30, 30, "", False, 3, True
    'BY_BUTTONS(BY_BUTTON_MAIN.setting).NoBack = True
    
    BY_BUTTON_MAIN.ReTest = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, -9999, -99999, 30, 30, "", False, 0, True
    BY_BUTTONS(BY_BUTTON_MAIN.ReTest).NoBack = True
    BY_BUTTONS(BY_BUTTON_MAIN.ReTest).Invisible = True
    
    BY_BUTTON_MAIN.AddMusic = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, -9999, -99999, 30, 30, "添加一些音乐", True, 0, True
    BY_BUTTONS(BY_BUTTON_MAIN.AddMusic).NoBack = True
    BY_BUTTONS(BY_BUTTON_MAIN.AddMusic).Invisible = True
    
    BY_BUTTON_MAIN.DelMusic = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, -9999, -99999, 30, 30, "删除选中的音乐", True, 0, True
    BY_BUTTONS(BY_BUTTON_MAIN.DelMusic).NoBack = True
    BY_BUTTONS(BY_BUTTON_MAIN.DelMusic).Invisible = True
Case 2
    BY_BUTTON_MAIN.InputYes = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "确定", True, 0, False
    
    BY_BUTTON_MAIN.InputNo = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "取消", True, 0, False
    
    BY_BUTTON_MAIN.InputOneMusic = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "仅导入一个音乐文件", True, 0, False
    
    BY_BUTTON_MAIN.InputADirMusic = BY_BUTTON_MAIN.num + 1
    iniBY_BUTTON ID, 0, 0, 30, 30, "批量导入同一个文件夹内的音乐文件", True, 0, False
    
    BY_TEXTBOX_MAIN.year = BY_TEXTBOX_MAIN.num + 1
    iniBY_TEXTBOX ID, 0, 0, 30, 30, 1
    
    BY_TEXTBOX_MAIN.month = BY_TEXTBOX_MAIN.num + 1
    iniBY_TEXTBOX ID, 0, 0, 30, 30, 1
    
    BY_TEXTBOX_MAIN.day = BY_TEXTBOX_MAIN.num + 1
    iniBY_TEXTBOX ID, 0, 0, 30, 30, 1
    
    BY_TEXTBOX_MAIN.hour = BY_TEXTBOX_MAIN.num + 1
    iniBY_TEXTBOX ID, 0, 0, 30, 30, 1
    
    BY_TEXTBOX_MAIN.min = BY_TEXTBOX_MAIN.num + 1
    iniBY_TEXTBOX ID, 0, 0, 30, 30, 1
    
    BY_TEXTBOX_MAIN.Plan = BY_TEXTBOX_MAIN.num + 1
    iniBY_TEXTBOX ID, 0, 0, 30, 30, 0
    
    BY_TEXTBOX_MAIN.test = BY_TEXTBOX_MAIN.num + 1
    iniBY_TEXTBOX ID, 0, 0, 30, 30, 1
End Select
End Sub

Public Sub DelPopForm()
IsPopShow = False
IsNotActMouse = True
Unload PopForm
'Dim b As BY_Container
'BY_Containers(4) = b
End Sub

Public Sub GetContainerInfo(ID As Long)
Select Case ID
Case 1
    GetMousePoint clock.hwnd, BY_Containers(ID).MPX, BY_Containers(ID).MPY
    BY_Containers(ID).Wi = clock.ScaleWidth
    BY_Containers(ID).Hi = clock.ScaleHeight
Case 2
    GetMousePoint InputForm.hwnd, BY_Containers(ID).MPX, BY_Containers(ID).MPY
    BY_Containers(ID).Wi = InputForm.ScaleWidth
    BY_Containers(ID).Hi = InputForm.ScaleHeight
End Select
End Sub

Public Sub DrawControl(ContainerID As Long, NOWTYPE As Long, NOWID As Long)
Select Case NOWTYPE
Case 1: DrawBUTTON NOWID
Case 2: DrawTEXTBOX NOWID
Case 3: DrawLISTBOX NOWID
Case 4: DrawSCROLL NOWID
Case 5: DrawCOMBO NOWID
End Select
End Sub

Public Sub DrawFrame(ContainerID As Long)
Dim RTStr As String
Dim RemainTime As DateType
Dim musicname As String
Dim gtc As Long
Dim passSysTime As Long
Dim passMin As Long, passSecond As Long, passMScond As Long
Dim passAllTime As Long
Dim CheckButton(4) As Boolean
Select Case ContainerID
Case 1
    With BY_Containers(ContainerID)
    
    Select Case ShowMode
    Case 0
        Select Case RunMode
        Case 0
            DrawTextGDI ContainerID, CStr(format(hour(Now), "00")) & ":" & CStr(format(Minute(Now), "00")) & ":" & CStr(format(Second(Now), "00")), 0, 160, .Wi, 200, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 48, 1, vbWhite
            DrawTextGDI ContainerID, CStr(month(Now)) & "月" & CStr(day(Now)) & "日 " & getWeekDayNow, 0, 300, .Wi, 200, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 20, 1, vbWhite
            DrawTextGDI ContainerID, "欢迎使用", 0, 400, .Wi, 200, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 24, 1, vbWhite
        Case 1, 2
            
            gtc = GetTickCount
            passSysTime = gtc - ClockData.StartSysTime
            passAllTime = passSysTime \ 1000
            passMin = passAllTime \ (60)
            passAllTime = passAllTime Mod (60)
            passSecond = passAllTime
            If passSecond = 0 Then
                ClockData.min = ClockData.StartMin - passMin
                ClockData.Second = ClockData.StartSecond - passSecond
                If ClockData.Second < 0 Then ClockData.Second = ClockData.Second + 60
            Else
                ClockData.min = ClockData.StartMin - 1 - passMin
                ClockData.Second = ClockData.StartSecond - passSecond
                If ClockData.Second < 0 Then ClockData.Second = ClockData.Second + 60
            End If
            
            If (ClockData.min <= 0 And ClockData.Second <= 0) Or ClockData.min < 0 Or ClockData.Second < 0 Then
                Select Case RunMode
                Case 1
                    BY_BUTTONS(BY_BUTTON_MAIN.Giveup).Invisible = True
                    BY_BUTTONS(BY_BUTTON_MAIN.Start).Invisible = True
                    BY_BUTTONS(BY_BUTTON_MAIN.Work).Invisible = False
                    Call ShowTip("MineTime时间智能管理", "进入休息模式吧~", 4)
                    StartRest
                    Exit Sub
                Case 2
                    BY_BUTTONS(BY_BUTTON_MAIN.Giveup).Invisible = False
                    BY_BUTTONS(BY_BUTTON_MAIN.Start).Invisible = True
                    BY_BUTTONS(BY_BUTTON_MAIN.Work).Invisible = True
                    Call ShowTip("MineTime时间智能管理", "休息结束~继续进行专注~" & vbCrLf & oldTaskName, 4)
                    StartClock
                    Exit Sub
                End Select
            End If
            
            Dim tempAngle As Single, allTime As Long, nowtime As Long, Rate As Single, tempDisColor As Long
            allTime = ClockData.StartMin * 60 + ClockData.StartSecond
            nowtime = ClockData.min * 60 + ClockData.Second
            If allTime > 0 Then Rate = nowtime / allTime
            
            For i = 0 To 35
                tempAngle = 2 * Pi / 36 * i
                If tempAngle < 2 * Pi * Rate Then
                    DrawRectVB ContainerID, .Wi / 2 - Sin(tempAngle) * 128 - 2, ClockCenterY - Cos(tempAngle) * 128 - 2, 4, 4, True, 255, 255, 255
                Else
                    'DrawRectVB ContainerID, .Wi / 2 - Sin(tempAngle) * 128 - 2, ClockCenterY - Cos(tempAngle) * 128 - 2, 4, 4, True, 120, 120, 120
                End If
            Next
            
            DrawRectVB ContainerID, .Wi / 2 - Sin(2 * Pi * Rate) * 128 - 4, ClockCenterY - Cos(2 * Pi * Rate) * 128 - 4, 8, 8, True, 255, 255, 255
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(2 * Pi * Rate) * 128 - 4, ClockCenterY - Cos(2 * Pi * Rate) * 128 - 4, 8, 8, False, bkColor
            
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(0) * 108 - 2, ClockCenterY - Cos(0) * 108 - 2, 4, 4, True, RGB(250, 250, 250)
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(0) * 88 - 2, ClockCenterY - Cos(0) * 88 - 2, 4, 4, True, RGB(245, 245, 245)
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(0) * 68 - 1, ClockCenterY - Cos(0) * 68 - 1, 2, 2, True, RGB(240, 240, 240)
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(0) * 48 - 1, ClockCenterY - Cos(0) * 48 - 1, 2, 2, True, RGB(235, 235, 235)
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(0) * 28 - 1, ClockCenterY - Cos(0) * 28 - 1, 2, 2, True, RGB(230, 230, 230)
            
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(2 * Pi * Rate) * 108 - 2, ClockCenterY - Cos(2 * Pi * Rate) * 108 - 2, 4, 4, True, RGB(250, 250, 250)
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(2 * Pi * Rate) * 88 - 2, ClockCenterY - Cos(2 * Pi * Rate) * 88 - 2, 4, 4, True, RGB(245, 245, 245)
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(2 * Pi * Rate) * 68 - 1, ClockCenterY - Cos(2 * Pi * Rate) * 68 - 1, 2, 2, True, RGB(240, 240, 240)
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(2 * Pi * Rate) * 48 - 1, ClockCenterY - Cos(2 * Pi * Rate) * 48 - 1, 2, 2, True, RGB(235, 235, 235)
            DrawRectVB2 ContainerID, .Wi / 2 - Sin(2 * Pi * Rate) * 28 - 1, ClockCenterY - Cos(2 * Pi * Rate) * 28 - 1, 2, 2, True, RGB(230, 230, 230)
            
            DrawTextGDI ContainerID, ClockData.min & ":" & IIf(ClockData.Second < 10, "0" & ClockData.Second, ClockData.Second), .Wi / 2 - 100, ClockCenterY - 100, 200, 200, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 36, 1, vbWhite
            
            Select Case RunMode
            Case 2
                Select Case RndWordIndex
                Case 1: RndWord = "休息一下"
                Case 2: RndWord = "放松片刻"
                Case 3: RndWord = "活动活动"
                End Select
                If OnLongRest Then RndWord = "稍微多休息一会吧"
                DrawTextGDI ContainerID, RndWord, .Wi / 2 - 300, 500, 600, 48, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 24, 1, vbWhite
            Case 1
                getTodoRestTime
                delNoRestTimeTodo
                NowTaskIndex = getNowTaskIndex
                If NowTaskIndex = 0 Then
                    oldTaskName = "当前没有任务哦"
                    DrawTextGDI ContainerID, "暂无任务", .Wi / 2 - 300, 500, 600, 48, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
                Else
                    DrawTextGDI ContainerID, TodoList(NowTaskIndex).Content, 0, 460, .Wi, 48, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbYellow
                    
                    RemainTime = getDateFromNow(TodoList(NowTaskIndex).time.year, TodoList(NowTaskIndex).time.month, TodoList(NowTaskIndex).time.day, TodoList(NowTaskIndex).time.hour, TodoList(NowTaskIndex).time.min, 0)
                    RTStr = CStr(RemainTime.D) & " 天 " & CStr(RemainTime.HH) & " 小时 " & CStr(RemainTime.MM) & " 分钟 " & CStr(RemainTime.SS) & " 秒 "
                    oldTaskName = "当前专注的任务：" & TodoList(NowTaskIndex).Content & vbCrLf & "剩余时间：" & RTStr
                    DrawTextGDI ContainerID, "剩余时间：" & RTStr, 0, 540, .Wi, 48, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 16, 1, vbWhite
                End If
            End Select
            If NowPlayMusicIndex <> 0 Then
                musicname = MusicInfos(NowPlayMusicIndex).Name
                DrawTextGDI ContainerID, musicname, .Wi / 2 - 300, .Hi - 120, 600, 100, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 14, 1, vbWhite
            End If
        Case 3
            gtc = GetTickCount
            passSysTime = gtc - ClockData.StartSysTime
            Dim inTime As Long, inTimeRate As Single
            inTime = passSysTime Mod 3000
            inTimeRate = inTime / 3000
            For i = 1 To 16
                If inTime > i * 3000 / 16 Then
                    DrawRectVB2 ContainerID, 112 + i * 32, 360, 8, 8, True, RGB(128, 128, 128) + RGB(128 * inTimeRate, 128 * inTimeRate, 128 * inTimeRate)
                    DrawRectVB2 ContainerID, 112 + i * 32, 360, 8, 8, False, RGB(255, 255, 255)
                    DrawRectVB2 ContainerID, 112 + i * 32 + 1, 360 + 1, 6, 6, False, RGB(255, 255, 255)
                End If
            Next
            
            getTodoRestTime
            delNoRestTimeTodo
            NowTaskIndex = getNowTaskIndex
            If NowTaskIndex = 0 Then
                DrawTextGDI ContainerID, "暂无任务", .Wi / 2 - 300, 500, 600, 48, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
            Else
                DrawTextGDI ContainerID, TodoList(NowTaskIndex).Content, 0, 460, .Wi, 48, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 24, 1, vbYellow
                RemainTime = getDateFromNow(TodoList(NowTaskIndex).time.year, TodoList(NowTaskIndex).time.month, TodoList(NowTaskIndex).time.day, TodoList(NowTaskIndex).time.hour, TodoList(NowTaskIndex).time.min, 0)
                
                RTStr = CStr(RemainTime.D) & " 天 " & CStr(RemainTime.HH) & " 小时 " & CStr(RemainTime.MM) & " 分钟 " & CStr(RemainTime.SS) & " 秒 "
                DrawTextGDI ContainerID, RTStr, 0, 240, .Wi, 64, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 36, 1, vbWhite
            End If
            If NowPlayMusicIndex <> 0 Then
                musicname = MusicInfos(NowPlayMusicIndex).Name
                DrawTextGDI ContainerID, musicname, .Wi / 2 - 300, .Hi - 120, 600, 100, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 14, 1, vbWhite
            End If
        End Select

    Case 1
        DrawTextGDI ContainerID, "我的任务", 72, 48, .Wi, 100, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 20, 800, vbWhite
        DrawTextGDI ContainerID, "设定您的任务，我们将按时间顺序提醒您专注于应最先完成的任务", 72, 92, .Wi, 100, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 16, 1, vbWhite
    Case 2
        DrawTextGDI ContainerID, "我的音乐", 72, 48, .Wi, 100, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 20, 800, vbWhite
        DrawTextGDI ContainerID, "当前音乐列表将在专注时自动播放", 72, 92, .Wi, 100, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 16, 1, vbWhite
    Case 3
        DrawTextGDI ContainerID, "计时器", 72, 48, .Wi, 100, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 20, 800, vbWhite
        DrawTextGDI ContainerID, "您可以设置您的专属计时计划，我们将实时提示您的计时状态", 72, 92, .Wi, 100, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 16, 1, vbWhite
        Dim color As Long
        If PhyTouch(BY_Containers(ContainerID).MPX, BY_Containers(ContainerID).MPY, 1, 1, 72, 200, .Wi, 60) Then
            color = vbYellow
            DrawRectVB2 ContainerID, 72, 200, .Wi - 100, 60, True, bkColor + RGB(16, 16, 16)
            If .MouseDown Then
                .MouseDown = False
                If ClockData.mode = 0 Then ClockData.mode = 1 Else ClockData.mode = 0
            End If
        Else
            color = RGB(100, 240, 250)
        End If
        Dim TempStr As String
        Select Case ClockData.mode
        Case 0: TempStr = "模式： 番茄工作法，利用微观的时间管理来完成任务"
        Case 1: TempStr = "模式： 任务驱动法，将所有时间完全专注于当前的任务"
        End Select
        DrawTextGDI ContainerID, TempStr, 92, 200, .Wi, 60, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 16, 1, color
        Select Case ClockData.mode
        Case 0
            If PhyTouch(BY_Containers(ContainerID).MPX, BY_Containers(ContainerID).MPY, 1, 1, 72, 260, .Wi, 60) Then
                color = vbYellow
                DrawRectVB2 ContainerID, 72, 260, .Wi - 100, 60, True, bkColor + RGB(16, 16, 16)
                If .MouseDown Then
                    .MouseDown = False
                    CheckButton(1) = True
                End If
            Else
                color = vbWhite
            End If
            DrawTextGDI ContainerID, "计时时长： " & ClockData.TimeLen & " 分钟", 92, 260, .Wi, 60, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 16, 1, color
            If PhyTouch(BY_Containers(ContainerID).MPX, BY_Containers(ContainerID).MPY, 1, 1, 72, 320, .Wi, 60) Then
                color = vbYellow
                DrawRectVB2 ContainerID, 72, 320, .Wi - 100, 60, True, bkColor + RGB(16, 16, 16)
                If .MouseDown Then
                    .MouseDown = False
                    CheckButton(2) = True
                End If
            Else
                color = vbWhite
            End If
            DrawTextGDI ContainerID, "休息时长： " & ClockData.RestTime & " 分钟", 92, 320, .Wi, 60, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 16, 1, color
            If PhyTouch(BY_Containers(ContainerID).MPX, BY_Containers(ContainerID).MPY, 1, 1, 72, 380, .Wi, 60) Then
                color = vbYellow
                DrawRectVB2 ContainerID, 72, 380, .Wi - 100, 60, True, bkColor + RGB(16, 16, 16)
                If .MouseDown Then
                    .MouseDown = False
                    CheckButton(3) = True
                End If
            Else
                color = vbWhite
            End If
            DrawTextGDI ContainerID, "多少次专注后进行一次长休息： " & ClockData.InvLongRest & " 次专注", 92, 380, .Wi, 60, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 16, 1, color
            If PhyTouch(BY_Containers(ContainerID).MPX, BY_Containers(ContainerID).MPY, 1, 1, 72, 440, .Wi, 60) Then
                color = vbYellow
                DrawRectVB2 ContainerID, 72, 440, .Wi - 100, 60, True, bkColor + RGB(16, 16, 16)
                If .MouseDown Then
                    .MouseDown = False
                    CheckButton(4) = True
                End If
            Else
                color = vbWhite
            End If
            DrawTextGDI ContainerID, "长休息时长： " & ClockData.LongRestTime & " 分钟", 92, 440, .Wi, 60, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 16, 1, color
        End Select
        
    End Select
    Select Case ShowMode
    Case 0
    Case Else
        DrawTextGDI ContainerID, "MineTime ○（*￣︶￣*）○", 72, 0, .Wi, 48, DT_LEFT Or DT_VCENTER Or DT_SINGLELINE, 16, 1, vbWhite
    End Select
    
    DrawRectVB ContainerID, 0, 0, .Wi - 1, .Hi - 1, False, 42, 140, 220
    
    
    If CheckButton(1) Then
        InputForm.InputType = 5
        InputForm.Show 1
    ElseIf CheckButton(2) Then
        InputForm.InputType = 6
        InputForm.Show 1
    ElseIf CheckButton(3) Then
        InputForm.InputType = 7
        InputForm.Show 1
    ElseIf CheckButton(4) Then
        InputForm.InputType = 8
        InputForm.Show 1
    End If
    End With
Case 2
    With BY_Containers(ContainerID)
    Select Case InputForm.InputType
    Case 0
    Case 1
        DrawTextGDI ContainerID, "年份:", 12, 90, 200, 36, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
        DrawTextGDI ContainerID, "月份:", 12, 140, 200, 36, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
        DrawTextGDI ContainerID, "日期:", 12, 190, 200, 36, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
        DrawTextGDI ContainerID, "小时:", 12, 240, 200, 36, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
        DrawTextGDI ContainerID, "分钟:", 12, 290, 200, 36, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
    Case 5, 6, 8
        DrawTextGDI ContainerID, "分钟", 290, 90, 200, 36, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
    Case 7
        DrawTextGDI ContainerID, "次", 290, 90, 200, 36, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
    End Select
    DrawTextGDI ContainerID, InputForm.Cap, 0, 28, .Wi, 32, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 20, 1, vbWhite
    DrawRectVB ContainerID, 0, 0, .Wi - 1, .Hi - 1, False, 255, 255, 255
    DrawRectVB ContainerID, 1, 1, .Wi - 3, .Hi - 3, False, 255, 255, 255
    End With
End Select
End Sub

Public Sub MouseEvent(ContainerID As Long, NOWTYPE As Long, NOWID As Long)
If ContainerID <> 4 Then
    If IsPopShow Then
        Exit Sub
    End If
    If IsNotActMouse Then
        IsNotActMouse = False
        BY_Containers(1).MouseDown = False
        BY_Containers(2).MouseDown = False
        BY_Containers(3).MouseDown = False
        BY_Containers(5).MouseDown = False
        Exit Sub
    End If
End If
If BY_Containers(ContainerID).MouseLock = False Then
    Select Case NOWTYPE
    Case 1: MouseOnBUTTON NOWID
    Case 2: MouseOnTEXTBOX NOWID
    Case 3: MouseOnLISTBOX NOWID
    Case 4: MouseOnSCROLL NOWID
    Case 5: MouseOnCOMBO NOWID
    End Select
Else
    Select Case BY_Containers(ContainerID).MouseLockType
    Case 1: MouseOnBUTTON BY_Containers(ContainerID).MouseLockID
    Case 2: MouseOnTEXTBOX BY_Containers(ContainerID).MouseLockID
    Case 3: MouseOnLISTBOX BY_Containers(ContainerID).MouseLockID
    Case 4: MouseOnSCROLL BY_Containers(ContainerID).MouseLockID
    Case 5: MouseOnCOMBO BY_Containers(ContainerID).MouseLockID
    End Select
End If
End Sub

Public Sub MakeLink(ContainerID As Long, NOWTYPE As Long, NOWID As Long)
With BY_Containers(ContainerID)
If .pControlDrawType = 0 Then '绘制链表表头为0，将当前加入表头
    .pControlDrawType = NOWTYPE
    .pControlDrawID = NOWID
End If
Select Case .pControlType
Case 1: BY_BUTTONS(.pControlID).NextType = NOWTYPE: BY_BUTTONS(.pControlID).NextID = NOWID
Case 2: BY_TEXTBOXS(.pControlID).NextType = NOWTYPE: BY_TEXTBOXS(.pControlID).NextID = NOWID
Case 3: BY_LISTBOXS(.pControlID).NextType = NOWTYPE: BY_LISTBOXS(.pControlID).NextID = NOWID
Case 4: BY_SCROLLS(.pControlID).NextType = NOWTYPE: BY_SCROLLS(.pControlID).NextID = NOWID
Case 5: BY_COMBOS(.pControlID).NextType = NOWTYPE: BY_COMBOS(.pControlID).NextID = NOWID
End Select
'逆向链表表头永远是设置为当前！！
.pControlType = NOWTYPE
.pControlID = NOWID
End With
End Sub


Public Sub ActControl(ContainerID As Long, NOWTYPE As Long, NOWID As Long)
Select Case NOWTYPE
Case 1: ActBUTTON NOWID
Case 2: ActTEXTBOX NOWID
Case 3: ActLISTBOX NOWID
Case 4: ActSCROLL NOWID
Case 5: ActCOMBO NOWID
End Select
End Sub

Public Sub Act(ContainerID As Long)
With BY_Containers(ContainerID)
If .MouseLockResize = False Then
    MouseEvent ContainerID, .pControlType, .pControlID
End If
If .Disabled Then Exit Sub
If .MouseDown Then
    If IsPopShow = True Then DelPopForm
    IsPopShow = False
End If
If .Resizing Then
    .ReDrawAll = True
End If
ActControl ContainerID, .pControlDrawType, .pControlDrawID
If .Disabled Then Exit Sub

Select Case ContainerID
Case 1: If .ReDrawAll Then clock.Cls: .hdc = clock.hdc: SetStretchBltMode .hdc, 4
Case 2: If .ReDrawAll Then InputForm.Cls: .hdc = InputForm.hdc: SetStretchBltMode .hdc, 4
End Select
If ContainerID = 1 Then
    If ShowMode <> 0 Then
        clock.Line (0, 0)-(.Wi, .Hi), bkColor, BF
    End If
End If
If .ReDrawAll Then
    .BY_FColor = 0
    .BY_FSize = 0
    .BY_FWeight = 0
End If
If .NoDraw = False Then
    DrawControl ContainerID, .pControlDrawType, .pControlDrawID
    If .ReDrawAll Then DrawFrame ContainerID
End If
.ReDrawAll = False
.Resizing = False
.DrawCount = 0
End With
End Sub

