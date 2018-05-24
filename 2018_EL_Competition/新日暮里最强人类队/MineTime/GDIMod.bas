Attribute VB_Name = "GDIMod"
Public Declare Function DrawText Lib "user32" Alias "DrawTextA" (ByVal hdc As Long, ByVal lpStr As String, ByVal nCount As Long, lpRect As rect, ByVal wFormat As Long) As Long
Public Declare Function RoundRect Lib "gdi32" (ByVal hdc As Long, ByVal X1 As Long, ByVal Y1 As Long, ByVal x2 As Long, ByVal y2 As Long, ByVal X3 As Long, ByVal Y3 As Long) As Long
Public Declare Function BitBlt Lib "gdi32" (ByVal hDestDC As Long, ByVal X As Long, ByVal Y As Long, ByVal nWidth As Long, ByVal nHeight As Long, ByVal hSrcDC As Long, ByVal xSrc As Long, ByVal ySrc As Long, ByVal dwRop As Long) As Long
Public Declare Function CreateCompatibleDC Lib "gdi32" (ByVal hdc As Long) As Long
Public Declare Function StretchBlt Lib "gdi32" (ByVal hdc As Long, ByVal X As Long, ByVal Y As Long, ByVal nWidth As Long, ByVal nHeight As Long, ByVal hSrcDC As Long, ByVal xSrc As Long, ByVal ySrc As Long, ByVal nSrcWidth As Long, ByVal nSrcHeight As Long, ByVal dwRop As Long) As Long
Public Declare Function SelectObject Lib "gdi32" (ByVal hdc As Long, ByVal hObject As Long) As Long
Public Declare Function DeleteDC Lib "gdi32" (ByVal hdc As Long) As Long
Public Declare Function SetTextColor Lib "gdi32" (ByVal hdc As Long, ByVal crColor As Long) As Long
Public Declare Function CreateFontIndirect Lib "gdi32" Alias "CreateFontIndirectA" (lpLogFont As LOGFONT) As Long
Public Declare Function DeleteObject Lib "gdi32" (ByVal hObject As Long) As Long
Public Declare Function SetStretchBltMode Lib "gdi32" (ByVal hdc As Long, ByVal nStretchMode As Long) As Long
Public Type BY_Color
R As Long
g As Long
b As Long
End Type

Public Type LOGFONT
    lfHeight As Long
    lfWidth As Long
    lfEscapement As Long
    lfOrientation As Long
    lfWeight As Long
    lfItalic As String * 1
    lfUnderline As String * 1
    lfStrikeOut As String * 1
    lfCharSet As String * 1
    lfOutPrecision As String * 1
    lfClipPrecision As String * 1
    lfQuality As String * 1
    lfPitchAndFamily As String * 1
    lfFaceName As String * 32
End Type
Public BY_WINFONTS(1) As LOGFONT
Private Const GB2312_CHARSET = 1
Public Const DT_CALCRECT = 1024
Public Const DT_CENTER = 1
Public Const DT_VCENTER = 4
Public Const DT_SINGLELINE = 32
Public Const DT_LEFT = 0
Public Const DT_WORDBREAK = &H10

Option Explicit
    
   Private Type RGB
           Red   As Byte
           Green   As Byte
           Blue   As Byte
   End Type
    
    
Public Function RGBToLong(ColorRGB As BY_Color) As Long
        '   Just   what   it   looks   like   =)
RGBToLong = RGB(ColorRGB.R, ColorRGB.g, ColorRGB.b)
End Function
    
Public Function LongToRGB(ColorVal As Long) As BY_Color
        '   And   vice-versa   =)
If ColorVal >= 0 Then
    With LongToRGB
        .R = (ColorVal And &HFF) Mod 256
        .g = ((ColorVal And &HFF00) \ &H100) Mod 256
        .b = ((ColorVal And &HFF0000) \ &H10000) Mod 256
    End With
End If
End Function

Public Function GetMeHdc(Mehdc As Long, hdc As Long) As Long '»ñÈ¡¾ä±ú
Mehdc = CreateCompatibleDC(hdc)
GetMeHdc = Mehdc
End Function

