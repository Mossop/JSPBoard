insert into JSPBoard.Folder (id,parent,name) select id,parent,name from Folder where board="wswym";
insert into JSPBoard.Folder (parent,name) select -1,name from Board;
update JSPBoard.Folder set parent=LAST_INSERT_ID() where parent=0;

insert into JSPBoard.Thread (id,folder,name,owner,created) select id,folder,name,owner,created from Thread where board="wswym";
update JSPBoard.Thread set folder=LAST_INSERT_ID() where folder=0;

insert into JSPBoard.Message (id,thread,owner,created,content) select id,thread,author,created,content from Message;

insert into JSPBoard.Person (id,fullname,email,nickname,mobilephone) select id,fullname,email,nickname,phone from People;
insert into JSPBoard.File (id,name,filename,message,description,mimetype) select id,name,filename,message,description,mimetype from File;
insert into JSPBoard.Login (id,password,lastaccess,person) select id,password,lastaccess,person from User;
insert into JSPBoard.EditedMessage (message,person,altered) select message_id,person,altered from EditedMessage;
insert into JSPBoard.UnreadMessage (message,person) select distinct message_id,person from UnreadMessage,User where user_id=id;

insert into JSPBoard.Groups
(id) values
("admin"),
("personadd"),
("folderadd"),
("messageadd"),
("loginadd"),
("personadmin"),
("folderadmin"),
("messageadmin"),
("loginadmin"),
("personview"),
("folderview"),
("messageview"),
("loginview");

insert into JSPBoard.UserGroup (id,group_id) values ('dave','admin');

update JSPBoard.Login set password=MD5("Tequila8791") where password=PASSWORD("Tequila8791");
update JSPBoard.Login set password=MD5("nma640v") where password=PASSWORD("nma640v");
update JSPBoard.Login set password=MD5("pear") where password=PASSWORD("pear");
update JSPBoard.Login set password=MD5("apple") where password=PASSWORD("apple");
update JSPBoard.Login set password=MD5("d615vrc") where password=PASSWORD("d615vrc");
update JSPBoard.Login set password=MD5("postman") where password=PASSWORD("postman");
