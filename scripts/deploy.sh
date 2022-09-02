#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

ssh -i /root/.ssh/id_rsa root@${SERVER_HOST} << EOF
  cd /root/jenkins/workspace/hyperledger-fabric-transfer-server/scripts
  chmod 755 *
  ./stop.sh
  ./start.sh
  ./health.sh
EOF
