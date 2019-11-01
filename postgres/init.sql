DROP TABLE resultat;
DROP TABLE lopare;
DROP TABLE tavling;

CREATE DATABASE eventor;

CREATE TABLE lopare (
id VARCHAR(40) PRIMARY KEY,
fornamn VARCHAR(40),
efternamn VARCHAR(40),
eventorid VARCHAR(20),
fodelsedatum VARCHAR(20),
vinster INT,
hundrapoangare INT,
poang INT
);

CREATE TABLE tavling (
id VARCHAR(40) PRIMARY KEY,
namn VARCHAR(100),
eventorid VARCHAR(40),
eventclassificationid INT,
eventstatusid INT,
disciplineid INT,
eventform VARCHAR(40)
);

CREATE TABLE resultat (
id VARCHAR(40) PRIMARY KEY,
lopare VARCHAR(40) REFERENCES lopare(id),
tavling VARCHAR(40) REFERENCES tavling(id),
status VARCHAR(20),
placering INT,
timediff VARCHAR(20),
poangreduktion INT,
maxpoang INT,
poang INT,
klass VARCHAR(40),
baseclass VARCHAR(10),
classtypeid INT,
baseclassid INT
);

