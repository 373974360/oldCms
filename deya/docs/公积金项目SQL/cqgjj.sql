alter table cp_survey add (back_status NUMBER(4) default 0 not null);
comment on column cp_survey.back_status is '归档状态';
commit;

alter table cp_survey_answer add (source varchar2(50) default 'pc' not null);
comment on column cp_survey_answer.source is '问卷答案来源：pc,wx,app...';
commit;

