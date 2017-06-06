#!/bin/sh

# ---------------------------------------------------------------------------
# tomcat Server Instance Runtime Control Script  modify from springtcServer tcruntimectl.sh
#
# Copyright (c) 2010-2011 ajay.hao  All rights reserved.
# ---------------------------------------------------------------------------
# version: 1.0.0. Beta
# build date: 20110707

# Uncomment to change the location of where runtime instances will be installed
# and override any possible setting via the instance script
INSTANCE_BASE=/usr

#function that prints out usage syntax
syntax () {
  echo "./tcruntime-ctl.sh instance_name cmd [options]"
  echo " "
  echo "  cmd is one of start | run | stop | restart | status"
  echo "    start             - starts a tc Runtime instance as a daemon process"
  echo "    run               - starts a tc Runtime instance as a foreground process"
  echo "    stop [timeout]    - stops a running tc Runtime instance, forcing termination"
  echo "                        of the process if it does not exit gracefully within"
  echo "                        timeout seconds [default: 5 seconds]"
  echo "    restart [timeout] - restarts a running tc Runtime instance, forcing"
  echo "                        termination of the process if it does not exit"
  echo "                        gracefully within timeout seconds [default: 5 seconds]"
  echo "    status            - reports the status of a tc Runtime instance"
  echo " "
  echo " "
  echo " "
}


getTOMCAT_VERSION () {
  # tomcat.version can contain just the version number (eg: 6.0.20.A)
  # or the full pathname (eg: /foo/bar/tomcat-6.0.20.A), so we need
  # to handle both. If TOMCAT_VER is already provided, just use that
  if [ -z "$TOMCAT_VER" ]; then
    if [ -r "$CATALINA_BASE/conf/tomcat.version" ]; then
      # read TOMCAT_VER2 < "$CATALINA_BASE/conf/tomcat.version"
      TOMCAT_VER=`cat "$CATALINA_BASE/conf/tomcat.version"`
    fi
  fi
}

setupCATALINA_HOME () {
	CATALINA_HOME=$CATALINA_BASE
  if [ ! -r "$CATALINA_HOME" ]; then
      echo "ERROR CATALINA_HOME directory $CATALINA_HOME does not exist or is not readable."
      exit 1
  fi
}

setupINSTANCE_BASE () {
  if [ -z "$INSTANCE_BASE" ]
  then
    FOUND_ARG=0
    for var in "$@"
    do
      if [ $FOUND_ARG -eq 1 ]
      then
        INSTANCE_BASE="$var"
        FOUND_ARG=0
        #echo "INFO Setting INSTANCE_BASE to $INSTANCE_BASE"
      fi
      if [ "$var" = "-n" ]
      then
        FOUND_ARG=1
      fi
    done
    # The directory where instances of tc Runtime will be created
    [ -z "$INSTANCE_BASE" ] && INSTANCE_BASE=`pwd -P`
  fi
}

noop () {
  echo -n ""
}

setupCATALINA_BASE () {
  if [ -z "$INSTANCE_NAME" ]; then
    echo "ERROR First parameter must be an instance name"
    syntax
    exit 1
  elif [ -z "$2" ]; then
    echo "ERROR Second parameter must be an instance command"
    syntax
    exit 1
  fi

  if [ "$2" = "create" ]; then
    if [ ! -x "$INSTANCE_BASE" ]; then
      echo "ERROR Instance directory not writeable (${INSTANCE_BASE})"
      exit 1
    else
      return
    fi
  fi

  CATALINA_BASE=$INSTANCE_BASE/$INSTANCE_NAME

  if [ ! -r "$CATALINA_BASE" ]; then
    echo "ERROR CATALINA_BASE directory $CATALINA_BASE does not exist or is not readable."
    exit 1
  fi
  if [ ! -d "$CATALINA_BASE" ]; then
    echo "ERROR CATALINA_BASE $CATALINA_BASE is not a directory."
    3exit 1
  fi

  CATALINA_BASE=`cd $CATALINA_BASE; pwd -P`
  INSTANCE_NAME=`basename $CATALINA_BASE`

}

