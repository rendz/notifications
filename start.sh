#!/bin/bash
CWD="$( pwd -P )"
SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

cd ${SCRIPTPATH}/infrastructure
docker-compose up -d
cd $CWD

until docker exec broker kafka-topics --list --bootstrap-server broker:9092 &> /dev/null
do
    echo "Waiting for kafka to start ..."
    sleep 1
done

docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic slack-notifications

docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic notifications

docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic sms-notifications
