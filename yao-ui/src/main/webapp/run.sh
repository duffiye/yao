#!/bin/sh
echo 'build static resources'
yarn build
# kill old container
if docker ps | grep -i yao-ui
    then
        echo 'kill old container'
        docker kill yao-ui
fi

# remove old container
if docker ps -a | grep -i yao-ui
    then
        echo 'rm old container'
        docker rm yao-ui
fi

# remove old images
if docker images | grep docker_yao-ui:latest
    then
        echo 'remove old image'
        docker rmi docker_yao-ui:latest
fi

echo 'build image'
docker build --rm -t docker_yao-ui:latest .

echo '开始推送镜像到docker hub ...'
docker tag docker_yao-ui:latest y3tu/yao-ui:latest
docker push y3tu/yao-ui:latest
echo '镜像推送结束 end...'

#echo 'run docker container'
#docker run -d -p 8080:80 --name yao-ui -d docker_yao-ui:latest