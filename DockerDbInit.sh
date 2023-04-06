#! /bin/bash
echo '---- Lauching docker script ----'
echo '---- Generating image ----'
sudo docker build -t dev-image ./
echo '---- Generating container ----'
sudo docker run -d --name dev-container -p 5432:5432 dev-image