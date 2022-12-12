#! /bin/sh


basepath=$(cd `dirname $0`; pwd)
cd $basepath
cd ../

WORKSPACE=$(pwd)

#output the info
echo "Using WORKSPACE:${WORKSPACE}";

#check whether the main class is running in the process. 
processid=$(ps auxwww|grep DOURDIR=$WORKSPACE|grep java|grep -v grep|awk '{print $2 }')
if [[ "$processid" = "" ]]
then
	echo "${WORKSPACE} is not running in the process!"
else
	kill -9 $processid
	echo "shutDown ${WORKSPACE} successfull! $processid "
fi