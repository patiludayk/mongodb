#!/bin/bash
export TERM=xterm-color
#script to start mongodb server

ports=()
checkIfServiceRunning(){
  #get running process detail and store in temp file
  lsof -i -n -P 2> /dev/null | grep $1 > $tempLogFile

  #get port number for running process
  while read line; do
    port=$(echo $line | cut -d ' ' -f 2)
    ports+=( $port )
  done < $tempLogFile

  #print ports - debug
  #echo "${ports[*]}"

  return "${#ports[@]}"
}

clear
current_date_time="`date "+%Y-%m-%d %H:%M:%S"`";
source src/main/resources/input.txt

#mongodb=/usr/local/var/mongodb
#logpath=/usr/local/var/log/mongodb/mongo.log
#mongodbLogfileLocal=../log/mongodb-log.log

echo "************************************************************************************************************"
printf "%s\n%s\n" "mongodb location: $mongodb" "logpath: $logpath"
echo "************************************************************************************************************"
echo ">>>>>>>>>  Starting mongodb $current_date_time <<<<<<<<<"
printf "%s\n" ">>>>>>>>>  Starting mongodb $current_date_time <<<<<<<<<" >> $mongodbLogfileLocal
mongod "--dbpath" $mongodb "--logpath" $logpath >> $mongodbLogfileLocal &
echo "sleeping for 3 sec to start mongodb"
sleep 3
: '
# Get its PID
PID=$!
# Wait for 2 seconds
sleep 2
'

#check if mongodb server started or not
checkIfServiceRunning "mongod"
serviceRunningStatus=$?
if [ "$serviceRunningStatus" == 0 ]
then
     echo "service not running"
else
     echo "service started on port/s ${ports[*]}"
fi

#rm -rf $tempLogFile
echo "************ exiting starter script ***************"