#!/bin/bash
CWD="$( pwd -P )"
SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

docker build -t notifications-server .

docker run --network=host notifications-server -a
