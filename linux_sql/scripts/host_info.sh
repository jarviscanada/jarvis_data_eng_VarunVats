```bash
#save hostname to a variable
hostname=$(hostname -f);

#save number of CPU to a variable
lscpu_out=`lscpu`;
#note: `xargs` is a trick to remove leading and trailing white spaces
cpu_number=$echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs;

#hardware
host= hostname -f;
cpu_num= echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs;
cpu_arch=echo "$lscpu_out"  | egrep "^Architecture\:" | awk '{print $2}' | xargs; 
cpu_mod= grep -m 1 'model name' /proc/cpuinfo;
cpu_mh= grep -m 1 'cpu MHz' /proc/cpuinfo;
total_memory= grep -m 1 'Total_mem' /proc/meminfo;
timest= vmstat-t ;














