#!/bin/sh

# ---------------------------------------------------------------------------
# compile.sh compile src & copy the war file
#
# Copyright (c) 2015-2017 ajay.hao  All rights reserved.
# ---------------------------------------------------------------------------
# version: 1.0.9. Beta
# build date: 20150620

# So cool the shell and the writer !

source  ~/.bash_profile
deploypath=/opt/deploy/war
SRC_PATH=/opt/deploy/src
WAR_PATH=/opt/deploy/war
appname=`ls $SRC_PATH`
qianweb2check=`ls /opt/deploy/src | grep qianweb2`
cashierdir=/opt/deploy/src/cashier
personaldir=/opt/deploy/src/personal

if [ ! -d "$SRC_PATH" ]; then
  mkdir -p $SRC_PATH
fi
if [ ! -d "$WAR_PATH" ]; then
  mkdir -p $WAR_PATH
fi

if [ "$appname" == "transcore" ];
then
	cd $SRC_PATH/$appname;
	chmod +x package.sh;
	sh package.sh;
	rm -rf /opt/application/*
	cp -rf `find $SRC_PATH -name "*.war"` /opt/application/"$appname".war
elif [ "$qianweb2check" == "qianweb2" ];
then	
	rm -rf $cashierdir
	rm -rf $personaldir
	if [ ! -d "$cashierdir" ]; then
		mkdir -p $cashierdir
	fi
	if [ ! -d "$personaldir" ]; then
                mkdir -p $personaldir
        fi
	cd $SRC_PATH
	git clone git@git.gungunqian.cn:qiangungun/cashier.git
	cd $cashierdir
	mvn clean install -U -Dmaven.test.skip=true -Dautoconfig.strict=false
	cd $SRC_PATH
        git clone git@git.gungunqian.cn:qiangungun/personal.git
        cd $personaldir 
        mvn clean install -U -Dmaven.test.skip=true -Dautoconfig.strict=false

	cd $SRC_PATH/$appname/
	mvn clean install  -U -Dmaven.test.skip=true -Dautoconfig.strict=false -Dcashier.dir=../cashier -Dpersonal.dir=../personal
		
	rm -rf /opt/application/*
	cp -rf `find $SRC_PATH/qianweb2 -name "qianweb*.war"` /opt/application/qianweb2.war
else
        cd $SRC_PATH/*;
        mvn clean install  -U -Dmaven.test.skip=true -Dautoconfig.strict=false;
	rm -rf /opt/application/*
	cp -rf `find $SRC_PATH -name "*.war"` /opt/application/"$appname".war
fi
#mvn clean install -U -Dmaven.test.skip=true -Dautoconfig.strict=false
