Attribute VB_Name = "BY_ButtonMod"
Public Type BY_BUTTON
X As Single
Y As Single
Wi As Long
Hi As Long
picid As Long
PIC As Long
PICM As Long
Caption As String
SubControl As Boolean
SubGiveID As GiveID
SubValue As Long
BorderLim As Boolean
Status As Long '状态 0 不可用 1 可用
Tipc As String
Tip As String
HangTime As Long
ShowTip As Boolean
Invisible As Boolean
MouseHangOn As Boolean
MouseDownOn As Boolean
Border As Boolean
oriColor As BY_Color
IsGetOriColor As Boolean
NoBack As Boolean
'BorderColor As BY_Color
tBackColor As Long
Hide As Boolean
SendMessage As Boolean
ContainerID As Long '容器id TOOLBOX 1 TOOLBAR 2 SELBOX 3
NextID As Long '同一容器内的绘图顺序
NextType As Long
PreID As Long '同一容器内的事件执行顺序（鼠标判断顺序）
PreType As Long
IsReDraw As Boolean
IsCheck As Boolean
Checkable As Boolean
oldCollision As Boolean
End Type

Public Type BY_BUTTON_MAIN_TYPE
num As Long
Start As Long
Exit As Long
max As Long
min As Long
Show As Long
List As Long
timer As Long
clock As Long
music As Long
setting As Long
ReTest As Long
InputYes As Long
InputNo As Long
Work As Long
Giveup As Long
AddMusic As Long
DelMusic As Long
InputOneMusic As Long
InputADirMusic As Long
PreMusic As Long
NextMusic As Long
LoopMusic As Long
End Type

Public BY_BUTTONS() As BY_BUTTON
Public BY_BUTTON_MAIN As BY_BUTTON_MAIN_TYPE
'Public BY_BUTTON_LISTS(16) As Long '按钮列表,指向按钮的ID

