VERSION 5.00
Begin VB.Form InputForm 
   AutoRedraw      =   -1  'True
   BackColor       =   &H80000002&
   BorderStyle     =   0  'None
   Caption         =   "Form1"
   ClientHeight    =   3650
   ClientLeft      =   0
   ClientTop       =   0
   ClientWidth     =   4170
   BeginProperty Font 
      Name            =   "Tahoma"
      Size            =   8
      Charset         =   0
      Weight          =   400
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   LinkTopic       =   "Form1"
   ScaleHeight     =   365
   ScaleMode       =   3  'Pixel
   ScaleWidth      =   417
   ShowInTaskbar   =   0   'False
   StartUpPosition =   1  '所有者中心
   Begin VB.PictureBox Picture1 
      Height          =   850
      Left            =   -99990
      ScaleHeight     =   810
      ScaleWidth      =   1290
      TabIndex        =   1
      TabStop         =   0   'False
      Top             =   -10000
      Width           =   1330
   End
   Begin VB.TextBox TextBox 
      BorderStyle     =   0  'None
      BeginProperty Font 
         Name            =   "微软雅黑"
         Size            =   12
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   370
      Left            =   240
      TabIndex        =   0
      Text            =   "Text1"
      Top             =   600
      Visible         =   0   'False
      Width           =   3730
   End
   Begin VB.Timer Timer1 
      Interval        =   10
      Left            =   120
      Top             =   120
   End
End
Attribute VB_Name = "InputForm"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Public Cap As String
Public InputType As Long
Dim ismousedown As Boolean
Dim X0!, Y0!, X1!, Y1!
Private Sub Form_Click()
Picture1.SetFocus
End Sub

Private Sub Form_Load()
SetWindowPos Me.hwnd, -1, 0, 0, 0, 0, 3
Me.BackColor = bkColor
ismousedown = False
BY_Containers(1).Disabled = False
BY_TEXTBOXS(BY_TEXTBOX_MAIN.Plan).Invisible = True
BY_TEXTBOXS(BY_TEXTBOX_MAIN.year).Invisible = True
BY_TEXTBOXS(BY_TEXTBOX_MAIN.month).Invisible = True
BY_TEXTBOXS(BY_TEXTBOX_MAIN.day).Invisible = True
BY_TEXTBOXS(BY_TEXTBOX_MAIN.hour).Invisible = True
BY_TEXTBOXS(BY_TEXTBOX_MAIN.min).Invisible = True
BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.InputADirMusic).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.InputOneMusic).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.InputNo).Invisible = False
BY_BUTTONS(BY_BUTTON_MAIN.InputYes).Invisible = False
Select Case InputType
Case 0
    Cap = "请写下您需要完成的任务"
    Me.Height = 410 * dpi_y
    Me.Width = 480 * dpi_x
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.Plan).Text = NowEditString
    TextBox = NowEditString
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.Plan).Invisible = False
Case 1
    Cap = "请设定当前任务的截止时间"
    Me.Height = 460 * dpi_y
    Me.Width = 480 * dpi_x
    If TodoList(NowEditTodoIndex).Enabled Then
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.year).Text = CStr(NowEditTime.year)
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.month).Text = CStr(NowEditTime.month)
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.day).Text = CStr(NowEditTime.day)
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.hour).Text = CStr(NowEditTime.hour)
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.min).Text = CStr(NowEditTime.min)
    Else
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.year).Text = CStr(year(Now))
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.month).Text = CStr(month(Now))
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.day).Text = CStr(day(Now))
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.hour).Text = CStr(hour(Now))
        BY_TEXTBOXS(BY_TEXTBOX_MAIN.min).Text = CStr(Minute(Now))
    End If
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.year).Invisible = False
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.month).Invisible = False
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.day).Invisible = False
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.hour).Invisible = False
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.min).Invisible = False
Case 2
    Cap = "确认删除当前的任务吗"
    Me.Height = 200 * dpi_y
    Me.Width = 480 * dpi_x
