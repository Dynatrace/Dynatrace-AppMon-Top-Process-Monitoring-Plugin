<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Top Process Monitoring Plugin</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
    <meta content="Scroll Wiki Publisher" name="generator"/>
    <link type="text/css" rel="stylesheet" href="css/blueprint/liquid.css" media="screen, projection"/>
    <link type="text/css" rel="stylesheet" href="css/blueprint/print.css" media="print"/>
    <link type="text/css" rel="stylesheet" href="css/content-style.css" media="screen, projection, print"/>
    <link type="text/css" rel="stylesheet" href="css/screen.css" media="screen, projection"/>
    <link type="text/css" rel="stylesheet" href="css/print.css" media="print"/>
</head>
<body>
                <h1>Top Process Monitoring Plugin</h1>
    <div class="section-2"  id="121340701_TopProcessMonitoringPlugin-Overview"  >
        <h2>Overview</h2>
    <p>
            <img src="images_community/download/attachments/121340701/top_plugin_windows_dashboard.png" alt="images_community/download/attachments/121340701/top_plugin_windows_dashboard.png" class="" />
            </p>
    <p>
            <img src="images_community/download/attachments/121340701/top_plugin_linux_dashboard.png" alt="images_community/download/attachments/121340701/top_plugin_linux_dashboard.png" class="" />
        The Top Process Monitor plugin enables monitoring processes consuming the most CPU Time and/or Resident/WorkingSet memory and stores the process name as a dynamic measure.    </p>
    <p>
Windows measures are collected through Powershell, so these measures must be executed from a Windows Collector.    </p>
    <p>
Linux measures are collected through SSH, and can be collected from both Windows or Linux collectors.    </p>
    </div>
    <div class="section-2"  id="121340701_TopProcessMonitoringPlugin-PluginDetails"  >
        <h2>Plugin Details</h2>
    <div class="tablewrap">
        <table>
<thead class=" "></thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
Plug-In Files    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<strong class=" ">dynaTrace 5.0+</strong>:    </p>
            </td>
        </tr>
</tbody>        </table>
            </div>
    <p>
<a href="attachments_137757091_1_br.com.compuware.topplugin_1.0.2.jar">Top Monitor Plugin 1.0.2</a><br/><a href="attachments_121569464_1_Windows_Top_Processes_Dashboard.dashboard.xml">Top Plugin Monitor Dashboard - Windows</a><br/><a href="attachments_121569465_1_Linux_Top_Processes_Dashboard.dashboard.xml">Top Plugin Monitor Dashboard - Linux</a>    </p>
    <div class="tablewrap">
        <table>
<thead class=" "></thead><tfoot class=" "></tfoot><tbody class=" ">    <tr>
            <td rowspan="1" colspan="1">
        <p>
Author    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<a href="https://community/display/~Paul.Kuit@compuware.com">Paul Kuit</a>    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
dynaTrace Versions    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
4.2, 5.x    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
License    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<a href="attachments_5275722_2_dynaTraceBSD.txt">dynaTrace BSD</a>    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Support    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
<a href="https://community/display/DL/Support+Levels">Not Supported</a>    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Known Problems    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
    </p>
            </td>
        </tr>
    <tr>
            <td rowspan="1" colspan="1">
        <p>
Release History    </p>
            </td>
                <td rowspan="1" colspan="1">
        <p>
2013-21-05 1.0.0 Initial Release<br/>2013-22-05 1.0.1 Number Format fix    </p>
    <p>
2013-12-09 1.0.2 Gigabyte fix    </p>
            </td>
        </tr>
</tbody>        </table>
            </div>
    </div>
    <div class="section-2"  id="121340701_TopProcessMonitoringPlugin-ProvidedMeasures"  >
        <h2>Provided Measures</h2>
    <div class="section-3"  id="121340701_TopProcessMonitoringPlugin-BothWindowsandUnix%3A"  >
        <h3>Both Windows and Unix:</h3>
<ul class=" "><li class=" ">    <p>
CPUTime (s)    </p>
</li><li class=" ">    <p>
Resident/Workingset memory (kB)    </p>
</li><li class=" ">    <p>
Virtual Memory (kB)    </p>
</li></ul>    </div>
    <div class="section-3"  id="121340701_TopProcessMonitoringPlugin-Linuxonly%3A"  >
        <h3>Linux only:</h3>
<ul class=" "><li class=" ">    <p>
CPU (%)    </p>
</li><li class=" ">    <p>
Memory (%)    </p>
</li><li class=" ">    <p>
Shared Memory (kB)    </p>
</li></ul>    </div>
    <div class="section-3"  id="121340701_TopProcessMonitoringPlugin-Windowsonly%3A"  >
        <h3>Windows only:</h3>