Public Sub iniBY_BUTTON(ContainerID As Long, X As Long, Y As Long, w As Long, h As Long, Caption As String, Border As Boolean, picid As Long, Optional Checkable As Boolean)
Dim ID As Long
BY_BUTTON_MAIN.num = BY_BUTTON_MAIN.num + 1
ReDim Preserve BY_BUTTONS(BY_BUTTON_MAIN.num)
ID = BY_BUTTON_MAIN.num
With BY_BUTTONS(ID)
.X = X: .Y = Y: .Wi = w: .Hi = h: .picid = picid: .ContainerID = ContainerID
.PreType = BY_Containers(.ContainerID).pControlType
.PreID = BY_Containers(.ContainerID).pControlID
.Checkable = Checkable
MakeLink .ContainerID, 1, ID
.Caption = Caption: .Border = Border
If .picid > 0 Then
    '读取按钮素材
    Call SelectObject(GetMeHdc(.PIC, BY_Containers(.ContainerID).hdc), LoadPicture(App.path + "\Data\Skin\Button\" & Trim(str(.picid)) & ".bmp"))
    Call SelectObject(GetMeHdc(.PICM, BY_Containers(.ContainerID).hdc), LoadPicture(App.path + "\Data\Skin\Button\" & Trim(str(.picid)) & "m.bmp"))
End If
Select Case ID
Case BY_BUTTON_MAIN.Start
    .Tipc = "": .Tip = ""
Case BY_BUTTON_MAIN.Show
     .Tip = ""
Case BY_BUTTON_MAIN.List
     .Tip = "我的任务"
Case BY_BUTTON_MAIN.music
     .Tip = "我的音乐"
Case BY_BUTTON_MAIN.timer
     .Tip = "我的计时器"
Case BY_BUTTON_MAIN.setting
     .Tip = "设置"
Case BY_BUTTON_MAIN.Work
    .Tip = ""
Case BY_BUTTON_MAIN.Giveup
    .Tip = ""
End Select
End With
End Sub

Public Sub DrawBUTTON(NOWID As Long)
With BY_BUTTONS(NOWID)
Dim DrawWi As Long
If .Invisible Then GoTo DrawNext
If .IsReDraw Then
If .NoBack = False Then DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, True, .oriColor.R, .oriColor.g, .oriColor.b
If .Border Then
    DrawRectVB2 .ContainerID, .X, .Y, .Wi, .Hi, True, bkColor + RGB(.oriColor.R + .tBackColor, .oriColor.g + .tBackColor, .oriColor.b)
Else
    If .Checkable Then
        If .IsCheck Then
            If .MouseDownOn Then
                DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, True, 110, 130, 252
            ElseIf .MouseHangOn Then
                DrawRectVB2 .ContainerID, .X, .Y, .Wi, .Hi, True, bkColor + RGB(50, 50, 60)
                DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, False, 200, 220, 242
                DrawRectVB .ContainerID, .X + 1, .Y + 1, .Wi - 2, .Hi - 2, False, 200, 220, 242
            Else
                DrawRectVB2 .ContainerID, .X, .Y, .Wi, .Hi, True, bkColor + RGB(50, 50, 60)
            End If
        Else
            If .MouseDownOn Then
                DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, True, 174, 174, 174
            ElseIf .MouseHangOn Then
                DrawWi = .Wi * (.tBackColor / 30)
                If DrawWi > .Wi Then DrawWi = .Wi
                Select Case NOWID
                Case BY_BUTTON_MAIN.ReTest
                    DrawRectVB .ContainerID, .X, .Y, DrawWi, .Hi, True, 100, 120, 242
                    DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, False, 200, 220, 242
                    DrawRectVB .ContainerID, .X + 1, .Y + 1, .Wi - 2, .Hi - 2, False, 200, 220, 242
                Case Else: DrawRectVB2 .ContainerID, .X, .Y, DrawWi, .Hi, True, bkColor + RGB(.oriColor.R + .tBackColor, .oriColor.g + .tBackColor, .oriColor.b + .tBackColor)
                End Select
                
            Else
                'nothing
            End If
        End If
    Else
        If .MouseDownOn Then
            DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, True, 212, 212, 212
        ElseIf .MouseHangOn Then
            Select Case NOWID
            Case BY_BUTTON_MAIN.Exit: DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, True, 233, 17, 35
            Case BY_BUTTON_MAIN.max, BY_BUTTON_MAIN.min: DrawRectVB2 .ContainerID, .X, .Y, .Wi, .Hi, True, bkColor + RGB(45, 45, 45)
            Case Else
                DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, False, 255, 255, 255
                DrawRectVB .ContainerID, .X + 1, .Y + 1, .Wi - 2, .Hi - 2, False, 255, 255, 255
            End Select
            
        Else
            'nothing
        End If
    End If
End If
BitBlt BY_Containers(.ContainerID).hdc, .X + (.Wi) / 2 - 13, .Y + (.Hi) / 2 - 13, 25, 25, .PICM, 0, 0, 8913094
BitBlt BY_Containers(.ContainerID).hdc, .X + (.Wi) / 2 - 13, .Y + (.Hi) / 2 - 13, 25, 25, .PIC, 0, 0, 15597702
If .Border Then
    If .MouseDownOn Then
        DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, False, 144 - 50, 180 - 50, 246 - 50
        If .BorderLim = False Then DrawRectVB .ContainerID, .X + 1, .Y + 1, .Wi - 2, .Hi - 2, False, 144 - 50, 180 - 50, 246 - 50
    ElseIf .MouseHangOn Then
        DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, False, 192 - 50, 220 - 50, 243 - 50
        If .BorderLim = False Then DrawRectVB .ContainerID, .X + 1, .Y + 1, .Wi - 2, .Hi - 2, False, 192 - 50, 220 - 50, 243 - 50
    Else
        'DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, False, 192 - 50, 220 - 50, 243 - 50
        'If .BorderLim = False Then DrawRectVB .ContainerID, .X + 1, .Y + 1, .Wi - 2, .Hi - 2, False, 192 - 50, 220 - 50, 243 - 50
    End If
End If
If .Caption <> "" Then DrawTextGDI .ContainerID, .Caption, .X, .Y, .Wi, .Hi, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
End If

Select Case NOWID
Case BY_BUTTON_MAIN.Exit
    DrawLineVB .ContainerID, .X + .Wi / 2 - 7, .Y + .Hi / 2 - 7, .X + .Wi / 2 + 8, .Y + .Hi / 2 + 8, 255, 255, 255
    DrawLineVB .ContainerID, .X + .Wi / 2 - 7, .Y + .Hi / 2 + 7, .X + .Wi / 2 + 8, .Y + .Hi / 2 - 8, 255, 255, 255
Case BY_BUTTON_MAIN.max
    DrawRectVB .ContainerID, .X + .Wi / 2 - 7, .Y + .Hi / 2 - 7, 15, 15, False, 255, 255, 255
Case BY_BUTTON_MAIN.min
    DrawLineVB .ContainerID, .X + .Wi / 2 - 7, .Y + .Hi / 2, .X + .Wi / 2 + 8, .Y + .Hi / 2, 255, 255, 255
Case BY_BUTTON_MAIN.Start, BY_BUTTON_MAIN.Giveup, BY_BUTTON_MAIN.Work
    DrawRectVB2 .ContainerID, .X, .Y, .Wi, .Hi, False, RGB(255, 255, 255)
    DrawRectVB2 .ContainerID, .X + 1, .Y + 1, .Wi - 2, .Hi - 2, False, RGB(255, 255, 255)
Case BY_BUTTON_MAIN.LoopMusic
    If .IsCheck Then
        DrawRectVB2 .ContainerID, .X, .Y, .Wi, .Hi, False, RGB(255, 255, 255)
        DrawRectVB2 .ContainerID, .X + 1, .Y + 1, .Wi - 2, .Hi - 2, False, RGB(255, 255, 255)
    End If
End Select

If .IsReDraw Then BY_Containers(.ContainerID).DrawCount = BY_Containers(.ContainerID).DrawCount + 1
DrawNext:
.MouseHangOn = False
.MouseDownOn = False
.IsReDraw = False
If .NextType <> 0 Then DrawControl .ContainerID, .NextType, .NextID
End With
End Sub

Public Sub MouseOnBUTTON(NOWID As Long)
With BY_BUTTONS(NOWID)
Dim pd As Boolean, i As Long, j As Long, temp As Long
Dim filepath As String
If .Invisible Then GoTo TipNext

If PhyTouch(.X, .Y, .Wi, .Hi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
    .oldCollision = True
    pd = True
    .MouseHangOn = True
    If BY_Containers(.ContainerID).MouseDown Then
        BY_Containers(.ContainerID).MouseDown = False
        BY_Containers(.ContainerID).MouseLock = True
        BY_Containers(.ContainerID).MouseLockType = 1
        BY_Containers(.ContainerID).MouseLockID = NOWID
        '响应鼠标按下操作
        .MouseDownOn = True
        If .Checkable Then
            If .IsCheck Then .IsCheck = False Else .IsCheck = True
        End If
        Select Case NOWID
        Case BY_BUTTON_MAIN.LoopMusic
            ClockData.LoopMusic = .IsCheck
        Case BY_BUTTON_MAIN.PreMusic
            NowPlayMusicIndex = NowPlayMusicIndex - 1
            If NowPlayMusicIndex <= 0 Then NowPlayMusicIndex = UBound(MusicInfos)
            If NowPlayMusicIndex <> 0 Then PlayMusicInList NowPlayMusicIndex
        Case BY_BUTTON_MAIN.NextMusic
            NowPlayMusicIndex = NowPlayMusicIndex + 1
            If NowPlayMusicIndex > UBound(MusicInfos) Then NowPlayMusicIndex = 1
            If UBound(MusicInfos) = 0 Then NowPlayMusicIndex = 0
            If NowPlayMusicIndex <> 0 Then PlayMusicInList NowPlayMusicIndex
        Case BY_BUTTON_MAIN.Start
            StartClock
            BY_BUTTONS(BY_BUTTON_MAIN.Giveup).Invisible = False
            BY_BUTTONS(BY_BUTTON_MAIN.Start).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.Work).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.PreMusic).Invisible = False
            BY_BUTTONS(BY_BUTTON_MAIN.NextMusic).Invisible = False
            NowPlayMusicIndex = 1
            If UBound(MusicInfos) = 0 Then NowPlayMusicIndex = 0
            If NowPlayMusicIndex <> 0 Then PlayMusicInList NowPlayMusicIndex
        Case BY_BUTTON_MAIN.Giveup
            ClockData.Run = False
            RunMode = 0
            BY_BUTTONS(BY_BUTTON_MAIN.Giveup).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.Start).Invisible = False
            BY_BUTTONS(BY_BUTTON_MAIN.Work).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.PreMusic).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.NextMusic).Invisible = True
            StopMusic
        Case BY_BUTTON_MAIN.Work
            StartClock
            BY_BUTTONS(BY_BUTTON_MAIN.Giveup).Invisible = False
            BY_BUTTONS(BY_BUTTON_MAIN.Start).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.Work).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.PreMusic).Invisible = False
            BY_BUTTONS(BY_BUTTON_MAIN.NextMusic).Invisible = False
        Case BY_BUTTON_MAIN.Exit
            Unload clock
            End
        Case BY_BUTTON_MAIN.max
            If clock.WindowState = 0 Then clock.WindowState = 2 Else clock.WindowState = 0
        Case BY_BUTTON_MAIN.min
            clock.WindowState = 1
        Case BY_BUTTON_MAIN.Show
            If .IsCheck Then
                BY_BUTTONS(BY_BUTTON_MAIN.List).Invisible = False
                BY_BUTTONS(BY_BUTTON_MAIN.music).Invisible = False
                BY_BUTTONS(BY_BUTTON_MAIN.timer).Invisible = False
                BY_BUTTONS(BY_BUTTON_MAIN.setting).Invisible = False
                BY_BUTTONS(BY_BUTTON_MAIN.List).IsCheck = False
                BY_BUTTONS(BY_BUTTON_MAIN.music).IsCheck = False
                BY_BUTTONS(BY_BUTTON_MAIN.timer).IsCheck = False
                BY_BUTTONS(BY_BUTTON_MAIN.setting).IsCheck = False
            Else
                BY_BUTTONS(BY_BUTTON_MAIN.LoopMusic).Invisible = True
                BY_BUTTONS(BY_BUTTON_MAIN.List).Invisible = True
                BY_BUTTONS(BY_BUTTON_MAIN.music).Invisible = True
                BY_BUTTONS(BY_BUTTON_MAIN.timer).Invisible = True
                BY_BUTTONS(BY_BUTTON_MAIN.setting).Invisible = True
                ShowMode = 0
                If ClockData.Run Then
                    BY_BUTTONS(BY_BUTTON_MAIN.Start).Invisible = True
                    BY_BUTTONS(BY_BUTTON_MAIN.Work).Invisible = False
                    BY_BUTTONS(BY_BUTTON_MAIN.Giveup).Invisible = False
                    BY_BUTTONS(BY_BUTTON_MAIN.PreMusic).Invisible = False
                    BY_BUTTONS(BY_BUTTON_MAIN.NextMusic).Invisible = False
                Else
                    BY_BUTTONS(BY_BUTTON_MAIN.Start).Invisible = False
                    BY_BUTTONS(BY_BUTTON_MAIN.Work).Invisible = True
                    BY_BUTTONS(BY_BUTTON_MAIN.Giveup).Invisible = True
                    BY_BUTTONS(BY_BUTTON_MAIN.PreMusic).Invisible = True
                    BY_BUTTONS(BY_BUTTON_MAIN.NextMusic).Invisible = True
                End If
                
                BY_LISTBOXS(BY_LISTBOX_MAIN.List).Invisible = True
                BY_SCROLLS(BY_SCROLL_MAIN.List).Invisible = True
            End If
        Case BY_BUTTON_MAIN.List, BY_BUTTON_MAIN.music, BY_BUTTON_MAIN.timer, BY_BUTTON_MAIN.setting
            BY_BUTTONS(BY_BUTTON_MAIN.List).IsCheck = False
            BY_BUTTONS(BY_BUTTON_MAIN.music).IsCheck = False
            BY_BUTTONS(BY_BUTTON_MAIN.timer).IsCheck = False
            BY_BUTTONS(BY_BUTTON_MAIN.setting).IsCheck = False
            
            BY_LISTBOXS(BY_LISTBOX_MAIN.List).Invisible = True
            BY_SCROLLS(BY_SCROLL_MAIN.List).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.Start).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.Work).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.Giveup).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.AddMusic).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.DelMusic).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.PreMusic).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.NextMusic).Invisible = True
            BY_BUTTONS(BY_BUTTON_MAIN.LoopMusic).Invisible = True
            .IsCheck = True
            Select Case NOWID
            Case BY_BUTTON_MAIN.List
                ShowMode = 1
                ResetLISTBOX BY_LISTBOX_MAIN.List
                BY_LISTBOXS(BY_LISTBOX_MAIN.List).Invisible = False
                BY_SCROLLS(BY_SCROLL_MAIN.List).Invisible = False
            Case BY_BUTTON_MAIN.music
                ShowMode = 2
                ResetLISTBOX BY_LISTBOX_MAIN.List
                BY_LISTBOXS(BY_LISTBOX_MAIN.List).Invisible = False
                BY_SCROLLS(BY_SCROLL_MAIN.List).Invisible = False
                BY_BUTTONS(BY_BUTTON_MAIN.AddMusic).Invisible = False
                BY_BUTTONS(BY_BUTTON_MAIN.DelMusic).Invisible = False
                BY_BUTTONS(BY_BUTTON_MAIN.LoopMusic).Invisible = False
            Case BY_BUTTON_MAIN.timer
                ShowMode = 3
            Case BY_BUTTON_MAIN.setting
                ShowMode = 4
            End Select
        Case BY_BUTTON_MAIN.ReTest
            Select Case .SubValue
            Case 1
                AddTodoList
                ResetLISTBOX BY_LISTBOX_MAIN.List
                BY_Containers(.ContainerID).ReDrawAll = True
                .IsCheck = False
                .Invisible = True
            Case 2
                InputForm.InputType = 2
                InputForm.Show 1
            End Select
        Case BY_BUTTON_MAIN.InputNo
            Unload InputForm
        Case BY_BUTTON_MAIN.InputYes
            Select Case InputForm.InputType
            Case 0
                InputForm.SendString
                NowEditString = BY_TEXTBOXS(BY_TEXTBOX_MAIN.Plan).Text
                TodoList(NowEditTodoIndex).Content = NowEditString
                ResetLISTBOX BY_LISTBOX_MAIN.List
            Case 1
                InputForm.SendString
                NowEditTime.year = Val(BY_TEXTBOXS(BY_TEXTBOX_MAIN.year).Text)
                NowEditTime.month = Val(BY_TEXTBOXS(BY_TEXTBOX_MAIN.month).Text)
                NowEditTime.day = Val(BY_TEXTBOXS(BY_TEXTBOX_MAIN.day).Text)
                NowEditTime.hour = Val(BY_TEXTBOXS(BY_TEXTBOX_MAIN.hour).Text)
                NowEditTime.min = Val(BY_TEXTBOXS(BY_TEXTBOX_MAIN.min).Text)
                TodoList(NowEditTodoIndex).time = NowEditTime
                TodoList(NowEditTodoIndex).Enabled = True
                ResetLISTBOX BY_LISTBOX_MAIN.List
            Case 2
                DelTodoList NowEditTodoIndex
                ResetLISTBOX BY_LISTBOX_MAIN.List
                BY_BUTTONS(BY_BUTTON_MAIN.ReTest).Invisible = True
            Case 4
                For i = 1 To BY_LISTBOXS(BY_LISTBOX_MAIN.List).ItemCount
                    If BY_LISTBOXS(BY_LISTBOX_MAIN.List).Item(i).Value = 1 Then
                        DelMusic i
                    End If
                Next
                ResetMusicInfo
                ResetLISTBOX BY_LISTBOX_MAIN.List
                Unload InputForm
            Case 5
                ClockData.TimeLen = Val(BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Text)
                Unload InputForm
            Case 6
                ClockData.RestTime = Val(BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Text)
                Unload InputForm
            Case 7
                ClockData.InvLongRest = Val(BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Text)
                Unload InputForm
            Case 8
                ClockData.LongRestTime = Val(BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Text)
                Unload InputForm
            End Select
            Unload InputForm
        Case BY_BUTTON_MAIN.AddMusic
            InputForm.InputType = 3
            InputForm.Show 1
        Case BY_BUTTON_MAIN.DelMusic
            InputForm.InputType = 4
            InputForm.Show 1
        Case BY_BUTTON_MAIN.InputOneMusic
            SetWindowPos clock.hwnd, -2, 0, 0, 0, 0, 3
            SetWindowPos InputForm.hwnd, -2, 0, 0, 0, 0, 3
            filepath = GetDialog("open", "导入单个MP3文件", "", App.path, "MP3音乐文件", "MP3")
            SetWindowPos clock.hwnd, -1, 0, 0, 0, 0, 3
            SetWindowPos InputForm.hwnd, -1, 0, 0, 0, 0, 3
            If filepath <> "" Then
                AddMusic filepath
                ResetLISTBOX BY_LISTBOX_MAIN.List
                Unload InputForm
            End If
        Case BY_BUTTON_MAIN.InputADirMusic
            SetWindowPos clock.hwnd, -2, 0, 0, 0, 0, 3
            SetWindowPos InputForm.hwnd, -2, 0, 0, 0, 0, 3
            filepath = GetDialog("open", "选择一个MP3文件来获取同目录下的所有MP3文件", "", App.path, "MP3音乐文件", "MP3")
            SetWindowPos clock.hwnd, -1, 0, 0, 0, 0, 3
            SetWindowPos InputForm.hwnd, -1, 0, 0, 0, 0, 3
            If filepath <> "" Then
                AddDirMuic getDirectory(filepath)
                ResetLISTBOX BY_LISTBOX_MAIN.List
                Unload InputForm
            End If
            
        End Select
    End If
