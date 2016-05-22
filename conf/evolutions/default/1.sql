# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book (
  id                        bigint not null,
  author                    varchar(255) not null,
  title                     varchar(255) not null,
  release_date              timestamp,
  price                     double,
  constraint pk_book primary key (id))
;

create table book_instance (
  id                        bigint not null,
  book_id                   bigint not null,
  delivery_point_id         bigint not null,
  date                      timestamp not null,
  constraint pk_book_instance primary key (id))
;

create table books_transfer (
  id                        bigint not null,
  book_instance_id          bigint not null,
  src_delivery_point_id     bigint not null,
  dst_delivery_point_id     bigint not null,
  send_date                 timestamp not null,
  receive_date              timestamp not null,
  constraint uq_books_transfer_book_instance_ unique (book_instance_id),
  constraint pk_books_transfer primary key (id))
;

create table delivery_point (
  id                        bigint not null,
  name                      varchar(255) not null,
  address                   varchar(255) not null,
  delivery_point_type       varchar(4) not null,
  constraint ck_delivery_point_delivery_point_type check (delivery_point_type in ('DESK','ROOM')),
  constraint pk_delivery_point primary key (id))
;

create table professor_user_category_characteristic (
  id                        bigint not null,
  professor_chair           varchar(255) not null,
  professor_rank            varchar(255) not null,
  professor_degree          varchar(255) not null,
  constraint pk_professor_user_category_chara primary key (id))
;

create table student_user_category_characteristic (
  id                        bigint not null,
  student_faculty           varchar(255) not null,
  student_group             varchar(255) not null,
  constraint pk_student_user_category_charact primary key (id))
;

create table taken_book (
  id                        bigint not null,
  user_id                   bigint not null,
  taken_book_status_id      bigint not null,
  book_instance_id          bigint not null,
  take_date                 timestamp not null,
  return_date               timestamp not null,
  constraint uq_taken_book_user_id unique (user_id),
  constraint uq_taken_book_book_instance_id unique (book_instance_id),
  constraint pk_taken_book primary key (id))
;

create table taken_book_status (
  id                        bigint not null,
  name                      varchar(255) not null,
  constraint pk_taken_book_status primary key (id))
;

create table user (
  id                        bigint not null,
  user_category_id          bigint not null,
  name                      varchar(255) not null,
  middlename                varchar(255) not null,
  surname                   varchar(255) not null,
  constraint pk_user primary key (id))
;

create table user_category (
  id                        bigint not null,
  user_category_privilege_id bigint not null,
  user_category_characteristic integer not null,
  name                      varchar(255) not null,
  constraint ck_user_category_user_category_characteristic check (user_category_characteristic in (0,1)),
  constraint pk_user_category primary key (id))
;

create table user_category_privilege (
  id                        bigint not null,
  name                      varchar(255) not null,
  constraint pk_user_category_privilege primary key (id))
;

create table user_fine (
  id                        bigint not null,
  user_id                   bigint not null,
  start                     timestamp not null,
  end                       timestamp not null,
  price                     double not null,
  constraint pk_user_fine primary key (id))
;

create sequence book_seq;

create sequence book_instance_seq;

create sequence books_transfer_seq;

create sequence delivery_point_seq;

create sequence professor_user_category_characteristic_seq;

create sequence student_user_category_characteristic_seq;

create sequence taken_book_seq;

create sequence taken_book_status_seq;

create sequence user_seq;

create sequence user_category_seq;

create sequence user_category_privilege_seq;

create sequence user_fine_seq;

alter table book_instance add constraint fk_book_instance_book_1 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_book_instance_book_1 on book_instance (book_id);
alter table book_instance add constraint fk_book_instance_deliveryPoint_2 foreign key (delivery_point_id) references delivery_point (id) on delete restrict on update restrict;
create index ix_book_instance_deliveryPoint_2 on book_instance (delivery_point_id);
alter table books_transfer add constraint fk_books_transfer_bookInstance_3 foreign key (book_instance_id) references book_instance (id) on delete restrict on update restrict;
create index ix_books_transfer_bookInstance_3 on books_transfer (book_instance_id);
alter table books_transfer add constraint fk_books_transfer_srcDeliveryP_4 foreign key (src_delivery_point_id) references delivery_point (id) on delete restrict on update restrict;
create index ix_books_transfer_srcDeliveryP_4 on books_transfer (src_delivery_point_id);
alter table books_transfer add constraint fk_books_transfer_dstDeliveryP_5 foreign key (dst_delivery_point_id) references delivery_point (id) on delete restrict on update restrict;
create index ix_books_transfer_dstDeliveryP_5 on books_transfer (dst_delivery_point_id);
alter table taken_book add constraint fk_taken_book_user_6 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_taken_book_user_6 on taken_book (user_id);
alter table taken_book add constraint fk_taken_book_takenBookStatus_7 foreign key (taken_book_status_id) references taken_book_status (id) on delete restrict on update restrict;
create index ix_taken_book_takenBookStatus_7 on taken_book (taken_book_status_id);
alter table taken_book add constraint fk_taken_book_bookInstance_8 foreign key (book_instance_id) references book_instance (id) on delete restrict on update restrict;
create index ix_taken_book_bookInstance_8 on taken_book (book_instance_id);
alter table user add constraint fk_user_userCategory_9 foreign key (user_category_id) references user_category (id) on delete restrict on update restrict;
create index ix_user_userCategory_9 on user (user_category_id);
alter table user_category add constraint fk_user_category_userCategory_10 foreign key (user_category_privilege_id) references user_category_privilege (id) on delete restrict on update restrict;
create index ix_user_category_userCategory_10 on user_category (user_category_privilege_id);
alter table user_fine add constraint fk_user_fine_user_11 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_fine_user_11 on user_fine (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists book;

drop table if exists book_instance;

drop table if exists books_transfer;

drop table if exists delivery_point;

drop table if exists professor_user_category_characteristic;

drop table if exists student_user_category_characteristic;

drop table if exists taken_book;

drop table if exists taken_book_status;

drop table if exists user;

drop table if exists user_category;

drop table if exists user_category_privilege;

drop table if exists user_fine;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists book_seq;

drop sequence if exists book_instance_seq;

drop sequence if exists books_transfer_seq;

drop sequence if exists delivery_point_seq;

drop sequence if exists professor_user_category_characteristic_seq;

drop sequence if exists student_user_category_characteristic_seq;

drop sequence if exists taken_book_seq;

drop sequence if exists taken_book_status_seq;

drop sequence if exists user_seq;

drop sequence if exists user_category_seq;

drop sequence if exists user_category_privilege_seq;

drop sequence if exists user_fine_seq;

