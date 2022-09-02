#!/usr/bin/env bash

function find_idle_port() {
  IDLE_PORT=$(cat IDLE_PORT)
  if [ ${IDLE_PORT} -eq 8082 ]
  then
    echo "8082"
  else
    echo "8081"
  fi
}