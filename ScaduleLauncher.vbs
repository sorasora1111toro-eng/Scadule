Set objShell = CreateObject("WScript.Shell")
strBatchFile = CreateObject("Scripting.FileSystemObject").GetParentFolderName(WScript.ScriptFullName) & "\ScaduleLauncher.bat"
objShell.Run strBatchFile, 0
