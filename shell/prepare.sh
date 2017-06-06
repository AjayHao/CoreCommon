#!/bin/sh

# ---------------------------------------------------------------------------
# Date:2017.6.1 # prepare.sh #  
#
# Copyright (c) 2016-2017 # created by ajay.hao  All rights reserved.
# ---------------------------------------------------------------------------
# Version : 1.0.0
# Last update : 2017-06-06

BUILD_PATH=/opt/deploy
SRC_PATH=/opt/deploy/src
BIN_PATH=/home/admin/ggbin
source  ~/.bash_profile

core=CoreCommon
corecommonurl="git@github.com:AjayHao/""$core"".git"
publicpompath="$SRC_PATH"/"$core"/public-pom
corecommonpath="$SRC_PATH"/"$core"/core-common



Prepare()
{
#[init build folder]
if [ ! -d "$BUILD_PATH" ]; then
		mkdir $BUILD_PATH
fi

if [ ! -d "$SRC_PATH" ]
	then
		mkdir $SRC_PATH
	else
		rm -rf $BUILD_PATH/src/*
fi

cd $SRC_PATH
echo -e "\nClone $core from repository ... \n"
git clone $corecommonurl
cd $SRC_PATH/"$core"
echo -e "\nGit Checkout master ... \n"
git checkout master;
echo -e "\nNow you are `git status` !!! \n"

cd $publicpompath;
echo -e "\nReady to Install public pom ...\n"
mvn clean install  -U -Dmaven.test.skip=true -Dautoconfig.strict=false;
echo -e "\nPublic-pom installed\n"

cd $corecommonpath;
echo -e "\nReady to install CoreCommon ...\n"
mvn clean install  -U -Dmaven.test.skip=true -Dautoconfig.strict=false;
echo -e "\nCoreCommon setup finished\n"
}

#===================
#Main Function Here
#===================
Prepare