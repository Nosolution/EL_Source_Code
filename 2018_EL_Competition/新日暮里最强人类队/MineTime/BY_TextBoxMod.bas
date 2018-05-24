Attribute VB_Name = "BY_TextBoxMod"
Public Type BY_TEXTBOX
X As Single
Y As Single
Wi As Long
Hi As Long
Text As String
Alignment As Long '0居中对齐 1左对齐 2右对齐 3多行
style As Long '0 文本输入 1 数字输入
SubControl As Boolean '子控件
SubGiveID As GiveID
Invisible As Boolean
Status As Long '状态 0 不可用 1 可用
MouseHangOn As Boolean
MouseDownOn As Boolean
MouseHangOnSub As Boolean
MouseHangOnAdd As Boolean
MouseDownOnSub As Boolean
MouseDownOnAdd As Boolean
'BorderColor As BY_Color
Hide As Boolean
SendMessage As Boolean
ContainerID As Long '容器id TOOLBOX 1 TOOLBAR 2 SELBOX 3
NextID As Long '同一容器内的绘图顺序
NextType As Long
PreID As Long '同一容器内的事件执行顺序（鼠标判断顺序）
PreType As Long
time As Long
IsReDraw As Boolean
End Type

Public Type BY_TEXTBOX_MAIN_TYPE
num As Long
Plan As Long
year As Long
month As Long
day As Long
hour As Long
min As Long
ReTest As Long
test As Long
End Type

Public BY_TEXTBOXS() As BY_TEXTBOX
Public BY_TEXTBOX_MAIN As BY_TEXTBOX_MAIN_TYPE
Public BY_NOWTEXTBOXINDEX As Long

Public Sub iniBY_TEXTBOX(ContainerID As Long, X As Long, Y As Long, w As Long, h As Long, style As Long, Optional Alignment As Long)
Dim ID As Long
BY_TEXTBOX_MAIN.num = BY_TEXTBOX_MAIN.num + 1
ReDim Preserve BY_TEXTBOXS(BY_TEXTBOX_MAIN.num)
ID = BY_TEXTBOX_MAIN.num
With BY_TEXTBOXS(ID)
.X = X: .Y = Y: .Wi = w: .Hi = h: .style = style: .ContainerID = ContainerID
.PreType = BY_Containers(.ContainerID).pControlType
.PreID = BY_Containers(.ContainerID).pControlID
.Alignment = Alignment
MakeLink .ContainerID, 2, ID
If .style = 1 Then .Text = "0"
End With
End Sub

Public Sub DrawTEXTBOX(NOWID As Long)
With BY_TEXTBOXS(NOWID)
If .Invisible Then GoTo DrawNext
If .IsReDraw Then

'DrawRectVB .ContainerID, .X, .Y, .Wi, .Hi, True, 52, 52, 52
If .style = 0 Then
    DrawRectVB2 .ContainerID, .X - 16, .Y - 16, .Wi + 32, .Hi + 32, True, bkColor + RGB(52, 52, 52)
ElseIf .style = 1 Then
    DrawRectVB2 .ContainerID, .X, .Y, .Wi, .Hi, True, bkColor + RGB(52, 52, 52)
    If .MouseDownOnSub Then
        DrawRectVB2 .ContainerID, .X, .Y, .Hi, .Hi, True, bkColor + RGB(128, 128, 128)
    ElseIf .MouseHangOnSub Then
        DrawRectVB2 .ContainerID, .X, .Y, .Hi, .Hi, True, bkColor + RGB(110, 110, 110)
    Else
        DrawRectVB2 .ContainerID, .X, .Y, .Hi, .Hi, True, bkColor + RGB(100, 100, 100)
    End If
    If .MouseDownOnAdd Then
        DrawRectVB2 .ContainerID, .X + .Wi - .Hi, .Y, .Hi, .Hi, True, bkColor + RGB(128, 128, 128)
    ElseIf .MouseHangOnAdd Then
        DrawRectVB2 .ContainerID, .X + .Wi - .Hi, .Y, .Hi, .Hi, True, bkColor + RGB(110, 110, 110)
    Else
        DrawRectVB2 .ContainerID, .X + .Wi - .Hi, .Y, .Hi, .Hi, True, bkColor + RGB(100, 100, 100)
    End If
    DrawRectVB .ContainerID, .X, .Y, .Hi, .Hi, False, 144 - 50, 200 - 50, 246 - 50
    DrawRectVB .ContainerID, .X + 1, .Y + 1, .Hi - 2, .Hi - 2, False, 144 - 50, 200 - 50, 246 - 50
    DrawRectVB .ContainerID, .X + .Wi - .Hi, .Y, .Hi, .Hi, False, 144 - 50, 200 - 50, 246 - 50
    DrawRectVB .ContainerID, .X + .Wi - .Hi + 1, .Y + 1, .Hi - 2, .Hi - 2, False, 144 - 50, 200 - 50, 246 - 50
