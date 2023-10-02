# Ungdomsligan

## Krav

* Postgresql-databas
* Java 8
* Eventor API-nyckel

## Konfiguration

* Skapa databasschema
* Sätt rätt datum för åldersspannet i script.sql, FetchResultat.java & EventorTest.java

## Process

1. FetchMedlemmar
2. FetchResultat (Fånga nya k värden / eventor events, lägg till i BaseClass)
3. FetchTavlingar
4. CalculateResult
5. Kör resource/script.sql
6. Importera ungdomsligan.csv till Google docs 
  //
  
## Postgres med docker compose: 

Spinn upp en postgres container och kör init-scriptet som ligger under docker/postgres  
  $ docker-compose -f docker/docker-compose.yml up


For DB-access i conteinter:
 $ docker exec -it ungdomsligan-postgres-1  (or just connect with IntelliJ db)
  
  Gå in i psql:
  '# psql -U postgres -h localhost -d eventor
  
  Ladda in datasbasscriptet - behövs in om man kör docker-compose med init-scriptet:  

    eventor#  \i '/Users/helena/Dropbox (Personal)/7.code/1.fromGithub/ungdomsligan/common/src/main/resources/script.sql'

  