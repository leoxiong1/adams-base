FROM adams/develop:builder as packager

# Install Git.
USER root
RUN apt update && \
    DEBIAN_FRONTEND=noninteractive apt install -y --no-install-recommends \
        git && \
    rm -rf /var/lib/apt/lists/*

# Package ADAMS.
USER adams

RUN git clone --depth=1 https://github.com/leoxiong1/adams-base.git && \
    curl https://adams.cms.waikato.ac.nz/resources/settings.xml > ~/.m2/settings.xml

WORKDIR adams-base

# Package ADAMS.
RUN mvn -T 2C package -DskipTests=true





FROM ubuntu

# Install runtime dependencies.
RUN apt update && \
    DEBIAN_FRONTEND=noninteractive apt install -y --no-install-recommends \
        ca-certificates \
        curl \
        imagemagick \
        libxrender1 \
        libxi6 \
        libxtst6 && \
    rm -rf /var/lib/apt/lists/*

# Install Oracle JRE 8u202b08.
RUN curl -H 'Cookie: oraclelicense=accept-securebackup-cookie' -L https://download.oracle.com/otn-pub/java/jdk/8u202-b08/1961070e4c9b4e26a04e7f5a083f551e/jre-8u202-linux-x64.tar.gz > /tmp/jre.tar.gz && \
    sha256sum /tmp/jre.tar.gz | grep 9efb1493fcf636e39c94f47bacf4f4324821df2d3aeea2dc3ea1bdc86428cb82 && \
    mkdir -p /opt/jre && \
    tar -C /opt/jre --strip-components 1 -zxf /tmp/jre.tar.gz && \
    rm /tmp/jre.tar.gz

# Add packaged ADAMS.
COPY --from=packager /adams/adams-base/adams-core/target/adams-core-19.2.0-jar-with-dependencies.jar /adams/adams-core-19.2.0-jar-with-dependencies.jar

ENTRYPOINT ["/opt/jre/bin/java", "-cp", "/adams/adams-core-19.2.0-jar-with-dependencies.jar", "adams.gui.Main"]