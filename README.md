# Top Process Monitoring Plugin

## Overview

![images_community/download/attachments/121340701/top_plugin_windows_dashboard.png](images_community/download/attachments/121340701/top_plugin_windows_dashboard.png)

![images_community/download/attachments/121340701/top_plugin_linux_dashboard.png](images_community/download/attachments/121340701/top_plugin_linux_dashboard.png) The Top Process Monitor plugin enables
monitoring processes consuming the most CPU Time and/or Resident/WorkingSet memory and stores the process name as a dynamic measure.

Windows measures are collected through Powershell, so these measures must be executed from a Windows Collector.

Linux measures are collected through SSH, and can be collected from both Windows or Linux collectors.

## Plugin Details

| Name | Top Process Monitoring Plugin
| :--- | :---
| Author | [Paul Kuit](Paul.Kuit@compuware.com)
| Supported dynaTrace Versions | >= 5.5
| License | [dynaTrace BSD](dynaTraceBSD.txt)
| Support | [Not Supported](https://community.compuwareapm.com/community/display/DL/Support+Levels)
| Release History | 2013-21-05 1.0.0 Initial Release  
|| 2013-22-05 1.0.1 Number Format fix
|| 2013-12-09 1.0.2 Gigabyte fix
| Download | [Top Monitor Plugin 1.0.2](br.com.compuware.topplugin_1.0.2.jar)  
|| [Top Plugin Monitor Dashboard - Windows](Top_Processes_Dashboard.dashboard.xml)  
||[Top Plugin Monitor Dashboard - Linux](Linux_Top_Processes_Dashboard.dashboard.xml)

## Provided Measures

### Both Windows and Unix:

  * CPUTime (s) 

  * Resident/Workingset memory (kB) 

  * Virtual Memory (kB) 

### Linux only:

  * CPU (%) 

  * Memory (%) 

  * Shared Memory (kB) 

### Windows only:

  * Handles (occ) 

  * Non Pageable Memory - NPM (kB) 

  * Pageable Memory - PM (kB) 

**DO NOT SUBSCRTIBE LINUX SPECIFIC MEASURES TO A MONITOR CONFIGURED FOR WINDOWS HOSTS OR WINDOWS SPECIFIC MEASURES TO A MONITOR FOR LINUX HOST. THE PLUGIN WILL VERIFY THIS AND FAIL TO SETUP.**

### Configuration Top Monitor

The monitor requires the following configuration settings:

  * ssh-username: (Linux only) The username used to logon through SSH 

  * ssh-password: (Linux only) The password used to logon through SSH 

  * operatingSystem: Select (Windows/Linux) the operating system of the monitored hosts within this monitor. Create different monitors for Unix and Windows destination hosts. 

  * topMemory: The amount of processes to collect consuming most Resident (or Working set) memory. Should be less then the total amount of processes running on the host. 

  * topCPU: The amount of processes to collect consuming top CPU Time. 

Values topCPU=5, topMemory=5 could bring up 10 dynamic measures for each host since most consuming memory processes are not necessarily most CPU consuming. If only interested in top memory consuming,
define topCPU=0 and vice versa.

### Installation

Import the Plugin into the dynaTrace Server via the dynaTrace Server Settings menu -> Plugins -> Install Plugin. For details how to do this please refer to the [dynaTrace
documentation](https://community.dynatrace.com/community/display/DOCDT32/Manage+and+Develop+Plugins#ManageandDevelopPlugins-ManageandDevelopPlugins).

To use the provided dashboards please open the Dashboard and set the Data Source accordingly.

### Access Requirements

#### Windows

Windows measure collections are only available for Collectors running on Windows, since it uses the following command:

    
    
    powershell invoke-command -computer <IP_ADDRESS> "{get-process | fl ProcessName, ID, Handles, NPM, PM, WS, VM, CPU}"

Make sure powershell´s remote execution is enabled on the remote machine by executing:

    
    
    powershell enable-psremoting -force

In case of AccessDenied errors, try execute the `powershell invoke-command` from the collector with the same user running the Collector process.

#### Linux

Linux measures are queried through SSH by executing command:

    
    
    "top -n 1 -b"

Make sure SSH is installed on the Linux destination machine and the ssh-username have access rights.

### Usage Notes

The name of the dynamic measure contains only the name of the process. When creating a chart for a specific measure, select measure splitting 'processname' and filter the chart dashlet for the
specific host or it will show processes from all monitored hosts within the monitor instance.

