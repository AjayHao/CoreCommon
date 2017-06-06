#!/bin/sh

# ---------------------------------------------------------------------------
# CheckTomcat.sh Check Listen Port 8080 && 20881
#
# Copyright (c) 2015-2017 ajay.hao  All rights reserved.
# ---------------------------------------------------------------------------
# version: 1.0.9. Beta
# build date: 20150620

# Uncomment to change the location of where runtime instances will be installed
# and override any possible setting via the instance script

BIN_PATH=/home/admin/ggbin

for ((i=1;i<90;i++))
do
  sleep 1
	echo "Tomcat is starting ... $i seconds "

	if [[ `/usr/sbin/lsof -i:8080 | grep java | awk '{printf $10}'` == "(LISTEN)" ]]
	then
		echo "$b"
		echo "It's OK !!! Tomcat is running ! Listen on 8080 ! "
		break
	else
		echo "$b"
		echo "OMG !!! Tomcat is not running ! Pls check tomcat logs !"
	fi

done
if [ $i = 90 ];then
echo -e "\n!!!----------------------------------------------------------------------\nTomcat started failed\n90s is too long for restarting Tomcat\nPlease check your tomcat Log\n!!!----------------------------------------------------------------------\n"
fi 
