# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table CRMUSER.ACCOUNTS (
  ORGKEY                    varchar(255),
  CUST_LAST_NAME            varchar(255),
  PREFERREDNAME             varchar(255))
;

create table CRMUSER.PHONEEMAIL (
  ORGKEY                    varchar(255),
  PHONENO                   varchar(255),
  EMAIL                     varchar(255),
  PHONEOREMAIL              varchar(255),
  PREFERREDFLAG             varchar(255))
;

create table TBAADM.DTD (
  TRAN_ID                   varchar(255),
  TRAN_DATE                 datetime(6),
  RCRE_TIME                 datetime(6),
  ACID                      varchar(255),
  CUST_ID                   varchar(255),
  PART_TRAN_TYPE            varchar(255),
  TRAN_AMT                  varchar(255),
  TRAN_PARTICULAR           varchar(255),
  PSTD_FLG                  varchar(255),
  PSTD_DATE                 datetime(6),
  TRAN_TYPE                 varchar(255),
  TRAN_SUB_TYPE             varchar(255))
;

create table TBAADM.GAM (
  FORACID                   varchar(255),
  ACID                      varchar(255),
  ACCT_NAME                 varchar(255),
  CIF_ID                    varchar(255),
  CLR_BAL_AMT               varchar(255),
  SCHM_TYPE                 varchar(255),
  SCHM_CODE                 varchar(255))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table CRMUSER.ACCOUNTS;

drop table CRMUSER.PHONEEMAIL;

drop table TBAADM.DTD;

drop table TBAADM.GAM;

SET FOREIGN_KEY_CHECKS=1;

