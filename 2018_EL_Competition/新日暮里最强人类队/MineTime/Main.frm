VERSION 5.00
Begin VB.Form Clock 
   AutoRedraw      =   -1  'True
   BackColor       =   &H80000008&
   BorderStyle     =   0  'None
   Caption         =   "MineTime"
   ClientHeight    =   6010
   ClientLeft      =   0
   ClientTop       =   0
   ClientWidth     =   5740
   Icon            =   "Main.frx":0000
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   Picture         =   "Main.frx":324A
   ScaleHeight     =   601
   ScaleMode       =   3  'Pixel
   ScaleWidth      =   574
   ShowInTaskbar   =   0   'False
   StartUpPosition =   2  '屏幕中心
   Begin VB.Timer tmrMove 
      Enabled         =   0   'False
      Interval        =   1
      Left            =   5280
      Top             =   5280
   End
   Begin VB.Timer tmrCheck 
      Enabled         =   0   'False
      Interval        =   50
      Left            =   4680
      Top             =   5280
   End
   Begin VB.Timer Timer1 
      Interval        =   10
      Left            =   120
      Top             =   240
   End
End
Attribute VB_Name = "Clock"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Const WM_NCLBUTTONDOWN = &HA1
Private Const WM_EXITSIZEMOVE = &H232
Private Const WM_MOVING = &H216
Private Type rect
        left As Long
        Top As Long
        right As Long
        Bottom As Long
End Type
Private Type PointAPI
        X As Long
        Y As Long
End Type
Private Declare Sub CopyMemory Lib "kernel32" Alias "RtlMoveMemory" (Destination As Any, Source As Any, ByVal Length As Long)
Private Declare Function PtInRect Lib "user32" (lpRect As rect, ByVal X As Long, ByVal Y As Long) As Long
Private Declare Function GetCursorPos Lib "user32" (lpPoint As PointAPI) As Long
Private Declare Function GetWindowRect Lib "user32" (ByVal hwnd As Long, lpRect As rect) As Long

Private Const SIZE_SHOW         As Long = 60    '隐藏后留出来的宽度或高度,单位缇
Private Const SHOWHIDE_SPEED    As Long = 30    '(自动显示隐藏速度，单位缇)
'显示标识
'0  自动隐藏
'1  自动显示
Private m_ShowFlag              As Long
'显示方向
'0  向左
'1  向右
'2  向上
Private m_ShowOrient            As Long
'显示速度
Private m_ShowSpeed             As Long
'是否已经启动自动隐藏(为了防止WM_MOVING调整窗口位置)
Private m_MoveEnabled           As Boolean

'//下面是把窗口移动Top=0且Left=0或Right=Screen.Width的时候让窗口高度=屏幕高度
'是否自动调整了大小
Private m_AutoSize              As Boolean
Private m_OldHeight             As Long




Dim ismousedown As Boolean
Dim isresize As Boolean
Dim resizetype As Long
Dim X0!, Y0!, X1!, Y1!, w1!, H1!

Private Sub Command1_Click()
SaveData
SaveTodoList

End Sub

Private Sub tmrCheck_Timer()
    Dim pt As PointAPI
    Dim rc As rect
    Call GetCursorPos(pt)
    Call GetWindowRect(Me.hwnd, rc)
    If PtInRect(rc, pt.X, pt.Y) Then
        '鼠标停留在窗口上
        If m_ShowFlag = 1 Then Exit Sub
        m_ShowSpeed = SHOWHIDE_SPEED
        m_ShowFlag = 1
        tmrMove.Enabled = True
    Else
        '鼠标不再窗口上
        If m_ShowFlag = 0 Then Exit Sub
        m_ShowSpeed = SHOWHIDE_SPEED
        m_ShowFlag = 0
        tmrMove.Enabled = True
    End If
End Sub

