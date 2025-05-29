#!/bin/bash

# 상대 경로로 Docker Compose 파일 지정
COMPOSE_FILE="./container/docker-compose.yaml"

# docker-compose.yml 파일 확인
if [ -f "$COMPOSE_FILE" ]; then
  echo "[$COMPOSE_FILE]에서 Docker Compose 실행 중..."

  # docker-compose 실행
  docker compose -f "$COMPOSE_FILE" up -d

  # 실행 결과 확인
  if [ $? -eq 0 ]; then
    echo "Docker Compose 실행 성공!"
  else
    echo "Docker Compose 실행 실패!"
  fi
else
  echo "Docker Compose 파일이 [$COMPOSE_FILE]에 존재하지 않습니다!"
fi

echo "Vault 서비스가 시작될 때까지 2초 대기..."
sleep 2


docker exec -it vault /bin/sh -c "export VAULT_ADDR='http://0.0.0.0:8200' && vault login okestro1"

docker exec -it vault /bin/sh -c "export VAULT_ADDR='http://127.0.0.1:8200' && vault kv put secret/mission1/db \
  url='jdbc:mariadb://localhost:3306/mission1?characterEncoding=UTF-8' \
  username='root' \
  password='okestro1' \
  driver-class-name='org.mariadb.jdbc.Driver'"

# 저장 확인
docker exec -it vault /bin/sh -c "export VAULT_ADDR='http://127.0.0.1:8200' && vault kv get secret/mission1/db"

echo "Vault 설정이 완료되었습니다."

