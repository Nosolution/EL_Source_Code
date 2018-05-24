Attribute VB_Name = "BY_ScrollMod"
Public Type BY_SCROLL
X As Single
Y As Single
Wi As Long
Hi As Long
BY As Single
bh As Long
Value As Single 'ppy
Invisible As Boolean
tBarBackColor As Long
tSubBackColor As Long
tAddBackColor As Long
ToControlType As Long
ToControlID As Long '对应的控件ID
LockMouseY As Single
LockBarY As Single
LockTheBar As Boolean
LockSub As Boolean
LockAdd As Boolean
Status As Long '状态 0 不可用 1 可用
MouseHangOn As Boolean
MouseDownOn As Boolean
MouseHangOnBar As Boolean
MouseHangOnSub As Boolean
MouseHangOnAdd As Boolean
ContainerID As Long '容器id TOOLBOX 1 TOOLBAR 2 SELBOX 3
NextID As Long '同一容器内的绘图顺序
NextType As Long
PreID As Long '同一容器内的事件执行顺序（鼠标判断顺序）
PreType As Long
IsReDraw As Boolean
pppy As Long
DrawWi As Long
End Type

Public Type BY_SCROLL_MAIN_TYPE
num As Long
Sel As Long
SelObj As Long
dataPro As Long
pop As Long
grid As Long
event As Long
Action As Long
Val As Long
EventList As Long
OS As Long
Layer As Long
List As Long
End Type

Public BY_SCROLLS() As BY_SCROLL
Public BY_SCROLL_MAIN As BY_SCROLL_MAIN_TYPE

Public Sub iniBY_SCROLL(ContainerID As Long, X As Long, Y As Long, w As Long, h As Long, ToControlType As Long, ToControlID As Long)
Dim ID As Long
BY_SCROLL_MAIN.num = BY_SCROLL_MAIN.num + 1
ReDim Preserve BY_SCROLLS(BY_SCROLL_MAIN.num)
ID = BY_SCROLL_MAIN.num
With BY_SCROLLS(ID)
.X = X: .Y = Y: .Wi = w: .Hi = h: .ContainerID = ContainerID
.PreType = BY_Containers(.ContainerID).pControlType
.PreID = BY_Containers(.ContainerID).pControlID
MakeLink .ContainerID, 4, ID
.ToControlType = ToControlType
.ToControlID = ToControlID
End With
End Sub

Public Sub DrawSCROLL(NOWID As Long)
With BY_SCROLLS(NOWID)
If .ContainerID = 4 Then
    If IsPopShow = False Then GoTo DrawNext
