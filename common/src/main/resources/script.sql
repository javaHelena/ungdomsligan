select
l.fornamn, l.efternamn, t.namn, t.eventclassificationid, t.eventstatusid, r.klass, r.classtypeid, r.baseclassid
from resultat r
inner join lopare l on l.eventorid = r.lopare
inner join tavling t on t.eventorid = r.tavling;

select l.fornamn, l.efternamn, l.fodelsedatum, l.poang, l.vinster, l.hundrapoangare from lopare l
where l.fodelsedatum > '2002-12-31'
order by l.poang desc, l.vinster desc, l.hundrapoangare desc;


select  l.eventorid, l.fornamn, l.efternamn, l.fodelsedatum, l.poang, l.vinster, l.hundrapoangare into tmp from lopare l
where l.fodelsedatum > '2002-12-31' and l.fodelsedatum < '2019-01-01'
order by l.poang desc, l.vinster desc, l.hundrapoangare desc;

\copy lopare TO '/Users/helena/ungdomsligan-lopare.csv' DELIMITER ',' CSV HEADER;
\copy tavling TO '/Users/helena/ungdomsligan-tavling.csv' DELIMITER ',' CSV HEADER;
\copy resultat TO '/Users/helena/ungdomsligan-resultat.csv' DELIMITER ',' CSV HEADER;
\copy tmp TO '/Users/helena/ungdomsligan.csv' DELIMITER ',' CSV HEADER;