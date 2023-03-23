#! /bin/bash
echo '---- Lauching docker remover ----'
docker stop dev-container 
docker rm dev-container
docker image rm dev-image
echo '---- All instances of docker stopped ----'