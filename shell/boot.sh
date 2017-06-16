#!/bin/sh

# ---------------------------------------------------------------------------
# Date:2017.6.1 # boot.sh #  
#
# Copyright (c) 2015-2017 # created by ajay.hao  All rights reserved.
# ---------------------------------------------------------------------------
# Version : 1.0.6
# Last update : 2017-06-02

BUILD_PATH=/opt/deploy
SRC_PATH=/opt/deploy/src
BIN_PATH=/home/admin/ggbin
DEPLOY_PATH=/opt/application
source  ~/.bash_profile

instname=tomcat
appname="$1"
branchname="$2"

url="git@github.com:AjayHao/""$appname"".git"

InitAndDeploy()
{
if [ $# -ne 2 ]; then
    echo -e "\n---------------------------------------------------------\nError : APPNAME or BRANCH NAME is Missed !\nPLS excute 'sh boot.sh appname branchname' !\nExample : sh boot.sh myApp master\n---------------------------------------------------------"
    echo "$b"
    return 1
fi


#[init build folder]
if [ ! -d "$BUILD_PATH" ]; then
	  echo -e "\nBuildPath not exists.. Create new BuildPath: $BUILD_PATH ... \n"
		mkdir $BUILD_PATH
fi

#[init src folder]
if [ ! -d "$SRC_PATH" ]; then
		echo -e "\nSrcPath not exists.. Create new SrcPath: $SRC_PATH ... \n"
		mkdir $SRC_PATH
	else
	  echo -e "\nClean SrcPath ... : $SRC_PATH/$appname ... \n"	
		rm -rf $SRC_PATH/$appname
fi

cd $SRC_PATH
echo -e "\nInit $appname ... \n"
git clone $url
cd $SRC_PATH/$appname
echo -e "\n-------------------------------------------------------------------------\n\nCheck Branch Name ... \n"
git branch -r | grep $branchname
if [ $? = 0 ]
	then
		git branch -a;
		echo -e "\nGit Checkout $branch ... \n"
		git checkout $branchname;
		echo -e "\nNow you are `git status` !!! \n"
	else
		echo -e "!!!Branch $branchname is not exist . Please check the branch name !!!\n\n-------------------------------------------------------------------------\n"
		git branch -a;
		echo "$b"
		exit;
fi

#[maven install]
cd $SRC_PATH/$appname;
mvn clean install  -U -Dmaven.test.skip=true -Dautoconfig.strict=false spring-boot:run;


#[find warfile and copy to warpath]
cp -rf `find $SRC_PATH/"$appname" -name "*.war"` $WAR_PATH/"$appname".war
}

#===================
#Main Function Here
#===================
InitAndDeploy $1 $2


