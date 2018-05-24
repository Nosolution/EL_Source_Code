Attribute VB_Name = "SLMod"
Public Sub OpenData()
Open App.path & "\Data\Info.dat" For Input As #1
s = Split(StrConv(InputB(LOF(1), 1), vbUnicode), vbCrLf)
Close #1
ClockData.mode = Val(s(0))
ClockData.TimeLen = Val(s(1))
ClockData.RestTime = Val(s(2))
ClockData.InvLongRest = Val(s(3))
ClockData.LongRestTime = Val(s(4))
ClockData.LoopMusic = NumToBoo(Val(s(5)))
End Sub

Public Sub SaveData()
Open App.path & "\Data\Info.dat" For Output As #1
Print #1, ClockData.mode
Print #1, ClockData.TimeLen
Print #1, ClockData.RestTime
Print #1, ClockData.InvLongRest
Print #1, ClockData.LongRestTime
Print #1, BooToNum(ClockData.LoopMusic)
Close #1
End Sub

Public Sub OpenTodoList()
Open App.path & "\Data\Todo.dat" For Input As #1
s = Split(StrConv(InputB(LOF(1), 1), vbUnicode), vbCrLf)
Close #1
ReDim TodoList(Val(s(0)))
Dim i As Long
For i = 1 To UBound(TodoList)
    TodoList(i).Content = s((i - 1) * 6 + 1)
    TodoList(i).time.year = Val(s((i - 1) * 6 + 1 + 1))
    TodoList(i).time.month = Val(s((i - 1) * 6 + 2 + 1))
    TodoList(i).time.day = Val(s((i - 1) * 6 + 3 + 1))
    TodoList(i).time.hour = Val(s((i - 1) * 6 + 4 + 1))
    TodoList(i).time.min = Val(s((i - 1) * 6 + 5 + 1))
    TodoList(i).Enabled = True
Next
End Sub

Public Sub SaveTodoList()
Dim i As Long
Open App.path & "\Data\Todo.dat" For Output As #1
Dim FF As Long
For i = 1 To UBound(TodoList)
    FF = FF + 1
Next
Print #1, FF
For i = 1 To UBound(TodoList)
    If TodoList(i).Enabled Then
        Print #1, TodoList(i).Content
        Print #1, TodoList(i).time.year
        Print #1, TodoList(i).time.month
        Print #1, TodoList(i).time.day
        Print #1, TodoList(i).time.hour
        Print #1, TodoList(i).time.min
    End If
Next
Close #1
End Sub
