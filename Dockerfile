FROM ubuntu:latest
LABEL authors="okestro"

ENTRYPOINT ["top", "-b"]