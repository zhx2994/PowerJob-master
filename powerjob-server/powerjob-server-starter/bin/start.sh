#!/bin/bash


basepath=$(cd `dirname $0`; pwd)
cd $basepath
cd ../
WORKSPACE=$(pwd)
echo "当前工程目录:${WORKSPACE}"

echo "当前的环境参数设置为APPENV:${APPENV} MYJAVA:${MYJAVA}"
if [ "${APPENV}" == "" ]; then
   echo "请传递启动环境信息； local|test|prod" 
   exit 1
fi
if [ "${MYJAVA}" == "" ]; then
   MYJAVA="java"
fi
echo "使用java:${MYJAVA}"


echo "start 脚本执行开始 WORKSPACE=${WORKSPACE}"
APP_LOG_FILE=${WORKSPACE}/logs/catalina.%Y-%m-%d.out

PID=`ps -ef |grep -w ${WORKSPACE} |grep "java" |awk '{print $2}'`
if [ "${PID}" != "" ]; then
    echo "${WORKSPACE} 已启动,需先停止, PID=${PID}"
    kill -9 ${PID}
    if [[ $? -eq 0 ]];then
        echo "停止成功"
    fi
fi

# echo "授予当前用户权限"
# chmod 777 ${WORKSPACE}/app/lib/*.jar
echo "执行....."
cd ${WORKSPACE}/app/
${MYJAVA} -Xms1024m -Xmx3072m -DOURDIR=${WORKSPACE} -jar powerjob-server-starter-4.2.0.jar --spring.profiles.active=${APPENV} 2>&1 |nohup /usr/local/sbin/cronolog ${APP_LOG_FILE} >> /dev/null &
echo "启动完成"