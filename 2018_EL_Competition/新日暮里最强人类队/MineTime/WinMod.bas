Attribute VB_Name = "WinMod"

Public Declare Function GetDC Lib "user32.dll" (ByVal hwnd As Long) As Long
Public Declare Function GetDeviceCaps Lib "gdi32.dll" (ByVal hdc As Long, ByVal nIndex As Long) As Long
Public Const LOGPIXELSX As Long = 88
Public Const LOGPIXELSY As Long = 90
Public Declare Function GetSystemMetrics Lib "user32" (ByVal nIndex As Long) As Long '获取系统信息函数
Public Declare Function AdjustWindowRect Lib "user32" (lpRect As rect, ByVal dwStyle As Long, ByVal bMenu As Long) As Long
Public Declare Function GetWindowLong Lib "user32" Alias "GetWindowLongA" (ByVal hwnd As Long, ByVal nIndex As Long) As Long
Public Declare Function GetCursorPos Lib "user32" (lpPoint As PointAPI) As Long
Public Declare Function ScreenToClient Lib "user32" (ByVal hwnd As Long, lpPoint As PointAPI) As Long
Public Declare Function MoveWindow Lib "user32" (ByVal hwnd As Long, ByVal X As Long, ByVal Y As Long, ByVal nWidth As Long, ByVal nHeight As Long, ByVal bRepaint As Long) As Long
Public Declare Function CreateWindowEx Lib "user32" Alias "CreateWindowExA" (ByVal dwExStyle As Long, ByVal lpClassName As String, ByVal lpWindowName As String, ByVal dwStyle As Long, ByVal X As Long, ByVal Y As Long, ByVal nWidth As Long, ByVal nHeight As Long, ByVal hWndParent As Long, ByVal hMenu As Long, ByVal hInstance As Long, lpParam As Any) As Long
Public Declare Function ShowWindow Lib "user32" (ByVal hwnd As Long, ByVal nCmdShow As Long) As Long
Public Declare Function DestroyWindow Lib "user32" (ByVal hwnd As Long) As Long
Public Declare Function DeleteDC Lib "gdi32" (ByVal hdc As Long) As Long
Public Declare Function SetWindowLong Lib "user32" Alias "SetWindowLongA" (ByVal hwnd As Long, ByVal nIndex As Long, ByVal dwNewLong As Long) As Long
Public Declare Function SetWindowPos& Lib "user32" (ByVal hwnd As Long, ByVal hWndInsertAfter As Long, ByVal X As Long, ByVal Y As Long, ByVal cx As Long, ByVal cy As Long, ByVal wFlags As Long)
Public Declare Function GetSystemMenu Lib "user32" (ByVal hwnd As Long, ByVal bRevert As Long) As Long
Public Declare Function RemoveMenu Lib "user32" (ByVal hMenu As Long, ByVal nPosition As Long, ByVal wFlags As Long) As Long

Public Declare Function GetLogicalDriveStrings Lib "kernel32" Alias "GetLogicalDriveStringsA" (ByVal nBufferLength As Long, ByVal lpBuffer As String) As Long

Public Const MF_REMOVE = &H1000
Public Const SC_CLOSE = &HF060

Public Type CREATESTRUCT
lpCreateParams As Long
hInstance As Long
hMenu As Long
hWndParent As Long
cy As Long
cx As Long
Y As Long
X As Long
style As Long
lpszName As String
lpszClass As String
ExStyle As Long
End Type
Const WS_EX_STATICEDGE = &H20000
Const WS_EX_TRANSPARENT = &H20&
Const WS_CHILD = &H40000000
Const CW_USEDEFAULT = &H80000000
Const SW_NORMAL = 1
Const WS_POPUP = &H80000000
Const SW_SHOWNA = 8
Const GWL_STYLE = (-16)
Const HWND_TOPMOST = -1
Const HWND_NOTOPMOST = -2
Const SWP_NOSIZE = &H1
Const SWP_NOMOVE = &H2
Const SWP_NOACTIVATE = &H10
Const SWP_SHOWWINDOW = &H40
Const WS_EX_NOACTIVATE = &H8000000
Const GWL_EXSTYLE = (-20)

Public dpi_x As Long, dpi_y As Long
Public Type rect
left As Long
Top As Long
right As Long
Bottom As Long
End Type
Public Type PointAPI
X As Long
Y As Long
End Type
Public MPX As Single, MPY As Single, GPX As Single, GPY As Single
Public Type TipInfos
Wi As Long
Hi As Long
Caption As String
str As String
Show As Boolean
mWnd As Long
hdc As Long
drawh As Long
ClueWin As Long
Draw As Boolean
End Type
Public TipInfo As TipInfos
Public ReturnFunForm As Long
Private Const CapSize = 16
Private Const ContentSize = 14


Public Function GetDPI() As Long
Dim hdc0 As Long
hdc0 = GetDC(0)
GetDPI = GetDeviceCaps(hdc0, LOGPIXELSX)
End Function

