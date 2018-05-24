Attribute VB_Name = "UTFMod"
Private Declare Function MultiByteToWideChar Lib "kernel32 " (ByVal CodePage As Long, ByVal dwFlags As Long, ByVal lpMultiByteStr As Long, ByVal cchMultiByte As Long, ByVal lpWideCharStr As Long, ByVal cchWideChar As Long) As Long
Private Declare Function WideCharToMultiByte Lib "kernel32 " (ByVal CodePage As Long, ByVal dwFlags As Long, ByVal lpWideCharStr As Long, ByVal cchWideChar As Long, ByVal lpMultiByteStr As Long, ByVal cchMultiByte As Long, ByVal lpDefaultChar As Long, ByVal lpUsedDefaultChar As Long) As Long
Public Declare Function GetShortPathName Lib "kernel32" Alias "GetShortPathNameA" (ByVal lpszLongPath As String, ByVal lpszShortPath As String, ByVal cchBuffer As Long) As Long

Private Const CP_ACP = 0 ' default to ANSI code page
Private Const CP_UTF8 = 65001 ' default to UTF-8 code page

'×Ö·û×ª UTF8
Public Function EncodeToBytes(ByVal sData As String) As Byte() ' Note: Len(sData) > 0
Dim aRetn() As Byte
Dim nSize As Long
nSize = WideCharToMultiByte(CP_UTF8, 0, StrPtr(sData), -1, 0, 0, 0, 0) - 1
If nSize = 0 Then Exit Function
ReDim aRetn(0 To nSize - 1) As Byte
WideCharToMultiByte CP_UTF8, 0, StrPtr(sData), -1, VarPtr(aRetn(0)), nSize, 0, 0
EncodeToBytes = aRetn
Erase aRetn
End Function

' UTF8 ×ª×Ö·û
Public Function DecodeToBytes(ByVal sData As String) As Byte() ' Note: Len(sData) > 0
Dim aRetn() As Byte
Dim nSize As Long
nSize = MultiByteToWideChar(CP_UTF8, 0, StrPtr(sData), -1, 0, 0) - 1
If nSize = 0 Then Exit Function
ReDim aRetn(0 To 2 * nSize - 1) As Byte
MultiByteToWideChar CP_UTF8, 0, StrPtr(sData), -1, VarPtr(aRetn(0)), nSize
DecodeToBytes = aRetn
Erase aRetn
End Function


Public Function ShortPath(ByVal FileName As String) As String
Dim s As String
On Error GoTo lls
s = String(255, " ")
GetShortPathName FileName, s, 255
ShortPath = left(s, InStr(s, Chr(0)) - 1)
lls:
End Function