Else
    If .oldCollision Then
        BY_Containers(.ContainerID).ReDrawAll = True
        .oldCollision = False
    End If
End If
TipNext:
If BY_Containers(.ContainerID).MouseLock = True Then Exit Sub
If pd = False And .PreType <> 0 Then MouseEvent .ContainerID, .PreType, .PreID
End With
End Sub

Public Sub ReleaseBY_BUTTON()
Dim i As Long
For i = 1 To UBound(BY_BUTTONS)
    With BY_BUTTONS(i)
    DeleteDC .PIC
    DeleteDC .PICM
    End With
Next
End Sub

Public Sub ActBUTTON(NOWID As Long)
With BY_BUTTONS(NOWID)

'修正位置
If .Invisible Then GoTo TipNext
Select Case NOWID
Case BY_BUTTON_MAIN.Start
    .X = (BY_Containers(.ContainerID).Wi) / 2 - 100: .Wi = 200
    .Y = (BY_Containers(.ContainerID).Hi) - 200: .Hi = 48
    .oriColor = LongToRGB(RGB(72, 72, 72))
    .Invisible = True
    If ShowMode = 0 And RunMode = 0 Then .Invisible = False
Case BY_BUTTON_MAIN.Work
    .X = (BY_Containers(.ContainerID).Wi) / 2 - 100: .Wi = 200
    .Y = (BY_Containers(.ContainerID).Hi) - 200: .Hi = 48
    .oriColor = LongToRGB(RGB(72, 72, 72))
    .Invisible = True
    If ShowMode = 0 And RunMode = 2 Then .Invisible = False
