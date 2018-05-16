alter table cp_survey add (back_status NUMBER(4) default 0 not null);
comment on column cp_survey.back_status is '归档状态';
commit;

alter table cp_survey_answer add (source varchar2(50) default 'pc' not null);
comment on column cp_survey_answer.source is '问卷答案来源：pc,wx,app...';
commit;


create table cs_info_workstep(
  id NUMBER(10) not null PRIMARY KEY,
  info_id NUMBER(20) not null ,
  user_id number(20) not null,
  user_name varchar2(255) DEFAULT '',
  step_id NUMBER(2) default 0,
  pass_status NUMBER(2) default 1,
  description varchar2(500),
  work_time varchar2(20) DEFAULT ''
);
INSERT INTO "WEBDATA"."CS_WCM_SEQUENCE" ("TABLE_NAME", "SEQ") VALUES ('cs_info_workstep', '1');

