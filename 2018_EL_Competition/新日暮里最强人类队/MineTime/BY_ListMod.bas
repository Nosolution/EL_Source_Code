Attribute VB_Name = "BY_ListMod"
Public Type BY_LISTITEM
Text As String '存储字符串
Text2 As String '标题
Text3 As String
str As String
PIC As Long '储存GDI++的图片
Value As Long '储存数据
style As Long '风格 1只标题 2只文本显示 3只数字显示 4可输入文本 5可输入数字 6下拉框 7 按钮
Minimum As Long
HangTime As Long
End Type

Public Type GiveID
index As Long
Button As Long
TextBox As Long
ListBox As Long
Combo As Long
End Type

Public Type BY_LISTBOX
X As Single
Y As Single
Wi As Long
Hi As Long
ppy As Single
SelectedIndex As Long
PointedIndex As Long
ShowTip As Boolean
TipIndex As Long
ItemHeight As Long
Item() As BY_LISTITEM
ShowPic As Boolean
PicSize As Long
InColor As Boolean
RelateType As Long '1 block
tBackColor As Long
FontColor As Long
ItemCount As Long
ToComboID As Long '对应的combo
GiveID As GiveID
Invisible As Boolean
Status As Long '状态 0 不可用 1 可用
style As Long '0-普通的列表 1-有2列的表格 2-有图片的列表
LeftW As Long '左栏的宽度
MouseHangOn As Boolean
MouseDownOn As Boolean
ContainerID As Long '容器id TOOLBOX 1 TOOLBAR 2 SELBOX 3
NextID As Long '同一容器内的绘图顺序
NextType As Long
PreID As Long '同一容器内的事件执行顺序（鼠标判断顺序）
PreType As Long
IsReDraw As Boolean
pSelectedIndex As Long
pPointedIndex As Long
End Type

Public Type BY_LISTBOX_MAIN_TYPE
num As Long
List As Long
Sel As Long
Data As Long
dataPro As Long
pop As Long
grid As Long
actiongrid As Long
SelObj As Long
Val As Long
event As Long
Layer As Long
End Type

Public BY_LISTBOXS() As BY_LISTBOX
Public BY_LISTBOX_MAIN As BY_LISTBOX_MAIN_TYPE

Public Sub iniBY_LISTBOX(ContainerID As Long, X As Long, Y As Long, w As Long, h As Long, style As Long, Optional ToComboID As Long)
Dim ID As Long
BY_LISTBOX_MAIN.num = BY_LISTBOX_MAIN.num + 1
ReDim Preserve BY_LISTBOXS(BY_LISTBOX_MAIN.num)
ID = BY_LISTBOX_MAIN.num
With BY_LISTBOXS(ID)
.X = X: .Y = Y: .Wi = w: .Hi = h: .ContainerID = ContainerID
.PreType = BY_Containers(.ContainerID).pControlType
.PreID = BY_Containers(.ContainerID).pControlID
MakeLink .ContainerID, 3, ID
.style = style
.ItemHeight = 22
.ToComboID = ToComboID
    '.ItemCount = 7
    '.ItemHeight = 28
    '.LeftW = 86
    '.GiveID.TextBox = 0
    '.GiveID.Combo = 0
    '.GiveID.Button = 0
    'ReDim .Item(.ItemCount)
    '.Item(1).Style = 1: .Item(1).Text2 = "This is a title"
    '.Item(2).Style = 2: .Item(2).Text2 = "Caption": .Item(2).Text = "BYISCOOL"
    '.Item(3).Style = 3: .Item(3).Text2 = "Password": .Item(3).Value = 123450
    '.Item(4).Style = 4: .Item(4).Text2 = "TLE": .Item(4).Text = "Wow"
    '.Item(5).Style = 5: .Item(5).Text2 = "WA": .Item(5).Value = 666
    '.Item(6).Style = 6: .Item(6).Text2 = "QQ": .Item(6).Value = 1: .Item(6).Text = "abc"
    '.Item(7).Style = 7: .Item(7).Text2 = "Button": .Item(7).Value = 1
    '8 弹出窗
End With
End Sub

