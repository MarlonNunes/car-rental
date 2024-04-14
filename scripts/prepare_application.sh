#!/bin/bash

cd /home/ec2-user/car-rental

JAR_FILE=$(ls *.jar)
mv $JAR_FILE car-rental.jar