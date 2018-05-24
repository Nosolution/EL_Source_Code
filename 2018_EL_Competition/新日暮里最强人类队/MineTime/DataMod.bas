Attribute VB_Name = "DataMod"
Public Type DataType
time As Long
min As Long
Second As Long
StartMin As Long
StartSecond As Long
Run As Boolean
mode As Long '0 ���� 1 ��������
TimeLen As Long
RestTime As Long
InvLongRest As Long
LongRestTime As Long
StartSysTime As Long
LoopMusic As Boolean
End Type

Public Type DateType
Y As Long
M As Long
D As Long
HH As Long
MM As Long
SS As Long
RealSecondTime As Long
End Type

Public ClockData As DataType
Public RestTimes As Long
Public OnLongRest As Boolean
Public RndWordIndex As Long, RndWord As String

Public Function getWeekDayNow() As String
Dim i As Long
i = Weekday(Now)
Select Case i
Case 1
    getWeekDayNow = "������"
Case 2
    getWeekDayNow = "����һ"
Case 3
    getWeekDayNow = "���ڶ�"
Case 4
    getWeekDayNow = "������"
Case 5
    getWeekDayNow = "������"
Case 6
    getWeekDayNow = "������"
Case 7
    getWeekDayNow = "������"
End Select
End Function

Public Function getDateFromNow(Y As Long, M As Long, D As Long, HH As Long, MM As Long, SS As Long) As DateType
Dim endtime As Date
Dim nowtime As Date
Dim difftime As Long
endtime = CDate(CStr(Y) & "-" & CStr(M) & "-" & CStr(D) & " " & CStr(HH) & ":" & CStr(MM) & ":" & CStr(SS))
nowtime = Now
difftime = DateDiff("s", nowtime, endtime)
Dim S As Long
S = difftime
getDateFromNow.RealSecondTime = S
getDateFromNow.D = S \ 86400
If getDateFromNow.D = 0 Then
    getDateFromNow.HH = S \ 3600
Else
    getDateFromNow.HH = (S Mod getDateFromNow.D * 86400) \ 3600
End If
getDateFromNow.MM = (S Mod 3600) \ 60
getDateFromNow.SS = S Mod 60
End Function

Public Sub StartClock()
Select Case ClockData.mode
Case 0
RunMode = 1
ClockData.StartMin = ClockData.TimeLen
ClockData.Run = True
ClockData.StartSysTime = GetTickCount
Case 1
RunMode = 3
ClockData.StartMin = ClockData.TimeLen
ClockData.Run = True
ClockData.StartSysTime = GetTickCount
End Select
End Sub

Public Sub StartRest()
RunMode = 2
RestTimes = RestTimes + 1
OnLongRest = False
If RestTimes <> 0 And RestTimes Mod ClockData.InvLongRest = 0 Then
    OnLongRest = True
    ClockData.StartMin = ClockData.LongRestTime
Else
    ClockData.StartMin = ClockData.RestTime
    RndWordIndex = Int(Rnd() * 3 + 1)
End If
ClockData.Run = True
ClockData.StartSysTime = GetTickCount
End Sub
