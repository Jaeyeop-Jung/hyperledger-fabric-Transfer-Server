#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

function switch_proxy() {

    echo "> 전환할 Port: ${IDLE_PORT}"
    echo "> Port 전환"
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    echo "> 엔진엑스 Reload"
    sudo service nginx reload

    if [ ${IDLE_PORT} -eq 8081 ]; then
      echo 8082 > IDLE_PORT
    else
      echo 8081 > IDLE_PORT
    fi
}