Attribute VB_Name = "ToDoListMod"
Public Type BY_Time
year As Long
month As Long
day As Long
hour As Long
min As Long
End Type

Public Type TodoListType
Content As String
time As BY_Time
Enabled As Boolean
RestTime As Long
End Type

Public TodoList() As TodoListType
Public NowEditTodoIndex As Long
Public NowEditString As String
Public NowEditTime As BY_Time

Public Sub AddTodoList()
ReDim Preserve TodoList(UBound(TodoList) + 1)
TodoList(UBound(TodoList)).Content = "我的任务"
End Sub

Public Sub DelTodoList(i As Long)
If i > UBound(TodoList) Then Exit Sub
Dim b As TodoListType
TodoList(i) = b
For j = i To UBound(TodoList) - 1
    TodoList(j) = TodoList(j + 1)
Next
ReDim Preserve TodoList(UBound(TodoList) - 1)
End Sub

Public Sub getTodoRestTime()
For i = 1 To UBound(TodoList)
    If TodoList(i).Enabled Then
        TodoList(i).RestTime = getDateFromNow(TodoList(i).time.year, TodoList(i).time.month, TodoList(i).time.day, TodoList(i).time.hour, TodoList(i).time.min, 0).RealSecondTime
    End If
Next
End Sub

Public Sub delNoRestTimeTodo()
Dim i As Long
i = 1
Do
    If i > UBound(TodoList) Then Exit Do
    If TodoList(i).Enabled Then
        If TodoList(i).RestTime <= 0 Then
            DelTodoList i
            i = i - 1
        End If
    End If
    i = i + 1
Loop Until i > UBound(TodoList)
End Sub

Public Function getNowTaskIndex()
Dim i As Long
Dim RST As Long
Dim min As Long
min = 99999999
For i = 1 To UBound(TodoList)
    If TodoList(i).Enabled Then
        RST = TodoList(i).RestTime
        If RST < min Then
            min = RST
            getNowTaskIndex = i
        End If
    End If
Next
End Function
