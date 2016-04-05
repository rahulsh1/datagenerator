# datagenerator
Random Data generation based on SQL Schema

[![Build Status](https://travis-ci.org/rahulsh1/datagenerator.svg?branch=master)](https://travis-ci.org/rahulsh1/datagenerator)


### Pre-requisites
- JDK 1.7/1.8
- Maven 3.x

Since one of the dependencies `jsqlparser` required for this project is not under maven repo, this has to be manually added to your local repository.

- Download  [jsqlparser-0.7.0.jar](https://sourceforge.net/projects/jsqlparser/files/)
- Extract it  `jar xvf  jsqlparser-0.7.0.jar`
- Deploy the file to local repo (Assuming you extracted it under /tmp)
 
       mvn install:install-file
         -Dfile=/tmp/jsqlparser/lib/jsqlparser.jar
         -DgroupId=net.sf
         -DartifactId=jsqlparser
         -Dversion=0.7.0
         -Dpackaging=jar
         -DgeneratePom=true


### Build
Download all sources and build with maven. Maven will download the correct dependencies.

    $ git clone https://github.com/rahulsh1/datagenerator.git
    $ cd datagenerator
    $ mvn install

If you get this error

[ERROR] Failed to execute goal on project datagenerator: 
Could not resolve dependencies for project test:datagenerator:jar:1.0.0-SNAPSHOT: 
Could not find artifact net.sf:jsqlparser:jar:0.7.0 in central (http://repo.maven.apache.org/maven2) -> [Help 1]

> Make sure the jsqlparser is deployed correctly in your local repo.


