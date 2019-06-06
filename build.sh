#!/bin/bash

echo ===========================
echo 自动打包脚本启动
echo ===========================

if [ ! -f "./pom.xml" ]; then
    echo "请将脚本放置与项目根目录与pom.xml同级"
    exit 1;
fi

echo ""
echo "删除target"
rm -rf target

echo ""
echo "打包项目"
mvn clean package -Dmaven.test.skip=true


echo ===========================
echo 自动打包脚本完成
echo ===========================
