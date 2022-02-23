#!/bin/bash
CWD="$( pwd -P )"
SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

cd ${SCRIPTPATH}/infrastructure
docker-compose down
cd $CWD
