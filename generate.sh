#!/usr/bin/env bash

FILES_CNT=20
DIR='/e/files'
MAX_NUMBERS=30000000
MAX_VALUES=999999999

mkdir -p ${DIR}
for i in $(seq 1 ${FILES_CNT}); do
    FILENAME="${DIR}/file_$i.txt"
    shuf -i 0-${MAX_VALUES} -n ${MAX_NUMBERS} | paste -s -d ',' | head --bytes -1 > ${FILENAME}
    echo "File created: ${FILENAME}"
done