End If
If .Invisible Then GoTo DrawNext
If .IsReDraw Then
    If .MouseDownOn Then
        DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, True, 216, 230, 242
    ElseIf .MouseHangOn Then
        DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, True, 216, 230, 242
    End If
    
    DrawRectVB2 .ContainerID, .X, .Y + .Wi, .Wi, .Hi - 2 * .Wi, True, bkColor + RGB(27, 27, 27)
    
    
    If .LockTheBar = False Then
        DrawRectVB2 .ContainerID, .X, .Y + .Wi + .BY, .Wi, .bh, True, bkColor + RGB(72 - .tBarBackColor, 72 - .tBarBackColor, 72 - .tBarBackColor)
    Else
        DrawRectVB2 .ContainerID, .X, .Y + .Wi + .BY, .Wi, .bh, True, bkColor + RGB(100 - .tBarBackColor, 100 - .tBarBackColor, 100 - .tBarBackColor)
    End If
    If .LockSub = False Then
        DrawRectVB2 .ContainerID, .X, .Y, .Wi, .Wi, True, bkColor + RGB(27 - .tSubBackColor, 27 - .tSubBackColor, 27 - .tSubBackColor)
    Else
        DrawRectVB2 .ContainerID, .X, .Y, .Wi, .Wi, True, bkColor + RGB(27 - .tSubBackColor, 27 - .tSubBackColor, 27 - .tSubBackColor)
    End If
    'DrawTextGDI .ContainerID, "∧", .x, .y, .Wi, .Wi, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 10, 1, vbWhite
    If .LockAdd = False Then
        DrawRectVB2 .ContainerID, .X, .Y + .Hi - .Wi, .Wi, .Wi, True, bkColor + RGB(27 - .tAddBackColor, 27 - .tAddBackColor, 27 - .tAddBackColor)
    Else
        DrawRectVB2 .ContainerID, .X, .Y + .Hi - .Wi, .Wi, .Wi, True, bkColor + RGB(27 - .tAddBackColor, 27 - .tAddBackColor, 27 - .tAddBackColor)
    End If
    'DrawTextGDI .ContainerID, "∨", .x, .y + .Hi - .Wi, .Wi, .Wi, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 10, 1, vbWhite
    DrawLineVB .ContainerID, .X + .Wi / 2, .Y + 10, .X + 8, .Y + 15, 255, 255, 255
    DrawLineVB .ContainerID, .X + .Wi / 2, .Y + 10, .X + .Wi - 8, .Y + 15, 255, 255, 255
    DrawLineVB .ContainerID, .X + .Wi / 2, .Y + 11, .X + 8, .Y + 16, 255, 255, 255
    DrawLineVB .ContainerID, .X + .Wi / 2, .Y + 11, .X + .Wi - 8, .Y + 16, 255, 255, 255
    
    DrawLineVB .ContainerID, .X + .Wi / 2, .Y + .Hi - 10, .X + 8, .Y + .Hi - 15, 255, 255, 255
    DrawLineVB .ContainerID, .X + .Wi / 2, .Y + .Hi - 10, .X + .Wi - 8, .Y + .Hi - 15, 255, 255, 255
    DrawLineVB .ContainerID, .X + .Wi / 2, .Y + .Hi - 11, .X + 8, .Y + .Hi - 16, 255, 255, 255
    DrawLineVB .ContainerID, .X + .Wi / 2, .Y + .Hi - 11, .X + .Wi - 8, .Y + .Hi - 16, 255, 255, 255
End If

If .IsReDraw Then BY_Containers(.ContainerID).DrawCount = BY_Containers(.ContainerID).DrawCount + 1

DrawNext:
.MouseHangOn = False
.MouseDownOn = False
.MouseHangOnBar = False
.MouseHangOnAdd = False
.MouseHangOnSub = False
.IsReDraw = False
If .NextType <> 0 Then DrawControl .ContainerID, .NextType, .NextID
End With
End Sub

Public Sub MouseOnSCROLL(NOWID As Long)
With BY_SCROLLS(NOWID)
Dim pd As Boolean
If .Invisible Then GoTo TipNext