Case BY_BUTTON_MAIN.Giveup
    .X = (BY_Containers(.ContainerID).Wi) / 2 - 100: .Wi = 200
    .Y = (BY_Containers(.ContainerID).Hi) - 200: .Hi = 48
    .oriColor = LongToRGB(RGB(72, 72, 72))
    .Invisible = True
    If ShowMode = 0 And (RunMode = 1 Or RunMode = 3) Then .Invisible = False
Case BY_BUTTON_MAIN.PreMusic
    .X = (BY_Containers(.ContainerID).Wi) / 2 - 100 - 48 - 16: .Wi = 48
    .Y = (BY_Containers(.ContainerID).Hi) - 200: .Hi = 48
Case BY_BUTTON_MAIN.NextMusic
    .X = (BY_Containers(.ContainerID).Wi) / 2 + 100 + 16: .Wi = 48
    .Y = (BY_Containers(.ContainerID).Hi) - 200: .Hi = 48
Case BY_BUTTON_MAIN.LoopMusic
    .X = 600: .Wi = 48
    .Y = 186: .Hi = 48
Case BY_BUTTON_MAIN.Exit
    .X = (BY_Containers(.ContainerID).Wi) - 70: .Wi = 70
    .Y = 0: .Hi = 48
Case BY_BUTTON_MAIN.max
    .X = (BY_Containers(.ContainerID).Wi) - 140: .Wi = 70
    .Y = 0: .Hi = 48