Public Sub DrawRectVB(ContainerID As Long, X As Single, Y As Single, w As Long, h As Long, Fill As Boolean, R As Long, g As Long, b As Long)
If R < 0 Then R = 0
If g < 0 Then g = 0
If b < 0 Then b = 0
Select Case ContainerID
Case 1
If Fill = False Then
    clock.Line (X, Y)-(X + w, Y + h), RGB(R, g, b), B
Else
    clock.Line (X, Y)-(X + w, Y + h), RGB(R, g, b), BF
End If
Case 2
If Fill = False Then
    InputForm.Line (X, Y)-(X + w, Y + h), RGB(R, g, b), B
Else
    InputForm.Line (X, Y)-(X + w, Y + h), RGB(R, g, b), BF
End If
End Select
End Sub

Public Sub DrawRectVB2(ContainerID As Long, X As Single, Y As Single, w As Long, h As Long, Fill As Boolean, color As Long)
If color > RGB(255, 255, 255) Then color = RGB(255, 255, 255)
If color < 0 Then color = 0
Select Case ContainerID
Case 1
If Fill = False Then
    clock.Line (X, Y)-(X + w, Y + h), color, B
Else
    clock.Line (X, Y)-(X + w, Y + h), color, BF
End If
Case 2
If Fill = False Then
    InputForm.Line (X, Y)-(X + w, Y + h), color, B
Else
    InputForm.Line (X, Y)-(X + w, Y + h), color, BF
End If
End Select
End Sub

Public Sub DrawLineVB(ContainerID As Long, X As Single, Y As Single, x2 As Single, y2 As Single, R As Long, g As Long, b As Long)
Select Case ContainerID
Case 1: clock.Line (X, Y)-(x2, y2), RGB(R, g, b)
Case 2: InputForm.Line (X, Y)-(x2, y2), RGB(R, g, b)
End Select
End Sub

Public Sub DrawTextGDI(ContainerID As Long, Text As String, X As Single, Y As Single, w As Long, h As Long, format As Long, size As Long, Weight As Long, color As Long)
Dim RectCut As rect
RectCut.left = X
RectCut.right = X + w
RectCut.Top = Y
RectCut.Bottom = Y + h
If ContainerID = 5 Then
    ContainerID = 5
End If
If size <> BY_Containers(ContainerID).BY_FSize Or Weight <> BY_Containers(ContainerID).BY_FWeight Then
    iniBY_FONT ContainerID, size, Weight
End If
If color <> BY_Containers(ContainerID).BY_FColor Then
    SetTextColor BY_Containers(ContainerID).hdc, color
    BY_Containers(ContainerID).BY_FColor = color
End If

DrawText BY_Containers(ContainerID).hdc, Text, -1, RectCut, format
'DT_CENTER Or DT_VCENTER Or DT_SINGLELINE
End Sub

Public Sub GetTextRect(hdc As Long, str As String, mRect As rect)
DrawText hdc, str, -1, mRect, DT_CALCRECT
End Sub

Public Sub iniBY_FONT(ContainerID As Long, size As Long, Weight As Long)
BY_Containers(ContainerID).BY_FSize = size
BY_Containers(ContainerID).BY_FWeight = Weight

Dim hFont As Long, ret As Long
BY_WINFONTS(0).lfCharSet = GB2312_CHARSET
BY_WINFONTS(0).lfEscapement = 0 '900    ' 180-degree rotation
BY_WINFONTS(0).lfFaceName = "Î¢ÈíÑÅºÚ" + Chr$(0)
BY_WINFONTS(0).lfWeight = Weight
Dim FontSize As Single
' Windows expects the font size to be in pixels and to be negative if you are specifying the character height you want.
FontSize = size / 3 * 2
BY_WINFONTS(0).lfHeight = (FontSize * -20) / dpi_y
hFont = CreateFontIndirect(BY_WINFONTS(0))
SelectObject BY_Containers(ContainerID).hdc, hFont

' Clean up by restoring original font.
ret = DeleteObject(hFont)
End Sub