Public Sub ResetLISTBOX(ID As Long)
Dim n As Long, i As Long
With BY_LISTBOXS(ID)
Select Case ID
Case BY_LISTBOX_MAIN.List
    '1 只标题 2 标题+文字 3 标题+数字 4 标题+可编辑文字 5 标题+可编辑数字 6 标题+下拉框 7 标题+按钮
    '.ppy = 0
    'BY_SCROLLS(BY_SCROLL_MAIN.List).BY = 0
    .style = 1
    Select Case ShowMode
    Case 1
        .ItemCount = UBound(TodoList) * 3 + 1
        .ItemHeight = 100
        .GiveID.TextBox = BY_TEXTBOX_MAIN.ReTest
        .GiveID.Combo = BY_COMBO_MAIN.ReTest
        .GiveID.Button = BY_BUTTON_MAIN.ReTest
        ReDim .Item(.ItemCount)
        For i = 1 To UBound(TodoList)
            n = n + 1: .Item(n).style = 4: .Item(n).Text2 = "任务 #" & i: .Item(n).Value = i: .Item(n).Text = TodoList(i).Content:  .Item(n).PIC = 1: .Item(n).Text3 = ""
            n = n + 1: .Item(n).style = 4: .Item(n).Text2 = "截止时间": .Item(n).Value = i: .Item(n).Text = IIf(TodoList(i).Enabled, TodoList(i).Time.year & "年" & TodoList(i).Time.month & "月" & TodoList(i).Time.day & "日" & TodoList(i).Time.hour & "时" & TodoList(i).Time.min & "分 截止", "点击此处设置时间，激活后立即生效。"):       .Item(n).PIC = 2: .Item(n).Text3 = ""
            n = n + 1: .Item(n).style = 7: .Item(n).Text2 = " ": .Item(n).Text = "删除任务": .Item(n).PIC = 2: .Item(n).Text3 = "": .Item(n).Value = i
        Next
        n = n + 1: .Item(n).style = 7: .Item(n).Text2 = "": .Item(n).Text = "创建新任务": .Item(n).PIC = 1: .Item(n).Text3 = "": .Item(n).Value = 1
    Case 2
        .ItemCount = UBound(MusicInfos)
        .ItemHeight = 72
        .GiveID.TextBox = BY_TEXTBOX_MAIN.ReTest
        .GiveID.Combo = BY_COMBO_MAIN.ReTest
        .GiveID.Button = BY_BUTTON_MAIN.ReTest
        ReDim .Item(.ItemCount)
        For i = 1 To UBound(MusicInfos)
            n = n + 1: .Item(n).style = 1: .Item(n).Text2 = MusicInfos(i).Name: .Item(n).Value = 0
        Next
    End Select
    
End Select
.IsReDraw = True
End With
End Sub

Public Sub DrawLISTBOX(NOWID As Long)
With BY_LISTBOXS(NOWID)
Dim i As Long
Dim DrawHeight As Long
If .ContainerID = 4 Then
    If IsPopShow = False Then GoTo DrawNext