If PhyTouch(.X, .Y, .Wi, .Hi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
    pd = True
    .MouseHangOn = True
    If PhyTouch(.X, .Y + .Wi + .BY, .Wi, .bh, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
        .MouseHangOnBar = True
    ElseIf PhyTouch(.X, .Y, .Wi, .Wi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
        .MouseHangOnSub = True
    ElseIf PhyTouch(.X, .Y + .Hi - .Wi, .Wi, .Wi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
        .MouseHangOnAdd = True
    End If
    If BY_Containers(.ContainerID).MouseDown Then
        BY_Containers(.ContainerID).MouseDown = False
        BY_Containers(.ContainerID).MouseLock = True
        BY_Containers(.ContainerID).MouseLockType = 4
        BY_Containers(.ContainerID).MouseLockID = NOWID
        .LockTheBar = False
        If .MouseHangOnBar Then
            .LockMouseY = BY_Containers(.ContainerID).MPY
            .LockBarY = .BY
            .LockTheBar = True
        End If
        If .MouseHangOnSub Then
            .LockSub = True
        End If
        If .MouseHangOnAdd Then
            .LockAdd = True
        End If
        '响应鼠标按下操作
        .MouseDownOn = True
    End If
End If
If BY_Containers(.ContainerID).MouseLock = True Then
    If .LockTheBar Then
        Dim DY As Single
        DY = BY_Containers(.ContainerID).MPY - .LockMouseY
        .BY = .LockBarY + DY
    ElseIf .LockAdd Then
        .BY = .BY + 1
    ElseIf .LockSub Then
        .BY = .BY - 1
    End If
    If .BY < 0 Then .BY = 0
    If .BY > .Hi - 2 * .Wi - .bh Then .BY = .Hi - 2 * .Wi - .bh
    Exit Sub
Else
    .LockAdd = False
    .LockSub = False
    .LockTheBar = False
End If
TipNext:
If pd = False And .PreType <> 0 Then MouseEvent .ContainerID, .PreType, .PreID
End With
End Sub
Public Sub ActSCROLL(NOWID As Long)
With BY_SCROLLS(NOWID)
If .Invisible Then GoTo TipNext
'自动调整尺寸
Select Case NOWID
Case BY_SCROLL_MAIN.List
    .X = BY_LISTBOXS(.ToControlID).X + BY_LISTBOXS(.ToControlID).Wi: .Y = BY_LISTBOXS(.ToControlID).Y
    .Wi = 26: .Hi = BY_LISTBOXS(.ToControlID).Hi
End Select
'即时计算
Dim QH As Long, SH As Long, CH As Long
Select Case .ToControlType
Case 3: SH = (BY_LISTBOXS(.ToControlID).ItemCount + 1) * BY_LISTBOXS(.ToControlID).ItemHeight: CH = BY_LISTBOXS(.ToControlID).Hi
End Select
If SH <> 0 Then
    QH = .Hi - 2 * .Wi
    .bh = CH / SH * QH
    If .bh >= QH Then
        .bh = QH
        .Value = 0
    Else
        .Value = .BY / (QH - .bh) * (SH - CH)
    End If
    If .Value > 0 Then
        Select Case .ToControlType
        Case 3
            BY_LISTBOXS(.ToControlID).ppy = .Value
            If BY_LISTBOXS(.ToControlID).ppy <> .pppy Then BY_LISTBOXS(.ToControlID).IsReDraw = True
            .pppy = BY_LISTBOXS(.ToControlID).ppy
        End Select
    Else
        Select Case .ToControlType
        Case 3
            BY_LISTBOXS(.ToControlID).ppy = 0
            If BY_LISTBOXS(.ToControlID).ppy <> .pppy Then BY_LISTBOXS(.ToControlID).IsReDraw = True
            .pppy = BY_LISTBOXS(.ToControlID).ppy
        End Select
    End If
End If
'特技
If .LockTheBar Then
    .tBarBackColor = -20
ElseIf .MouseHangOnBar Then
    If .tBarBackColor > -20 Then .tBarBackColor = .tBarBackColor - 2
Else
    If .tBarBackColor < 0 Then .tBarBackColor = .tBarBackColor + 1
End If

If .LockSub Then
    .tSubBackColor = -20
ElseIf .MouseHangOnSub Then
    If .tSubBackColor > -20 Then .tSubBackColor = .tSubBackColor - 2
Else
    If .tSubBackColor < 0 Then .tSubBackColor = .tSubBackColor + 1
End If

If .LockAdd Then
    .tAddBackColor = -20
ElseIf .MouseHangOnAdd Then
    If .tAddBackColor > -20 Then .tAddBackColor = .tAddBackColor - 2
Else
    If .tAddBackColor < 0 Then .tAddBackColor = .tAddBackColor + 1
End If
If BY_Containers(.ContainerID).ReDrawAll Then
    .IsReDraw = True
End If
If .IsReDraw = False Then '判断是否需要重绘
    If .tAddBackColor < 0 Or .tSubBackColor < 0 Or .tBarBackColor < 0 Then .IsReDraw = True
End If
TipNext:
If .Y + .Hi < 0 Then .IsReDraw = False
If .NextType <> 0 Then ActControl .ContainerID, .NextType, .NextID
End With
End Sub
