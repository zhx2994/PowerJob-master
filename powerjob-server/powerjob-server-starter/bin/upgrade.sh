#!/bin/bash

#确定当前目录
basepath=$(cd `dirname $0`; pwd)
cd $basepath
cd ../

PROJECT_DIR=$(pwd)
echo "当前工程目录:${PROJECT_DIR}"

UPGRADE_DIR="${PROJECT_DIR}/upgrade"
echo "当前工程升级目录:${UPGRADE_DIR}"

APP_DIR="${PROJECT_DIR}/app"
echo "当前工程APP目录:${APP_DIR}"

BACKUP_DIR="${PROJECT_DIR}/backup"
echo "当前工程备份目录:${BACKUP_DIR}"


#确定当前工程的环境变量
APPENV=$1
MYJAVA=$2
TABLENAME=$3
ROWKEY=$4

echo "当前的环境参数设置为 ${APPENV}"
if [ "${APPENV}" == "" ]; then
   echo "请传递启动环境信息； local|test|online|chinaComm|hongkong|japan|korea|usawest" 
   exit 1
fi
if [ "${MYJAVA}" == "" ]; then
   MYJAVA="java"
fi
echo "使用java:${MYJAVA}"


if [ ! -d ${APP_DIR} ];then
 echo "APP目录不存在"
 exit 1
fi

if [ ! -d ${BACKUP_DIR} ];then
 echo "备份目录不存在"
 exit 1
fi

if [ ! -d ${UPGRADE_DIR} ];then
 echo "升级目录不存在"
 exit 1
fi

WORKCOUNT=`ls ${UPGRADE_DIR}/*|wc -w`
if [ "$WORKCOUNT" = "0" ];
then
 echo "本次没有文件更新，不进行升级"
else
 echo "准备升级"
 cd ${UPGRADE_DIR}

 #unzip classes.zip
 #unzip lib.zip
 #rm classes.zip
 #rm lib.zip

 echo "开始备份数据..."
 BACKUP=app.$(date +%Y%m%d%H%M%S).zip
 zip -r ${BACKUP_DIR}/${BACKUP} ${APP_DIR}
 echo "完成备份； 备份路径：${BACKUP_DIR}/${BACKUP}"
 
 echo "删除app目录..."
 rm -r ${APP_DIR}/*
 
 echo "正在拷贝数据..."
 cp -r ${UPGRADE_DIR}/* ${APP_DIR}/
 
 echo "正在生成bin脚本数据..."
 sed -i "2a APPENV=\"${APPENV}\"" ${APP_DIR}/start.sh
 sed -i "2a MYJAVA=\"${MYJAVA}\"" ${APP_DIR}/start.sh
 sed -i "2a TABLENAME=\"${TABLENAME}\"" ${APP_DIR}/start.sh
 sed -i "2a ROWKEY=\"${ROWKEY}\"" ${APP_DIR}/start.sh
 sed -i "2a APPENV=\"${APPENV}\"" ${APP_DIR}/stop.sh
 sed -i "2a MYJAVA=\"${MYJAVA}\"" ${APP_DIR}/stop.sh


 chmod 744 ${APP_DIR}/*.sh

 rm -r ${UPGRADE_DIR}/*
 
 echo "拷贝完成" 

 echo "升级完成"
 cd ${APP_DIR}
 sh start.sh
fi

