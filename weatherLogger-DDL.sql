create table if not exists weather_data (
  id  bigint(20) not null auto_increment,
  date  datetime default null,
  dew_point  double not null,
  relative_humidity  double default null,
  temperature  double not null,
  temperature_unit  int(11) not null,
  PRIMARY KEY (id)
);

create table if not exists user (
  username varchar(50) primary key not null,
  email varchar(50) default null,
  password varchar(500) default null,
  name varchar(50) default null,
  activated tinyint(1) default '0',
  activationkey varchar(50) default null,
  resetpasswordkey varchar(50) default null
);

create table if not exists authority (  
  name varchar(50) not null primary key  
);

create table if not exists user_authority (  
  username varchar(50) not null,  
  authority varchar(50) not null,  
  foreign key (username) references user (username),  
  foreign key (authority) references authority (name),
  unique index (username, authority)  
);
   
create table if not exists oauth_access_token (  
  token_id varchar(256) default null,  
  token blob,  
  authentication_id varchar(256) default null,  
  user_name varchar(256) default null,  
  client_id varchar(256) default null,  
  authentication blob,  
  refresh_token varchar(256) default null  
); 
  
create table if not exists oauth_refresh_token (  
  token_id varchar(256) default null,  
  token blob,
  authentication blob  
);

# Password is root
insert into user (username, email, password, activated) values ('root', 'root@root.com', '$2a$06$LOO5IrurVYKTAIIH/Ojd/.0FL9oVXRlsmj8OZRb0xh0C/Nr1WEEFm', true);
