Attribute VB_Name = "BY_AnimationMod"
Public showClock As Boolean
Public ClockCenterX As Long
Public ClockCenterY As Long
Public ShowMode As Long '0 主界面 1 任务 2 音乐 3 时间

Public NowTaskIndex As Long
Public ClockState As Long
Public RunMode As Long '0 没有运行 1 正在运行 2 休息 2 肝模式

Public Sub AnimationControl()
ClockCenterY = 240
End Sub