End If
If .Invisible Then GoTo DrawNext
If .IsReDraw Then
Select Case .style
Case 1
    For i = .ppy / .ItemHeight To .ItemCount
        If .ItemHeight * i - .ppy < .Hi Then DrawHeight = .ItemHeight
        Select Case .Item(i).style
        Case 1
            If i = .PointedIndex And .Item(i).Value = 0 Then
                DrawRectVB2 .ContainerID, .X, .Y + .ItemHeight * (i - 1) - .ppy, .Wi, .ItemHeight, True, bkColor + RGB(16, 16, 16)
            End If
        End Select
        If .ItemHeight * i - .ppy >= .Hi Then Exit For
    Next
    For i = .ppy / .ItemHeight To .ItemCount
        If .ItemHeight * i - .ppy < .Hi Then DrawHeight = .ItemHeight
        Select Case .Item(i).style
        Case 1
            If .Item(i).Value = 1 Then
                DrawRectVB2 .ContainerID, .X, .Y + .ItemHeight * (i - 1) - .ppy, .Wi, .ItemHeight, True, bkColor + RGB(32, 32, 32)
                DrawLineVB .ContainerID, .X, .Y + .ItemHeight * (i - 1) - .ppy, .X, .Y + .ItemHeight * i - .ppy, 242, 242, 242
                DrawLineVB .ContainerID, .X + 1, .Y + .ItemHeight * (i - 1) - .ppy, .X + 1, .Y + .ItemHeight * i - .ppy, 242, 242, 242
                DrawLineVB .ContainerID, .X + .Wi - 1, .Y + .ItemHeight * (i - 1) - .ppy, .X + .Wi - 1, .Y + .ItemHeight * i - .ppy, 242, 242, 242
                DrawLineVB .ContainerID, .X + .Wi - 2, .Y + .ItemHeight * (i - 1) - .ppy, .X + .Wi - 2, .Y + .ItemHeight * i - .ppy, 242, 242, 242
                If i + 1 <= .ItemCount Then
                    If .Item(i + 1).Value = 0 Then
                        DrawLineVB .ContainerID, .X, .Y + .ItemHeight * (i) - .ppy + 1, .X + .Wi, .Y + .ItemHeight * (i) - .ppy + 1, 242, 242, 242
                        DrawLineVB .ContainerID, .X, .Y + .ItemHeight * (i) - .ppy + 2, .X + .Wi, .Y + .ItemHeight * (i) - .ppy + 2, 242, 242, 242
                    End If
                End If
                If i - 1 >= 1 Then
                    If .Item(i - 1).Value = 0 Then
                        DrawLineVB .ContainerID, .X, .Y + .ItemHeight * (i - 1) - .ppy, .X + .Wi, .Y + .ItemHeight * (i - 1) - .ppy, 242, 242, 242
                        DrawLineVB .ContainerID, .X, .Y + .ItemHeight * (i - 1) - .ppy - 1, .X + .Wi, .Y + .ItemHeight * (i - 1) - .ppy - 1, 242, 242, 242
                    End If
                End If
                If i = 1 Then
                    DrawLineVB .ContainerID, .X, .Y + .ItemHeight * (i - 1) - .ppy + 1, .X + .Wi, .Y + .ItemHeight * (i - 1) - .ppy + 1, 242, 242, 242
                    DrawLineVB .ContainerID, .X, .Y + .ItemHeight * (i - 1) - .ppy + 2, .X + .Wi, .Y + .ItemHeight * (i - 1) - .ppy + 2, 242, 242, 242
                End If
                If i = .ItemCount Then
                    DrawLineVB .ContainerID, .X, .Y + .ItemHeight * (i) - .ppy, .X + .Wi, .Y + .ItemHeight * (i) - .ppy, 242, 242, 242
                    DrawLineVB .ContainerID, .X, .Y + .ItemHeight * (i) - .ppy - 1, .X + .Wi, .Y + .ItemHeight * (i) - .ppy - 1, 242, 242, 242
                End If
            End If
            DrawTextGDI .ContainerID, .Item(i).Text2, .X + 16, .Y + (i - 1) * .ItemHeight - .ppy, .Wi - 16, .ItemHeight, DT_VCENTER Or DT_SINGLELINE, 16, 1, vbWhite
        Case 2, 3, 4, 5, 6, 7
            DrawTextGDI .ContainerID, .Item(i).Text2, .X + 16, .Y + (i - 1) * .ItemHeight - .ppy, .LeftW - 16, .ItemHeight / 2, DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
            Select Case .Item(i).style
            Case 1, 2, 4, 6
                If i = .PointedIndex Then
                    DrawRectVB2 .ContainerID, .X + 16, .Y + (i - 1) * .ItemHeight - .ppy + .ItemHeight / 2, .LeftW, DrawHeight / 2, True, bkColor + RGB(16, 16, 32)
                End If
                DrawTextGDI .ContainerID, .Item(i).Text, .X + 32, .Y + (i - 1) * .ItemHeight - .ppy + .ItemHeight / 2, .LeftW - 32, .ItemHeight / 2, DT_VCENTER Or DT_SINGLELINE, 16, 1, vbWhite
                If i = .PointedIndex Then
                    DrawRectVB .ContainerID, .X + 16, .Y + (i - 1) * .ItemHeight - .ppy + .ItemHeight / 2, .LeftW, DrawHeight / 2, False, 255, 255, 255
                    DrawRectVB .ContainerID, .X + 16 + 1, .Y + (i - 1) * .ItemHeight - .ppy + .ItemHeight / 2 + 1, .LeftW - 2, DrawHeight / 2 - 2, False, 255, 255, 255
                Else
                    DrawRectVB .ContainerID, .X + 16, .Y + (i - 1) * .ItemHeight - .ppy + .ItemHeight / 2, .LeftW, DrawHeight / 2, False, 166, 166, 166
                    DrawRectVB .ContainerID, .X + 16 + 1, .Y + (i - 1) * .ItemHeight - .ppy + .ItemHeight / 2 + 1, .LeftW - 2, DrawHeight / 2 - 2, False, 166, 166, 166
                End If
            Case 7
                DrawRectVB2 .ContainerID, .X + 16, .Y + (i - 1) * .ItemHeight - .ppy + .ItemHeight / 2, .LeftW, DrawHeight / 2, True, bkColor + RGB(52, 52, 52)
                DrawTextGDI .ContainerID, .Item(i).Text, .X + 16, .Y + (i - 1) * .ItemHeight - .ppy + .ItemHeight / 2, .LeftW, .ItemHeight / 2, DT_CENTER Or DT_VCENTER Or DT_SINGLELINE, 18, 1, vbWhite
            Case 3, 5
                DrawTextGDI .ContainerID, CStr(.Item(i).Value), .X + .LeftW + 4, .Y + (i - 1) * .ItemHeight - .ppy, .Wi - .LeftW - 4, .ItemHeight, DT_VCENTER Or DT_SINGLELINE, 12, 800, vbBlack
            End Select
        Case 8
            DrawTextGDI .ContainerID, .Item(i).Text2, .X + 4, .Y + (i - 1) * .ItemHeight - .ppy, .Wi - 4, .ItemHeight, DT_VCENTER Or DT_SINGLELINE, 13, 1, vbBlack
            DrawLineVB .ContainerID, .X, .Y + .ItemHeight * i - .ppy - 1, .X + .Wi, .Y + .ItemHeight * i - .ppy - 1, 160, 160, 160
        End Select
        If .ItemHeight * i - .ppy >= .Hi Then Exit For
    Next