Case BY_BUTTON_MAIN.min
    .X = (BY_Containers(.ContainerID).Wi) - 140: .Wi = 70
    .Y = 0: .Hi = 48
Case BY_BUTTON_MAIN.Show
    .X = 0: .Wi = 48
    .Y = 0: .Hi = 48
Case BY_BUTTON_MAIN.List
    .X = 0: .Wi = 48
    .Y = 48: .Hi = 48
Case BY_BUTTON_MAIN.music
    .X = 0: .Wi = 48
    .Y = 96: .Hi = 48
Case BY_BUTTON_MAIN.timer
    .X = 0: .Wi = 48
    .Y = 144: .Hi = 48
Case BY_BUTTON_MAIN.setting
    .X = 0: .Wi = 48
    .Y = 192: .Hi = 48
Case BY_BUTTON_MAIN.InputNo
    .Wi = 176: .Hi = 48
    .X = BY_Containers(.ContainerID).Wi / 4 * 3 - 176 / 2
    .Y = BY_Containers(.ContainerID).Hi - 90
    .oriColor = LongToRGB(RGB(52, 52, 52))
Case BY_BUTTON_MAIN.InputYes
    .Wi = 176: .Hi = 48
    .X = BY_Containers(.ContainerID).Wi / 4 * 1 - 176 / 2
    .Y = BY_Containers(.ContainerID).Hi - 90
    .oriColor = LongToRGB(RGB(52, 52, 52))