Public Sub GetTwipsAPixiv(dpi_x As Long, dpi_y As Long)
'获取一个像素等于多少缇
'Dim hdc0 As Long
'hdc0 = GetDC(0)
'dpi_x = GetDeviceCaps(hdc0, LOGPIXELSX)
'dpi_y = GetDeviceCaps(hdc0, LOGPIXELSY)
'dpi_x = 1440 / dpi_x
'dpi_y = 1440 / dpi_y
dpi_x = Screen.TwipsPerPixelX
dpi_y = Screen.TwipsPerPixelY
End Sub

Public Function Getit() As Long
GetSystemMetrics (SM_CYCAPTION)
End Function

Public Sub GetMousePoint(hwnd As Long, MPX As Single, MPY As Single)
Dim POINT As PointAPI
Call GetCursorPos(POINT)
GPX = POINT.X
GPY = POINT.Y
Call ScreenToClient(hwnd, POINT)
MPX = POINT.X
MPY = POINT.Y
End Sub

Public Sub CreateTip(Caption As String, str As String, ClueWin As Long)
'初始化Tip控件
If TipInfo.Show Then DestroyTipWindow
TipInfo.ClueWin = ClueWin
Dim CS As CREATESTRUCT
TipInfo.mWnd = CreateWindowEx(&H80, "STATIC", " ", WS_POPUP, 0, 0, 300, 50, 0, 0, App.hInstance, CS)
TipInfo.hdc = GetDC(TipInfo.mWnd)
Dim TipRectCaption As rect, TipRectStr As rect
iniTipFONT TipInfo.hdc, CapSize, 600
GetTextRect TipInfo.hdc, Caption, TipRectCaption
iniTipFONT TipInfo.hdc, ContentSize, 1
GetTextRect TipInfo.hdc, str, TipRectStr
Dim Wi As Long, Hi As Long
If TipRectCaption.right > TipRectStr.right Then TipInfo.Wi = TipRectCaption.right + 32 Else TipInfo.Wi = TipRectStr.right + 32
TipInfo.Hi = TipRectCaption.Bottom + TipRectStr.Bottom + 4
TipInfo.drawh = TipRectCaption.Bottom
ShowWindow TipInfo.mWnd, SW_SHOWNA
Dim X As Long, Y As Long
X = GPX - TipInfo.Wi / 2
If X + TipInfo.Wi + 16 > Screen.Width / dpi_x Then X = Screen.Width / dpi_x - TipInfo.Wi - 16
If X < 0 Then X = 0
MoveWindow TipInfo.mWnd, X, GPY - TipInfo.Hi - 32, TipInfo.Wi, TipInfo.Hi, 0
TipInfo.Caption = Caption
TipInfo.str = str
TipInfo.Show = True
End Sub

Public Sub DrawTip()
If TipInfo.Show Then
    With TipInfo
    If .Draw = False Then
        RoundRect .hdc, 0, 0, .Wi, .Hi, 0, 0
        Dim RectCut As rect
        RectCut.left = 16
        RectCut.Top = 4
        RectCut.right = .Wi - 1
        RectCut.Bottom = .drawh - 1
        iniTipFONT TipInfo.hdc, CapSize, 600
        DrawText .hdc, .Caption, -1, RectCut, DT_LEFT
        RectCut.left = 16
        RectCut.right = .Wi - 1
        RectCut.Top = .drawh + 1
        RectCut.Bottom = .Hi - 1
        iniTipFONT TipInfo.hdc, ContentSize, 1
        DrawText .hdc, .str, -1, RectCut, DT_LEFT
        'RoundRect TipInfo.Hdc, 1, 1, 66, 66, 0, 0
        .Draw = True
    End If
    End With
End If
End Sub

Public Sub DestroyTipWindow()
DestroyWindow TipInfo.mWnd
DeleteDC TipInfo.hdc
Dim b As TipInfos
TipInfo = b
End Sub

Public Sub iniTipFONT(hdc As Long, size As Long, Weight As Long)
Dim hFont As Long, ret As Long
BY_WINFONTS(0).lfCharSet = GB2312_CHARSET
BY_WINFONTS(0).lfEscapement = 0 '900    ' 180-degree rotation
BY_WINFONTS(0).lfFaceName = "微软雅黑" + Chr$(0)
BY_WINFONTS(0).lfWeight = Weight

' Windows expects the font size to be in pixels and to be negative if you are specifying the character height you want.
FontSize = size / 3 * 2
BY_WINFONTS(0).lfHeight = (FontSize * -20) / dpi_y
hFont = CreateFontIndirect(BY_WINFONTS(0))
SelectObject hdc, hFont
' Clean up by restoring original font.
ret = DeleteObject(hFont)
End Sub

Public Function DisabledX(cHwnd As Long)
Dim hMenu, hendMenu As Long
Dim c As Long
hMenu = GetSystemMenu(cHwnd, 0)
RemoveMenu hMenu, SC_CLOSE, MF_REMOVE
End Function

Public Function getDirectory(filepath As String) As String
getDirectory = Mid(filepath, 1, InStrRev(filepath, "\"))
End Function

Public Function getFileName(filepath As String) As String
getFileName = Replace$(filepath, getDirectory(filepath), "")
End Function

Public Function getPureFileName(filename As String) As String
getPureFileName = Mid$(filename, 1, InStrRev(filename, ".") - 1)
End Function