End If
Select Case .style
Case 0
    Select Case .Alignment
    Case 0: DrawTextGDI .ContainerID, .Text, .X, .Y, .Wi, .Hi, DT_LEFT Or DT_WORDBREAK, 18, 1, vbWhite
    Case 1: DrawTextGDI .ContainerID, .Text, .X + 4, .Y, .Wi - 4, .Hi, DT_VCENTER Or DT_SINGLELINE, 12, 1, vbBlack
    Case 2: DrawTextGDI .ContainerID, .Text, .X, .Y, .Wi - 4, .Hi, DT_RIGHT Or DT_VCENTER Or DT_SINGLELINE, 12, 1, vbBlack
    Case 3: DrawTextGDI .ContainerID, .Text, .X + 4, .Y, .Wi - 4, .Hi, DT_LEFT Or dt_top, 12, 1, vbBlack
    End Select
Case 1
    DrawTextGDI .ContainerID, .Text, .X + .Hi, .Y, .Wi - 2 * .Hi, .Hi, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite

    DrawTextGDI .ContainerID, "-", .X, .Y, .Hi, .Hi, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
    DrawTextGDI .ContainerID, "+", .X + .Wi - .Hi, .Y, .Hi, .Hi, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
End Select
End If

DrawNext:
.MouseHangOn = False
.MouseDownOn = False
.MouseDownOnAdd = False
.MouseDownOnSub = False
.MouseHangOnAdd = False
.MouseHangOnSub = False
.IsReDraw = False
If .NextType <> 0 Then DrawControl .ContainerID, .NextType, .NextID
End With
End Sub

