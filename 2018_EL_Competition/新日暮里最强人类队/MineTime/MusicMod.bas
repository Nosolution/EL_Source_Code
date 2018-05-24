Attribute VB_Name = "MusicMOd"
Public sfx_are_loaded As Boolean
Public musicA As Long
Public Type MusicInfo
Enabled As Boolean
Name As String
onAppPath As Boolean
file As String
End Type

Public MusicInfos() As MusicInfo
Public Music_Disabled As Boolean
Public NowPlayMusicIndex As Long

Public Sub delMusicInfo()
Erase MusicInfos
End Sub

Public Sub iniMusicInfos()
Dim s() As String
Open App.path & "\Data\MusicList.dat" For Input As #1
s = Split(StrConv(InputB(LOF(1), 1), vbUnicode), vbCrLf)
Close
Dim Cnt As Long, pointer As Long
Cnt = Val(s(0))
pointer = 1
ReDim MusicInfos(Cnt)
For i = 1 To Cnt
    MusicInfos(i).file = s(pointer)
    MusicInfos(i).Name = getPureFileName(getFileName(s(pointer)))
    MusicInfos(i).onAppPath = NumToBoo(Val(s(pointer + 1)))
    MusicInfos(i).Enabled = True
    pointer = pointer + 2
Next
End Sub

Public Sub SaveMusicInfos()
Open App.path & "\Data\MusicList.dat" For Output As #1
Print #1, UBound(MusicInfos)
For i = 1 To UBound(MusicInfos)
    Print #1, MusicInfos(i).file
    Print #1, BooToNum(MusicInfos(i).onAppPath)
Next
Close #1
End Sub

Public Sub AddMusic(file As String)
ReDim Preserve MusicInfos(UBound(MusicInfos) + 1)
Dim a As Long
a = UBound(MusicInfos)
MusicInfos(a).Enabled = True
MusicInfos(a).file = file
MusicInfos(a).Name = getPureFileName(getFileName(file))
MusicInfos(a).onAppPath = False
End Sub

Public Sub DelMusic(index As Long)
Dim b As MusicInfo
MusicInfos(index) = b
End Sub

Public Sub ResetMusicInfo()
Dim b() As MusicInfo
ReDim b(UBound(MusicInfos))
Dim a As Long
For i = 1 To UBound(MusicInfos)
    If MusicInfos(i).Enabled Then
        a = a + 1
        b(a) = MusicInfos(i)
    End If
Next
ReDim Preserve b(a)
MusicInfos = b
End Sub

Public Sub AddDirMuic(DirPath As String)
Dim myfile As String
myfile = Dir(DirPath & "*mp3")
Do While myfile <> ""
    AddMusic DirPath & myfile
    myfile = Dir
Loop
End Sub

Public Sub IniSDL()
sfx_are_loaded = False
    'mysfx1 = 0
    'mysfx2 = 0
    'mysfx3 = 0
    'mysfx4 = 0
If SDL_InitAudio < 0 Then         'Initialize SDL Library first
    MsgBox "SDL Error: " & SDL_GetError, vbOKOnly + vbExclamation
    Exit Sub
End If
Mix_Init '0                                   'Init SDL Mixer itself
Mix_OpenAudio 44100, AUDIO_S16LSB, 2, 2048   'Init Open audio stream
Mix_VolumeMusic 100                'Set current volume
End Sub

Public Sub UnloadSDL()
If sfx_are_loaded Then ' Unload SFXes if there are loaded
    'Mix_FreeChunk mysfx1
    'Mix_FreeChunk mysfx2
    'Mix_FreeChunk mysfx3
    'Mix_FreeChunk mysfx4
End If
Mix_CloseAudio
Mix_Quit
SDL_Quit
End Sub

Public Sub UnloadSFX()
For i = 1 To UBound(SoundInfos)
    Mix_FreeChunk SoundInfos(i).sfx
    SoundInfos(i).sfx = 0
Next
Mix_AllocateChannels 0
End Sub

Public Sub PlayMusic(filepath As String, LoopTime As Long)
Mix_HaltMusic                       'Stop previous
Mix_FreeMusic musicA
If Music_Disabled Then Exit Sub
musicA = Mix_LoadMUS(StrConv(EncodeToBytes(ShortPath(filepath)), vbUnicode))  'Open new music
'Starting of music playback
Mix_PlayMusic musicA, LoopTime
End Sub

Public Sub StopMusic()
Mix_HaltMusic
oldMusicID = 0
End Sub

Public Sub PlayMusicInList(i As Long)
Select Case MusicInfos(i).onAppPath
Case False: PlayMusic MusicInfos(i).file, IIf(ClockData.LoopMusic, -1, 1)
Case True: PlayMusic App.path & "\Data\Music\" & MusicInfos(i).file, IIf(ClockData.LoopMusic, -1, 1)
End Select
End Sub

Public Sub MusicChecking()
If RunMode > 0 Then
    If NowPlayMusicIndex <> 0 Then
        Select Case ClockData.LoopMusic
        Case False
            If Mix_PlayingMusic <= 0 Then
                NowPlayMusicIndex = NowPlayMusicIndex + 1
                If NowPlayMusicIndex > UBound(MusicInfos) Then NowPlayMusicIndex = 1
                PlayMusicInList NowPlayMusicIndex
            End If
        End Select
    End If
End If
End Sub