isrunning() {
  #returns 0 if the process is running
  #returns 1 if the process is not running
  if [ -f "$CATALINA_PID" ];
    then
        PID=`cat "$CATALINA_PID"`
        #the process file exists, make sure the process is not running
        LINES=`ps -p $PID`
        PIDRET=$?
        if [ $PIDRET -eq 0 ];
        then
            export PID
            return 0;
        fi
        rm -f "$CATALINA_PID"
     else 
        # the prcess file does not exists, grep the process
        PID=`ps -ef | grep java | grep "$CATALINA_BASE" | grep -v grep |awk '{print $2}'`
        if [ ! -z "$PID" ]
          then 
            echo $PID > "$CATALINA_PID"
            export PID  
            return 0
        fi
    fi
    return 1
}


instance_start() {
    isrunning
    if [ $? -eq 0 ]; then
      echo "ERROR Instance is already running as PID=$PID"
      exit 1
    fi
    "$SCRIPT_TO_RUN" start
    sleep 2
    isrunning
    exit $?
}

instance_run() {
    isrunning
    if [ $? -eq 0 ]; then
      echo "ERROR Instance is already running as PID=$PID"
      exit 1
    fi
    # catalina.sh won't create a PID file when using the run command
    if [ ! -f "$CATALINA_PID" ]; then
      echo $$ > "$CATALINA_PID"
    fi
    exec "$SCRIPT_TO_RUN" run
}

instance_stop() {
    if [ -z "$3" ]; then
      WAIT_FOR_SHUTDOWN=60
    else
      # Add a leading zero to $3 else -n may be treated as a switch for echo
      echo "0$3" | grep "[^0-9]" > /dev/null 2>&1
      if [ "$?" -eq "0" ]; then
        # If the grep found something other than 0-9
        # then it's not an integer.
        WAIT_FOR_SHUTDOWN=5
      else
        WAIT_FOR_SHUTDOWN=$3
      fi
    fi

    isrunning
    if [ $? -eq 0 ]; then
        #tomcat process is running
        echo "Instance is running as PID=$PID, shutting down..."
        kill $PID
    else
        echo "Instance is not running. No action taken"
        return 0
    fi
    isrunning
    if [ $? -eq 0 ]; then
        #process still exists
        echo "Instance is running PID=$PID, sleeping for up to $WAIT_FOR_SHUTDOWN seconds waiting for shutdown"
        i=0
        while [ $i -lt $WAIT_FOR_SHUTDOWN ]; do
            sleep 1
            isrunning
            if [ $? -eq 1 ]; then
                break
            fi
            i=`expr $i + 1`
        done
    fi
    isrunning
    if [ $? -eq 0 ];
    then
        echo "Instance is still running PID=$PID, forcing a shutdown"
        kill -9 $PID
    else
        echo "Instance shut down gracefully"
    fi
    if [ -f "$CATALINA_PID" ]; then
        rm -f "$CATALINA_PID"
    fi
}

instance_restart() {
    instance_stop $@
    if [ $? -eq 0 ]; then
        instance_start
    fi
    exit $?

}

instance_status() {
    isrunning
    if [ $? -eq 0 ]; then
      echo "STATUS Instance is RUNNING as PID=$PID"
    else
      echo "STATUS Instance is NOT RUNNING"
    fi
    exit 0
}

#Strip a trailing slash
INSTANCE_NAME=$1
INSTANCE_NAME=`echo $INSTANCE_NAME | sed 's/\/$//g'`

# MAIN SCRIPT EXECUTION
#setupdir $@
setupINSTANCE_BASE $@
setupCATALINA_BASE $@
getTOMCAT_VERSION $@
#getLAYOUT $@
setupCATALINA_HOME $@


echo "INFO Instance name:      $INSTANCE_NAME"
echo "INFO Instance base:      $INSTANCE_BASE"
echo "INFO tc Runtime location:${CATALINA_BASE}"
echo "INFO Binary dir:         ${CATALINA_HOME}"
echo "INFO tomcat  version:    ${TOMCAT_VER}"

CATALINA_PID="$CATALINA_BASE/logs/tcserver.pid"
SCRIPT_TO_RUN="$CATALINA_BASE/bin/catalina.sh"

export CATALINA_HOME
export CATALINA_BASE
export CATALINA_PID
export SCRIPT_TO_RUN
export LOGGING_CONFIG
export LOGGING_MANAGER
export INSTANCE_NAME

# Check cmd is expected
if [ $2 != "start" ] && [ $2 != "run" ] && [ $2 != "stop" ] && [ $2 != "restart" ] && [ $2 != "status" ]; then
    echo " "
    echo "ERROR Invalid command $2"
    echo " "
    syntax
    exit 1
else
    #execute the correct function
    instance_$2 $@
fi
