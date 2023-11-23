-- FÖR LISTOR

--Select alla tävlingar och alla klasser i varje tävling med BaseClass ID ohch baseClass namn, maxpoäng och lite mer info.
select distinct t.eventorid,  t.namn, r.klass, r.baseclass, r.baseclassid, r.maxpoang, r.classtypeid,  t.disciplineid, t.eventclassificationid, t.eventform
from resultat r
         left join tavling  t on t.id = r.tavling
order by t.namn, t.eventorid, r.klass;

--Select alla tävlingar och klasser och gruppera med antal deltagare i varje klass.
select distinct t.eventorid,  t.namn, count(*), r.klass, r.baseclass, r.baseclassid, r.maxpoang, r.classtypeid, t.disciplineid, t.eventclassificationid, t.eventform
from resultat r
         left join tavling  t on t.id = r.tavling
group by t.eventorid,  t.namn, r.klass, r.baseclass, r.maxpoang, r.classtypeid, r.baseclassid, t.disciplineid, t.eventclassificationid, t.eventform
order by t.namn, t.eventorid, r.klass;


--- Select väsentlig info om löpares alla tävlingar, resultat, placering, timediff, poängreduction och slutpoäng
select distinct l.eventorid, l.fornamn, l.efternamn, t.eventorid, t.namn, r.klass, r.baseclass, r.baseclassid, r.placering, r.poang, r.timediff, r.poangreduktion, r.maxpoang, r.classtypeid,  t.disciplineid, t.eventclassificationid, t.eventform
from resultat r
         left join tavling  t on t.id = r.tavling
         left join lopare l on l.id = r.lopare
order by l.eventorid;


--Select alla resultat för en specifik löpare:
select distinct r.placering, r.timediff, r.poangreduktion, r.poang, l.eventorid, l.fornamn, l.efternamn, t.eventorid,  t.namn, r.klass, r.baseclass, r.baseclassid, r.maxpoang, r.classtypeid,  t.disciplineid, t.eventclassificationid, t.eventform
from resultat r
         left join tavling  t on t.id = r.tavling
         left join lopare l on l.id = r.lopare
where t.eventorid = '45985';
-- Malte Fogeby: 162257

-- Select alla lopare
select distinct r.lopare, l.eventorId, l.fornamn, l.efternamn, l.fodelsedatum
from lopare l
         left join resultat r on r.lopare = l.id
order by l.eventorId;

-- Select antal löpare, grupperat på födelseår
from lopare
group by fodelsedatum
order by fodelsedatum;

