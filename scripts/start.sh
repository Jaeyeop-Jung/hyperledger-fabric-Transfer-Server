ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/root/jenkins/workspace/hyperledger-fabric-transfer-server/build/libs

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR NAME: $JAR_NAME"
echo "> $JAR_NAME 실행권한 추가"
chmod +x $JAR_NAME
echo "> $JAR_NAME 실행"

if [ $(find_idle_port) -eq 8081 ]
then
  echo "> $JAR_NAME 를 profile=home, port8081 로 실행합니다."
  nohup java -jar \
      -Dspring.profiles.active="home, port8081" \
      $JAR_NAME > /root/nohup.out 2>&1 &

else
  echo "> $JAR_NAME 를 profile=home, port8082 로 실행합니다."
  nohup java -jar \
      -Dspring.profiles.active="home, port8082" \
      $JAR_NAME > /root/nohup.out 2>&1 &
fi