Case 3
    Cap = "从本地添加音乐"
    Me.Height = 460 * dpi_y
    Me.Width = 480 * dpi_x
    BY_BUTTONS(BY_BUTTON_MAIN.InputADirMusic).Invisible = False
    BY_BUTTONS(BY_BUTTON_MAIN.InputOneMusic).Invisible = False
    BY_BUTTONS(BY_BUTTON_MAIN.InputNo).Invisible = False
    BY_BUTTONS(BY_BUTTON_MAIN.InputYes).Invisible = True
Case 4
    Cap = "确认删除选中的音乐吗"
    Me.Height = 200 * dpi_y
    Me.Width = 480 * dpi_x
Case 5
    Cap = "请设定计时时长"
    Me.Height = 260 * dpi_y
    Me.Width = 480 * dpi_x
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Invisible = False
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Text = CStr(ClockData.TimeLen)
Case 6
    Cap = "请设定休息时长"
    Me.Height = 260 * dpi_y
    Me.Width = 480 * dpi_x
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Invisible = False
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Text = CStr(ClockData.RestTime)
Case 7
    Cap = "请设定多少次专注后进行一次长休息"
    Me.Height = 260 * dpi_y
    Me.Width = 480 * dpi_x
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Invisible = False
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Text = CStr(ClockData.InvLongRest)
Case 8
    Cap = "请设定长休息时长"
    Me.Height = 260 * dpi_y
    Me.Width = 480 * dpi_x
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Invisible = False
    BY_TEXTBOXS(BY_TEXTBOX_MAIN.test).Text = CStr(ClockData.LongRestTime)
End Select


Me.Cls
TextBox.BackColor = bkColor + RGB(52, 52, 52)
TextBox.ForeColor = vbWhite

Dim ContainerID As Long
ContainerID = 2
BY_Containers(ContainerID).Disabled = False

BY_Containers(ContainerID).Wi = Me.Width
BY_Containers(ContainerID).Hi = Me.Height
BY_Containers(ContainerID).MouseLock = False

BY_Containers(ContainerID).Resizing = True
BY_Containers(ContainerID).BY_FSize = 0
BY_Containers(ContainerID).BY_FWeight = 0
BY_Containers(ContainerID).BY_FColor = 0

BY_Containers(ContainerID).ReDrawAll = True
End Sub

Private Sub Form_MouseDown(Button As Integer, Shift As Integer, X As Single, Y As Single)
If BY_Containers(2).MPY > 0 And BY_Containers(2).MPY < 32 Then
    ismousedown = True
    BY_Containers(2).MouseLock = True
End If
X0 = GPX
Y0 = GPY
X1 = Me.left / dpi_x: Y1 = Me.Top / dpi_y: w1 = Me.Width / dpi_x: H1 = Me.Height / dpi_y
BY_Containers(2).MouseDown = True
End Sub

Private Sub Form_MouseMove(Button As Integer, Shift As Integer, X As Single, Y As Single)
If ismousedown = True And Me.WindowState = 0 Then
    Me.left = (X1 + GPX - X0) * dpi_x
    Me.Top = (Y1 + GPY - Y0) * dpi_y
End If
End Sub

Private Sub Form_MouseUp(Button As Integer, Shift As Integer, X As Single, Y As Single)
ismousedown = False
BY_Containers(2).MouseDown = False
BY_Containers(2).MouseLock = False
End Sub

Private Sub Form_Unload(Cancel As Integer)
BY_Containers(2).Disabled = True
End Sub

Private Sub TextBox_LostFocus()
SendString
End Sub

Public Sub SendString()
BY_TEXTBOXS(BY_NOWTEXTBOXINDEX).Text = TextBox.Text
If BY_TEXTBOXS(BY_NOWTEXTBOXINDEX).style = 1 Then
    BY_TEXTBOXS(BY_NOWTEXTBOXINDEX).Text = Val(BY_TEXTBOXS(BY_NOWTEXTBOXINDEX).Text)
End If
TextBox.Visible = False
BY_TEXTBOXS(BY_NOWTEXTBOXINDEX).IsReDraw = True
End Sub

Private Sub Timer1_Timer()
BY_Containers(2).ReDrawAll = True
GetContainerInfo 2
Act 2
End Sub