Private Sub tmrMove_Timer()
    If Me.WindowState <> 0 Then Exit Sub
    Dim nTop    As Long
    Dim nLeft   As Long
    m_ShowSpeed = m_ShowSpeed + SHOWHIDE_SPEED
    '如果大于300T则加快速度
    If m_ShowSpeed > 300 Then m_ShowSpeed = m_ShowSpeed + m_ShowSpeed * 0.2
    Select Case m_ShowOrient
        Case 0  '0  向左
            If m_ShowFlag = 0 Then
                nLeft = Me.left - m_ShowSpeed
                If nLeft < -Me.Width + SIZE_SHOW Then nLeft = -Me.Width + SIZE_SHOW: tmrMove.Enabled = False
            Else
                nLeft = Me.left + m_ShowSpeed
                If nLeft > -SIZE_SHOW Then nLeft = -SIZE_SHOW: tmrMove.Enabled = False
            End If
            Me.left = nLeft
            
        Case 1  '1  向右
            If m_ShowFlag = 0 Then
                nLeft = Me.left + m_ShowSpeed
                If nLeft > Screen.Width - SIZE_SHOW Then nLeft = Screen.Width - SIZE_SHOW: tmrMove.Enabled = False
            Else
                nLeft = Me.left - m_ShowSpeed
                If nLeft < Screen.Width - Me.Width + SIZE_SHOW Then nLeft = Screen.Width - Me.Width + SIZE_SHOW: tmrMove.Enabled = False
            End If
            Me.left = nLeft
            
        Case 2  '2  向上
            If m_ShowFlag = 0 Then
                nTop = Me.Top - m_ShowSpeed
                If nTop < -Me.Height + SIZE_SHOW Then nTop = -Me.Height + SIZE_SHOW: tmrMove.Enabled = False
            Else
                nTop = Me.Top + m_ShowSpeed
                If nTop > -SIZE_SHOW Then nTop = -SIZE_SHOW: tmrMove.Enabled = False
            End If
            Me.Top = nTop
            
    End Select
End Sub


Private Sub Form_Load()
Call CreatTray(Me, "MineTime", "MineTime已启动", "开始专注于您的任务吧", 4)
SetWindowPos Me.hwnd, -1, 0, 0, 0, 0, 3
Randomize
ClockData.mode = 0
ClockData.TimeLen = 25
ClockData.InvLongRest = 4
ClockData.RestTime = 5
ClockData.LongRestTime = 15

ismousedown = False
IniSDL
iniMusicInfos
OpenData
OpenTodoList
Me.Cls
Me.BackColor = RGB(0, 0, 10)
GetTwipsAPixiv dpi_x, dpi_y


Me.Height = 860 * dpi_y
Me.Width = 740 * dpi_x
Me.Picture = LoadPicture(App.path & "\Data\Skin\Bk\" & Int(Rnd * 10 + 1) & ".jpg")
bkColor = Me.POINT(4, 4)

Dim ContainerID As Long
ContainerID = 1
BY_Containers(ContainerID).Disabled = False

BY_Containers(ContainerID).Wi = Me.Width
BY_Containers(ContainerID).Hi = Me.Height
BY_Containers(ContainerID).MouseLock = False

BY_Containers(ContainerID).Resizing = True
BY_Containers(ContainerID).BY_FSize = 0
BY_Containers(ContainerID).BY_FWeight = 0
BY_Containers(ContainerID).BY_FColor = 0

BY_Containers(ContainerID).ReDrawAll = True

iniBY_Container 1
iniBY_Container 2

BY_BUTTONS(BY_BUTTON_MAIN.List).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.music).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.timer).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.setting).Invisible = True

BY_BUTTONS(BY_BUTTON_MAIN.Work).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.Giveup).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.PreMusic).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.NextMusic).Invisible = True
BY_BUTTONS(BY_BUTTON_MAIN.LoopMusic).Invisible = True

ShowMode = 0
End Sub

Private Sub Form_MouseDown(Button As Integer, Shift As Integer, X As Single, Y As Single)
If BY_Containers(1).MPY > 0 And BY_Containers(1).MPY < 48 Then
    If BY_Containers(1).MPX > 42 And BY_Containers(1).MPX < (BY_Containers(1).Wi) - 140 Then
        ismousedown = True
        BY_Containers(1).MouseLock = True
    End If
