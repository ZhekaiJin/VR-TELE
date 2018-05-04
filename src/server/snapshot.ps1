#reference http://www.daveamenta.com
#author : zhekaijin scott
#date:5/4/2018
function click(){
    [CmdletBinding()]
param($Interval = 5000, [switch]$RightClick, [switch]$NoMove)
 
[Reflection.Assembly]::LoadWithPartialName("System.Drawing") | Out-Null
$DebugViewWindow_TypeDef = @'
[DllImport("user32.dll")]
public static extern IntPtr FindWindow(string ClassName, string Title);
[DllImport("user32.dll")]
public static extern IntPtr GetForegroundWindow();
[DllImport("user32.dll")]
public static extern bool SetCursorPos(int X, int Y);
[DllImport("user32.dll")]
public static extern bool GetCursorPos(out System.Drawing.Point pt);
 
[DllImport("user32.dll", CharSet = CharSet.Auto, CallingConvention = CallingConvention.StdCall)]
public static extern void mouse_event(long dwFlags, long dx, long dy, long cButtons, long dwExtraInfo);
 
private const int MOUSEEVENTF_LEFTDOWN = 0x02;
private const int MOUSEEVENTF_LEFTUP = 0x04;
private const int MOUSEEVENTF_RIGHTDOWN = 0x08;
private const int MOUSEEVENTF_RIGHTUP = 0x10;
 
public static void LeftClick(){
    mouse_event(MOUSEEVENTF_LEFTDOWN | MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
}
 
public static void RightClick(){
    mouse_event(MOUSEEVENTF_RIGHTDOWN | MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0);
}
'@
Add-Type -MemberDefinition $DebugViewWindow_TypeDef -Namespace AutoClicker -Name Temp -ReferencedAssemblies System.Drawing
$pt = New-Object System.Drawing.Point
if ([AutoClicker.Temp]::GetCursorPos([ref]$pt)) {
        Write-host "Cursor at $($pt.X), $($pt.Y) " -NoNewline
        $start = [AutoClicker.Temp]::FindWindow("ImmersiveLauncher", "Start menu")
        $fg = [AutoClicker.Temp]::GetForegroundWindow()
 
        if ($start -eq $fg) { 
            Write-Host "Start opened. Exiting"
            return 
        }
        if (!$NoMove) {
            [AutoClicker.Temp]::SetCursorPos(746, 486) | Out-Null
        }
        if ($RightClick) {
            [AutoClicker.Temp]::RightClick()
        } else {
            [AutoClicker.Temp]::LeftClick()
        }
}
}
function Open([string]$url = "http://e4eaec49.ngrok.io",[int]$zoomlevel = 200){
     $path = "C:\Users\special_lab\Desktop\img.png"
     $IE = new-object -com internetexplorer.application\
     $IE.Top = 0 
     $IE.Left = -8
     $IE.Height = 1024
     $IE.Width = 1696
     $IE.FullScreen=$True
     $IE.ToolBar = $false
     $IE.StatusBar = $false
     $IE.MenuBar = $false
     $IE.visible = $true
     $IE.Navigate( $url )
     while($IE.Busy){sleep -Milliseconds 200} #wait to load
     $IE.ExecWB(63,2,$zoomlevel,[ref]$null) 
     [Reflection.Assembly]::LoadWithPartialName("System.Drawing")
     $bounds = [Drawing.Rectangle]::FromLTRB($IE.Left, $IE.Top, $IE.Left + $IE.Width, $IE.Top + $IE.Height)
     click
    try {
        $i = 0
        while($true)
        {
            snapshot $bounds $path
            $i++
            Write-Host “taking snapshot # $i"
            Start-Sleep -s 2

        }
    } finally {
        Write-Output "goodbye!" | Out-Default # works fine
        $IE.Quit()
    }
 }
function snapshot([Drawing.Rectangle]$bounds, $path) {
   $bmp = New-Object Drawing.Bitmap $bounds.width, $bounds.height
   $graphics = [Drawing.Graphics]::FromImage($bmp)

   $graphics.CopyFromScreen($bounds.Location, [Drawing.Point]::Empty, $bounds.size)

   $bmp.Save($path)

   $graphics.Dispose()
   $bmp.Dispose()
}
open
