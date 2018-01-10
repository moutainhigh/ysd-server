#!/bin/bash
env=$1
if [ ! -n "$env" ]; then
	echo '参数1，打包环境不能为空'
	exit 2
fi
mvn clean package -P${env} -Dmaven.test.skip=true -f ../pom.xml
