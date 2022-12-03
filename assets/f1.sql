create user u11 identified by u11 ;
#accessgrant resource, connect to u11;
#conn  u11/u11

create table etudiant
( id_etudiant number(11) NOT NULL , 
nom varchar2(45) NOT NULL ,
prenom varchar2(45) NOT NULL ,
primary key (id_etudiant)
) ;

create table gestionnaire 
( id_gest number (11) NOT NULL ,
login varchar2 (40) NOT NULL ,
password varchar2 (40) NOT NULL ,
prenom varchar (40) NOT NULL ,
primary key (id_gest)
) ;

create table livre 
(id_livre number (11) NOT NULL ,
titre varchar2 (100) NOT NULL ,
auteur varchar2 (40) NOT NULL ,
primary key ( id_livre)
) ;


create table Emprunt
( id_emprunt NUMBER(11) NOT NULL,
date_db  Date ,
date_ret Date ,
id_livre number (11) NOT NULL ,
id_etudiant number (11) NOT NULL ,
foreign key (id_livre) REFERENCES Livre ,
foreign key (id_etudiant) REFERENCES etudiant 
) ;


insert into etudiant values ('112' , 'KADIRI', 'hind' ) ;
insert into etudiant values ('113' , 'HOUAS' , 'yassir' ) ;
insert into etudiant values ('114' , 'YOUABD' , 'khadija' ) ;
insert into etudiant values ('115' , 'MEHDI' , 'akram' ) ;
insert into etudiant values ('116' , 'IDGHADDOUR' , 'khawla' ) ;
insert into etudiant values ('117' , 'MEZIANI' , 'chaimaa' ) ;
insert into etudiant values ('118' , 'MEZIAN' , 'achraf' ) ;
insert into etudiant values ('119' , 'AZOUAR' , 'hamza' ) ;
insert into etudiant values ('120' , 'MQADMI' , 'omar' ) ;
insert into etudiant values ('121' , 'NOUNSI' , 'ilham' ) ;
insert into etudiant values ('122' , 'BELHAJ' , 'ilyass' ) ;
insert into etudiant values ('123' , 'MAKHLOK' , 'meryem' ) ;
insert into etudiant values ('124' , 'MOUTAWAKIL' , 'marwa' ) ;
insert into etudiant values ('125' , 'BAALA' , 'achraf' ) ;
insert into etudiant values ('126' , 'AMINE' , 'mohamed' ) ;
insert into etudiant values ( '127' , 'ZAIRI' , 'nadia' ) ;
insert into etudiant values ( '128' , 'CHOROK' , 'ichrak' ) ;

delete from etudiant where (nom = 'MEHDI') ;

insert into livre values ( '00001' , 'les misérables' , 'victor hugo' ) ;
insert into livre values ( '00002' , 'tout en un' , 'claude deschamps' ) ;
insert into livre values ( '00003' , 'demain' , 'guillaum musso' ) ;
insert into livre values ( '00004' , 'the merchant of venice' , 'chakespeare' ) ;
insert into livre values ( '00005' , 'antigone' , 'jean anouill' ) ;

update livre SET id_livre='1500' where titre = 'les misérables' ;

insert into gestionnaire values ( 1234 , 'mekaoui' , 'ahmed' , 'bhyj' ) ;
insert into commande values (12003 , 000002 , 1234 ) ;

insert into Emprunt values ( '200008' , '01-01-2020' , '20-01-2020' ,'00003', '128' ) ;
insert into Emprunt values ( 200009 , '05-01-2020' , '25-01-2020' , 00003 , 121 ) ;

select id_etudiant , nom , prenom from etudiant where prenom='achraf' ;

select date_db ,date_ret ,id_livre , id_etudiant from Emprunt 
order by date_db ;

select date_db, date_ret , id_livre , id_etudiant from Emprunt 
order by date_ret desc ;

commit ;
select * from cat ;