Else
    'If BY_Containers(1).MPX <= 12 And BY_Containers(1).MPY <= 12 Then
    '    isresize = True: resizetype = 1
    'ElseIf BY_Containers(1).MPX >= BY_Containers(1).Wi - 12 And BY_Containers(1).MPY >= BY_Containers(1).Hi - 12 Then
    '    isresize = True: resizetype = 8
    'End If
End If
X0 = GPX
Y0 = GPY
X1 = Me.left / dpi_x: Y1 = Me.Top / dpi_y: w1 = Me.Width / dpi_x: H1 = Me.Height / dpi_y
BY_Containers(1).MouseDown = True
'tmrCheck.Enabled = False
tmrMove.Enabled = False
End Sub

Private Sub Form_MouseMove(Button As Integer, Shift As Integer, X As Single, Y As Single)
If ismousedown = True And Me.WindowState = 0 Then
    Me.left = (X1 + GPX - X0) * dpi_x
    Me.Top = (Y1 + GPY - Y0) * dpi_y
    tmrCheck.Enabled = False
    m_MoveEnabled = False
    Dim rcWnd   As rect
    Call GetWindowRect(Me.hwnd, rcWnd)
    If rcWnd.left <= 0 Or rcWnd.Top <= 0 Or _
        rcWnd.right >= Screen.Width / Screen.TwipsPerPixelX Then
        '如果窗口停靠在屏幕边缘
        '让检查鼠标位置的Timer工作
        
        '设置显示方向
        If rcWnd.left < 0 Then
            m_ShowOrient = 0
        ElseIf rcWnd.right >= Screen.Width / Screen.TwipsPerPixelX Then
            m_ShowOrient = 1
        ElseIf rcWnd.Top < 0 Then
            m_ShowOrient = 2
        End If
        tmrCheck.Enabled = True
    End If
    
    
ElseIf isresize = True Then
    Select Case resizetype
    Case 1
        Me.left = (X1 + (GPX - X0)) * dpi_x
        Me.Top = (Y1 + (GPY - Y0)) * dpi_y
        Me.Width = (w1 - (GPX - X0)) * dpi_x
        Me.Height = (H1 - (GPY - Y0)) * dpi_y
        Me.MousePointer = vbSizeNWSE
    Case 8
        Me.Width = (w1 + (GPX - X0)) * dpi_x
        Me.Height = (H1 + (GPY - Y0)) * dpi_y
        Me.MousePointer = vbSizeNWSE
    End Select
End If
End Sub

Private Sub Form_MouseUp(Button As Integer, Shift As Integer, X As Single, Y As Single)
ismousedown = False
isresize = False
resizetype = 0
Me.MousePointer = vbDefault
BY_Containers(1).MouseDown = False
BY_Containers(1).MouseLock = False
End Sub

Private Sub Form_Resize()
With Me
    If Status = STA_NORMAL And .WindowState = vbMinimized And .Visible = True Then
        .Visible = False '最小化的时候，隐藏到托盘
        Status = STA_MIN
        Else
        Status = STA_NORMAL
    End If
End With
If (Me.WindowState = 0) Then
    If (Me.Width < 420 * dpi_x) Then     '限制最小宽度
        Me.Enabled = False
        Me.Width = 420 * dpi_x
        Me.Enabled = True
        isresize = False
        resizetype = 0
    End If
    If (Me.Height < 460 * dpi_y) Then    '限制最小高度
        Me.Enabled = False
        Me.Height = 460 * dpi_y
        Me.Enabled = True
        isresize = False
        resizetype = 0
    End If
End If
BY_Containers(1).Resizing = True
End Sub

Private Sub Form_Unload(Cancel As Integer)
SaveMusicInfos
SaveData
SaveTodoList
UnloadSDL
Call UnloadTray
End Sub

Private Sub Timer1_Timer()
Static pd As Boolean
'DrawTip
If pd = False Then
    ClockData.StartMin = 30
    ClockData.StartSecond = 0
    ClockData.min = ClockData.StartMin
    ClockData.Second = ClockData.StartSecond
    pd = True
End If
BY_Containers(1).ReDrawAll = True
AnimationControl
GetContainerInfo 1
Act 1
MusicChecking
End Sub
