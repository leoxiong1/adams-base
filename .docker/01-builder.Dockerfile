FROM ubuntu

# Install dependencies.
RUN sed -ie 's/archive.ubuntu.com/nz.archive.ubuntu.com/g' /etc/apt/sources.list && \
    apt update && \
    DEBIAN_FRONTEND=noninteractive apt install -y --no-install-recommends \
        curl \
        imagemagick \
        libxrender1 \
        libxi6 \
        libxtst6 \
        texlive-full \
        unzip && \
    rm -rf /var/lib/apt/lists/*

# Configure tzdata.
RUN ln -fs /usr/share/zoneinfo/Pacific/Auckland /etc/localtime && \
    dpkg-reconfigure --frontend noninteractive tzdata

# Install Oracle JDK 8u202b08.
RUN curl -H 'Cookie: oraclelicense=accept-securebackup-cookie' -L https://download.oracle.com/otn-pub/java/jdk/8u202-b08/1961070e4c9b4e26a04e7f5a083f551e/jdk-8u202-linux-x64.tar.gz > /tmp/jdk.tar.gz && \
    sha256sum /tmp/jdk.tar.gz | grep 9a5c32411a6a06e22b69c495b7975034409fa1652d03aeb8eb5b6f59fd4594e0 && \
    mkdir -p /opt/jdk && \
    tar -C /opt/jdk --strip-components 1 -zxf /tmp/jdk.tar.gz && \
    rm /tmp/jdk.tar.gz

ENV PATH "/opt/jdk/bin:${PATH}"

# Install Maven.
RUN curl https://www-us.apache.org/dist/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz > /tmp/maven.tar.gz && \
    sha512sum /tmp/maven.tar.gz | grep fae9c12b570c3ba18116a4e26ea524b29f7279c17cbaadc3326ca72927368924d9131d11b9e851b8dc9162228b6fdea955446be41207a5cfc61283dd8a561d2f && \
    mkdir -p /opt/maven && \
    tar -C /opt/maven --strip-components 1 -zxf /tmp/maven.tar.gz && \
    rm /tmp/maven.tar.gz

ENV M2_HOME /opt/maven
ENV PATH "${M2_HOME}/bin:${PATH}"

# Create user with same UID/GID as host.
ARG UID
ARG GID

RUN groupadd -g $GID adams && \
    useradd -m -u $UID -g $GID -o -s /bin/bash adams

RUN mkdir /adams && chown adams:adams /adams

USER adams

RUN mkdir ~/.m2

WORKDIR /adams