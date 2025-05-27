#!/bin/bash

# 상대 경로로 Docker Compose 파일 지정
COMPOSE_FILE="./container/docker-compose.yaml"

# docker-compose.yml 파일 확인
if [ -f "$COMPOSE_FILE" ]; then
  echo "[$COMPOSE_FILE]에서 Docker Compose 실행 중..."

  # docker-compose 실행
  docker-compose -f "$COMPOSE_FILE" up -d
#  docker compose -f "$COMPOSE_FILE" up -d

  # 실행 결과 확인
  if [ $? -eq 0 ]; then
    echo "Docker Compose 실행 성공!"
  else
    echo "Docker Compose 실행 실패!"
  fi
else
  echo "Docker Compose 파일이 [$COMPOSE_FILE]에 존재하지 않습니다!"
fi

export VAULT_ADDR='http://localhost:8200'
vault login myroot

# 데이터베이스 접근 정보 저장
vault secrets enable -path=secret kv
vault kv put secret/myapp/db \
  url="jdbc:mariadb://localhost:3306/mission1?characterEncoding=UTF-8" \
  username="root" \
  password="okestro1" \
  driver-class-name="org.mariadb.jdbc.Driver"
