Attribute VB_Name = "BY_ComboMod"
Public Type BY_COMBO
X As Single
Y As Single
Wi As Long
Hi As Long
Status As Long '状态 0 不可用 1 可用
Disabled As Boolean
index As Long
pIndex As Long
SubControl As Boolean
SubGiveID As GiveID
tBackColor As Long
Invisible As Boolean
Item() As BY_LISTITEM
ItemCount As Long
SendLong As Boolean
LockShow As Boolean
Hide As Boolean
SendMessage As Boolean
MouseHangOn As Boolean
MouseDownOn As Boolean
ContainerID As Long '容器id TOOLBOX 1 TOOLBAR 2 SELBOX 3
NextID As Long '同一容器内的绘图顺序
NextType As Long
PreID As Long '同一容器内的事件执行顺序（鼠标判断顺序）
PreType As Long
IsReDraw As Boolean
End Type

Public Type BY_COMBOS_MAIN_TYPE
num As Long
SelType As Long
SelGroup As Long
SelCategory As Long
GridTest As Long
ReTest As Long
Action1 As Long
Action2 As Long
Action3 As Long
Action4 As Long
Action5 As Long
Action6 As Long
Action7 As Long
Event1 As Long
Event2 As Long
Event3 As Long
Section As Long
End Type

Public BY_COMBOS() As BY_COMBO
Public BY_COMBO_MAIN As BY_COMBOS_MAIN_TYPE

Public Sub iniBY_COMBO(ContainerID As Long, X As Long, Y As Long, w As Long, h As Long)
Dim ID As Long
BY_COMBO_MAIN.num = BY_COMBO_MAIN.num + 1
ReDim Preserve BY_COMBOS(BY_COMBO_MAIN.num)
ID = BY_COMBO_MAIN.num
With BY_COMBOS(ID)
.X = X: .Y = Y: .Wi = w: .Hi = h: .ContainerID = ContainerID
.PreType = BY_Containers(.ContainerID).pControlType
.PreID = BY_Containers(.ContainerID).pControlID
MakeLink .ContainerID, 5, ID
.ItemCount = 10
ReDim .Item(.ItemCount)
Dim i As Long
For i = 1 To .ItemCount
    .Item(i).Text = "ComboItem " & i
Next
Select Case ID
Case BY_COMBO_MAIN.SelType
    .ItemCount = 3
    ReDim .Item(.ItemCount)
    .Item(1).Text = "方块（地面、砖块等实体对象）"
    .Item(2).Text = "背景（景物一类的虚体对象）"
    .Item(3).Text = "NPC（非玩家角色对象）"
    .index = 1
End Select
End With
End Sub

Public Sub ResetCombo(ID As Long)
Dim i As Long
BY_COMBOS(ID).Disabled = False
Select Case ID

End Select
End Sub

Public Sub DrawCOMBO(NOWID As Long)
With BY_COMBOS(NOWID)
If .Invisible Then GoTo DrawNext
If .IsReDraw Then
    If .Disabled = False Then
        DrawRectVB .ContainerID, .X, .Y, .Wi - .Hi, .Hi, True, 255 + .tBackColor, 255 + .tBackColor, 255
        DrawRectVB .ContainerID, .X + .Wi - .Hi, .Y, .Hi, .Hi, True, 200 + .tBackColor * 1.2, 200 + .tBackColor * 1.5, 255
        
        DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, False, 40, 90, 200
        DrawRectVB .ContainerID, .X + .Wi - .Hi, .Y, .Hi, .Hi, False, 40, 90, 200
        
        DrawTextGDI .ContainerID, .Item(.index).Text, .X + 4, .Y, .Wi - 4 - .Hi, .Hi, DT_VCENTER Or DT_SINGLELINE, 12, 1, vbBlack
        
        DrawTextGDI .ContainerID, "∨", .X + .Wi - .Hi, .Y, .Hi, .Hi, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 12, 1, vbBlack
    Else
        DrawRectVB .ContainerID, .X, .Y, .Wi - .Hi, .Hi, True, 230, 230, 240
        DrawRectVB .ContainerID, .X + .Wi - .Hi, .Y, .Hi, .Hi, True, 230, 230, 240
        
        DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, False, 160, 160, 160
        DrawRectVB .ContainerID, .X + .Wi - .Hi, .Y, .Hi, .Hi, False, 160, 160, 160
    End If
End If


If .IsReDraw Then BY_Containers(.ContainerID).DrawCount = BY_Containers(.ContainerID).DrawCount + 1

DrawNext:
.MouseHangOn = False
.MouseDownOn = False
.IsReDraw = False
If .NextType <> 0 Then DrawControl .ContainerID, .NextType, .NextID
End With
End Sub

