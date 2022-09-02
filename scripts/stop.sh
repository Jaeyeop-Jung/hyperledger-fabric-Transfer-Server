ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

echo "서버 호스트는 $SERVER_HOST 입니다."
echo "IDLE_PORT는 ${IDLE_PORT} 입니다."

if [ ${IDLE_PORT} -eq 8081 ]
then
  echo "> 8082 에서 구동 중인 애플리케이션 pid 확인"
  IDLE_PID=$(lsof -ti tcp:8082)
else
  echo "> 8081 에서 구동 중인 애플리케이션 pid 확인"
  IDLE_PID=$(lsof -ti tcp:8081)
fi

if [ -z ${IDLE_PID} ]
then
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi