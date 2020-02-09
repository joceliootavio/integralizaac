FROM tomcat:7.0.99-jdk8-openjdk

COPY ./target/integralizaac/WEB-INF/lib/postgresql-9.0-801.jdbc4.jar /usr/local/tomcat/lib
COPY ./target/integralizaac.war /usr/local/tomcat/webapps

RUN apt-get update

# make sure that locales package is available
RUN apt-get install --reinstall -y locales

# uncomment chosen locale to enable it's generation
RUN sed -i 's/# pt_BR.UTF-8 UTF-8/pt_BR.UTF-8 UTF-8/' /etc/locale.gen

# generate chosen locale
RUN locale-gen pt_BR.UTF-8

# set system-wide locale settings
ENV LANG pt_BR.UTF-8  
ENV LANGUAGE pt_BR:pt  
ENV LC_ALL pt_BR.UTF-8  

# verify modified configuration
RUN dpkg-reconfigure --frontend noninteractive locales