# Ungdomsligan

## Krav

* Postgresql-databas
* Java 8
* Eventor API-nyckel

## Konfiguration

* Skapa databasschema
* Sätt datum

## Process

1. FetchMedlemmar
2. FetchResultat
3. FetchTavlingar
4. CalculateResult
5. Kör resource/script.sql
6. Importera ungdomsligan.csv till Google docs 
  //
  
## Postgres med docker compose: 
  
  $ docker-compose up
  
  Gå in i psql:
  
  $ psql -U postgres -h localhost -d eventor
  
  Ladda in datasbasscriptet: 
  
  eventor#  \i '/Users/helena/Dropbox (Personal)/7.code/1.fromGithub/ungdomsligan/common/src/main/resources/createdatabase.sql'

    eventor#  \i '/Users/helena/Dropbox (Personal)/7.code/1.fromGithub/ungdomsligan/common/src/main/resources/script.sql'

  