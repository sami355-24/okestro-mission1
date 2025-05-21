#!/bin/bash

# 상대 경로로 Docker Compose 파일 지정
COMPOSE_FILE="./container/docker-compose.yaml"

# docker-compose.yml 파일 확인
if [ -f "$COMPOSE_FILE" ]; then
  echo "[$COMPOSE_FILE]에서 Docker Compose 실행 중..."

  # docker-compose 실행
  docker-compose -f "$COMPOSE_FILE" up -d

  # 실행 결과 확인
  if [ $? -eq 0 ]; then
    echo "Docker Compose 실행 성공!"
  else
    echo "Docker Compose 실행 실패!"
  fi
else
  echo "Docker Compose 파일이 [$COMPOSE_FILE]에 존재하지 않습니다!"
fi