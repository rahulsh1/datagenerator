# datagenerator
Random Data generation based on SQL Schema

[![Build Status](https://travis-ci.org/rahulsh1/datagenerator.svg?branch=master)](https://travis-ci.org/rahulsh1/datagenerator)


### Pre-requisites
- JDK 1.7/1.8
- Maven 3.x

Since one of the dependencies `jsqlparser` required for this project is not under maven repo, this has to be manually added to your local repository.

- Download  [jsqlparser-0.7.0.jar](https://sourceforge.net/projects/jsqlparser/files/)
- Extract it  `jar xvf  jsqlparser-0.7.0.jar`
- Deploy the `jsqlparser.jar` file to local repo. Assuming you extracted it under `/tmp`, run the following:
 
       mvn install:install-file
         -Dfile=/tmp/jsqlparser/lib/jsqlparser.jar
         -DgroupId=net.sf
         -DartifactId=jsqlparser
         -Dversion=0.7.0
         -Dpackaging=jar
         -DgeneratePom=true


### Schema File
The Schema file is the crux of this entire generation. TODO 
   
    CREATE TABLE "APP_SESSION"
    (	
    "ID" NUMBER '##R',
    "START_DATE" TIMESTAMP (0),
    "END_DATE" TIMESTAMP (0),
    "CONTEXT" VARCHAR2(255) 'Context\d{1,9}',
    "PROCESSED" CHAR(1) 'Y',
    "EVENT_URI" VARCHAR2(50) 'event\d{1,10}\@example\.com',
    "EVENT_TYPE" CHAR(2) '(CR|ME)',
    "EVENT_AT" TIMESTAMP (0)
    );
    
    CREATE TABLE "APP_SESSION_EVENT"
    (	
    "ID" NUMBER '##R',
    "APP_ID" NUMBER '##R',
    "URL" VARCHAR2(50) 'file:\/\/data[0-1]/\d{5,5}\.htm',
    "CREATION_DATE" TIMESTAMP (0),
    "DURATION" NUMBER '\d{3,5}',
    "SOURCE_TYPE" CHAR(2) '(XY|CD|RS)',
    "TYPE" VARCHAR2(20) '(E1|E2|E3|E4|E5)',
    "TAGKEY" VARCHAR2(50) 'Tag([1-9]|1[0-9]|20)',
    "TAGVALUE" VARCHAR2(2000) 'Value([1-9]|1[0-9]|20)'
    );



### Build
Download all sources and build with maven. Maven will download the correct dependencies.

    $ git clone https://github.com/rahulsh1/datagenerator.git
    $ cd datagenerator
    $ mvn install

If you get this error,  make sure the jsqlparser is deployed correctly in your local repo.

    [ERROR] Failed to execute goal on project datagenerator: 
    Could not resolve dependencies for project test:datagenerator:jar:1.0.0-SNAPSHOT: 
    Could not find artifact net.sf:jsqlparser:jar:0.7.0 in central (http://repo.maven.apache.org/maven2) -> [Help 1]


### Run
By default, the `schema.sql` is picked from the current directory. Also the records are generated under `./data`. This can be changed in the `pom.xml`
To generate 1000000 rows, pass `-Dnumrows=1000000`.

    mvn install -Prun -Dnumrows=5
    
    Generating Records for APP_SESSION Bulk:false
    ..
    Records generated for APP_SESSION in /Users/oraclesdp/oracle/rndcode/poc/jdatagenerator/data/APP_SESSION.dat Records:5 - Time taken: 0.006 seconds.
    Generating Records for APP_SESSION_EVENT Bulk:false
    ..
    Records generated for APP_SESSION_EVENT in /Users/oraclesdp/oracle/rndcode/poc/jdatagenerator/data/APP_SESSION_EVENT.dat Records:5 - Time taken: 0.005 seconds.
    Done

### Bulk Mode
This is a special mode supported for Oracle DB which allows bulk insert operations. The generated file can be used in the following control file for `sqlldr`

    $ cat app_session.ctl
      load data
      infile '/tmp/data/APP_SESSION.dat'
      into table APP_SESSION
      fields terminated by ","
      (ID, START_DATE, END_DATE, CONTEXT, PROCESSED, EVENT_URI, EVENT_TYPE, EVENT_AT)

    $ sqlldr user/password control=/tmp/control/app_session.ctl

## License

MIT