<ul class=" "><li class=" ">    <p>
Handles (occ)    </p>
</li><li class=" ">    <p>
Non Pageable Memory - NPM (kB)    </p>
</li><li class=" ">    <p>
Pageable Memory - PM (kB)    </p>
</li></ul>    <p>
<strong class=" ">DO NOT SUBSCRTIBE LINUX SPECIFIC MEASURES TO A MONITOR CONFIGURED FOR WINDOWS HOSTS OR WINDOWS SPECIFIC MEASURES TO A MONITOR FOR LINUX HOST. THE PLUGIN WILL VERIFY THIS AND FAIL TO SETUP.</strong>    </p>
    </div>
    <div class="section-3"  id="121340701_TopProcessMonitoringPlugin-ConfigurationTopMonitor"  >
        <h3>Configuration Top Monitor</h3>
    <p>
The monitor requires the following configuration settings:    </p>
<ul class=" "><li class=" ">    <p>
ssh-username: (Linux only) The username used to logon through SSH    </p>
</li><li class=" ">    <p>
ssh-password: (Linux only) The password used to logon through SSH    </p>
</li><li class=" ">    <p>
operatingSystem: Select (Windows/Linux) the operating system of the monitored hosts within this monitor. Create different monitors for Unix and Windows destination hosts.    </p>
</li><li class=" ">    <p>
topMemory: The amount of processes to collect consuming most Resident (or Working set) memory. Should be less then the total amount of processes running on the host.    </p>
</li><li class=" ">    <p>
topCPU: The amount of processes to collect consuming top CPU Time.    </p>
</li></ul>    <p>
Values topCPU=5, topMemory=5 could bring up 10 dynamic measures for each host since most consuming memory processes are not necessarily most CPU consuming. If only interested in top memory consuming, define topCPU=0 and vice versa.    </p>
    </div>
    <div class="section-3"  id="121340701_TopProcessMonitoringPlugin-Installation"  >
        <h3>Installation</h3>
    <p>
Import the Plugin into the dynaTrace Server via the dynaTrace Server Settings menu -&gt; Plugins -&gt; Install Plugin. For details how to do this please refer to the <a href="https://community.dynatrace.com/community/display/DOCDT32/Manage+and+Develop+Plugins#ManageandDevelopPlugins-ManageandDevelopPlugins">dynaTrace documentation</a>.    </p>
    <p>
To use the provided dashboards please open the Dashboard and set the Data Source accordingly.    </p>
    </div>
    <div class="section-3"  id="121340701_TopProcessMonitoringPlugin-AccessRequirements"  >
        <h3>Access Requirements</h3>
    <div class="section-4"  id="121340701_TopProcessMonitoringPlugin-Windows"  >
        <h4>Windows</h4>
    <p>
Windows measure collections are only available for Collectors running on Windows, since it uses the following command:    </p>
    <div class="confbox programlisting">
                <div class="content">
        <pre><code>powershell invoke-command -computer &lt;IP_ADDRESS&gt; &quot;{get-process | fl ProcessName, ID, Handles, NPM, PM, WS, VM, CPU}&quot;</code></pre>
        </div>
    </div>
    <p>
Make sure powershell&acute;s remote execution is enabled on the remote machine by executing:    </p>
    <div class="confbox programlisting">
                <div class="content">
        <pre><code>powershell enable-psremoting -force</code></pre>
        </div>
    </div>
    <p>
In case of AccessDenied errors, try execute the `powershell invoke-command` from the collector with the same user running the Collector process.    </p>
    </div>
    <div class="section-4"  id="121340701_TopProcessMonitoringPlugin-Linux"  >
        <h4>Linux</h4>
    <p>
Linux measures are queried through SSH by executing command:    </p>
    <div class="confbox programlisting">
                <div class="content">
        <pre><code>&quot;top -n 1 -b&quot;</code></pre>
        </div>
    </div>
    <p>
Make sure SSH is installed on the Linux destination machine and the ssh-username have access rights.    </p>
    </div>
    </div>
    <div class="section-3"  id="121340701_TopProcessMonitoringPlugin-UsageNotes"  >
        <h3>Usage Notes</h3>
    <p>
The name of the dynamic measure contains only the name of the process. When creating a chart for a specific measure, select measure splitting 'processname' and filter the chart dashlet for the specific host or it will show processes from all monitored hosts within the monitor instance.    </p>
    </div>
    </div>
            </div>
        </div>
        <div class="footer">
        </div>
    </div>
</body>
</html>
