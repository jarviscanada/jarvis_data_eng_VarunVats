

if [[$1 == 'localhost']] || [[$2 == '5432']] || [[$3 == 'postgres']] || [[$4 =='password']]

        vmstat_mb=$(vmstat --unit M)
        hostname=$(hostname -f)


       memory_free=$(echo "$vmstat_mb" | awk '{print $4}'| tail -n1 | xargs)
       cpu_idle=$(echo "$vmstat_mb" | awk '{print $15}'| tail -n1 | xargs)
       cpu_kernel=$(echo "$vmstat_mb" | awk '{print $13}'| tail -n1 | xargs)
       disk_io=$($vmstat -d | awk '{print $10}'| tail -n1 | xargs)
       disk_available=$(df -BM / | awk '{print $4}')

       timestamp=$(vmstat -t  | awk '{print $18,$19}'| tail -n1 | xargs)

exit 1;

















