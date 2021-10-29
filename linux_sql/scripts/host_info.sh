
#save number of CPU to a variable
lscpu_out=`lscpu`

#hardware
hostname= hostname -f
cpu_number= echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs
cpu_number= echo "$lscpu_out"  | egrep "^Architecture\:" | awk '{print $2}' | xargs

cpu_model= grep -m 1 'model name' /proc/cpuinfo
cpu_mhz= grep -m 1 'cpu MHz' /proc/cpuinfo
total_memory= grep -m 1 'Total_mem' /proc/meminfo
timestamp= vmstat -t  | awk '{print $18,$19}'| tail -n1 | xargs

insert_stmt="INSERT INTO host_info (hostname,cpu_number,cpu_architecture,cpu_model,cpu_mhz,total_memory,"timestamp")"

















