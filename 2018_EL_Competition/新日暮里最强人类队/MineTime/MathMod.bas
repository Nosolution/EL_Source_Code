Attribute VB_Name = "MathMod"
Public Declare Function GetTickCount Lib "kernel32" () As Long
Public Const Pi = 3.1415926
Public Function PhyTouch(X1 As Single, Y1 As Single, w1 As Long, H1 As Long, x2 As Single, y2 As Single, w2 As Long, H2 As Long) As Boolean
If (Y1 + H1 > y2) And (y2 + H2 > Y1) Then PhyTouch = (X1 + w1 > x2) And (x2 + w2 > X1)
End Function
Public Function GetAngle(X1 As Single, Y1 As Single, x2 As Single, y2 As Single) As Single
    If x2 = X1 Then
        If y2 > Y1 Then GetAngle = Pi / 2 Else GetAngle = Pi / 2 * 3
    Else
        If x2 > X1 And y2 >= Y1 Then
            GetAngle = Atn(Abs(y2 - Y1) / Abs(x2 - X1))
        ElseIf x2 < X1 And y2 >= Y1 Then
            GetAngle = -Atn(Abs(y2 - Y1) / Abs(x2 - X1)) + Pi
        ElseIf x2 < X1 And y2 < Y1 Then
            GetAngle = Atn(Abs(y2 - Y1) / Abs(x2 - X1)) + Pi
        Else
            GetAngle = -Atn(Abs(y2 - Y1) / Abs(x2 - X1)) + Pi * 2
        End If
    End If
End Function
Public Function NumToBoo(num As Long) As Boolean
If num = 0 Then NumToBoo = False Else NumToBoo = True
End Function
Public Function BooToNum(Boo As Boolean) As Integer
If Boo = False Then BooToNum = 0 Else BooToNum = 1
End Function
Public Function GetBracketInStr(SMXStr As String)
Dim IntStart As Long, IntEnd As Long
IntStart = InStr(SMXStr, "(")
IntEnd = InStr(SMXStr, ")")
If IntStart > 0 And IntEnd > 0 And IntEnd > IntStart Then
    GetBracketInStr = Mid(SMXStr, IntStart + 1, IntEnd - IntStart - 1)
End If
End Function
Public Function SaveStrFormat(str As String) As String
Dim i As Long, s As String
For i = 1 To Len(str)
    s = s & "%" & Asc(Mid(str, i, 1))
Next
SaveStrFormat = s
End Function
Public Function OpenStrFormat(str As String) As String
Dim s() As String, sr As String
s = Split(str, "%")
For i = 1 To UBound(s)
    If s(i) <> "" Then sr = sr & Chr(Val(s(i)))
Next
OpenStrFormat = sr
End Function

