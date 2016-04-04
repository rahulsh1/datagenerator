--
-- Schema file with regex
-- No escaping is required for \
-- For fixed values, just put the entire value in there without any Regex (e.g PROCESSED column 'Y')

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

