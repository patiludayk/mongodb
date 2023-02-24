#!/bin/bash
export TERM=xterm-color
#script to stop mongodb server

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
echo ">>>>>>>>>  Shutting down mongodb $current_date_time <<<<<<<<<"
printf "%s\n" ">>>>>>>>>  Shutting down mongodb $current_date_time <<<<<<<<<" >> $mongodbLogfileLocal

#get port on which mongodb server is running
checkIfServiceRunning "mongod"
serviceRunningStatus=$?
if [ "$serviceRunningStatus" == 0 ]
then
  echo "service already down."
else
  echo "stopping mongodb server"
  for port in "${ports[*]}"
    do
      echo "killing port $port"
      kill $port
    done
fi

#rm -rf $tempLogFile
echo "************ exiting shutdown script ***************"