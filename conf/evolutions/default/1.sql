# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table document (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  file_path                 varchar(255),
  date                      datetime(6),
  user                      varchar(255),
  status                    varchar(8),
  purpose                   varchar(255),
  document_range_breast_id  bigint,
  constraint ck_document_status check (status in ('UPLOADED','TRAINED','TESTED')),
  constraint uq_document_document_range_breast_id unique (document_range_breast_id),
  constraint pk_document primary key (id))
;

create table document_range_breast (
  id                        bigint auto_increment not null,
  max_mean_radius           double,
  max_mean_area             double,
  max_mean_perimeter        double,
  max_mean_texture          double,
  max_mean_smoothness       double,
  max_mean_compactness      double,
  max_mean_concavity        double,
  max_mean_concave_points   double,
  max_mean_symmetry         double,
  max_mean_fractal_dimension double,
  min_mean_radius           double,
  min_mean_area             double,
  min_mean_perimeter        double,
  min_mean_texture          double,
  min_mean_smoothness       double,
  min_mean_compactness      double,
  min_mean_concavity        double,
  min_mean_concave_points   double,
  min_mean_symmetry         double,
  min_mean_fractal_dimension double,
  max_se_radius             double,
  max_se_area               double,
  max_se_perimeter          double,
  max_se_texture            double,
  max_se_smoothness         double,
  max_se_compactness        double,
  max_se_concavity          double,
  max_se_concave_points     double,
  max_se_symmetry           double,
  max_se_fractal_dimension  double,
  min_se_radius             double,
  min_se_area               double,
  min_se_perimeter          double,
  min_se_texture            double,
  min_se_smoothness         double,
  min_se_compactness        double,
  min_se_concavity          double,
  min_se_concave_points     double,
  min_se_symmetry           double,
  min_se_fractal_dimension  double,
  max_worst_radius          double,
  max_worst_area            double,
  max_worst_perimeter       double,
  max_worst_texture         double,
  max_worst_smoothness      double,
  max_worst_compactness     double,
  max_worst_concavity       double,
  max_worst_concave_points  double,
  max_worst_symmetry        double,
  max_worst_fractal_dimension double,
  min_worst_radius          double,
  min_worst_area            double,
  min_worst_perimeter       double,
  min_worst_texture         double,
  min_worst_smoothness      double,
  min_worst_compactness     double,
  min_worst_concavity       double,
  min_worst_concave_points  double,
  min_worst_symmetry        double,
  min_worst_fractal_dimension double,
  document_id               bigint,
  constraint uq_document_range_breast_document_id unique (document_id),
  constraint pk_document_range_breast primary key (id))
;

create table normalized_patient (
  id                        bigint auto_increment not null,
  category                  double,
  mean_radius               double,
  mean_area                 double,
  mean_perimeter            double,
  mean_texture              double,
  mean_smoothness           double,
  mean_compactness          double,
  mean_concavity            double,
  mean_concave_points       double,
  mean_symmetry             double,
  mean_fractal_dimension    double,
  se_radius                 double,
  se_area                   double,
  se_perimeter              double,
  se_texture                double,
  se_smoothness             double,
  se_compactness            double,
  se_concavity              double,
  se_concave_points         double,
  se_symmetry               double,
  se_fractal_dimension      double,
  worst_radius              double,
  worst_area                double,
  worst_perimeter           double,
  worst_texture             double,
  worst_smoothness          double,
  worst_compactness         double,
  worst_concavity           double,
  worst_concave_points      double,
  worst_symmetry            double,
  worst_fractal_dimension   double,
  patient_id                bigint,
  constraint uq_normalized_patient_patient_id unique (patient_id),
  constraint pk_normalized_patient primary key (id))
;

create table patient (
  id                        bigint auto_increment not null,
  test_prediction           double,
  patient_id                varchar(255),
  category                  varchar(255),
  mean_radius               double,
  mean_area                 double,
  mean_perimeter            double,
  mean_texture              double,
  mean_smoothness           double,
  mean_compactness          double,
  mean_concavity            double,
  mean_concave_points       double,
  mean_symmetry             double,
  mean_fractal_dimension    double,
  se_radius                 double,
  se_area                   double,
  se_perimeter              double,
  se_texture                double,
  se_smoothness             double,
  se_compactness            double,
  se_concavity              double,
  se_concave_points         double,
  se_symmetry               double,
  se_fractal_dimension      double,
  worst_radius              double,
  worst_area                double,
  worst_perimeter           double,
  worst_texture             double,
  worst_smoothness          double,
  worst_compactness         double,
  worst_concavity           double,
  worst_concave_points      double,
  worst_symmetry            double,
  worst_fractal_dimension   double,
  document_id               bigint,
  normalized_patient_id     bigint,
  constraint uq_patient_normalized_patient_id unique (normalized_patient_id),
  constraint pk_patient primary key (id))
;

create table process (
  id                        bigint auto_increment not null,
  user                      varchar(255),
  hidden_layer              integer,
  scg_start                 datetime(6),
  rprop_start               datetime(6),
  scg_end                   datetime(6),
  rprop_end                 datetime(6),
  scg_epoch                 double,
  rprop_epoch               double,
  scg_minimal_error         double,
  rprop_minimal_error       double,
  scg_true_positives        double,
  rprop_true_positives      double,
  scg_true_negatives        double,
  rprop_true_negatives      double,
  scg_false_positives       double,
  rprop_false_positives     double,
  scg_false_negatives       double,
  rprop_false_negatives     double,
  document_id               bigint,
  test_document_id          bigint,
  constraint pk_process primary key (id))
;

create table user_table (
  id                        bigint auto_increment not null,
  username                  varchar(255),
  password_hash             varchar(255),
  is_enabled                tinyint(1) default 0,
  is_first_login            tinyint(1) default 0,
  constraint uq_user_table_username unique (username),
  constraint pk_user_table primary key (id))
;

alter table document add constraint fk_document_documentRangeBreast_1 foreign key (document_range_breast_id) references document_range_breast (id) on delete restrict on update restrict;
create index ix_document_documentRangeBreast_1 on document (document_range_breast_id);
alter table document_range_breast add constraint fk_document_range_breast_document_2 foreign key (document_id) references document (id) on delete restrict on update restrict;
create index ix_document_range_breast_document_2 on document_range_breast (document_id);
alter table normalized_patient add constraint fk_normalized_patient_patient_3 foreign key (patient_id) references patient (id) on delete restrict on update restrict;
create index ix_normalized_patient_patient_3 on normalized_patient (patient_id);
alter table patient add constraint fk_patient_document_4 foreign key (document_id) references document (id) on delete restrict on update restrict;
create index ix_patient_document_4 on patient (document_id);
alter table patient add constraint fk_patient_normalizedPatient_5 foreign key (normalized_patient_id) references normalized_patient (id) on delete restrict on update restrict;
create index ix_patient_normalizedPatient_5 on patient (normalized_patient_id);
alter table process add constraint fk_process_document_6 foreign key (document_id) references document (id) on delete restrict on update restrict;
create index ix_process_document_6 on process (document_id);
alter table process add constraint fk_process_testDocument_7 foreign key (test_document_id) references document (id) on delete restrict on update restrict;
create index ix_process_testDocument_7 on process (test_document_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table document;

drop table document_range_breast;

drop table normalized_patient;

drop table patient;

drop table process;

drop table user_table;

SET FOREIGN_KEY_CHECKS=1;

