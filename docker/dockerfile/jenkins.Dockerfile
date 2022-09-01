FROM jenkins/jenkins:lts-jdk11

USER root

RUN mkdir -p /var/testimagefile
RUN mkdir -p /var/imagefile
