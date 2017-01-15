#!/bin/bash

cd /home/ubuntu
mkdir server-artifacts
cd server-artifacts
#wget http://a.mbbsindia.com/tomcat/tomcat-8/v8.0.32/bin/apache-tomcat-8.0.32.tar.gz
#tar xzf apache-tomcat-8.0.32.tar.gz
#echo "export CATALINA_HOME=\"/opt/apache-tomcat-8.0.32\"" >> ~/.bashrc
source ~/.bashrc
cd tomcat/webapps
sudo rm -R platform
sudo rm -R platform.war

#sudo /home/ubuntu/server-artifacts/tomcat/bin/catalina.sh stop
sudo /usr/local/bin/supervisorctl -u vedantu -p v3d4ntu@123 -c /home/ubuntu/server-artifacts/supervisor/supervisord.conf  stop all