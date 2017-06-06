#!/bin/sh

# ---------------------------------------------------------------------------
# Date:2017.6.1 # allin.sh #  
#
# Copyright (c) 2015-2017 # created by ajay.hao  All rights reserved.
# ---------------------------------------------------------------------------
# Version : 1.0.6
# Last update : 2017-06-02

BUILD_PATH=/opt/deploy
SRC_PATH=/opt/deploy/src
WAR_PATH=/opt/deploy/war
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
    echo -e "\n---------------------------------------------------------\nError : APPNAME or BRANCH NAME is Missed !\nPLS excute 'sh allin.sh appname branchname' !\nExample : sh allin.sh myApp master\n---------------------------------------------------------"
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
mvn clean install  -U -Dmaven.test.skip=true -Dautoconfig.strict=false;


#[init war folder]
if [ ! -d "$WAR_PATH" ]; then
		echo -e "\nWarPath not exists.. Create new WarPath: $WAR_PATH ... \n"
  	mkdir -p $WAR_PATH
  else
	  echo -e "\nClean WarPath ... : $WAR_PATH/$appname ... \n"	  
		rm -rf $WAR_PATH/"$appname".war
fi

#[find warfile and copy to warpath]
cp -rf `find $SRC_PATH/"$appname" -name "*.war"` $WAR_PATH/"$appname".war

#[unpack war to deploy path]
if [ -f "$WAR_PATH/$appname.war" ];
then
	#[init deploy folder]
	if [ ! -d "$DEPLOY_PATH" ]; then
			echo -e "\nDeployPath not exists.. Create new DeployPath: $DEPLOY_PATH ... \n"
	  	mkdir -p $DEPLOY_PATH
	  else
		  echo -e "\nClean DeployPath ... : $DEPLOY_PATH ... \n"	  
			rm -rf $DEPLOY_PATH/*
	fi

	
	cd $WAR_PATH
	#$JAVA_HOME/bin/jar -xvf $WAR_PATH/$appname.war $DEPLOY_PATH
	unzip -o "$appname".war -d $DEPLOY_PATH
	sh $BIN_PATH/tomcat-ctl.sh $instname restart
	sh $BIN_PATH/checktomcat.sh
else
	echo "$b"
	echo "$b"
	echo -e "!!! compile is Failure . Pls check your src && compile again !"
	echo "$b"
	exit;
fi
}

#===================
#Main Function Here
#===================
InitAndDeploy $1 $2


