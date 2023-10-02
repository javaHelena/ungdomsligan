select distinct t.eventorid, t.namn, r.klass, r.baseclassid, r.classtypeid, r.baseclass, r.maxpoang
from resultat r
         inner join tavling t on r.tavling = t.id
order by t.eventorid, classtypeid;


select distinct t.eventorid, t.namn, r.klass, r.baseclassid, r.classtypeid, r.baseclass, r.maxpoang
from resultat r
         inner join tavling t on r.tavling = t.id
order by t.eventorid, classtypeid;



select *
from resultat
where lopare = 'a8507c22-e169-400f-a79a-17e14e9d14a8';

select distinct l.eventorid, l.fornamn, l.efternamn, r.placering, r.poang, r.timediff,  t.eventorid, t.namn, r.klass,  r.maxpoang, r.baseclass, r.baseclassid, r.classtypeid
from resultat r
         inner join tavling t on r.tavling = t.id
         inner join lopare l on r.lopare = l.id
order by l.eventorid;


where lopare = 'b6979e51-ee76-40be-b147-fbe2864ed793';


where lopare = 'd5942494-9cae-4680-9196-5fb3b5014c9b'; John Nyman

where lopare = 'a8507c22-e169-400f-a79a-17e14e9d14a8';  DAVID Nyman
