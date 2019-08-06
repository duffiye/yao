#!/bin/bash

func() {
  ip=$1         #接收IP的变量
  port=$2       #接收端口的变量
  user=$3       #接收用户名的变量
  password=$4   #接收密码的变量
  serverName=$5 #接收的服务名变量
  serverPort=$6 #接收的服务端口

  echo $ip
  echo $port
  echo $user
  echo $password
  echo $serverName
  echo $serverPort
  expect <<EOF
set timeout 60

spawn ssh $user@$ip -p $port
expect {
    "yes/no" {send "yes\n";exp_continue}
    "password" {send "$password\n"}
}

# 更新最新的image
expect "#" {send "docker pull y3tu/$serverName\n"}
# 停止旧的容器
expect "#" {send "docker kill $serverName \r"}
# 删除旧的容器
expect "#" {send "docker rm $serverName \r"}
# 启动新的容器
expect "#" {send "docker run -d -p $serverPort:$serverPort -v /data/logs/$serverName/:/logs/$serverName/ --name $serverName y3tu/$serverName:latest \r"}

expect "#" {send "exit\n"}
expect eof
EOF
}

while read line; do
  IP=$(echo $line | cut -d'|' -f1)
  PORT=$(echo $line | cut -d'|' -f2)
  USER=$(echo $line | cut -d'|' -f3)
  PAWD=$(echo $line | cut -d'|' -f4)
  SERVER_NAME=$(echo $line | cut -d'|' -f5)
  SERVER_PORT=$(echo $line | cut -d'|' -f6)
  func $IP $PORT $USER $PAWD $SERVER_NAME $SERVER_PORT
done <./server.txt