Public Sub MouseOnCOMBO(NOWID As Long)
With BY_COMBOS(NOWID)
Dim pd As Boolean
If .Invisible Then GoTo TipNext
If .Disabled Then GoTo TipNext
If PhyTouch(.X, .Y, .Wi, .Hi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
    pd = True
    .MouseHangOn = True
    If BY_Containers(.ContainerID).MouseDown Then
        BY_Containers(.ContainerID).MouseDown = False
        BY_Containers(.ContainerID).MouseLock = True
        BY_Containers(.ContainerID).MouseLockType = 5
        BY_Containers(.ContainerID).MouseLockID = NOWID
        '响应鼠标按下操作
        .MouseDownOn = True
        
        Dim DX As Long, DY As Long
        DX = BY_Containers(.ContainerID).MPX - .X
        DY = BY_Containers(.ContainerID).MPY - .Y
        IsPopShow = True
        ShowWindow PopForm.hwnd, 8
        'PopForm.Show
        If .ItemCount <= 12 Then
            MoveWindow PopForm.hwnd, GPX - DX, GPY - DY + .Hi + 1, .Wi + 1, .ItemCount * 22 + 1, True
            BY_LISTBOXS(BY_LISTBOX_MAIN.pop).Wi = .Wi
            BY_LISTBOXS(BY_LISTBOX_MAIN.pop).Hi = .ItemCount * 22
            BY_LISTBOXS(BY_LISTBOX_MAIN.pop).ToComboID = NOWID
            BY_SCROLLS(BY_SCROLL_MAIN.pop).X = .Wi
            BY_SCROLLS(BY_SCROLL_MAIN.pop).Y = 0
            BY_SCROLLS(BY_SCROLL_MAIN.pop).Wi = 0
            BY_SCROLLS(BY_SCROLL_MAIN.pop).Hi = .ItemCount * 22
            ResetLISTBOX BY_LISTBOX_MAIN.pop
            BY_SCROLLS(BY_SCROLL_MAIN.pop).BY = 0
            IsPopShow = True
        Else
            MoveWindow PopForm.hwnd, GPX - DX, GPY - DY + .Hi + 1, .Wi + 1, 12 * 22 + 1, True
            BY_LISTBOXS(BY_LISTBOX_MAIN.pop).Wi = .Wi - .Hi
            BY_LISTBOXS(BY_LISTBOX_MAIN.pop).Hi = 12 * 22
            BY_LISTBOXS(BY_LISTBOX_MAIN.pop).ToComboID = NOWID
            BY_SCROLLS(BY_SCROLL_MAIN.pop).X = .Wi - .Hi
            BY_SCROLLS(BY_SCROLL_MAIN.pop).Y = 0
            BY_SCROLLS(BY_SCROLL_MAIN.pop).Wi = .Hi
            BY_SCROLLS(BY_SCROLL_MAIN.pop).Hi = 12 * 22
            ResetLISTBOX BY_LISTBOX_MAIN.pop
            BY_SCROLLS(BY_SCROLL_MAIN.pop).BY = 0
            IsPopShow = True
        End If
        ReDim BY_LISTBOXS(BY_LISTBOX_MAIN.pop).Item(.ItemCount)
        BY_LISTBOXS(BY_LISTBOX_MAIN.pop).ItemCount = .ItemCount
        Dim i As Long
        For i = 1 To .ItemCount
            BY_LISTBOXS(BY_LISTBOX_MAIN.pop).Item(i).Text = .Item(i).Text
        Next
        BY_LISTBOXS(BY_LISTBOX_MAIN.pop).SelectedIndex = .index
    End If
End If
TipNext:
If BY_Containers(.ContainerID).MouseLock = True Then Exit Sub
If pd = False And .PreType <> 0 Then MouseEvent .ContainerID, .PreType, .PreID
End With
End Sub
Public Sub ActCOMBO(NOWID As Long)
With BY_COMBOS(NOWID)
If .Invisible Then GoTo TipNext
Select Case NOWID
Case BY_COMBO_MAIN.SelType
    .X = 6: .Y = 76: .Wi = BY_Containers(.ContainerID).Wi - 12: .Hi = 24
Case BY_COMBO_MAIN.SelGroup
    .X = 6: .Y = 116: .Wi = BY_Containers(.ContainerID).Wi / 2 - 9: .Hi = 24
Case BY_COMBO_MAIN.SelCategory
    .X = BY_Containers(.ContainerID).Wi / 2 + 3: .Y = 116: .Wi = BY_Containers(.ContainerID).Wi / 2 - 9: .Hi = 24
Case BY_COMBO_MAIN.Action1
    Select Case EVENT_ACTION_CODE.OPR
    Case 2, 3, 14, 15, 20, 22, 23, 24, 36, 44, 52: .X = 80: .Y = 20: .Wi = 300: .Hi = 24
    Case 4: .X = 12: .Y = 20: .Wi = BY_Containers(.ContainerID).Wi - 24: .Hi = 24
    Case 31, 32, 33, 37, 40, 41, 42, 43, 46, 47, 48, 49: .X = 80: .Y = 60: .Wi = 300: .Hi = 24
    Case 45: .X = 12: .Y = 20: .Wi = BY_Containers(.ContainerID).Wi - 24: .Hi = 24
    End Select
Case BY_COMBO_MAIN.Action2
    Select Case EVENT_ACTION_CODE.OPR
    Case 2, 3: .X = 12: .Y = 60: .Wi = BY_Containers(.ContainerID).Wi - 24: .Hi = 24
    Case 4: .X = 24: .Y = 60: .Wi = BY_Containers(.ContainerID).Wi - 48: .Hi = 24
    Case 14: .X = 80: .Y = 60: .Wi = 300: .Hi = 24
    Case 45: .X = 80: .Y = 260: .Wi = BY_Containers(.ContainerID).Wi - 104: .Hi = 24
    End Select
Case BY_COMBO_MAIN.Action3
    Select Case EVENT_ACTION_CODE.OPR
    Case 2, 3: .X = 80: .Y = 100: .Wi = BY_Containers(.ContainerID).Wi - 104: .Hi = 24
    Case 4: .X = 80: .Y = 100: .Wi = BY_Containers(.ContainerID).Wi - 116: .Hi = 24
    Case 14: .X = 102: .Y = 100: .Wi = 80: .Hi = 24
    End Select
Case BY_COMBO_MAIN.Action4
    Select Case EVENT_ACTION_CODE.OPR
    Case 2, 3: .X = 80: .Y = 152: .Wi = 300: .Hi = 24
    Case 4: .X = 24: .Y = 152: .Wi = BY_Containers(.ContainerID).Wi - 48: .Hi = 24
    End Select
Case BY_COMBO_MAIN.Action5
    Select Case EVENT_ACTION_CODE.OPR
    Case 4: .X = 80: .Y = 192: .Wi = BY_Containers(.ContainerID).Wi - 116: .Hi = 24
    Case 45: .X = 132: .Y = 312: .Wi = 60: .Hi = 24
    End Select
Case BY_COMBO_MAIN.Action6
    Select Case EVENT_ACTION_CODE.OPR
    Case 4: .X = 80: .Y = 246: .Wi = BY_Containers(.ContainerID).Wi - 48 - 56: .Hi = 24
    End Select
Case BY_COMBO_MAIN.Action7
    Select Case EVENT_ACTION_CODE.OPR
    Case 4: .X = 132: .Y = 292: .Wi = 60: .Hi = 24
    End Select
Case BY_COMBO_MAIN.Event1
    .X = 32: .Y = 460: .Wi = 300 - 64: .Hi = 24
Case BY_COMBO_MAIN.Event2
    .X = 32: .Y = 494: .Wi = 300 - 64: .Hi = 24
Case BY_COMBO_MAIN.Event3
    .X = 150: .Y = 530: .Wi = 106: .Hi = 24
End Select

'处理特技
If .MouseHangOn Then
    If .tBackColor > -40 Then .tBackColor = .tBackColor - 2
Else
    If .tBackColor < 0 Then .tBackColor = .tBackColor + 1
End If
'处理子控件
If .SubControl = True Then
    .IsReDraw = True
    If .Hide = False Then
        If BY_Containers(.ContainerID).MouseLockType <> 5 Or BY_Containers(.ContainerID).MouseLockID <> NOWID Then HideCOMBO NOWID
    End If
    If .SendMessage = True Then
        BY_LISTBOXS(.SubGiveID.ListBox).Item(.SubGiveID.index).Value = .index - 1
        'ListSendValue .SubGiveID.ListBox, BY_LISTBOXS(.SubGiveID.ListBox).Item(.SubGiveID.index).Text2
        .SendMessage = False
    End If
End If
If BY_Containers(.ContainerID).ReDrawAll Then
    .IsReDraw = True
End If
If .IsReDraw = False Then '判断是否需要重绘
    If .tBackColor < 0 Then .IsReDraw = True
End If
TipNext:
If .Y + .Hi < 0 Then .IsReDraw = False
If .NextType <> 0 Then ActControl .ContainerID, .NextType, .NextID
End With
End Sub

Public Sub HideCOMBO(NOWID As Long)
With BY_COMBOS(NOWID)
.Y = -999999
.Hide = True
End With
End Sub
