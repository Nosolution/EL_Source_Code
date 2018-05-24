Attribute VB_Name = "FileMod"
Public Declare Function GetOpenFileName Lib "comdlg32.dll" Alias "GetOpenFileNameA" (pOPENFILENAME As OPENFILENAME) As Long
Public Declare Function GetSaveFileName Lib "comdlg32.dll" Alias "GetSaveFileNameA" (pOPENFILENAME As OPENFILENAME) As Long
Type OPENFILENAME
    lStructSize As Long
    hwndOwner As Long
    hInstance As Long
    lpstrFilter As String
    lpstrCustomFilter As String
    nMaxCustFilter As Long
    nFilterIndex As Long
    lpstrFile As String
    nMaxFile As Long
    lpstrFileTitle As String
    nMaxFileTitle As Long
    lpstrInitialDir As String
    lpstrTitle As String
    flags As Long
    nFileOffset As Integer
    nFileExtension As Integer
    lpstrDefExt As String
    lCustData As Long
    lpfnHook As Long
    lpTemplateName As String
End Type
Public Const OFN_OVERWRITEPROMPT = &H2
Public Const OFN_HIDEREADONLY = &H4
Public Const OFN_PATHMUSTEXIST = &H800
Public Const OFN_FILEMUSTEXIST = &H1000
Public Const OFN_NOCHANGEDIR = &H8

Public Function GetDialog(ByVal sMethod As String, ByVal sTitle As String, ByVal sFileName As String, ByVal iniDir As String, ByVal sFilterDes As String, ByVal sFilter As String) As String
    On Error GoTo myError
    Dim rtn As Long, pos As Integer
    Dim file As OPENFILENAME
    file.lStructSize = Len(file)
    file.hInstance = App.hInstance
    file.lpstrFile = sFileName & String$(255 - Len(sFileName), 0)
    file.nMaxFile = 255
    file.lpstrFileTitle = String$(255, 0)
    file.nMaxFileTitle = 255
    'file.lpstrInitialDir = App.path & "\Maps"
    file.lpstrInitialDir = iniDir
    'file.lpstrFilter = "nsmx工程文件(*.nxproj)" + Chr$(0) + "*.nxproj" + Chr$(0) + Chr$(0)
    If sFilter <> "" Then
        file.lpstrFilter = sFilterDes + "(*." + sFilter + ")" + Chr$(0) + "*." + sFilter + Chr$(0) + Chr$(0)
    Else
        file.lpstrFilter = "All files (*.*)" + Chr$(0) + "*.*" + Chr$(0) + Chr$(0)
    End If
    file.lpstrTitle = sTitle
    If UCase(sMethod) = "OPEN" Then
        file.flags = OFN_HIDEREADONLY + OFN_PATHMUSTEXIST + OFN_FILEMUSTEXIST + OFN_NOCHANGEDIR
        rtn = GetOpenFileName(file)
    Else
        file.lpstrDefExt = "exe"
        file.flags = OFN_HIDEREADONLY + OFN_PATHMUSTEXIST + OFN_OVERWRITEPROMPT + OFN_NOCHANGEDIR
        rtn = GetSaveFileName(file)
    End If
    If rtn > 0 Then
        pos = InStr(file.lpstrFile, Chr$(0))
        If pos > 0 Then
            GetDialog = left$(file.lpstrFile, pos - 1)
        End If
    End If
    Exit Function
myError:
    MsgBox "操作失败！", vbCritical + vbOKOnly, APP_NAME
End Function

