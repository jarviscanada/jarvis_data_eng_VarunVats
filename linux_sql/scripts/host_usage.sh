

 memory_free= vmstat --unit M | awk '{print $4}'| tail -n1 | xargs
 cpu_idle= vmstat --unit M| awk '{print $15}'| tail -n1 | xargs
 cpu_kernel= vmstat --unit M | awk '{print $13}'| tail -n1 | xargs
 disk_io= vmstat -d | awk '{print $10}'| tail -n1 | xargs
 disk_available= df -BM / | awk '{print $4}'
 timestamp= vmstat -t  | awk '{print $18,$19}'| tail -n1 | xargs


host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

insert_stmt="INSERT INTO host_usage(timestamp,memory_free,cpu_idle,cpu_kernel,disk_io,disk_availaible) VALUES('$timestamp', '$memory_free','$cpu_idle','$cpu_kernel','$disk_io','$disk_availaible')"

export PGPASSWORD=$psql_password

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?














