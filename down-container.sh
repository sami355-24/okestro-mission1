#!/bin/bash

cd ./container && docker compose down

if [ $? -eq 0 ]; then
  echo "Docker 컨테이너 종료 성공"
else
  echo "Docker 컨테이너 종료 실패"
fi