Case BY_BUTTON_MAIN.AddMusic
    .X = 72: .Wi = 240
    .Y = 186: .Hi = 48
    If ShowMode = 2 Then .Invisible = False Else .Invisible = True
    .oriColor = LongToRGB(RGB(52, 52, 52))
Case BY_BUTTON_MAIN.DelMusic
    .X = 340: .Wi = 240
    .Y = 186: .Hi = 48
    If ShowMode = 2 Then .Invisible = False Else .Invisible = True
    .oriColor = LongToRGB(RGB(52, 52, 52))
Case BY_BUTTON_MAIN.InputOneMusic
    .X = BY_Containers(.ContainerID).Wi / 2 - 210: .Wi = 420
    .Y = 100: .Hi = 100
    .oriColor = LongToRGB(RGB(52, 52, 52))
Case BY_BUTTON_MAIN.InputADirMusic
    .X = BY_Containers(.ContainerID).Wi / 2 - 210: .Wi = 420
    .Y = 222: .Hi = 100
    .oriColor = LongToRGB(RGB(52, 52, 52))
End Select

'更改特技
If .MouseDownOn Then
    .tBackColor = 42
ElseIf .MouseHangOn Then
    If .tBackColor < 30 Then .tBackColor = .tBackColor + 3
    If .tBackColor > 30 Then .tBackColor = .tBackColor - 1
End If
If .MouseHangOn = False Then
    If .tBackColor > 0 Then .tBackColor = .tBackColor - 1
End If
'子控件
If .SubControl = True Then
    .IsReDraw = True
    If BY_Containers(.ContainerID).MouseLockType <> 1 Or BY_Containers(.ContainerID).MouseLockID <> NOWID Then
        HideBUTTON NOWID
    End If
    If .SendMessage Then
        BY_LISTBOXS(.SubGiveID.ListBox).Item(.SubGiveID.index).Value = .SubValue
        BY_LISTBOXS(.SubGiveID.ListBox).Item(.SubGiveID.index).Text = .Caption
        .SendMessage = False
    End If
End If

If BY_Containers(.ContainerID).ReDrawAll Then
    .IsReDraw = True
End If
If .IsReDraw = False Then '判断是否需要重绘
    If .tBackColor > 0 Then .IsReDraw = True
End If
TipNext:
If .Y + .Hi < 0 Then .IsReDraw = False
If .NextType <> 0 Then ActControl .ContainerID, .NextType, .NextID
End With
End Sub

Public Sub HideBUTTON(NOWID As Long)
With BY_BUTTONS(NOWID)
.Y = -9999999
.Hide = True
End With
End Sub
