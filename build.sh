#!/bin/bash

source build.config

echo "$skipCI";
echo "$profile"

if [ "$skipCI" == "false" ] 
then
tag=$tag
branch=default
if [ "$CI_BRANCH" == "master" ] || [ "$CI_BRANCH" == "develop" ] 
	then 
		branch="$CI_BRANCH"
fi
#echo $tag
if [ "$tag" == "" ] 
	then 
		tag=$CI_COMMIT_ID
fi
echo $tag
echo $profile

HOME="/home/rof/src/bitbucket.org/vedantu"
REPO=vedantu-platform-workspace
SUBSYSTEM=platform

echo ${BUILD_PROFILE}

mvn clean install
mvn compile -P "${BUILD_PROFILE}"
mvn package -P "${BUILD_PROFILE}"

filename="${HOME}/${REPO}/${SUBSYSTEM}/target/build/${SUBSYSTEM}.war"
mkdir -p "${HOME}/${REPO}/${SUBSYSTEM}/target/build"

echo ${filename}

mv "${HOME}/${REPO}/${SUBSYSTEM}/target/${SUBSYSTEM}.war" ${filename}
cp "${HOME}/${REPO}/${SUBSYSTEM}/appspec.yml" "${HOME}/${REPO}/${SUBSYSTEM}/target/build/appspec.yml"
cp -R "${HOME}/${REPO}/${SUBSYSTEM}/deploymentScripts" "${HOME}/${REPO}/${SUBSYSTEM}/target/build/deploymentScripts"
tar -czvf "${BUILD_PROFILE}-${tag}.tar.gz" -C "${HOME}/${REPO}/${SUBSYSTEM}/target/build" .
mkdir -p "${HOME}/${REPO}/${SUBSYSTEM}/target/build/tar"
#mv "${profile}-${tag}.tar.gz" "${HOME}/${REPO}/${SUBSYSTEM}/target/build/tar/${tag}.tar.gz"
aws s3 cp . "s3://builds-vedantu/${REPO}/${branch}/${SUBSYSTEM}/" --recursive --exclude "*" --include "*.tar.gz"
#aws s3 cp "${BUILD_PROFILE}-${tag}.tar.gz" "s3://builds-vedantu/${REPO}/${branch}/${SUBSYSTEM}/"
fi