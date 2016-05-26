# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book (
  id                        bigint auto_increment not null,
  author                    varchar(255) not null,
  title                     varchar(255) not null,
  release_date              timestamp,
  price                     double,
  constraint pk_book primary key (id))
;

create table book_instance (
  id                        bigint auto_increment not null,
  book_id                   bigint not null,
  delivery_point_id         bigint,
  book_instance_status      varchar(255) not null,
  date                      timestamp,
  constraint pk_book_instance primary key (id))
;

create table book_transfer (
  id                        bigint auto_increment not null,
  book_instance_id          bigint not null,
  library_user_id           bigint not null,
  src_delivery_point_id     bigint not null,
  dst_delivery_point_id     bigint not null,
  send_date                 timestamp not null,
  receive_date              timestamp not null,
  constraint uq_book_transfer_book_instance_i unique (book_instance_id),
  constraint pk_book_transfer primary key (id))
;

create table delivery_point (
  id                        bigint auto_increment not null,
  name                      varchar(255) not null,
  address                   varchar(255) not null,
  delivery_point_type_id    bigint not null,
  constraint pk_delivery_point primary key (id))
;

create table delivery_point_type (
  id                        bigint auto_increment not null,
  name                      varchar(255) not null,
  constraint pk_delivery_point_type primary key (id))
;

create table library_user (
  id                        bigint auto_increment not null,
  user_category_id          bigint not null,
  name                      varchar(255) not null,
  middlename                varchar(255) not null,
  surname                   varchar(255) not null,
  constraint pk_library_user primary key (id))
;

create table professor_user_category_characteristic (
  id                        bigint auto_increment not null,
  library_user_id           bigint not null,
  professor_chair           varchar(255) not null,
  professor_rank            varchar(255) not null,
  professor_degree          varchar(255) not null,
  constraint uq_professor_user_category_chara unique (library_user_id),
  constraint pk_professor_user_category_chara primary key (id))
;

create table student_user_category_characteristic (
  id                        bigint auto_increment not null,
  library_user_id           bigint not null,
  student_faculty           varchar(255) not null,
  student_group             varchar(255) not null,
  constraint uq_student_user_category_charact unique (library_user_id),
  constraint pk_student_user_category_charact primary key (id))
;

create table taken_book (
  id                        bigint auto_increment not null,
  library_user_id           bigint not null,
  taken_book_status         varchar(255) not null,
  book_instance_id          bigint not null,
  take_date                 timestamp not null,
  return_date               timestamp not null,
  constraint pk_taken_book primary key (id))
;

create table user_category (
  id                        bigint auto_increment not null,
  name                      varchar(255) not null,
  constraint pk_user_category primary key (id))
;

create table user_fine (
  id                        bigint auto_increment not null,
  library_user_id           bigint not null,
  fine_start                timestamp,
  fine_end                  timestamp,
  price                     double,
  constraint pk_user_fine primary key (id))
;

alter table book_instance add constraint fk_book_instance_book_1 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_book_instance_book_1 on book_instance (book_id);
alter table book_instance add constraint fk_book_instance_deliveryPoint_2 foreign key (delivery_point_id) references delivery_point (id) on delete restrict on update restrict;
create index ix_book_instance_deliveryPoint_2 on book_instance (delivery_point_id);
alter table book_transfer add constraint fk_book_transfer_bookInstance_3 foreign key (book_instance_id) references book_instance (id) on delete restrict on update restrict;
create index ix_book_transfer_bookInstance_3 on book_transfer (book_instance_id);
alter table book_transfer add constraint fk_book_transfer_libraryUser_4 foreign key (library_user_id) references library_user (id) on delete restrict on update restrict;
create index ix_book_transfer_libraryUser_4 on book_transfer (library_user_id);
alter table book_transfer add constraint fk_book_transfer_srcDeliveryPo_5 foreign key (src_delivery_point_id) references delivery_point (id) on delete restrict on update restrict;
create index ix_book_transfer_srcDeliveryPo_5 on book_transfer (src_delivery_point_id);
alter table book_transfer add constraint fk_book_transfer_dstDeliveryPo_6 foreign key (dst_delivery_point_id) references delivery_point (id) on delete restrict on update restrict;
create index ix_book_transfer_dstDeliveryPo_6 on book_transfer (dst_delivery_point_id);
alter table delivery_point add constraint fk_delivery_point_deliveryPoin_7 foreign key (delivery_point_type_id) references delivery_point_type (id) on delete restrict on update restrict;
create index ix_delivery_point_deliveryPoin_7 on delivery_point (delivery_point_type_id);
alter table library_user add constraint fk_library_user_userCategory_8 foreign key (user_category_id) references user_category (id) on delete restrict on update restrict;
create index ix_library_user_userCategory_8 on library_user (user_category_id);
alter table professor_user_category_characteristic add constraint fk_professor_user_category_cha_9 foreign key (library_user_id) references library_user (id) on delete restrict on update restrict;
create index ix_professor_user_category_cha_9 on professor_user_category_characteristic (library_user_id);
alter table student_user_category_characteristic add constraint fk_student_user_category_char_10 foreign key (library_user_id) references library_user (id) on delete restrict on update restrict;
create index ix_student_user_category_char_10 on student_user_category_characteristic (library_user_id);
alter table taken_book add constraint fk_taken_book_libraryUser_11 foreign key (library_user_id) references library_user (id) on delete restrict on update restrict;
create index ix_taken_book_libraryUser_11 on taken_book (library_user_id);
alter table taken_book add constraint fk_taken_book_bookInstance_12 foreign key (book_instance_id) references book_instance (id) on delete restrict on update restrict;
create index ix_taken_book_bookInstance_12 on taken_book (book_instance_id);
alter table user_fine add constraint fk_user_fine_libraryUser_13 foreign key (library_user_id) references library_user (id) on delete restrict on update restrict;
create index ix_user_fine_libraryUser_13 on user_fine (library_user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists book;

drop table if exists book_instance;

drop table if exists book_transfer;

drop table if exists delivery_point;

drop table if exists delivery_point_type;

drop table if exists library_user;

drop table if exists professor_user_category_characteristic;

drop table if exists student_user_category_characteristic;

drop table if exists taken_book;

drop table if exists user_category;

drop table if exists user_fine;

SET REFERENTIAL_INTEGRITY TRUE;