Public Sub MouseOnTEXTBOX(NOWID As Long)
With BY_TEXTBOXS(NOWID)
Dim pd As Boolean
If .Invisible Then GoTo TipNext
If PhyTouch(.X, .Y, .Wi, .Hi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
    pd = True
    .MouseHangOn = True
    If .style = 1 Then
        If PhyTouch(.X, .Y, .Hi, .Hi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
            '减
            .MouseHangOnSub = True
        ElseIf PhyTouch(.X + .Wi - .Hi, .Y, .Hi, .Hi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
            '加
            .MouseHangOnAdd = True
        End If
    End If
    If BY_Containers(.ContainerID).MouseDown Then
        '响应鼠标按下操作
        .MouseDownOn = True
        Select Case .style
        Case 0
            BY_Containers(.ContainerID).MouseDown = False
            BY_Containers(.ContainerID).MouseLock = True
            BY_Containers(.ContainerID).MouseLockType = 2
            BY_Containers(.ContainerID).MouseLockID = NOWID
            Select Case .ContainerID
            Case 2
                InputForm.TextBox.left = .X + 1
                InputForm.TextBox.Top = .Y + 1
                InputForm.TextBox.Width = .Wi - 1
                InputForm.TextBox.Height = .Hi - 1
                Select Case .Alignment
                Case 0: InputForm.TextBox.Alignment = 0
                End Select
                InputForm.TextBox.Visible = True
                InputForm.TextBox.Text = .Text
                InputForm.TextBox.SelLength = Len(.Text)
                InputForm.TextBox.SetFocus
                BY_NOWTEXTBOXINDEX = NOWID
            End Select
        Case 1
            BY_Containers(.ContainerID).MouseLock = True
            BY_Containers(.ContainerID).MouseLockType = 2
            BY_Containers(.ContainerID).MouseLockID = NOWID
            If PhyTouch(.X, .Y, .Hi, .Hi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
                '减
                .time = .time + 1
                .MouseDownOnSub = True
                .Text = Val(.Text) - IIf(.time Mod 4 = 0, 1, 0)
                BY_TEXTBOX_FIX NOWID
            ElseIf PhyTouch(.X + .Wi - .Hi, .Y, .Hi, .Hi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
                '加
                .time = .time + 1
                .MouseDownOnAdd = True
                .Text = Val(.Text) + IIf(.time Mod 4 = 0, 1, 0)
                BY_TEXTBOX_FIX NOWID
            Else
                BY_Containers(.ContainerID).MouseDown = False
                Select Case .ContainerID
                Case 8
                    ActionForm.TextBoxVal.left = .X + 1 + .Hi
                    ActionForm.TextBoxVal.Top = .Y + 1
                    ActionForm.TextBoxVal.Width = .Wi - 1 - 2 * .Hi
                    ActionForm.TextBoxVal.Height = .Hi - 1
                    Select Case .Alignment
                    Case 0: MDI.TextBox.Alignment = 2
                    Case 1: MDI.TextBox.Alignment = 0
                    Case 2: MDI.TextBox.Alignment = 1
                    End Select
                    ActionForm.TextBoxVal.Visible = True
                    ActionForm.TextBoxVal.Text = .Text
                    ActionForm.TextBoxVal.SelLength = Len(.Text)
                    ActionForm.TextBoxVal.SetFocus
                    BY_NOWTEXTBOXINDEX = NOWID
                Case 6
                    MDI.TextBox.left = .X + 1 + .Hi
                    MDI.TextBox.Top = .Y + 1
                    MDI.TextBox.Width = .Wi - 1 - 2 * .Hi
                    MDI.TextBox.Height = .Hi - 1
                    Select Case .Alignment
                    Case 0: MDI.TextBox.Alignment = 2
                    Case 1: MDI.TextBox.Alignment = 0
                    Case 2: MDI.TextBox.Alignment = 1
                    End Select
                    MDI.TextBox.Visible = True
                    MDI.TextBox.Text = .Text
                    MDI.TextBox.SelLength = Len(.Text)
                    MDI.TextBox.SetFocus
                    BY_NOWTEXTBOXINDEX = NOWID
                Case 5
                    ResourceEditor.TextBox.left = .X + 1 + .Hi
                    ResourceEditor.TextBox.Top = .Y + 1
                    ResourceEditor.TextBox.Width = .Wi - 1 - 2 * .Hi
                    ResourceEditor.TextBox.Height = .Hi - 1
                    ResourceEditor.TextBox.Alignment = 2
                    ResourceEditor.TextBox.Visible = True
                    ResourceEditor.TextBox.Text = .Text
                    ResourceEditor.TextBox.SelLength = Len(.Text)
                    ResourceEditor.TextBox.SetFocus
                    BY_NOWTEXTBOXINDEX = NOWID
                End Select
            End If
        End Select
    Else
        .time = 0
    End If
End If
TipNext:
If BY_Containers(.ContainerID).MouseLock = True Then Exit Sub
If pd = False And .PreType <> 0 Then MouseEvent .ContainerID, .PreType, .PreID
End With
End Sub
Public Sub ActTEXTBOX(NOWID As Long)
With BY_TEXTBOXS(NOWID)
Select Case NOWID
Case BY_TEXTBOX_MAIN.Plan
    .Wi = BY_Containers(.ContainerID).Wi - 96: .Hi = 144
    .X = 48: .Y = 102
Case BY_TEXTBOX_MAIN.year
    .X = 210: .Y = 90: .Wi = BY_Containers(.ContainerID).Wi - 280: .Hi = 36
Case BY_TEXTBOX_MAIN.month
    .X = 210: .Y = 140: .Wi = BY_Containers(.ContainerID).Wi - 280: .Hi = 36
Case BY_TEXTBOX_MAIN.day
    .X = 210: .Y = 190: .Wi = BY_Containers(.ContainerID).Wi - 280: .Hi = 36
Case BY_TEXTBOX_MAIN.hour
    .X = 210: .Y = 240: .Wi = BY_Containers(.ContainerID).Wi - 280: .Hi = 36
Case BY_TEXTBOX_MAIN.min
    .X = 210: .Y = 290: .Wi = BY_Containers(.ContainerID).Wi - 280: .Hi = 36
Case BY_TEXTBOX_MAIN.test
    .X = 48: .Y = 90: .Wi = BY_Containers(.ContainerID).Wi - 200: .Hi = 36
End Select
If .Invisible Then GoTo TipNext
If .SubControl = True Then
End If
If BY_Containers(.ContainerID).ReDrawAll Then
    .IsReDraw = True
End If
If .IsReDraw = False Then '判断是否需要重绘
    If .MouseHangOn < 0 Then .IsReDraw = True
End If
If .SubControl = True Then
    .IsReDraw = True
End If
TipNext:
If .Y + .Hi < 0 Then .IsReDraw = False
If .NextType <> 0 Then ActControl .ContainerID, .NextType, .NextID
End With
End Sub

Public Sub HideTEXTBOX(NOWID As Long)
With BY_TEXTBOXS(NOWID)
.Y = -999999
.Hide = True
End With
End Sub

Public Sub BY_TEXTBOX_FIX(NOWID As Long)
With BY_TEXTBOXS(NOWID)
Select Case NOWID
Case BY_TEXTBOX_MAIN.year
    If .Text = "0" Then .Text = CStr(year(Now))
Case BY_TEXTBOX_MAIN.month
    If .Text = "0" Then .Text = CStr(12)
    If .Text = "13" Then .Text = CStr(1)
Case BY_TEXTBOX_MAIN.day
    If .Text = "0" Then .Text = CStr(31)
    If .Text = "32" Then .Text = CStr(1)
Case BY_TEXTBOX_MAIN.hour
    If .Text = "-1" Then .Text = CStr(23)
    If .Text = "24" Then .Text = CStr(0)
Case BY_TEXTBOX_MAIN.min
    If .Text = "-1" Then .Text = CStr(59)
    If .Text = "60" Then .Text = CStr(0)
Case BY_TEXTBOX_MAIN.num
    If .Text = "0" Then .Text = "1"
End Select
End With
End Sub