End Select
'DrawRectVB .ContainerID, .x, .y, .Wi, .Hi, False, 24, 140, 210
End If
DrawRectVB2 .ContainerID, .X, 0, .Wi, CLng(.Y), True, bkColor

If .IsReDraw Then BY_Containers(.ContainerID).DrawCount = BY_Containers(.ContainerID).DrawCount + 1

DrawNext:
.MouseHangOn = False
.MouseDownOn = False
.IsReDraw = False
.pPointedIndex = .PointedIndex
.pSelectedIndex = .SelectedIndex
.PointedIndex = 0

If .NextType <> 0 Then DrawControl .ContainerID, .NextType, .NextID
End With
End Sub

Public Sub MouseOnLISTBOX(NOWID As Long)
Dim i As Long
With BY_LISTBOXS(NOWID)
Dim pd As Boolean
If .Invisible Then GoTo TipNext

.PointedIndex = 0
If PhyTouch(.X, .Y, .Wi, .Hi, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
    pd = True
    .MouseHangOn = True
    
    If BY_Containers(.ContainerID).MouseDown Then
        BY_Containers(.ContainerID).MouseLock = True
        BY_Containers(.ContainerID).MouseDown = False
        BY_Containers(.ContainerID).MouseLockType = 3
        BY_Containers(.ContainerID).MouseLockID = NOWID
        '响应鼠标按下操作
        .MouseDownOn = True
        .SelectedIndex = 0
        .PointedIndex = 0
    End If
    For i = .ppy / .ItemHeight To .ItemCount
        'If PhyTouch(BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1, .X, .Y + (i - 1) * .ItemHeight - .ppy, .Wi, .ItemHeight) Then
        Select Case ShowMode
        Case 1
            If PhyTouch(.X + 16, .Y + (i - 1) * .ItemHeight - .ppy + .ItemHeight / 2, .LeftW, .ItemHeight / 2, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
                .PointedIndex = i
                If .MouseDownOn Then .SelectedIndex = i
            End If
        Case 2
            If PhyTouch(.X, .Y + (i - 1) * .ItemHeight - .ppy, .Wi, .ItemHeight, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
                .PointedIndex = i
            End If
        End Select
        
        If .MouseDownOn Then
            If .Item(i).style = 1 Then
                If PhyTouch(.X, .Y + (i - 1) * .ItemHeight - .ppy, .Wi, .ItemHeight, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
                    If .Item(i).Value = 0 Then
                        .Item(i).Value = 1
                    Else
                        .Item(i).Value = 0
                    End If
                End If
            End If
        End If
        If .ItemHeight * i - .ppy >= .Hi Then Exit For
    Next
    If BY_Containers(.ContainerID).MouseRightDown Then
        Select Case NOWID
        Case BY_LISTBOX_MAIN.Sel
            If .SelectedIndex = .PointedIndex Then
                MDI.PopupMenu MDI.popSel
                BY_Containers(.ContainerID).MouseRightDown = False
            End If
        End Select
    End If
    If .MouseDownOn Then
        Select Case NOWID
        Case BY_LISTBOX_MAIN.List
            If .Item(.SelectedIndex).style = 4 Then
                Select Case .Item(.SelectedIndex).PIC
                Case 1
                    NowEditTodoIndex = .Item(.SelectedIndex).Value
                    NowEditString = TodoList(NowEditTodoIndex).Content
                    InputForm.InputType = 0
                    InputForm.Show 1
                Case 2
                    NowEditTodoIndex = .Item(.SelectedIndex).Value
                    NowEditTime = TodoList(NowEditTodoIndex).Time
                    InputForm.InputType = 1
                    InputForm.Show 1
                End Select
            End If
        End Select
    End If
End If

TipNext:
If BY_Containers(.ContainerID).MouseLock = True Then Exit Sub
If pd = False And .PreType <> 0 Then MouseEvent .ContainerID, .PreType, .PreID
End With
End Sub

Public Sub ActLISTBOX(NOWID As Long)
With BY_LISTBOXS(NOWID)
'自动调整尺寸
If .Invisible Then GoTo TipNext
If .PointedIndex <> 0 Then
    .tBackColor = -1
Else
    If .tBackColor < 0 Then .tBackColor = .tBackColor + 1
End If
Select Case NOWID
Case BY_LISTBOX_MAIN.List
    Select Case ShowMode
    Case 1
        .X = 64: .Y = 168
        .Wi = BY_Containers(.ContainerID).Wi - 64 - 26: .Hi = BY_Containers(.ContainerID).Hi - 168
        .LeftW = 600
    Case 2
        .X = 64: .Y = 256
        .Wi = BY_Containers(.ContainerID).Wi - 64 - 26: .Hi = BY_Containers(.ContainerID).Hi - 256
        .LeftW = 600
    End Select
End Select
Select Case .style
Case 1
    If .pPointedIndex <> .PointedIndex Then
        .Item(.pPointedIndex).HangTime = 0
    End If
    If .Item(.PointedIndex).Text3 <> "" Then
        If .ShowTip = False Then
            If .Item(.PointedIndex).HangTime < 10 Then
                .Item(.PointedIndex).HangTime = .Item(.PointedIndex).HangTime + 1
            Else
                .Item(.PointedIndex).HangTime = 0
                .ShowTip = True
                .TipIndex = .PointedIndex
                CreateTip .Item(.PointedIndex).Text2, .Item(.PointedIndex).Text3, .ContainerID
            End If
        End If
    End If
    'If .MouseDownOn Then
    If 1 = 1 Then
        Select Case .Item(.PointedIndex).style
        Case 7
            If PhyTouch(.X + 16, .Y + (.PointedIndex - 1) * .ItemHeight - .ppy + .ItemHeight / 2, .LeftW, .ItemHeight / 2, BY_Containers(.ContainerID).MPX, BY_Containers(.ContainerID).MPY, 1, 1) Then
                BY_BUTTONS(.GiveID.Button).X = .X + 16
                BY_BUTTONS(.GiveID.Button).Y = .Y + (.PointedIndex - 1) * .ItemHeight - .ppy + .ItemHeight / 2
                BY_BUTTONS(.GiveID.Button).Wi = .LeftW
                BY_BUTTONS(.GiveID.Button).Hi = .ItemHeight / 2
                BY_BUTTONS(.GiveID.Button).tBackColor = 0
                BY_BUTTONS(.GiveID.Button).Invisible = False

                BY_BUTTONS(.GiveID.Button).SubControl = True
                BY_BUTTONS(.GiveID.Button).SubGiveID.ListBox = NOWID
                BY_BUTTONS(.GiveID.Button).SubValue = .Item(.PointedIndex).PIC
                BY_BUTTONS(.GiveID.Button).Caption = .Item(.PointedIndex).Text

                BY_BUTTONS(.GiveID.Button).SubGiveID.index = .PointedIndex
                BY_BUTTONS(.GiveID.Button).IsReDraw = True
                BY_Containers(.ContainerID).MouseLockType = 1
                BY_Containers(.ContainerID).MouseLockID = .GiveID.Button
                BY_BUTTONS(.GiveID.Button).Hide = False
                DestroyTipWindow
            End If
        End Select
    End If
End Select

If .ShowTip Then
    If .TipIndex <> .PointedIndex Then
        .ShowTip = False
        DestroyTipWindow
    End If
End If
If BY_Containers(.ContainerID).ReDrawAll Then
    .IsReDraw = True
End If
If .IsReDraw = False Then '判断是否需要重绘
    If .pPointedIndex <> .PointedIndex Then .IsReDraw = True
    If .pSelectedIndex <> .SelectedIndex Then .IsReDraw = True
End If
TipNext:
If .Y + .Hi < 0 Then .IsReDraw = False
If .NextType <> 0 Then ActControl .ContainerID, .NextType, .NextID
End With
End Sub
