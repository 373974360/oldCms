-- 留言分类表
create table cs_guestbook_cat(
  id NUMBER(20) not null PRIMARY KEY,
  cat_id NUMBER(20) default 0,
  title VARCHAR2(250) default '',
  publish_status NUMBER(4) default 0,
  direct_publish NUMBER(4) default 0,
  is_member NUMBER(4) default 0,
  is_receive_show NUMBER(4) default 0,
  is_auth_code NUMBER(4) default 0,
  sort_id	NUMBER(4) default 999,
  template_index NUMBER(20) default 0,
  template_list NUMBER(20) default 0,
  template_form NUMBER(20) default 0,
  template_content NUMBER(20) default 0,
  site_id VARCHAR2(250) default '',
  description clob default ''
);

-- 留言类型表
create table cs_guestbook_class(
  id NUMBER(20) not null PRIMARY KEY,
  class_id NUMBER(20) default 0,
  cat_id NUMBER(20) default 0,
  name VARCHAR2(250) default '',
  description VARCHAR2(1000) default '',
  site_id VARCHAR2(250) default '',
  sort_id	NUMBER(4) default 999
);

-- 留言板主题表
create table cs_guestbook_sub(
  id NUMBER(20) not null PRIMARY KEY,
  gbs_id NUMBER(20) default 0,
  cat_id NUMBER(20) default 0,
  title VARCHAR2(250) default '',
  publish_status NUMBER(4) default 0,
  publish_time VARCHAR2(20) default '',
  direct_publish NUMBER(4) default 0,
  is_member NUMBER(4) default 0,
  is_receive_show NUMBER(4) default 0,
  is_auth_code NUMBER(4) default 0,
  template_index NUMBER(20) default 0,
  template_list NUMBER(20) default 0,
  template_form NUMBER(20) default 0,
  template_content NUMBER(20) default 0,
  site_id VARCHAR2(250) default '',
  add_time VARCHAR2(20) default '',
  description clob default '',
  remark VARCHAR2(1000) default ''
);

-- 留言板数据表
create table cs_guestbook(
  id NUMBER(20) not null PRIMARY KEY,
  gbs_id NUMBER(20) default 0,
  nickname  VARCHAR2(250) default '',
  title  VARCHAR2(250) default '',
  address  VARCHAR2(250) default '',
  content clob default '',
  member_id NUMBER(20) default 0,
  ip  VARCHAR2(250) default '',
  class_id VARCHAR2(250) default '',
  publish_status NUMBER(4) default 0,
  reply_status NUMBER(4) default 0,
  add_time VARCHAR2(20) default '',
  reply_time VARCHAR2(20) default '',
  reply_content clob default '',
  realname VARCHAR2(250) default '',
  phone VARCHAR2(250) default '',
  tel VARCHAR2(250) default '',
  post_code VARCHAR2(250) default '',
  idcard  varchar(250) default '',
  vocation VARCHAR2(250)  DEFAULT '',
  age VARCHAR2(250)  DEFAULT '',
  sex NUMBER(4) DEFAULT 0,
  hits NUMBER(20) DEFAULT 0,
  site_id VARCHAR2(250) default ''
);

-- 留言分类管理员表
create table cs_guestbook_user(
  id NUMBER(20) not null PRIMARY KEY,
  cat_id NUMBER(20) default 0,
  user_id NUMBER(20) default 0,
  site_id VARCHAR2(250) default '',
  app_id VARCHAR2(250) default ''
);

create table cp_model(
  model_id NUMBER(20) not null PRIMARY KEY,
  model_cname VARCHAR2(250) default '',
  relevance_type NUMBER(4) default 0,
  is_sort NUMBER(4) default 0,
  time_limit NUMBER(4) default 15,
  warn_num NUMBER(4) default 0,
  yellow_num NUMBER(4) default 0,
  red_num NUMBER(4) default 0,
  code_pre VARCHAR2(20) default 'GPPS',
  code_rule VARCHAR2(20) default 'YYYYMMDD',
  code_num NUMBER(4) default 4,
  query_num NUMBER(4) default 4,
  must_member NUMBER(4) default 0,
  wf_id NUMBER(4) default 0,
  remind_type VARCHAR2(20) default '',
  user_secret NUMBER(4) default 0,
  is_auto_publish NUMBER(4) default 0,
  template_form NUMBER(20) default 0,
  template_list NUMBER(20) default 0,
  template_content NUMBER(20) default 0,
  template_comment NUMBER(20) default 0,
  template_search_list NUMBER(20) default 0,
  model_memo VARCHAR2(1000) default '',
  hits NUMBER(20) default 0,
  day_hits NUMBER(20) default 0,
  week_hits NUMBER(20) default 0,
  month_hits NUMBER(20) default 0,
  lasthit_dtime VARCHAR2(20) default '',
  template_print NUMBER(20) default 0,
  is_comment_checked NUMBER(4) default 0,
  is_allow_comment NUMBER(4) default 0
);
		   
create table cp_from(
  field_id NUMBER(4) not null,
  model_id NUMBER(20) not null,
  field_ename VARCHAR2(250) not null,
  field_cname VARCHAR2(250) default ''
);

create table cp_sq_custom(
  cu_id NUMBER(20) not null PRIMARY KEY,
  model_id NUMBER(20) not null,
  sq_id NUMBER(20) not null,
  cu_key VARCHAR2(250) not null,
  cu_value clob default ''
);

create table cp_model_dept(
  model_id NUMBER(20) not null,
  dept_id NUMBER(20)
);

create table cp_model_user(
  model_id NUMBER(20) not null,
  user_id NUMBER(20)
);

create table cp_area(
  area_id NUMBER(20) not null PRIMARY KEY,
  parent_id NUMBER(20) default '0',
  area_cname VARCHAR2(250),
  area_position VARCHAR2(250),
  area_level NUMBER(4),
  sort_id NUMBER(4) default '0'
);

create table cp_calendar(
  ca_id NUMBER(20) not null PRIMARY KEY,
  ca_name VARCHAR2(250),
  start_dtime VARCHAR2(20),
  end_dtime VARCHAR2(20),
  ca_flag NUMBER(4) default '0',
  ca_type NUMBER(4) default '0'
);

create table cp_sq(
  sq_id NUMBER(20) not null PRIMARY KEY,
  sq_code VARCHAR2(250) default '',
  model_id NUMBER(20),
  me_id NUMBER(20) default 0,
  sq_realname VARCHAR2(250) default '',
  sq_sex NUMBER(4),
  sq_vocation VARCHAR2(250) default '',
  sq_age NUMBER(4),
  sq_address VARCHAR2(250) default '',
  sq_email VARCHAR2(250) default '',
  sq_tel VARCHAR2(250) default '',
  sq_phone VARCHAR2(250) default '',
  sq_card_id VARCHAR2(250) default '',
  sq_dtime VARCHAR2(20) default '',
  is_open NUMBER(4) default 0,
  sq_ip VARCHAR2(50) default '',
  sq_title VARCHAR2(250) default '',
  sq_title2 VARCHAR2(250) default '',
  sq_content clob default '',
  sq_content2 clob default '',
  sq_reply clob default '',
  sq_type NUMBER(4)  default 0,
  query_code VARCHAR2(50) default '',
  cat_id NUMBER(20),
  area_id NUMBER(20),
  pur_id NUMBER(20),
  accept_dtime VARCHAR2(50) default '',
  over_dtime  VARCHAR2(50) default '',
  sq_flag NUMBER(4) default 0,
  initial_sq_id NUMBER(20) default 0,
  sq_status NUMBER(4) default 0,
  step_id NUMBER(4) default 0,
  publish_status NUMBER(4) default 0,
  supervise_flag NUMBER(4) default 0,
  is_back NUMBER(4) default 0,
  time_limit NUMBER(4) default 0,
  limit_flag NUMBER(4) default 0,
  alarm_flag NUMBER(4) default 0,
  timeout_flag NUMBER(4) default 0,
  prove_type NUMBER(4) default 1,
  people_num NUMBER(4) default 1,
  do_dept NUMBER(20),
  submit_name VARCHAR2(250) default '',
  publish_user NUMBER(20),
  publish_dtime VARCHAR2(20) default '',
  sat_score NUMBER(4) default 0,
  hits NUMBER(20),
  day_hits NUMBER(20),
  week_hits NUMBER(20),
  month_hits NUMBER(20),
  weight NUMBER(4) default 60,
  lasthit_dtime VARCHAR2(20) default '',
  sq_all_number NUMBER(20) default 0,
  sq_number NUMBER(20) default 0
);

create table cp_process(
  pro_id NUMBER(20) not null PRIMARY KEY,
  sq_id NUMBER(20),
  user_id NUMBER(20),
  user_realname VARCHAR2(250) default '',
  do_dept NUMBER(20),
  dept_name VARCHAR2(250) default '',
  pro_dtime VARCHAR2(20) default '',
  pro_type NUMBER(4),
  pro_note clob,
  to_dept_name VARCHAR2(250) default '',
  old_dept_id NUMBER(20) default 0,
  old_sq_status NUMBER(4) default 0,
  old_prove_type NUMBER(4) default 0
);

create table cp_sq_attachment(
  sq_id NUMBER(20) not null,
  att_name varchar2(255),
  att_path varchar2(255),
  relevance_type NUMBER(4)
);

-- 机构注册表
create table cp_dept(
  dept_id NUMBER(20) not null PRIMARY KEY,
  parent_id NUMBER(20) default '0',
  dept_name VARCHAR2(250),
  dept_position VARCHAR2(250),
  dept_level NUMBER(4),
  dept_memo VARCHAR2(1000),
  sort_id NUMBER(4)
);

create table cp_lead(
  lead_id NUMBER(20) not null PRIMARY KEY,
  lead_name VARCHAR2(250) default '',
  lead_job VARCHAR2(250) default '',
  lead_img VARCHAR2(250) default '',
  lead_url VARCHAR2(250) default '',
  lead_memo VARCHAR2(1000)  default '',
  sort_id NUMBER(4) default 999,
  dept_id NUMBER(20)
);



-- 用户注册表
create table cp_user(
  user_id NUMBER(20) not null PRIMARY KEY,
  dept_id NUMBER(20) default '0'
);

-- 常用语
create table cp_phrasal(
  ph_id number(20) primary key,
  ph_title varchar2(255) default '',
  ph_content varchar2(2000) default '',
  ph_type number(4) default 0,
  sort_id number(4) default 0
);

-- 满意度指标配置
create table cp_satisfaction(
  sat_id NUMBER(20) not null PRIMARY KEY,
  sat_item VARCHAR2(250),
  sat_score NUMBER(4)
);

-- 诉求目的
create table cp_purpose(
  pur_id NUMBER(20) not null PRIMARY KEY,
  pur_name VARCHAR2(250),
  sort_id NUMBER(4)
);

-- 诉求内容分类
create table cp_category(
  cat_id NUMBER(20) not null PRIMARY KEY,
  parent_id NUMBER(20) default '0',
  cat_cname VARCHAR2(250),
  cat_position VARCHAR2(250),
  cat_level NUMBER(4),
  sort_id NUMBER(4)
);


-- 满意度投票记录
create table cp_sat_record(
  rec_id NUMBER(20) not null PRIMARY KEY,
  sq_id NUMBER(20) default '0',
  sat_id NUMBER(20) default '0',
  sat_score NUMBER(4) default '0',
  add_dtime VARCHAR2(250) default ''
);

-- 诉求tags
create table cp_sq_tag(
  sq_id NUMBER(20) not null,
  tag_id NUMBER(20) not null
);

create table cs_workflow(
  wf_id NUMBER(20) not null PRIMARY KEY,
  wf_name VARCHAR2(250) default '',
  wf_memo VARCHAR2(1000) default '',
  wf_steps NUMBER(4)
);

create table cs_workflow_step(
  wf_id NUMBER(20) not null,
  step_id NUMBER(4) default 1,
  step_name VARCHAR2(250) default '',
  role_id VARCHAR2(250) default '',
  required NUMBER(4) default 1
);

create table cs_workflow_log(
  log_id NUMBER(20) not null PRIMARY KEY,
  content_id NUMBER(20),
  wf_before NUMBER(4),
  wf_after NUMBER(4),
  wf_action VARCHAR2(250) default '',
  wf_reason VARCHAR2(1000) default '',
  opt_time VARCHAR2(250) default '',
  user_id VARCHAR2(250) default '',
  ip VARCHAR2(250) default ''
);

create table cs_info_status(
  status_id NUMBER(20) not null PRIMARY KEY,
  status_name VARCHAR2(250) default '',
  status_memo VARCHAR2(1000) default ''
);

create table cs_info_class(
  class_id NUMBER(20) not null PRIMARY KEY,
  class_ename VARCHAR2(250) default '',
  class_cname VARCHAR2(250) default '',
  class_codo VARCHAR2(250) default '',
  class_define VARCHAR2(1000) default '',
  class_type NUMBER(4),
  app_ids VARCHAR2(250) default ''
);

create table cs_info_category(
  id   NUMBER(20) not null,
  cat_id  NUMBER(20) not null,
  class_id NUMBER(20),
  parent_id   NUMBER(20),
  wf_id NUMBER(20),
  is_wf_publish  NUMBER(4),
  cat_code varchar2(250) default '',
  cat_ename   varchar2(250) default '',
  cat_cname   varchar2(250) default '',
  cat_position varchar2(250) default '',
  cat_level   NUMBER(4),
  is_mutilpage NUMBER(4),
  template_index NUMBER(20),
  template_list  NUMBER(20),
  is_generate_index  NUMBER(4) default 1,
  urlrule_index  varchar2(250) default '',
  urlrule_list varchar2(250) default '',
  urlrule_content varchar2(250) default '',
  is_allow_submit NUMBER(4) default 0,
  is_allow_comment   NUMBER(4) default 0,
  is_comment_checked NUMBER(4) default 0,
  is_show NUMBER(4) default 1,
  cat_keywords varchar2(1000) default '',
  cat_description varchar2(1000) default '',
  cat_sort NUMBER(4) default 999,
  is_sync NUMBER(4) default 0,
  cat_source_id  NUMBER(20) default 0,
  cat_class_id NUMBER(20) default 0,
  is_disabled NUMBER(4) default 1,
  cat_type NUMBER(4) default 0,
  zt_cat_id   NUMBER(4) default 0,
  is_archive  NUMBER(4) default 0,
  cat_memo varchar2(1000) default '',
  app_id   varchar2(250) default '',
  site_id  varchar2(250) default '',
  jump_url varchar2(250),
  hj_sql  varchar2(1000) default '',
  is_show_tree NUMBER(4) default 1
);

create table cs_info_digg(
  info_id NUMBER(20) not null PRIMARY KEY,
  supports NUMBER(20),
  againsts NUMBER(20),
  app_id  VARCHAR2(250),
  site_id VARCHAR2(250)
);

create table cs_info_digg_log(
  info_id NUMBER(20),
  flag NUMBER(4),
  user_id NUMBER(20),
  username VARCHAR2(250),
  ip varchar(250),
  digg_dtime VARCHAR2(250),
  app_id VARCHAR2(250),
  site_id VARCHAR2(250)
);

create table cs_info_category_shared(
  s_site_id VARCHAR2(250) default '',
  cat_id NUMBER(20),
  t_site_id VARCHAR2(250) default '',
  shared_type NUMBER(4),
  range_type NUMBER(4)
);

create table cs_zt_category(
  id NUMBER(20) not null PRIMARY KEY,
  zt_cat_id NUMBER(20) not null,
  zt_cat_name VARCHAR2(250) default '',
  sort_id NUMBER(4) default 999,
  site_id VARCHAR2(250) default '',
  app_id VARCHAR2(250) default ''
);

create table cs_category_rele(
  cat_id NUMBER(20),
  prv_id NUMBER(20),
  priv_type NUMBER(4),
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_info_pic(
  pic_id NUMBER(20) not null PRIMARY KEY,
  info_id NUMBER(20),
  att_id NUMBER(4),
  pic_path VARCHAR2(250) default '',
  pic_note clob default '',
  pic_url VARCHAR2(250) default '',
  pic_sort NUMBER(4) default 999,
  pic_title varchar2(250),
  pic_content clob default ''
);

create table cs_info_pic_group(
  pic_id   NUMBER(20) not null,
  info_id  NUMBER(4),
  att_id   NUMBER(20),
  pic_path varchar2(250) default '',
  pic_note clob,
  pic_url  varchar2(250) default '',
  pic_sort NUMBER(4)
);

create table cs_info_video(
  info_id number(20) not null primary key,
  att_id number(20),
  video_path varchar2(250),
  play_time number(20),
  info_content clob
);

create table cs_info(
  id  number(20) not null PRIMARY KEY,
  info_id number(20),
  cat_id number(20),
  model_id number(20),
  from_id number(20),
  top_title varchar2(250),
  pre_title varchar2(250),
  title varchar2(250),
  subtitle varchar2(500),
  title_color varchar2(250)  default '#000',
  thumb_url varchar2(250),
  description varchar2(1000),
  author varchar2(250)   default '',
  editor varchar2(250)   default '',
  source varchar2(250) default '',
  info_keywords varchar2(250),
  info_description varchar2(250),
  tags varchar2(250),
  content_url varchar2(250),
  wf_id number(20),
  step_id number(4)   default 0,
  info_status number(4)  default 0,
  final_status number(4)  default 0,
  page_count number(4)  default 1,
  weight number(4)  default 60,
  hits number(20),
  day_hits number(20),
  week_hits number(20),
  month_hits number(20),
  lasthit_dtime varchar2(20),
  is_allow_comment number(4),
  comment_num number(20)  default 0,
  released_dtime varchar2(20),
  input_user number(20),
  input_dtime varchar2(20),
  modify_user number(20),
  modify_dtime varchar2(20),
  opt_dtime varchar2(20),
  is_auto_up number(4)  default 0,
  up_dtime varchar2(20),
  is_auto_down number(4)  default 0,
  down_dtime varchar2(20),
  is_host number(4)  default 0,
  title_hashkey number(20),
  app_id varchar2(250),
  site_id varchar2(250),
  i_ver number(4),
  is_pic number(4) default 0,
  auto_desc varchar2(1000) default'',
  is_am_tupage		NUMBER(4) default 0,
  tupage_num			NUMBER(7) default 1000,
  istop  number(4) default 0,
  info_level varchar2(4),
  isIpLimit varchar2(4),
  publish_source varchar2(255)
);

create table cs_info_article(
  id  number(20) not null PRIMARY KEY,
  info_id number(20),
  info_content   clob,
  page_count number(20),
  prepage_wcount number(20),
  word_count number(20)
);

create table cs_info_info(
  info_id  number(20),
  related_info_id number(20),
  title varchar2(255),
  thumb_url varchar2(255),
  content_url  varchar2(255),
  sort_id  number(20),
  model_id number(20)
);

-- 栏目获取规则表
create table cs_info_category_regu(
  id NUMBER(20) not null,
  cat_id NUMBER(20) not null,
  regu_type NUMBER(4) default 0,
  site_ids VARCHAR2(1000) default '',
  cat_ids	VARCHAR2(1000) default '',
  site_id VARCHAR2(250) default '',
  app_id VARCHAR2(250) default ''
);

create table cs_info_category_model(
  cat_model_id NUMBER(20) not null,
  cat_id NUMBER(20) default 0,
  model_id  NUMBER(20) default 0,
  template_content NUMBER(20) default 0,
  app_id varchar2(250) default '',
  site_id   varchar2(250) default '',
  isAdd NUMBER(4) default 0
);

create table cs_info_sync(
  s_site_id varchar2(250) default '',
  s_cat_id NUMBER(20),
  t_site_id varchar2(250) default '',
  t_cat_id NUMBER(20),
  is_auto_publish NUMBER(4) default 0,
  cite_type NUMBER(4) default 1,
  orientation NUMBER(4) default 0
);

create table cs_info_tag(
  info_id NUMBER(20),
  tag_id  NUMBER(20)
);

create table cs_info_access(
  id  NUMBER(20) not null PRIMARY KEY,
  info_id NUMBER(20),
  cat_id NUMBER(20),
  info_title varchar2(255),
  app_id varchar2(255),
  access_ip  varchar2(255),
  access_url varchar2(255),
  access_time varchar2(255),
  access_day varchar2(255),
  access_month  varchar2(255),
  access_year varchar2(255),
  site_domain varchar2(255),
  site_id varchar2(255)
);

create table cs_site_group(
  sgroup_id VARCHAR2(250) default '',
  parent_id VARCHAR2(250) default '0',
  sgroup_name VARCHAR2(250) default '',
  sgroup_ip VARCHAR2(250) default '',
  sgroup_prot VARCHAR2(250) default '',
  dept_id NUMBER(20),
  sgroup_sort NUMBER(20) default 0,
  sgroup_memo VARCHAR2(1000) default ''
);

create table cs_site_server(
  server_id NUMBER(20) not null PRIMARY KEY,
  server_name VARCHAR2(250) default '',
  server_ip VARCHAR2(250) default '',
  server_port VARCHAR2(250) default '',
  server_type NUMBER(4),
  server_memo VARCHAR2(1000) default ''
);

create table cs_site(
  site_id VARCHAR2(250) default '',
  parent_id VARCHAR2(250) default '',
  server_id NUMBER(20),
  dept_id NUMBER(4),
  site_name VARCHAR2(250) default '',
  site_cdkey VARCHAR2(250) default '',
  site_createtime VARCHAR2(250) default '',
  site_pausetime VARCHAR2(250) default '',
  site_position VARCHAR2(250) default '',
  site_status NUMBER(4) default 0,
  site_sort NUMBER(10) default 0,
  site_path VARCHAR2(250) default '',
  sgroup_id VARCHAR2(250) default '',
  site_demo VARCHAR2(1000) default ''
);

create table cs_site_domain(
  domain_id NUMBER(20) not null PRIMARY KEY,
  site_id VARCHAR2(250) default '',
  site_domain VARCHAR2(250) default '',
  is_host NUMBER(4) default 0,
  is_default NUMBER(4) default 0
);

create table cs_site_config(
  config_id NUMBER(20) not null PRIMARY KEY,
  site_id VARCHAR2(250) default '',
  config_key VARCHAR2(250) default '',
  config_value VARCHAR2(250) default ''
);

create table cs_site_app(
  sa_id NUMBER(20) not null PRIMARY KEY,
  site_id VARCHAR2(250) not null,
  app_id VARCHAR2(250) not null,
  mark1 varchar2(250) default '',
  mark2 varchar2(250) default '',
  mark3 varchar2(250) default '',
  mark4 varchar2(250) default '',
  mark5 varchar2(250) default ''
);

create table cs_site_count(
  id NUMBER(20),
  site_id VARCHAR2(250) not null,
  app_id VARCHAR2(250) not null,
  hits NUMBER(20),
  day_hits NUMBER(20),
  week_hits   NUMBER(20),
  month_hits  NUMBER(20)
);

CREATE TABLE cs_artInfo (
  id NUMBER(20) not null PRIMARY KEY,
  rule_id NUMBER(20) DEFAULT '0',
  art_title varchar2(2000) DEFAULT '',
  art_content clob,
  art_pubTime varchar2(100) DEFAULT '',
  art_source varchar2(2000) DEFAULT '',
  art_author varchar2(2000) DEFAULT '',
  art_keyWords varchar2(2000) DEFAULT '',
  art_docNo varchar2(2000) DEFAULT '',
  art_hits NUMBER(4) DEFAULT '0',
  cat_id varchar2(10) DEFAULT '',
  artis_user NUMBER(4) DEFAULT '0',
  coll_time varchar2(30) DEFAULT '',
  url varchar2(200) DEFAULT NULL
);

CREATE TABLE cs_collection (
  id NUMBER(20) not null PRIMARY KEY,
  rule_id NUMBER(20) DEFAULT '0',
  rcat_id NUMBER(20) DEFAULT '0',
  title varchar2(250) DEFAULT '',
  listUrl_start varchar2(250) DEFAULT '',
  listUrl_end varchar2(250) DEFAULT '',
  infotitle_start varchar2(250) DEFAULT '',
  infotitle_end varchar2(250) DEFAULT '',
  description_start varchar2(250) DEFAULT '',
  description_end varchar2(250) DEFAULT '',
  content_start varchar2(250) DEFAULT '',
  content_end varchar2(250) DEFAULT '',
  source_start varchar2(250) DEFAULT '',
  source_end varchar2(250) DEFAULT '',
  addTime_start varchar2(250) DEFAULT '',
  addTime_end varchar2(250) DEFAULT '',
  keywords_start varchar2(250) DEFAULT '',
  keywords_end varchar2(250) DEFAULT '',
  docNo_start varchar2(250) DEFAULT '',
  docNo_end varchar2(250) DEFAULT '',
  hits_start varchar2(250) DEFAULT '',
  hits_end varchar2(250) DEFAULT '',
  author_start varchar2(250) DEFAULT '',
  author_end varchar2(250) DEFAULT '',
  site_id varchar2(250) DEFAULT '',
  cate_ids varchar2(250) DEFAULT '',
  stop_time NUMBER(20) DEFAULT '0',
  pic_isGet NUMBER(20) DEFAULT '0',
  timeFormatType varchar2(250) DEFAULT '',
  pageEncoding varchar2(250) DEFAULT '',
  coll_url varchar2(250) DEFAULT '',
  contentUrl_start varchar2(250) DEFAULT '',
  contentUrl_end varchar2(250) DEFAULT '',
  cat_id varchar2(50) DEFAULT '',
  cat_name varchar2(50) DEFAULT '',
  coll_interval varchar2(50) DEFAULT ''
);

create table cs_rule_category(
  id NUMBER(20) not null PRIMARY KEY,
  rcat_id NUMBER(20) default 0,
  parent_id NUMBER(20) default 0,
  rcat_name VARCHAR2(250) default '',
  rcat_position VARCHAR2(250) default '',
  rcat_level NUMBER(4) default 0,
  rcat_memo VARCHAR2(1000) default '',
  sort_id NUMBER(4) default 0,
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default '',
  receive_recom NUMBER(4) default 0
);

create table cs_rule_priv(
  id NUMBER(20) not null,
  rcat_id   NUMBER(20) default 0,
  prv_id NUMBER(20) default 0,
  priv_type NUMBER(4) default 0,
  app_id varchar2(250) default '',
  site_id   varchar2(250) default ''
);

create table cp_scategory(
  id NUMBER(10) not null PRIMARY KEY,
  category_id VARCHAR2(200) default '',
  category_name VARCHAR2(255) default '',
  description  VARCHAR2(4000) default '',
  publish_status NUMBER(1) default 0,
  publish_time VARCHAR2(40) default '',
  is_grade NUMBER(1) default 0,
  is_comment NUMBER(1) default 0,
  is_com_audit NUMBER(1) default 0,
  is_com_filter NUMBER(1) default 0,
  is_p_audit NUMBER(1) default 0,
  is_p_filter NUMBER(1) default 0,
  is_show_text NUMBER(1) default 1,
  is_register NUMBER(1) default 0,
  is_t_flink NUMBER(1) default 0,
  is_t_keyw NUMBER(1) default 0,
  is_t_audit NUMBER(1) default 0,
  m_forecast_path VARCHAR2(500) default '',
  m_hlist_path VARCHAR2(500) default '',
  m_on_path VARCHAR2(500) default '',
  m_h_path VARCHAR2(500) default '',
  m_rlist_path VARCHAR2(500) default '',
  m_rcontent_list VARCHAR2(500) default '',
  add_time VARCHAR2(200) default '',
  add_user VARCHAR2(200) default '',
  update_time VARCHAR2(200) default '',
  update_user VARCHAR2(200) default '',
  is_delete NUMBER(1) default 0,
  sort NUMBER(5) default 0,
  site_id VARCHAR2(200) default ''
);

create table cp_subject(
  id NUMBER(10) not null PRIMARY KEY,
  sub_id VARCHAR2(200) default '',
  category_id VARCHAR2(200) default '',
  sub_name VARCHAR2(300) default '',
  summary VARCHAR2(4000) default '',
  intro VARCHAR2(4000) default '',
  info clob default '',
  start_time VARCHAR2(40) default '',
  end_time VARCHAR2(40) default '',
  status NUMBER(1) default 0,
  audit_status NUMBER(1) default 0,
  publish_status NUMBER(1) default 0,
  publish_time VARCHAR2(40) default '',
  is_auto_audit NUMBER(1) default 0,
  auto_audit_time NUMBER(5) default 0,
  apply_time VARCHAR2(40) default '',
  apply_user VARCHAR2(50) default '',
  apply_dept VARCHAR2(50) default '',
  update_time VARCHAR2(40) default '',
  update_user VARCHAR2(50) default '',
  audit_time VARCHAR2(40) default '',
  audit_user VARCHAR2(50) default '',
  submit_status NUMBER(1) default 0,
  is_delete NUMBER(1) default 0,
  sort NUMBER(5) default 0,
  click_count NUMBER(10) default 0,
  recommend_flag NUMBER(5) default 0,
  recommend_time VARCHAR2(40) default '',
  history_record_pic clob default '',
  history_record_text clob default '',
  count_user NUMBER(5) default 0,
  site_id VARCHAR2(200) default ''
);

create table cp_resouse(
  id NUMBER(10) not null PRIMARY KEY,
  sub_id VARCHAR2(200) default '',
  affix_type VARCHAR2(50) default '',
  affix_path VARCHAR2(500) default '',
  description Varchar(4000) default '',
  affix_name VARCHAR2(255) default '',
  affix_status VARCHAR2(50) default '',
  add_time VARCHAR2(200) default '',
  add_user VARCHAR2(200) default '',
  update_time VARCHAR2(200) default '',
  update_user VARCHAR2(200) default '',
  sort NUMBER(5) default 0,
  is_delete NUMBER(1) default 0
);

create table cp_actor(
  id NUMBER(10) not null PRIMARY KEY,
  actor_id VARCHAR2(200) default '',
  sub_id VARCHAR2(200) default '',
  actor_name VARCHAR2(255) default '',
  age  VARCHAR2(10) default '',
  sex  VARCHAR2(10) default '',
  email  VARCHAR2(255) default '',
  company  VARCHAR2(300) default '',
  a_post  VARCHAR2(255) default '',
  address  VARCHAR2(300) default '',
  description  VARCHAR2(4000) default '',
  pic_path  VARCHAR2(500) default '',
  add_time VARCHAR2(200) default '',
  add_user VARCHAR2(200) default '',
  update_time VARCHAR2(200) default '',
  update_user VARCHAR2(200) default '',
  is_delete NUMBER(1) default 0,
  sort NUMBER(5) default 0
);

create table cp_releinfo(
  id NUMBER(10) not null PRIMARY KEY,
  info_id VARCHAR2(200) default '',
  sub_id VARCHAR2(200) default '',
  info_name VARCHAR2(200) default '',
  info_type VARCHAR2(100) default '',
  content clob default '',
  curl VARCHAR2(255) default '',
  affix_path VARCHAR2(1000) default '',
  add_time VARCHAR2(200) default '',
  add_user VARCHAR2(200) default '',
  update_time VARCHAR2(200) default '',
  update_user VARCHAR2(200) default '',
  is_delete NUMBER(1) default 0,
  sort NUMBER(5) default 0,
  publish_flag NUMBER(5) default 0
);

create table cp_chat(
  id NUMBER(10) not null PRIMARY KEY,
  chat_id VARCHAR2(200) default '',
  sub_id VARCHAR2(200) default '',
  chat_user VARCHAR2(200) default '',
  content VARCHAR2(4000) default '',
  put_time VARCHAR2(40) default '',
  ip VARCHAR2(40) default '',
  chat_area  VARCHAR2(40) default '',
  audit_status NUMBER(1) default 0,
  chat_type VARCHAR2(200) default '',
  is_show NUMBER(1) default 0
);

create table cs_attachment_folder(
  f_id NUMBER(20) not null PRIMARY KEY,
  parent_id NUMBER(20),
  cname VARCHAR2(250) default '',
  f_treeposition VARCHAR2(250) default '',
  creat_dtime VARCHAR2(250) default '',
  user_id NUMBER(20),
  APP_ID VARCHAR2(250) default '',
  SITE_ID VARCHAR2(250) default ''
);

create table cs_attachment(
  att_id NUMBER(20) not null PRIMARY KEY,
  f_id NUMBER(20),
  att_ename VARCHAR2(250) default '',
  att_cname VARCHAR2(250) default '',
  att_path VARCHAR2(250) default '',
  alias_name VARCHAR2(250) default '',
  thumb_url VARCHAR2(250) default '',
  hd_url VARCHAR2(250) default '',
  fileext VARCHAR2(250) default '',
  filesize NUMBER(20),
  att_type NUMBER(4),
  att_description VARCHAR2(1000) default '',
  user_id NUMBER(20),
  upload_dtime VARCHAR2(250) default '',
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

CREATE TABLE cs_member (
  me_id NUMBER(20) not null PRIMARY KEY,
  mcat_id NUMBER(20) DEFAULT '',
  me_realname VARCHAR2(250)  DEFAULT '',
  me_nickname VARCHAR2(250)  DEFAULT '',
  me_card_id VARCHAR2(250)  DEFAULT '',
  me_sex NUMBER(4) DEFAULT '0',
  me_vocation VARCHAR2(250)  DEFAULT '',
  me_age VARCHAR2(250)  DEFAULT '',
  xiangzhen VARCHAR2(250)  DEFAULT '',
  shequ VARCHAR2(250)  DEFAULT '',
  me_address VARCHAR2(250)  DEFAULT '',
  me_email VARCHAR2(250)  DEFAULT '',
  me_tel VARCHAR2(250)  DEFAULT '',
  me_phone VARCHAR2(250)  DEFAULT '',
  user_photo VARCHAR2(250)  DEFAULT '',
  place_photo VARCHAR2(250)  DEFAULT '',
  add_time VARCHAR2(250)  DEFAULT '',
  update_time VARCHAR2(250)  DEFAULT '',
  me_status NUMBER(4) DEFAULT '0',
  app_id VARCHAR2(250)  DEFAULT '',
  site_id VARCHAR2(250)  DEFAULT ''
);

CREATE TABLE cs_member_category (
  mcat_id NUMBER(20) not null PRIMARY KEY,
  mcat_name VARCHAR2(250)  DEFAULT '',
  mcat_memo VARCHAR2(1000)  DEFAULT '',
  sort_id NUMBER(4) DEFAULT '0'
);

CREATE TABLE cs_member_config (
  config_id NUMBER(20) not null PRIMARY KEY,
  is_allow NUMBER(4) DEFAULT '0',
  is_check NUMBER(4) DEFAULT '0',
  reg_pro clob ,
  close_pro VARCHAR2(1000)  DEFAULT '',
  forbidden_name clob
);

CREATE TABLE cs_member_register (
  register_id NUMBER(20) not null PRIMARY KEY,
  me_id NUMBER(20) DEFAULT '',
  me_account VARCHAR2(250)  DEFAULT '',
  me_password VARCHAR2(250)  DEFAULT '',
  register_type NUMBER(4) DEFAULT '0'
);

create table cs_org_app(
  app_id VARCHAR2(250) default '',
  app_name VARCHAR2(250) default '',
  app_sort NUMBER(20),
  app_ico VARCHAR2(250) default '',
  app_memo VARCHAR2(1000) default ''
);

create table cs_org_dept(
  dept_id NUMBER(20) not null PRIMARY KEY,
  parent_id NUMBER(20),
  deptlevel_value NUMBER(4),
  dept_fullname VARCHAR2(250) default '',
  dept_name VARCHAR2(250) default '',
  area_code VARCHAR2(250) default '',
  dept_code VARCHAR2(250) default '',
  functions VARCHAR2(500) default '',
  tel VARCHAR2(250) default '',
  fax VARCHAR2(250) default '',
  email VARCHAR2(250) default '',
  address VARCHAR2(250) default '',
  dept_memo VARCHAR2(1000) default '',
  tree_position VARCHAR2(250) default '',
  is_delete NUMBER(1) default 0,
  dept_sort NUMBER(20) default 0,
  is_publish NUMBER(4) default 0,
  postcode VARCHAR2(20) default ''
);

create table cs_org_dept_manager(
  dept_manager_id NUMBER(20) not null PRIMARY KEY,
  dept_id NUMBER(20),
  user_id NUMBER(20)
);

create table cs_org_deptlevel(
  deptlevel_id NUMBER(20) not null PRIMARY KEY,
  deptlevel_name VARCHAR2(250) default '',
  deptlevel_value NUMBER(4),
  deptlevel_memo VARCHAR2(1000) default '',
  is_delete NUMBER(1) default 0
);

create table cs_org_user(
  user_id NUMBER(20) not null PRIMARY KEY,
  dept_id NUMBER(20),
  userlevel_value NUMBER(4),
  user_realname  VARCHAR2(250) default '',
  user_aliasname VARCHAR2(250) default '',
  user_photo  VARCHAR2(250) default '',
  sex NUMBER(4),
  birthday  VARCHAR2(250) default '',
  nation  VARCHAR2(250) default '',
  age NUMBER(4),
  is_marriage NUMBER(4),
  natives  VARCHAR2(250) default '',
  functions  VARCHAR2(250) default '',
  degree  VARCHAR2(250) default '',
  colleges  VARCHAR2(250) default '',
  graduation_time  VARCHAR2(250) default '',
  professional  VARCHAR2(250) default '',
  health  VARCHAR2(250) default '',
  tel  VARCHAR2(250) default '',
  phone  VARCHAR2(250) default '',
  email  VARCHAR2(250) default '',
  address  VARCHAR2(250) default '',
  postcode  VARCHAR2(250) default '',
  idcard  VARCHAR2(250) default '',
  user_status NUMBER(4),
  resume  clob default '',
  user_memo  clob default '',
  is_publish NUMBER(4) default 0,
  photo VARCHAR2(250) default '',
  politics_status VARCHAR2(250) default '',
  dept_name VARCHAR2(250) default '',
  to_work_time VARCHAR2(250) default '',
  work_desc clob default '',
  summary clob default '',
  sort NUMBER(4) default 999,
  bein_dept VARCHAR2(250) default ''
);

create table cs_org_userlevel(
  userlevel_id NUMBER(20) not null PRIMARY KEY,
  userlevel_name VARCHAR2(250) default '',
  userlevel_value NUMBER(4),
  userlevel_memo VARCHAR2(1000) default '',
  is_delete NUMBER(1) default 0
);

create table cs_org_register(
  register_id NUMBER(20) not null PRIMARY KEY,
  user_id NUMBER(20),
  username VARCHAR2(250) default '',
  password VARCHAR2(250) default '',
  register_status NUMBER(4) default 0
);

create table cs_org_group(
  group_id NUMBER(20) not null PRIMARY KEY,
  parent_id NUMBER(20),
  group_name VARCHAR2(250) default '',
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default '',
  group_memo VARCHAR2(1000) default ''
);

create table cs_org_group_user(
  group_user_id NUMBER(20) not null PRIMARY KEY,
  group_id NUMBER(20),
  user_id NUMBER(20),
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_org_role(
  role_id NUMBER(20) not null PRIMARY KEY,
  role_name VARCHAR2(250) default '',
  rele_shared NUMBER(4),
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default '',
  role_memo VARCHAR2(1000) default ''
);

create table cs_org_role_app(
  role_app_id NUMBER(20) not null PRIMARY KEY,
  role_id NUMBER(20),
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_org_user_role(
  user_role_id NUMBER(20) not null PRIMARY KEY,
  role_id NUMBER(20),
  user_id NUMBER(20),
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_org_group_role(
  group_role_id NUMBER(20) not null PRIMARY KEY,
  role_id NUMBER(20),
  group_id NUMBER(20),
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_org_opt(
  opt_id NUMBER(20) not null PRIMARY KEY,
  parent_id NUMBER(20),
  tree_position VARCHAR2(250) default '',
  opt_name VARCHAR2(250) default '',
  app_id VARCHAR2(250) default '',
  controller VARCHAR2(250) default '',
  action VARCHAR2(250) default '',
  opt_flag VARCHAR2(250) default '',
  opt_memo VARCHAR2(1000) default ''
);

create table cs_org_menu(
  menu_id NUMBER(20) not null PRIMARY KEY,
  parent_id NUMBER(20),
  menu_name VARCHAR2(250) default '',
  menu_url VARCHAR2(250) default '',
  handls VARCHAR2(250) default '',
  opt_id NUMBER(20),
  menu_level NUMBER(20),
  menu_position VARCHAR2(250) default '',
  menu_sort NUMBER(20) default 0,
  menu_memo VARCHAR2(1000) default ''
);

create table cs_org_role_opt(
  role_opt_id NUMBER(20) not null PRIMARY KEY,
  role_id NUMBER(20),
  opt_id NUMBER(20)
);

create table cs_site_user(
  user_id NUMBER(20) not null,
  app_id varchar2(250) default '',
  site_id varchar2(250) default ''
);

-- 公务员名录关联模板表
create table cs_org_minlu_template(
  id NUMBER(20) not null PRIMARY KEY,
  template_index NUMBER(20) default 0,
  template_list NUMBER(20) default 0,
  template_content NUMBER(20) default 0,
  reinfo_temp_list NUMBER(20) default 0,
  reinfo_temp_content NUMBER(20) default 0,
  reinfo_temp_pic_list NUMBER(20) default 0,
  reinfo_temp_pic_content NUMBER(20) default 0,
  app_id varchar2(250) default '',
  site_id varchar2(250) default ''
);

-- 用户桌面设置表
create table cs_user_desktop(
  id NUMBER(20) not null PRIMARY KEY,
  user_id NUMBER(20) not null,
  app_type varchar2(250) default '',
  k_v varchar2(250) default ''
);

create table cs_page(
  id NUMBER(20) not null PRIMARY KEY,
  page_id NUMBER(20) default 0,
  parent_id NUMBER(20) default 0,
  page_ename VARCHAR2(250) default '',
  page_cname VARCHAR2(250) default '',
  template_id NUMBER(20) default 0,
  page_path VARCHAR2(250) default '',
  page_interval NUMBER(20) default 0,
  last_dtime VARCHAR2(20) default '',
  next_dtime VARCHAR2(20) default '',
  sort_id NUMBER(4) default 0,
  page_memo VARCHAR2(1000) default '',
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_wcm_sequence(
  table_name VARCHAR2(200) not null PRIMARY KEY,
  seq NUMBER(10)
);

create table cs_log_setting(
  audit_id NUMBER(20) not null PRIMARY KEY,
  user_id NUMBER(20),
  user_name VARCHAR2(250) default '',
  audit_des VARCHAR2(250) default '',
  audit_time VARCHAR2(250) default '',
  ip VARCHAR2(250) default '',
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_log_login(
  audit_id   NUMBER(20) not null,
  user_id NUMBER(20),
  user_name  varchar2(250),
  audit_des  varchar2(250),
  audit_time char(20),
  ip  varchar2(250),
  app_id varchar2(250),
  site_id varchar2(250)
);

create table cs_dz_chaxun_conf(
  conf_id NUMBER(20) not null PRIMARY KEY,
  conf_title VARCHAR2(1000) default '',
  conf_description VARCHAR2(2000) default '',
  t_list_id NUMBER(20),
  t_content_id NUMBER(20),
  site_id VARCHAR2(250) default '',
  app_id VARCHAR2(250) default ''
);

create table cs_dz_chaxun_dic(
  dic_id NUMBER(4) not null,
  conf_id NUMBER(20) not null,
  field_cname VARCHAR2(250) default '',
  is_selected NUMBER(20) not null,
  is_query NUMBER(20) not null,
  is_result NUMBER(20) not null,
  sort_id NUMBER(20) not null,
  site_id VARCHAR2(250) default ''
);

create table cs_dz_chaxun_item(
  item_id NUMBER(20) not null,
  conf_id NUMBER(20) not null,
  item_key NUMBER(20) not null,
  item_value VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table dz_search_customkeys(
  id NUMBER(20) not null PRIMARY KEY,
  title VARCHAR2(1000) default '',
  site_id VARCHAR2(20) DEFAULT '',
  countnum VARCHAR2(250) DEFAULT '0'
);

create table dz_search_keys(
  id NUMBER(20) not null PRIMARY KEY,
  title VARCHAR2(1000) DEFAULT '',
  add_time VARCHAR2(20) DEFAULT '',
  site_id VARCHAR2(20) DEFAULT '',
  sort_id NUMBER(20) DEFAULT 0
);

create table cp_survey_category(
  id NUMBER(10) not null PRIMARY KEY,
  category_id VARCHAR2(200) default '',
  c_name VARCHAR2(1000) default '',
  description  clob  default '',
  model_path VARCHAR2(255) default '',
  publish_status NUMBER(1) default 0,
  publish_time VARCHAR2(40) default '',
  add_time VARCHAR2(200) default '',
  add_user VARCHAR2(200) default '',
  update_time VARCHAR2(200) default '',
  update_user VARCHAR2(200) default '',
  is_delete NUMBER(1) default 0,
  sort NUMBER(5) default 0,
  site_id VARCHAR2(200) default '',
  template_list_path NUMBER(20) default 0,
  template_content_path NUMBER(20) default 0,
  template_result_path NUMBER(20) default 0
);

create table cp_survey(
  id NUMBER(10) not null PRIMARY KEY,
  s_id VARCHAR2(200) default '',
  category_id VARCHAR2(200) default '',
  s_name VARCHAR2(1000) default '',
  description  clob  default '',
  s_type VARCHAR2(255) default '',
  is_register NUMBER(1) default 0,
  is_show_subsort NUMBER(1) default 0,
  ip_fre NUMBER(3) default 1,
  start_time VARCHAR2(40) default '',
  end_time VARCHAR2(40) default '',
  model_path VARCHAR2(255) default '',
  pic_path VARCHAR2(500) default '',
  show_result_status NUMBER(1) default 1,
  verdict clob default '',
  ip_restrict VARCHAR2(4000) default '',
  publish_status NUMBER(1) default 0,
  publish_time VARCHAR2(40) default '',
  survey_content clob default '',
  is_show_title NUMBER(1) default 0,
  is_show_result NUMBER(1) default 0,
  recommend_flag NUMBER(5) default 0,
  recommend_time VARCHAR2(40) default '',
  add_time VARCHAR2(200) default '',
  add_user VARCHAR2(200) default '',
  update_time VARCHAR2(200) default '',
  update_user VARCHAR2(200) default '',
  is_delete NUMBER(1) default 0,
  sort NUMBER(5) default 0,
  spacing_interval VARCHAR2(20) default '',
  site_id VARCHAR2(200) default '',
  is_checkcode NUMBER(1) default 0
);

create table cp_survey_sub(
  id NUMBER(10) not null PRIMARY KEY,
  survey_id VARCHAR2(200) default '',
  subject_id VARCHAR2(200) default '',
  sub_name VARCHAR2(1000) default '',
  subject_type VARCHAR2(100) default '',
  explains VARCHAR2(4000) default '',
  item_value VARCHAR2(4000) default '',
  is_required NUMBER(1) default 0,
  sort NUMBER(4) default 0
);

create table cp_survey_item(
  survey_id VARCHAR2(200) default '',
  subject_id VARCHAR2(200) default '',
  item_id VARCHAR2(200) default '',
  item_num NUMBER(10) default 0,
  item_name VARCHAR2(1000) default '',
  is_text NUMBER(1) default 0,
  text_value VARCHAR2(255) default '',
  sort NUMBER(5) default 0
);

create table cp_survey_child(
  subject_id VARCHAR2(200) default '',
  item_id VARCHAR2(200) default '',
  item_num NUMBER(10) default 0,
  item_name VARCHAR2(1000) default '',
  is_text NUMBER(1) default 0,
  text_value VARCHAR2(255) default '',
  score NUMBER(5) default 0,
  sort NUMBER(5) default 0
);

create table cp_survey_answer(
  id NUMBER(10) not null PRIMARY KEY,
  answer_id VARCHAR2(200) default '',
  s_id VARCHAR2(200) default '',
  ip VARCHAR2(100) default '',
  answer_time VARCHAR2(40) default '',
  operate_time NUMBER(10) default 0,
  user_name VARCHAR2(200) default ''
);

create table cp_survey_answer_item(
  answer_id VARCHAR2(200) default '',
  s_id VARCHAR2(200) default '',
  item_id VARCHAR2(100) default '',
  item_value VARCHAR2(40) default '',
  item_text VARCHAR2(4000) default ''
);


create table cs_info_meta(
  meta_id NUMBER(20) not null PRIMARY KEY,
  meta_ename VARCHAR2(250) default '',
  meta_sname VARCHAR2(250) default '',
  meta_cname VARCHAR2(250) default '',
  meta_define VARCHAR2(250) default '',
  meta_datatype VARCHAR2(250) default '',
  meta_codomain VARCHAR2(250) default '',
  meta_iselect NUMBER(4) default 0,
  meta_maxtimes NUMBER(4) default 0,
  meta_sample VARCHAR2(250) default '',
  meta_type VARCHAR2(250) default '',
  parent_id NUMBER(4) default 0,
  is_core NUMBER(4) default 0
);

create table cs_info_model(
  model_id NUMBER(20) not null PRIMARY KEY,
  model_ename VARCHAR2(250) default '',
  model_name VARCHAR2(250) default '',
  table_name VARCHAR2(250) default '',
  model_sort NUMBER(4) default 999,
  template_list VARCHAR2(250) default '',
  template_show VARCHAR2(250) default '',
  model_icon VARCHAR2(250) default '',
  app_id VARCHAR2(250) default '',
  disabled NUMBER(4) default 0,
  model_memo VARCHAR2(1000) default '',
  add_page VARCHAR2(250) default '',
  view_page VARCHAR2(250) default '',
  model_type VARCHAR2(10) default '0'
);

create table cs_info_field(
  field_id NUMBER(20) not null PRIMARY KEY,
  model_id NUMBER(20),
  meta_id NUMBER(20),
  field_ename VARCHAR2(250) default '',
  field_cname VARCHAR2(250) default '',
  table_name VARCHAR2(250) default '',
  is_show NUMBER(4) default 0,
  field_memo VARCHAR2(1000) default ''
);

create table cs_info_hotword(
  hot_id NUMBER(20) not null PRIMARY KEY,
  hot_name VARCHAR2(250) default '',
  hot_url VARCHAR2(500) default '',
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_info_author(
  author_id  NUMBER(20) not null,
  author_name VARCHAR2(250) default '',
  author_initial VARCHAR2(250) default '',
  author_url VARCHAR2(500) default '',
  app_id  VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_sys_filterword(
  filterword_id NUMBER(20) not null PRIMARY KEY,
  pattern VARCHAR2(250) default '',
  replacement VARCHAR2(500) default '',
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_ware_category(
  id NUMBER(20) not null PRIMARY KEY,
  wcat_id NUMBER(20) default 0,
  parent_id NUMBER(20) default 0,
  wcat_name VARCHAR2(250) default '',
  wcat_position VARCHAR2(250) default '',
  wcat_level NUMBER(4) default 0,
  wcat_memo VARCHAR2(1000) default '',
  sort_id NUMBER(4) default 0,
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default '',
  receive_recom NUMBER(4) default 0
);

create table cs_ware(
  id NUMBER(20) not null PRIMARY KEY,
  ware_id NUMBER(20) default 0,
  wcat_id NUMBER(20) default 0,
  ware_name VARCHAR2(250) default '',
  rss_url VARCHAR2(250) default '',
  ware_content clob default '',
  ware_type NUMBER(4) default 0,
  info_num NUMBER(4) default 0,
  ware_width NUMBER(4) default 0,
  ware_interval NUMBER(4) default 0,
  ware_url VARCHAR2(250) default '',
  sort_id NUMBER(4) default 0,
  ware_memo VARCHAR2(1000) default '',
  last_dtime VARCHAR2(250) default '',
  next_dtime VARCHAR2(250) default '',
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default '',
  update_dtime VARCHAR2(250) default '',
  receive_recom NUMBER(4) default 0
);

create table cs_ware_info(
  winfo_id NUMBER(20) not null PRIMARY KEY,
  ware_id NUMBER(20) default 0,
  sort_id NUMBER(4) default 999,
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_ware_infos(
  id NUMBER(20) not null PRIMARY KEY,
  ware_id NUMBER(20) default 0,
  winfo_id NUMBER(20) default 0,
  pre_title VARCHAR2(250) default '',
  title VARCHAR2(250) default '',
  subtitle VARCHAR2(250) default '',
  title_color VARCHAR2(250) default '',
  description VARCHAR2(1000) default '',
  thumb_url VARCHAR2(250) default '',
  content_url VARCHAR2(250) default '',
  sort_id NUMBER(4) default 999,
  publish_dtime VARCHAR2(20) default '',
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_info_ref(
  ref_id NUMBER(20) not null PRIMARY KEY,
  ware_id NUMBER(20) default 0,
  info_id NUMBER(20) default 0,
  update_dtime VARCHAR2(20) default '',
  ref_user NUMBER(20) default 0,
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_sys_config(
  config_id  NUMBER(20) not null PRIMARY KEY,
  config_group	VARCHAR2(255),
  config_key	VARCHAR2(255),
  config_value	VARCHAR2(2000),
  site_id	VARCHAR2(255),
  app_id	VARCHAR2(255)
);

create table cs_sys_dict_category(
  id NUMBER(20) not null PRIMARY KEY,
  dict_cat_id VARCHAR2(255),
  dict_cat_name VARCHAR2(255),
  dict_cat_memo VARCHAR2(1000),
  is_sys NUMBER(4)  default 0,
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_sys_dict(
  id NUMBER(20) not null PRIMARY KEY,
  dict_cat_id VARCHAR2(255),
  dict_id VARCHAR2(255),
  dict_name VARCHAR2(255),
  dict_sort NUMBER(4),
  is_defult NUMBER(4)  default 0,
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_comment(
  cmt_id  NUMBER(20) not null,
  item_id NUMBER(20),
  parent_id   NUMBER(20) default 0,
  cmt_content VARCHAR2(2000) default '',
  me_id NUMBER(20),
  me_nickname VARCHAR2(250) default '',
  add_dtime   VARCHAR2(20) not null,
  cmt_ip  VARCHAR2(50) default '',
  support_num NUMBER(20) default 0,
  cmt_status  NUMBER(4) default 0,
  is_deleted  NUMBER(4) default 0,
  app_id  VARCHAR2(250) default '',
  site_id VARCHAR2(250) default ''
);

create table cs_info_source(
  source_id  NUMBER(20) not null,
  source_name VARCHAR2(250),
  source_initial VARCHAR2(250),
  source_url VARCHAR2(500),
  logo_path  VARCHAR2(250),
  app_id  VARCHAR2(250),
  site_id VARCHAR2(250)
);

create table cs_info_tags(
  tag_id NUMBER(20) not null,
  tag_name  VARCHAR2(250) default '',
  tag_color VARCHAR2(250) default '',
  font_size NUMBER(20),
  tag_times NUMBER(20),
  app_id VARCHAR2(250),
  site_id   VARCHAR2(250)
);

create table cs_info_udefined(
  ud_id NUMBER(20) not null,
  info_id NUMBER(20),
  model_id NUMBER(20),
  field_ename VARCHAR2(250) default '',
  field_value VARCHAR2(1000) default ''
);

create table cs_wcat_priv(
  id NUMBER(20) not null,
  wcat_id   NUMBER(20) default 0,
  prv_id NUMBER(20) default 0,
  priv_type NUMBER(4) default 0,
  app_id VARCHAR2(250) default '',
  site_id   VARCHAR2(250) default ''
);

create table cs_err_report(
  id NUMBER(20) not null PRIMARY KEY,
  info_id NUMBER(20),
  info_title VARCHAR2(1000),
  info_url VARCHAR2(1000),
  err_type VARCHAR2(255),   -- 错误类型  1:错别字  2:图片显示错误  3:页面格式错误  4:其他错误
  err_content clob,-- 错误内容
  err_name VARCHAR2(255),   -- 提交人姓名
  err_name_tel VARCHAR2(255),   -- 提交人电话
  err_name_email VARCHAR2(255),   -- 提交人邮箱
  err_name_ip VARCHAR2(255),   -- 提交人ip
  err_time VARCHAR2(255),  -- 提交时间
  err_state VARCHAR2(2),  -- 状态 1:用户提交（未处理）  2:不予处理3:已处理
  err_note  clob,-- 意见
  site_id VARCHAR2(255),   -- 站点id
  o_time VARCHAR2(255)   -- 处理时间
);

create table cs_comment_set(
  id NUMBER(20) not null PRIMARY KEY,
  app_id VARCHAR2(250),
  site_id VARCHAR2(250),
  is_public VARCHAR2(10),
  com_prefix VARCHAR2(1000),
  is_need VARCHAR2(10),
  is_code VARCHAR2(10),
  time_spacer VARCHAR2(250),
  ip_time VARCHAR2(250),
  pass_size NUMBER(20),
  tact_word  clob
);

create table cs_comment_main(
  id NUMBER(20) not null PRIMARY KEY,
  info_id NUMBER(20) default 0,
  info_uuid VARCHAR2(250) default '',
  app_id VARCHAR2(250) default '',
  site_id VARCHAR2(250) default '',
  info_type NUMBER(4) default 0,
  is_member NUMBER(4) default 0,
  nick_name VARCHAR2(250) default '',
  user_name VARCHAR2(250) default '',
  member_id NUMBER(20) default 0,
  tel VARCHAR2(250) default '',
  email VARCHAR2(250) default '',
  add_time VARCHAR2(20) default '',
  content clob ,
  content2 clob ,
  is_report NUMBER(4) default 0,
  report_count NUMBER(20) default 0,
  report_last_time VARCHAR2(20) default '',
  is_quest NUMBER(4) default 0,
  ip VARCHAR2(250) default '',
  is_status  NUMBER(4) default 0,
  support_count NUMBER(20) default 0,
  parent_id NUMBER(20) default 0,
  parent_str VARCHAR2(4000) default '',
  is_replay NUMBER(4) default 0,
  is_delete NUMBER(4) default 0,
  is_top NUMBER(4) default 0,
  top_time VARCHAR2(20) default ''
);

create table cs_field_data(
  id NUMBER(20) not null PRIMARY KEY,
  field_enname VARCHAR2(255),
  field_cnname VARCHAR2(255),
  field_type VARCHAR2(20),
  is_sys VARCHAR2(20),
  is_null VARCHAR2(20),
  is_display VARCHAR2(20),
  field_text VARCHAR2(255),
  field_info clob,
  add_time  VARCHAR2(255)
);

create table cs_model_data(
  id NUMBER(20) not null PRIMARY KEY,
  from_id NUMBER(20),
  model_id NUMBER(20),
  field_enname VARCHAR2(255),
  field_cnname VARCHAR2(255),
  field_type VARCHAR2(20),
  is_sys VARCHAR2(20),
  is_null VARCHAR2(20),
  is_display VARCHAR2(20),
  field_text VARCHAR2(255),
  field_sort NUMBER(20),
  field_info clob,
  add_time  VARCHAR2(255),
  field_flag VARCHAR2(255),
  field_flag2 VARCHAR2(255)
);

create table cs_wcminfo_zykinfo(
  id VARCHAR2(255) not null PRIMARY KEY,
  info_id NUMBER(20)
);

create table cs_zykinfo_file(
  id NUMBER(20) not null PRIMARY KEY,
  info_id NUMBER(20),
  file_id VARCHAR2(255) ,
  fileSize NUMBER(20),
  fileSufix VARCHAR2(255),
  fileName VARCHAR2(255),
  businessId VARCHAR2(255),
  fieldName VARCHAR2(255)
);

create table rmi_server_conf(
  id NUMBER(20) not null PRIMARY KEY,
  ip VARCHAR2(20) not null ,
  port VARCHAR2(20) not null
);

create table cs_snippet(
  id NUMBER(20) not null PRIMARY KEY,
  sni_id NUMBER(20) not null,
  sni_name VARCHAR2(250),
  sni_content clob,
  app_id VARCHAR2(250),
  site_id VARCHAR2(250) default ''
);

create table cs_template(
  id NUMBER(20) not null PRIMARY KEY,
  t_id NUMBER(20) not null,
  t_ver  NUMBER(4),
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_template_class(
  id NUMBER(20) not null PRIMARY KEY,
  tclass_id	   NUMBER(20) not null,
  tclass_ename	VARCHAR2(255),
  tclass_cname	VARCHAR2(255),
  tclass_memo	VARCHAR2(1000),
  app_id VARCHAR2(255)
);

create table cs_template_category(
  id NUMBER(20) not null PRIMARY KEY,
  tcat_id	   NUMBER(20) not null,
  tclass_id	 NUMBER(20),
  tcat_ename	 VARCHAR2(255),
  tcat_cname	 VARCHAR2(255),
  parent_id 	NUMBER(20),
  tcat_position 	VARCHAR2(255),
  tcat_memo	VARCHAR2(1000),
  app_id	VARCHAR2(255),
  site_id	VARCHAR2(255),
  sort_id NUMBER(4) default 999
);

create table cs_template_edit(
  id NUMBER(20) not null PRIMARY KEY,
  t_id	NUMBER(20) not null,
  tcat_id	NUMBER(20),
  t_ename	VARCHAR2(255),
  t_cname	VARCHAR2(255),
  t_path	VARCHAR2(255),
  t_content	 clob ,
  t_ver	NUMBER(4),
  creat_user	NUMBER(20),
  creat_dtime	VARCHAR2(20),
  modify_user	NUMBER(20),
  modify_dtime	VARCHAR2(20),
  app_id	VARCHAR2(255),
  site_id	VARCHAR2(255),
  t_status VARCHAR2(4) default 0
);

create table cs_template_ver(
  id NUMBER(20) not null PRIMARY KEY,
  t_id	NUMBER(20) not null,
  tcat_id	NUMBER(20),
  t_ename	VARCHAR2(255),
  t_cname	VARCHAR2(255),
  t_path	VARCHAR2(255),
  t_content	 clob ,
  t_ver	NUMBER(4),
  creat_user	NUMBER(20),
  creat_dtime	VARCHAR2(20),
  modify_user	NUMBER(20),
  modify_dtime	VARCHAR2(20),
  app_id	VARCHAR2(255),
  site_id	VARCHAR2(255),
  t_status varchar2(4)
);

create table cs_design_css(
  id NUMBER(20) not null PRIMARY KEY,
  css_id	NUMBER(20) not null,
  css_ename VARCHAR2(255),
  css_name VARCHAR2(255),
  thumb_url VARCHAR2(255),
  weight NUMBER(4) default 0,
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_design_layout(
  id NUMBER(20) not null PRIMARY KEY,
  layout_id	NUMBER(20) not null,
  layout_name VARCHAR2(255),
  layout_content clob,
  thumb_url VARCHAR2(255),
  weight NUMBER(4) default 0,
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_design_module(
  id NUMBER(20) not null PRIMARY KEY,
  module_id NUMBER(20) not null,
  datasouce_type NUMBER(4) default 0,
  module_name VARCHAR2(255),
  module_content clob,
  v_code clob,
  thumb_url VARCHAR2(255),
  style_ids VARCHAR2(500),
  attr_ids VARCHAR2(500),
  weight NUMBER(4) default 0,
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_design_style(
  id NUMBER(20) not null PRIMARY KEY,
  style_id NUMBER(20) not null,
  style_name VARCHAR2(255),
  class_name VARCHAR2(255),
  thumb_url VARCHAR2(255),
  weight NUMBER(4) default 0,
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_design_frame(
  id NUMBER(20) not null PRIMARY KEY,
  frame_id NUMBER(20) not null,
  frame_name VARCHAR2(255),
  frame_content clob,
  thumb_url VARCHAR2(255),
  weight NUMBER(4) default 0,
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_design_case(
  id NUMBER(20) not null PRIMARY KEY,
  case_id NUMBER(20) not null,
  css_id NUMBER(20) not null,
  case_name VARCHAR2(255),
  case_content clob,
  thumb_url VARCHAR2(255),
  weight NUMBER(4) default 0,
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_design_category(
  id  NUMBER(20) not null PRIMARY KEY,
  cat_id NUMBER(20) not null,
  css_id NUMBER(20) not null,
  template_id NUMBER(20) not null,
  design_content clob,
  page_type VARCHAR2(50),
  publish_status NUMBER(4) default 0,
  app_id VARCHAR2(255),
  site_id VARCHAR2(255)
);

create table cs_gk_node_category(
  nodcat_id NUMBER(20) not null PRIMARY KEY,
  parent_id NUMBER(20),
  nodcat_name VARCHAR2(250) default '',
  nod_position VARCHAR2(250) default '',
  sort_id NUMBER(4),
  app_id VARCHAR2(250) default ''
);

create table cs_gk_node(
  id NUMBER(20) not null PRIMARY KEY,
  node_id VARCHAR2(250) default '',
  nodcat_id NUMBER(20),
  dept_id NUMBER(20),
  rela_site_id VARCHAR2(250) default '',
  node_name VARCHAR2(250) default '',
  node_fullname VARCHAR2(250) default '',
  node_status NUMBER(4),
  index_template_id VARCHAR2(20) default '',
  sort_id NUMBER(4),
  dept_code VARCHAR2(250) default '',
  is_apply NUMBER(4),
  address varchar2(255),
  postcode varchar2(255),
  office_dtime varchar2(255),
  tel varchar2(255),
  fax varchar2(255),
  email varchar2(255),
  apply_name VARCHAR2(250) default '',
  node_demo VARCHAR2(1000) default ''
);

create table cs_gk_indexrole
(
  id  number(20) primary key,
  ir_id varchar2(250) default '',
  ir_item varchar2(250) default '0',
  ir_value varchar2(250) default '',
  ir_space varchar2(250) default '',
  is_valid number(4) default 1,
  sort_id number(4) default 0,
  ir_type number(4) default 0
);

create table cs_gk_sequence
(
  id number(20) primary key,
  seq_item varchar2(250) default '',
  seq_cur_value number(20) default 1,
  seq_ymd varchar2(250) default'',
  seq_type number(4) default 0,
  app_id varchar2(250) default 'GK',
  site_id varchar2(250) default ''
);

create table cs_gk_info(
  info_id NUMBER(20) not null,
  gk_index VARCHAR2(250) default '',
  gk_year VARCHAR2(250) default '',
  gk_num NUMBER(20) default 0,
  doc_no VARCHAR2(250) default '',
  gk_file VARCHAR2(250) default '',
  generate_dtime VARCHAR2(20) default '',
  effect_dtime VARCHAR2(20) default '',
  aboli_dtime VARCHAR2(20) default '',
  topic_id NUMBER(20) default 0,
  topic_name VARCHAR2(250) default '',
  theme_id NUMBER(20) default 0,
  theme_name VARCHAR2(250) default '',
  serve_id NUMBER(20) default 0,
  serve_name VARCHAR2(250) default '',
  topic_key VARCHAR2(250) default '',
  place_key VARCHAR2(250) default '',
  language VARCHAR2(250) default '',
  carrier_type VARCHAR2(250) default '',
  gk_validity VARCHAR2(250) default '',
  gk_format VARCHAR2(250) default '',
  gk_way VARCHAR2(250) default '',
  gk_type NUMBER(4) default 0,
  gk_no_reason VARCHAR2(1000) default '',
  gk_time_limit  VARCHAR2(250) default '',
  gk_range VARCHAR2(250) default '',
  gk_proc VARCHAR2(1000) default '',
  dict_cat_id VARCHAR2(250) default '',
  sxlb_id VARCHAR2(250) default '',
  sxlb_name VARCHAR2(250) default '',
  gk_duty_dept VARCHAR2(250) default '',
  gk_input_dept VARCHAR2(250) default '',
  file_head VARCHAR2(250) default '',
  gk_signer VARCHAR2(250) default ''
);

create table cs_gk_f_tygs(
  info_id NUMBER(20) not null,
  gk_content clob
);

create table cs_gk_f_jggk(
  info_id number(20) primary key,
  gk_nsjg clob default '',
  gk_gzzz clob default '',
  gk_xmzw clob default '',
  gk_dz varchar2(250) default '',
  gk_yzbm varchar2(250) default '',
  gk_zbdh varchar2(250) default '',
  gk_chzh varchar2(250) default '',
  gk_email varchar2(250) default '',
  gk_weburl varchar2(250) default '',
  gk_sltsbm varchar2(250) default '',
  gk_sltsdh varchar2(250) default ''
);

create table cs_gk_f_flgw(
  info_id NUMBER(20) not null,
  gk_uniform_num VARCHAR2(250) default '',
  gk_qsrq VARCHAR2(20) default '',
  gk_djrq VARCHAR2(20) default '',
  gk_xxsxx VARCHAR2(250) default '',
  gk_content clob
);

create table cs_gk_resfile(
  res_id NUMBER(20) not null PRIMARY KEY,
  info_id NUMBER(20) not null,
  res_name VARCHAR2(250) default '',
  res_url VARCHAR2(250) default '',
  sort_id NUMBER(4) default 999
);

create table cs_gk_f_bszn
(
  info_id number(20),
  gk_fwlb varchar2(250) default '',
  gk_bsjg varchar2(250) default '',
  gk_sxyj clob default '',
  gk_bldx varchar2(250) default '',
  gk_bltj clob default '',
  gk_blsx clob default '',
  gk_bllc clob default '',
  gk_gsfs varchar2(250) default '',
  gk_blshixian clob default '',
  gk_sfbz clob default '',
  gk_sfyj clob default '',
  gk_zxqd clob default '',
  gk_bgsj clob default '',
  gk_cclx clob default '',
  gk_jgwz varchar2(250) default '',
  gk_jdjc clob default '',
  gk_zrzj clob default '',
  gk_wszx clob default '',
  gk_wsbl clob default '',
  gk_ztcx clob default '',
  gk_wsts clob default '',
  gk_memo varchar2(1000) default ''

);

create table cs_gk_f_ldcy(
  info_id NUMBER(20) not null,
  gk_ldzw VARCHAR2(250) default '',
  gk_grjl clob,
  gk_gzfg clob,
  gk_dz VARCHAR2(250) default '',
  gk_tel VARCHAR2(250) default '',
  gk_email VARCHAR2(250) default '',
  gk_pic	 VARCHAR2(250) default '',
  gk_content clob
);

create table cs_gk_f_xsjg(
  info_id NUMBER(20) not null,
  gk_gzzz clob,
  gk_fzr VARCHAR2(250) default '',
  gk_bgdz VARCHAR2(250) default '',
  gk_yzbm VARCHAR2(250) default '',
  gk_gkdh VARCHAR2(250) default '',
  gk_chzh VARCHAR2(250) default '',
  gk_email VARCHAR2(250) default ''
);

create table cs_gk_f_xzzf(
  info_id NUMBER(20) not null,
  gk_zflb VARCHAR2(250) default '',
  gk_xzxw clob,
  gk_xzcl clob,
  gk_flyj clob,
  gk_nrfj clob
);

create table cs_gk_count
(
  cat_id number(20) not null,
  info_count number(20) default 0,
  z_count number(20) default 0,
  y_count number(20) default 0,
  b_count number(20) default 0,
  update_time char(20) default '',
  app_id varchar2(250) default '',
  site_id varchar2(250) default ''
);

create table cs_gk_ysq_config
(
id  number(20) primary key,
time_limit number(4) default 15,
code_pre  varchar2(20) default 'YSQ',
code_rule varchar2(20) default 'YYYYMMDD',
code_num  number(4)  default 4,
query_num number(4)  default 4,
file_url  varchar2(250) default '',
must_member number(4) default 0,
remind_type varchar2(20) default '',
user_secret number(4) default 0,
is_auto_publish number(4) default 1,
template_form number(4) default 0,
template_list number(4) default 0,
template_content number(4) default 0,
template_over number(4) default 0,
template_print number(4) default 0,
template_query number(4) default 0
);

create table cs_gk_ysq(
  ysq_id NUMBER(20) not null,
  ysq_code VARCHAR2(250) default '',
  query_code VARCHAR2(50) default '',
  ysq_type number(4) default 0,
  name  VARCHAR2(250) default '',
  company VARCHAR2(250) default '',
  card_name VARCHAR2(250) default '',
  card_code VARCHAR2(250) default '',
  org_code VARCHAR2(250) default '',
  licence VARCHAR2(250) default '',
  legalperson VARCHAR2(250) default '',
  linkman VARCHAR2(250) default '',
  tel VARCHAR2(250) default 0,
  fax VARCHAR2(250) default '',
  phone VARCHAR2(250) default 0,
  email VARCHAR2(250) default '',
  postcode VARCHAR2(250) default '',
  address VARCHAR2(250) default '',
  put_dtime VARCHAR2(250) default '',
  content clob,
  gk_index VARCHAR2(250) default '',
  description clob,
  is_derate NUMBER(4) default 0,
  offer_type VARCHAR2(250) default '',
  get_method  VARCHAR2(250) default '',
  is_other NUMBER(4) default 0,
  is_third NUMBER(4) default 0,
  is_extend NUMBER(4) default 0,
  accept_content clob,
  accept_dtime VARCHAR2(20) default '',
  accept_user  NUMBER(10) default 0,
  reply_type  NUMBER(4) default 0,
  reply_content  clob,
  reply_dtime  VARCHAR2(20) default '',
  reply_user  NUMBER(10) default 0,
  is_mail  NUMBER(4) default 0,
  node_id VARCHAR2(250) default '',
  node_name VARCHAR2(250) default '',
  do_state NUMBER(4) default 0,
  final_status NUMBER(4) default 0,
  publish_state NUMBER(4) default 0,
  supervise_flag NUMBER(4) default 0,
  time_limit NUMBER(4) default 15,
  timeout_flag NUMBER(4) default 0,
  sat_score NUMBER(4) default 0,
  hits NUMBER(20) default 0,
  day_hits NUMBER(20) default 0,
  week_hits NUMBER(20) default 0,
  month_hits NUMBER(20) default 0,
  lasthit_dtime VARCHAR2(20) default '',
  weight NUMBER(4) default 60
);

create table cs_gk_phrasal(
  gph_id NUMBER(20) not null,
  gph_title VARCHAR2(255) default '',
  gph_content VARCHAR2(2000) default '',
  gph_type NUMBER(4) default 0,
  sort_id NUMBER(4)
);

create table cs_ser_category(
  id NUMBER(20) not null,
  ser_id NUMBER(20),
  parent_id NUMBER(20),
  cat_type varchar2(20) default 'sub',
  tree_position varchar2(250) default '',
  cat_name varchar2(250) default '',
  description varchar2(1000) default '',
  next_desc varchar2(250) default '',
  thumb_url varchar2(250) default '',
  url varchar2(250) default '',
  template_index NUMBER(20) default 0,
  template_list NUMBER(20) default 0,
  template_content NUMBER(20) default 0,
  xgwt_cat_id NUMBER(20) default 0,
  cjwt_cat_id NUMBER(20) default 0,
  sort_id  NUMBER(4) default 999,
  publish_status NUMBER(4) default 0,
  publish_time varchar2(20) default '',
  res_flag NUMBER(4) default 0,
  dict_id varchar2(250) default '',
  add_time varchar2(20) default '',
  add_user varchar2(20) default '',
  update_time varchar2(20) default '',
  update_user varchar2(20) default ''
);

create table cs_ser_resouce(
  id NUMBER(20) not null,
  res_id NUMBER(20),
  ser_id NUMBER(20),
  title varchar2(250) default '',
  content clob,
  url varchar2(250) default '',
  sort_id NUMBER(4) default 999,
  res_status NUMBER(20) default 0,
  publish_status NUMBER(4) default 0,
  publish_time varchar2(20) default '',
  add_time varchar2(20) default '',
  add_user varchar2(20) default '',
  update_time varchar2(20) default '',
  update_user varchar2(20) default ''
);

-- 公开应用目录主表
create table cs_gk_app_catalog(
  id NUMBER(20) not null,
  cata_id NUMBER(20) not null,
  cata_cname VARCHAR2(250) default '',
  parent_id NUMBER(20),
  tree_position VARCHAR2(250) default '',
  template_index NUMBER(20),
  template_list NUMBER(20),
  is_mutilpage NUMBER(4),
  jump_url VARCHAR2(250) default '',
  cat_keywords VARCHAR2(250) default '',
  cat_description VARCHAR2(250) default '',
  cat_memo VARCHAR2(1000) default '',
  cat_sort NUMBER(4) default 999,
  c_sql VARCHAR2(1000) default ''
);

-- 公开应用目录同步规则表
create table cs_gk_app_regu(
  id NUMBER(20) not null,
  cata_id NUMBER(20) not null,
  regu_type NUMBER(4) default 0,
  site_ids VARCHAR2(1000) default '',
  cat_ids	VARCHAR2(1000) default ''
);

insert into cs_wcm_sequence(table_name,seq)values('cs_site_server',1);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_user_role',1);
insert into cs_wcm_sequence(table_name,seq)values('cs_member_config',1);
insert into cs_wcm_sequence(table_name,seq)values('cp_category',1);
insert into cs_wcm_sequence(table_name,seq)values('cp_area',1);
insert into cs_wcm_sequence(table_name,seq)values('cp_dept',1);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_dept_manager',1);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_dept',1);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_user',1);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_register',1);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_opt',530);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_menu',570);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_role',100);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_role_opt',11000);
insert into cs_wcm_sequence(table_name,seq)values('cs_org_role_app',100);
insert into cs_wcm_sequence(table_name,seq)values('cs_info_model',50);
insert into cs_wcm_sequence(table_name,seq)values('cs_info_category',10000);
insert into cs_wcm_sequence(table_name,seq)values('cs_info_class',1000);
insert into cs_wcm_sequence(table_name,seq)values('cs_sys_dict_category',100);
insert into cs_wcm_sequence(table_name,seq)values('cs_sys_dict',1000);
insert into cs_org_dept_manager(dept_manager_ID,dept_id,user_id)values(1,1,1);
insert into cs_org_app (app_id, app_name, app_sort, app_ico, app_memo)values ('org', '组织机构管理', 1, '', '');
insert into cs_org_app (app_id, app_name, app_sort, app_ico, app_memo)values ('system', '数据模型管理', 2, '', '');
insert into cs_org_app(app_id,app_name,app_sort)values('sendinfo','站群数据报送',3);
insert into cs_org_app (app_id, app_name, app_sort, app_ico, app_memo)values ('control', '站群管理', 4, '', '');
insert into cs_org_app (app_id, app_name, app_sort, app_ico, app_memo)values ('cms', '站点内容管理', 5, '', '');
insert into cs_org_app (app_id, app_name, app_sort, app_ico, app_memo)values ('zwgk', '信息公开系统', 6, '', '');
insert into cs_org_app (app_id, app_name, app_sort, app_ico, app_memo)values ('statistics', '统计分析', 7, '', '');
insert into cs_org_app (app_id, app_name, app_sort, app_ico, app_memo)values ('appeal', '诉求系统', 8, '', '');
insert into cs_org_app (app_id, app_name, app_sort, app_ico, app_memo)values ('ggfw', '公共服务系统', 9, '', '');
insert into cs_org_dept(dept_id,parent_id,dept_fullname,dept_name,tree_position,dept_sort)values(1,0,'组织机构','组织机构','$1$',1);
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (1, '系统管理员', 0, 'system', '', '');
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (2, '组织机构管理员', 0, 'system', '', '');
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (3, '站群管理员', 0, 'system', '', '');
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (5, '站点管理员', 0, 'control', '', '');
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (6, '公共服务管理员', 0, 'system', '', '');
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (10, '部门管理员', 0, 'org', '', '');
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (11, '信息公开管理员', 0, 'system', '', '');
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (12, '公开节点管理员', 0, 'system', '', '');
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (30, '诉求系统管理员', 0, 'system', '', '');
insert into cs_org_role (role_id, role_name, rele_shared, app_id, site_id, role_memo)values (31, '信件业务管理员', 0, 'appeal', '', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (1, 1, 'system', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (2, 2, 'system', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (3, 2, 'org', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (4, 3, 'system', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (5, 3, 'control', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (6, 5, 'cms', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (7, 5, 'control', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (8, 6, 'ggfw', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (9, 6, 'system', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (10, 10, 'org', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (11, 11, 'zwgk', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (12, 11, 'system', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (13, 12, 'system', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (14, 12, 'zwgk', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (15, 30, 'appeal', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (16, 30, 'system', '');
insert into cs_org_role_app (role_app_id, role_id, app_id, site_id)values (17, 31, 'appeal', '');
insert into cs_org_user(user_id,dept_id,userlevel_value,user_realname,user_status)values('1','1','1','系统管理员',0);
insert into cs_org_register(register_id,user_id,username,password,register_status)values('1','1','system','=#=hlQpsPUdyX+OAB9CkPBjnA==','0');
insert into cs_org_user_role(user_role_id,role_id,user_id,app_id,site_id)values('1','1','1','system','');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (386, 161, '$1$159$161$386$', '信件管理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (400, 396, '$1$3$218$396$400$', '内容样式', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (401, 396, '$1$3$218$396$401$', '模块资源', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (405, 6, '$1$6$405$', '站点统计', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (387, 386, '$1$159$161$386$387$', '待处理件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (391, 6, '$1$6$391$', '应用目录', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (393, 360, '$1$6$360$393$', '自定义查询', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (394, 393, '$1$6$360$393$394$', '查询业务配置', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (407, 405, '$1$6$405$407$', '工作量考核', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (385, 273, '$1$7$273$385$', '生成内容页', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (399, 396, '$1$3$218$396$399$', '外框样式', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (129, 214, '$1$6$214$129$', '角色管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (153, 214, '$1$6$214$153$', '会员管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (171, 164, '$1$159$160$164$171$', '内容审核', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (176, 164, '$1$159$160$164$176$', '信件判重', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (185, 162, '$1$159$160$162$185$', '待审件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (186, 162, '$1$159$160$162$186$', '延期待审', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (209, 214, '$1$6$214$209$', '标签分类', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (366, 364, '$1$7$287$364$366$', '处理状态统计', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (227, 224, '$1$159$224$227$', '按处理单位', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (234, 227, '$1$159$224$227$234$', '警示状态', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (381, 292, '$1$7$279$292$381$', '生成内容页', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (303, 298, '$1$6$298$303$', '审核', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (304, 298, '$1$6$298$304$', '获取', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (310, 298, '$1$6$298$310$', '清空回收站', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (314, 214, '$1$6$214$314$', '附件设置', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (352, 342, '$1$5$342$352$', '工作流', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (357, 342, '$1$5$342$357$', '来源管理', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (358, 342, '$1$5$342$358$', '模板管理', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (1, 0, '$1$', '系统权限', '', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (135, 3, '$1$3$135$', '共享目录管理', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (155, 153, '$1$6$214$153$155$', '会员管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (151, 129, '$1$6$214$129$151$', '用户组管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (152, 129, '$1$6$214$129$152$', '站点用户管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (161, 159, '$1$159$161$', '信件管理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (167, 164, '$1$159$160$164$167$', '转办', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (172, 164, '$1$159$160$164$172$', '申请延期', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (181, 162, '$1$159$160$162$181$', '首接件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (193, 163, '$1$159$160$163$193$', '重复件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (197, 196, '$1$159$161$196$197$', '待审核件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (202, 199, '$1$159$161$199$202$', '黄牌件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (214, 6, '$1$6$214$', '系统管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (232, 227, '$1$159$224$227$232$', '办理情况', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (240, 225, '$1$159$225$240$', '用户注册', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (244, 225, '$1$159$225$244$', '过滤词', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (249, 225, '$1$159$225$249$', '诉求目的', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (252, 225, '$1$159$225$252$', '节假日管理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (257, 220, '$1$6$360$220$257$', '访谈模型管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (367, 291, '$1$7$279$291$367$', '申请单统计', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (282, 292, '$1$7$279$292$282$', '目录管理', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (284, 7, '$1$7$284$', '公开指南', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (290, 279, '$1$7$279$290$', '依申请公开', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (316, 291, '$1$7$279$291$316$', '公开信息统计', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (318, 317, '$1$7$287$317$318$', '信息量统计', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (340, 5, '$1$5$340$', '站群统计', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (341, 5, '$1$5$341$', '系统日志', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (372, 371, '$1$8$371$372$', '系统配置', 'ggfw', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (373, 371, '$1$8$371$373$', '用户管理', 'ggfw', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (374, 371, '$1$8$371$374$', '角色管理', 'ggfw', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (377, 214, '$1$6$214$377$', '场景主题管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (2, 1, '$1$2$', '组织机构管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (162, 160, '$1$159$160$162$', '待处理信件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (130, 129, '$1$6$214$129$130$', '角色管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (149, 214, '$1$6$214$149$', '目录管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (157, 153, '$1$6$214$153$157$', '会员分类管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (191, 163, '$1$159$160$163$191$', '交办件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (208, 163, '$1$159$160$163$208$', '延期审核件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (291, 279, '$1$7$279$291$', '公开统计', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (236, 228, '$1$159$224$228$236$', '按处理部门', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (302, 298, '$1$6$298$302$', '发布', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (323, 195, '$1$159$161$195$323$', '已发布件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (329, 325, '$1$2$325$329$', '已停用帐号', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (337, 3, '$1$3$337$', '元数据管理', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (342, 5, '$1$5$342$', '系统管理', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (350, 214, '$1$6$214$350$', '站点配置', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (365, 364, '$1$7$287$364$365$', '处理方式统计', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (8, 1, '$1$8$', '公共服务系统', 'ggfw', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (6, 1, '$1$6$', '站点内容管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (201, 199, '$1$159$161$199$201$', '预警件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (166, 164, '$1$159$160$164$166$', '回复', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (180, 164, '$1$159$160$164$180$', '信件打印', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (206, 163, '$1$159$160$163$206$', '回复件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (222, 3, '$1$3$222$', '数据字典', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (231, 226, '$1$159$224$226$231$', '警示状态', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (233, 227, '$1$159$224$227$233$', '信件类型', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (241, 225, '$1$159$225$241$', '领导人注册', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (245, 225, '$1$159$225$245$', '满意度指标配置', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (246, 225, '$1$159$225$246$', '常用语', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (250, 225, '$1$159$225$250$', '特征标记', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (251, 225, '$1$159$225$251$', '工作流管理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (258, 220, '$1$6$360$220$258$', '主题管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (263, 221, '$1$6$360$221$263$', '调查管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (276, 273, '$1$7$273$276$', '节点注册', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (292, 279, '$1$7$279$292$', '系统管理', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (299, 298, '$1$6$298$299$', '添加', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (322, 164, '$1$159$160$164$322$', '删除', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (328, 325, '$1$2$325$328$', '已开通帐号', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (333, 108, '$1$2$108$333$', '用户组管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (348, 342, '$1$5$342$348$', '服务器注册', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (369, 8, '$1$8$369$', '内容管理', 'ggfw', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (378, 360, '$1$6$360$378$', '场景式服务', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (382, 371, '$1$8$371$382$', '生成内容页', 'ggfw', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (109, 108, '$1$2$108$109$', '角色管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (160, 159, '$1$159$160$', '信件办理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (187, 163, '$1$159$160$163$187$', '受理件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (170, 164, '$1$159$160$164$170$', '不予受理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (235, 227, '$1$159$224$227$235$', '诉求目的', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (221, 360, '$1$6$360$221$', '问卷调查', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (228, 224, '$1$159$224$228$', '满意度统计', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (259, 220, '$1$6$360$220$259$', '访谈统计', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (287, 7, '$1$7$287$', '公开统计', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (301, 298, '$1$6$298$301$', '删除', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (312, 218, '$1$3$218$312$', '邮件设置', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (321, 214, '$1$6$214$321$', '依申请公开常用语', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (338, 5, '$1$5$338$', '站群注册', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (356, 342, '$1$5$342$356$', '编辑管理', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (127, 218, '$1$3$218$127$', '角色管理', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (178, 164, '$1$159$160$164$178$', '信件编辑', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (190, 163, '$1$159$160$163$190$', '呈办件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (218, 3, '$1$3$218$', '系统管理', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (224, 159, '$1$159$224$', '统计分析', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (226, 224, '$1$159$224$226$', '按诉求目的', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (229, 226, '$1$159$224$226$229$', '办理情况', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (239, 225, '$1$159$225$239$', '部门注册', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (262, 221, '$1$6$360$221$262$', '分类管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (275, 273, '$1$7$273$275$', '节点分类管理', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (283, 7, '$1$7$283$', '公开指引', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (285, 7, '$1$7$285$', '公开年报', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (306, 298, '$1$6$298$306$', '移动', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (326, 325, '$1$2$325$326$', '全部帐号', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (347, 341, '$1$5$341$347$', '登录日志', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (351, 342, '$1$5$342$351$', '角色管理', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (354, 342, '$1$5$342$354$', '热词管理', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (360, 6, '$1$6$360$', '应用组件', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (363, 317, '$1$7$287$317$363$', '工作量排行', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (3, 1, '$1$3$', '数据模型管理', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (110, 2, '$1$2$110$', '用户管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (371, 8, '$1$8$371$', '系统管理', 'ggfw', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (169, 164, '$1$159$160$164$169$', '呈办', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (175, 164, '$1$159$160$164$175$', '发布', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (248, 225, '$1$159$225$248$', '内容分类', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (288, 279, '$1$7$279$288$', '公开指南', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (305, 298, '$1$6$298$305$', '推送', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (308, 298, '$1$6$298$308$', '还原', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (311, 218, '$1$3$218$311$', '索引规则管理', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (320, 214, '$1$6$214$320$', '依申请公开业务', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (343, 340, '$1$5$340$343$', '信息量统计', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (332, 298, '$1$6$298$332$', '高级删除', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (376, 342, '$1$5$342$376$', '搜索管理', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (315, 298, '$1$6$298$315$', '高级修改', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (108, 2, '$1$2$108$', '系统管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (379, 218, '$1$3$218$379$', '系统配置', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (154, 153, '$1$6$214$153$154$', '注册设置', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (158, 6, '$1$6$158$', '素材管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (163, 160, '$1$159$160$163$', '已处理信件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (173, 164, '$1$159$160$164$173$', '延期审核', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (198, 196, '$1$159$161$196$198$', '已审核件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (207, 163, '$1$159$160$163$207$', '内容审核件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (237, 228, '$1$159$224$228$237$', '按诉求目的', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (242, 225, '$1$159$225$242$', '角色管理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (247, 225, '$1$159$225$247$', '地区分类', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (274, 273, '$1$7$273$274$', '公开系统配置', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (277, 273, '$1$7$273$277$', '用户注册', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (294, 3, '$1$3$294$', '工作流管理', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (295, 161, '$1$159$161$295$', '评论管理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (325, 2, '$1$2$325$', '帐号管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (334, 108, '$1$2$108$334$', '用户级别管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (346, 341, '$1$5$341$346$', '审计日志', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (349, 342, '$1$5$342$349$', '站群配置', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (317, 287, '$1$7$287$317$', '公开信息统计', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (211, 164, '$1$159$160$164$211$', '信件收回', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (210, 214, '$1$6$214$210$', '标签管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (213, 214, '$1$6$214$213$', '模板管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (215, 6, '$1$6$215$', '内容维护', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (216, 6, '$1$6$216$', '标签维护', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (261, 259, '$1$6$360$220$259$261$', '访谈热度排行', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (266, 264, '$1$6$360$221$264$266$', '问卷热度排行', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (286, 360, '$1$6$360$286$', '依申请公开', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (289, 279, '$1$7$279$289$', '公开年报', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (319, 317, '$1$7$287$317$319$', '信息量排行', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (336, 3, '$1$3$336$', '内容模型管理', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (368, 342, '$1$5$342$368$', '共享资源管理', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (370, 8, '$1$8$370$', '服务目录管理', 'ggfw', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (5, 1, '$1$5$', '站群管理', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (159, 1, '$1$159$', '公众诉求系统', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (383, 214, '$1$7$279$292$383$', '索引码重置', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (344, 340, '$1$5$340$344$', '工作量统计', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (431, 340, '$1$5$340$431$', '访问量统计', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (192, 163, '$1$159$160$163$192$', '无效件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (195, 161, '$1$159$161$195$', '发布管理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (196, 161, '$1$159$161$196$', '延期审核', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (199, 161, '$1$159$161$199$', '督查督办', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (212, 6, '$1$6$212$', '页面管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (7, 1, '$1$7$', '信息公开系统', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (380, 214, '$1$6$214$380$', '生成内容页', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (188, 163, '$1$159$160$163$188$', '办结件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (156, 153, '$1$6$214$153$156$', '会员审核', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (225, 159, '$1$159$225$', '系统管理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (230, 226, '$1$159$224$226$230$', '信件类型', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (293, 292, '$1$7$279$292$293$', '节点配置', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (298, 6, '$1$6$298$', '内容操作', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (330, 108, '$1$2$108$330$', '权限管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (335, 108, '$1$2$108$335$', '部门级别管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (339, 5, '$1$5$339$', '站点注册', 'control', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (364, 287, '$1$7$287$364$', '依申请公开统计', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (384, 218, '$1$7$273$384$', '索引码重置', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (164, 160, '$1$159$160$164$', '信件操作', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (165, 164, '$1$159$160$164$165$', '受理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (300, 298, '$1$6$298$300$', '修改', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (194, 163, '$1$159$160$163$194$', '不予受理件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (220, 360, '$1$6$360$220$', '访谈系统', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (260, 259, '$1$6$360$220$259$260$', '访谈模型统计', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (265, 264, '$1$6$360$221$264$265$', '问卷类型统计', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (278, 273, '$1$7$273$278$', '角色管理', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (280, 279, '$1$7$279$280$', '内容维护', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (307, 298, '$1$6$298$307$', '撤消', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (309, 298, '$1$6$298$309$', '彻底删除', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (324, 195, '$1$159$161$195$324$', '未发布件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (177, 164, '$1$159$160$164$177$', '置为无效', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (327, 325, '$1$2$325$327$', '待开通帐号', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (168, 164, '$1$159$160$164$168$', '交办', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (174, 164, '$1$159$160$164$174$', '督办', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (182, 162, '$1$159$160$162$182$', '转办件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (183, 162, '$1$159$160$162$183$', '退回件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (184, 162, '$1$159$160$162$184$', '警示件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (189, 163, '$1$159$160$163$189$', '转办件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (200, 199, '$1$159$161$199$200$', '已督办', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (203, 199, '$1$159$161$199$203$', '红牌件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (219, 218, '$1$3$218$219$', '过滤词', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (238, 159, '$1$159$238$', '业务管理', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (264, 221, '$1$6$360$221$264$', '问卷统计', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (398, 396, '$1$3$218$396$398$', '页面布局', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (273, 7, '$1$7$273$', '系统管理', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (279, 7, '$1$7$279$', '节点内容管理', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (281, 292, '$1$7$279$292$281$', '用户管理', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (331, 108, '$1$2$108$331$', '菜单管理', 'org', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (388, 386, '$1$159$161$386$388$', '处理中件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (392, 225, '$1$159$225$392$', '系统配置', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (404, 298, '$1$6$298$404$', '高级获取', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (408, 164, '$1$159$160$164$408$', '生成word', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (390, 386, '$1$159$161$386$390$', '已办结件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (397, 396, '$1$3$218$396$397$', '主题风格', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (402, 396, '$1$3$218$396$402$', '方案套餐', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (396, 218, '$1$3$218$396$', '页面制作资源', 'system', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (406, 405, '$1$6$405$406$', '信息统计', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (389, 386, '$1$159$161$386$389$', '待审核件', 'appeal', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (409, 360, '$1$6$360$409$', '公务员名录', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (410, 214, '$1$6$214$410$', '页面纠错管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (411, 273, '$1$7$273$411$', '公开目录导出', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (413, 292, '$1$7$279$292$413$', '公开目录导出', 'zwgk', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (414, 360, '$1$6$360$414$', '留言板', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (415, 414, '$1$6$360$414$415$', '留言分类管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (416, 414, '$1$6$360$414$416$', '留言主题管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (417, 414, '$1$6$360$414$417$', '留言分类统计', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id)values (418,360,'$1$6$360$418$','评论管理','cms');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id)values (419,418,'$1$6$360$418$419$','评论配置','cms');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id)values (445,418,'$1$6$360$418$445$','评论审核','cms');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id)values (446,418,'$1$6$360$418$446$','敏感评论列表','cms');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id)values (447,149,'$1$6$214$149$447$','批量修改','cms');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id)values (448,282,'$1$7$279$292$282$448$','批量修改','cms');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id)values (449,371,'$1$8$371$449$','批量修改目录','ggfw');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo) values ('505', '502', '$1$6$360$502$505$', '数据采集记录', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo) values ('504', '502', '$1$6$360$502$504$', '采集进度', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo) values ('503', '502', '$1$6$360$502$503$', '规则管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo) values ('502', '360', '$1$6$360$502$', '数据采集', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo) values ('506', '502', '$1$6$360$502$506$', '分类管理', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo) values ('501', '405', '$1$6$405$501$', '信息更新统计', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo) values ('507', '1', '$1$507$', '特殊权限', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo) values ('508', '507', '$1$507$508$', '只看自己添加信息', 'cms', '', '', '', '');
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo) values ('509', '507', '$1$507$509$', '已发信息修改权限', 'cms', '', '', '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (1, 0, '系统菜单', '', '0', '0', '$1$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (2, 396, '组织机构管理', '', 2, '0', '$1$396$2$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (3, 396, '数据模型管理', '', 3, '0', '$1$396$3$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (4, 397, '信息公开系统', '', 7, '0', '$1$397$4$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (5, 396, '站群管理', '', 5, '0', '$1$396$5$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (6, 396, '站点内容管理', '', 6, '0', '$1$396$6$', 6, '', 'createSiteMenu()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (8, 4, '节点内容管理', '', 0, '0', '$1$397$4$8$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (106, 5, '站点注册', '/sys/control/site/siteList.jsp', 339, '0', '$1$396$5$106$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (107, 2, '用户管理', '/sys/org/dept/deptList.jsp', 110, '0', '$1$396$2$107$', 1, '', 'getDeptTreeJsonData()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (108, 2, '帐号管理', '', 0, '0', '$1$396$2$108$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (109, 108, '全部帐号', '/sys/org/user/registerList.jsp?rtype=all', 326, '0', '$1$396$2$108$109$', 0, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (110, 108, '待开通帐号', '/sys/org/user/registerList.jsp?rtype=wait', 327, '0', '$1$396$2$108$110$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (111, 108, '已开通帐号', '/sys/org/user/registerList.jsp?rtype=already', 328, '0', '$1$396$2$108$111$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (112, 108, '已停用帐号', '/sys/org/user/registerList.jsp?rtype=stop', 329, '0', '$1$396$2$108$112$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (113, 2, '系统管理', '', 0, '0', '$1$396$2$113$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (114, 113, '菜单管理', '/sys/org/operate/menuList.jsp?menuID=1', 331, '0', '$1$396$2$113$114$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (147, 113, '角色管理', '/sys/org/role/roleList.jsp?app=org', 109, '0', '$1$396$2$113$147$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (148, 113, '权限管理', '/sys/org/operate/operateList.jsp', 330, '0', '$1$396$2$113$148$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (154, 5, '站群注册', '/sys/control/group/groupList.jsp', 338, '0', '$1$396$5$154$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (155, 156, '站群配置', '/sys/control/group/groupInfo.jsp', 349, '0', '$1$396$5$156$155$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (156, 5, '系统管理', '', 342, '0', '$1$396$5$156$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (157, 156, '服务器注册', '/sys/control/server/serverList.jsp', 348, '0', '$1$396$5$156$157$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (158, 5, '系统日志', '', 341, '0', '$1$396$5$158$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (159, 158, '审计日志', '/sys/system/log/csLogList.jsp', 346, '0', '$1$396$5$158$159$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (160, 158, '登录日志', '/sys/system/log/loginLogList.jsp', 347, '0', '$1$396$5$158$160$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (162, 5, '站群统计', '', 340, '0', '$1$396$5$162$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (163, 162, '信息量统计', '/sys/control/count/cmsSiteList.jsp', 343, '0', '$1$396$5$162$163$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (165, 162, '工作量统计', '/sys/control/count/cmsAssessList.jsp', 344, '0', '$1$396$5$162$165$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (474, 162, '访问量统计', '/sys/control/count/AccessCountList.jsp', 431, '0', '$1$396$5$162$474$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (166, 156, '角色管理', '/sys/org/role/roleList.jsp?app=control', 351, '0', '$1$396$5$156$166$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (169, 156, '工作流', '/sys/cms/workflow/workFlowList.jsp?app_id=cms', 352, '0', '$1$396$5$156$169$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (178, 3, '元数据管理', '/sys/system/metadata/metaDataList.jsp', 337, '0', '$1$396$3$178$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (179, 3, '内容模型管理', '/sys/system/formodel/modelList.jsp', 336, '0', '$1$396$3$179$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (180, 113, '用户组管理', '/sys/org/group/groupList.jsp?appId=org&site_id=', 333, '0', '$1$396$2$113$180$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (184, 156, '热词管理', '/sys/system/assist/hotwords/hotWordDataList.jsp', 354, '0', '$1$396$5$156$184$', 7, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (188, 353, '角色管理', '/sys/org/role/roleList.jsp?app=system', 127, '0', '$1$396$3$353$188$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (189, 156, '编辑管理', '/sys/system/assist/author/authorDataList.jsp', 356, '0', '$1$396$5$156$189$', 9, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (190, 156, '来源管理', '/sys/system/assist/source/sourceDataList.jsp', 357, '0', '$1$396$5$156$190$', 10, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (192, 349, '角色管理', '/sys/org/role/roleList.jsp?app=cms', 129, '0', '$1$396$6$349$192$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (198, 113, '用户级别管理', '/sys/org/userLevel/userLevelList.jsp', 334, '0', '$1$396$2$113$198$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (199, 349, '站点配置', '/sys/control/siteConfig/site_config.jsp', 350, '0', '$1$396$6$349$199$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (201, 113, '部门级别管理', '/sys/org/deptLevel/deptLevelList.jsp', 335, '0', '$1$396$2$113$201$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (202, 192, '角色', '/sys/org/role/roleList.jsp?app=cms', 130, '0', '$1$396$6$349$192$202$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (205, 3, '共享目录管理', '/sys/cms/cateClass/cateClassList.jsp', 135, '0', '$1$396$3$205$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (221, 349, '目录管理', '/sys/cms/category/categoryList.jsp?app_id=cms', 149, '0', '$1$396$6$349$221$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (223, 192, '用户组管理', '/sys/org/group/groupList.jsp?appId=cms', 151, '0', '$1$396$6$349$192$223$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (224, 192, '站点用户管理', '/sys/org/siteUser/SiteUserList.jsp?appId=cms', 152, '0', '$1$396$6$349$192$224$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (226, 397, '公众诉求系统', '', 159, '0', '$1$397$226$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (227, 226, '信件办理', '', 0, '0', '$1$397$226$227$', 1, '', 'getAppealSQCount()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (228, 227, '待处理信件', '', 0, '0', '$1$397$226$227$228$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (229, 228, '首接件', '/sys/appeal/sq/sqList.jsp?sq_flag=0&prove_type=1&sq_status=0,1&is_back=0', 181, '0', '$1$397$226$227$228$229$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (230, 228, '转办件', '/sys/appeal/sq/sqList.jsp?sq_flag=0&prove_type=2,3,4,5&sq_status=0,1&is_back=0', 182, '0', '$1$397$226$227$228$230$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (231, 228, '退回件', '/sys/appeal/sq/sqList.jsp?is_back=1&sq_flag=0&sq_status=0,1', 183, '0', '$1$397$226$227$228$231$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (232, 228, '警示件', '/sys/appeal/sq/sqList.jsp?alarm_flag=1,2,3&sq_status=0,1&sq_flag=0', 184, '0', '$1$397$226$227$228$232$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (235, 228, '待审件', '/sys/appeal/sq/sqList.jsp?sq_status=2&page_type=dsj', 185, '0', '$1$397$226$227$228$235$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (236, 227, '已处理信件', '', 0, '0', '$1$397$226$227$236$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (237, 236, '受理件', '/sys/appeal/sq/transactSQList.jsp?pro_type=0', 187, '0', '$1$397$226$227$236$237$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (238, 236, '办结件', '/sys/appeal/sq/sqList.jsp?sq_status=3', 188, '0', '$1$397$226$227$236$238$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (239, 236, '转办件', '/sys/appeal/sq/transactSQList.jsp?pro_type=2', 189, '0', '$1$397$226$227$236$239$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (240, 236, '呈办件', '/sys/appeal/sq/transactSQList.jsp?pro_type=4', 190, '0', '$1$397$226$227$236$240$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (241, 236, '交办件', '/sys/appeal/sq/transactSQList.jsp?pro_type=3', 191, '0', '$1$397$226$227$236$241$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (242, 236, '无效件', '/sys/appeal/sq/transactSQList.jsp?pro_type=6', 192, '0', '$1$397$226$227$236$242$', 7, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (243, 236, '重复件', '/sys/appeal/sq/transactSQList.jsp?pro_type=5', 193, '0', '$1$397$226$227$236$243$', 8, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (244, 236, '不予受理件', '/sys/appeal/sq/transactSQList.jsp?pro_type=7', 194, '0', '$1$397$226$227$236$244$', 9, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (246, 226, '信件管理', '', 0, '0', '$1$397$226$246$', 2, '', 'getAppealSQCount()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (247, 246, '发布管理', '', 195, '0', '$1$397$226$246$247$', 1, '', 'getAppealSQCount()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (248, 246, '延期审核', '', 0, '0', '$1$397$226$246$248$', 3, '', 'getAppealSQCount()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (249, 248, '待审核件', '/sys/appeal/sq/sqList.jsp?sq_flag=0&limit_flag=2&is_back=0&is_manager_page=true', 197, '0', '$1$397$226$246$248$249$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (250, 248, '已审核件', '/sys/appeal/sq/sqList.jsp?sq_flag=0&limit_flag=1&is_back=0&is_manager_page=true', 198, '0', '$1$397$226$246$248$250$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (251, 246, '信件跟踪', '', 0, '0', '$1$397$226$246$251$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (252, 251, '信件特征跟踪', '', 0, '0', '$1$397$226$246$251$252$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (253, 251, '信件状态跟踪', '', 0, '0', '$1$397$226$246$251$253$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (254, 246, '督查督办', '', 0, '0', '$1$397$226$246$254$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (255, 254, '已督办', '/sys/appeal/sq/sqList.jsp?sq_flag=0&supervise_flag=1&is_manager_page=true', 200, '0', '$1$397$226$246$254$255$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (256, 254, '预警件', '/sys/appeal/sq/sqList.jsp?sq_flag=0&alarm_flag=1&is_manager_page=true', 201, '0', '$1$397$226$246$254$256$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (257, 254, '黄牌件', '/sys/appeal/sq/sqList.jsp?sq_flag=0&alarm_flag=2&is_manager_page=true', 202, '0', '$1$397$226$246$254$257$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (258, 254, '红牌件', '/sys/appeal/sq/sqList.jsp?sq_flag=0&alarm_flag=3&is_manager_page=true', 203, '0', '$1$397$226$246$254$258$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (259, 226, '统计分析', '', 0, '0', '$1$397$226$259$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (262, 259, '按处理单位', '', 0, '0', '$1$397$226$259$262$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (264, 259, '满意度统计', '', 0, '0', '$1$397$226$259$264$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (265, 226, '系统管理', '', 0, '0', '$1$397$226$265$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (266, 226, '业务管理', '/sys/appeal/model/modelList.jsp', 238, '0', '$1$397$226$266$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (267, 265, '部门注册', '/sys/appeal/cpDept/cpDeptList.jsp?dept_id=1', 239, '0', '$1$397$226$265$267$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (268, 265, '用户注册', '/sys/appeal/cpUser/cpUserList.jsp?dept_id=1', 240, '0', '$1$397$226$265$268$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (269, 265, '角色管理', '/sys/org/role/roleList.jsp?app=appeal', 242, '0', '$1$397$226$265$269$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (270, 265, '过滤词', '/sys/system/filterWord/filterWordList.jsp?app_id=appeal', 244, '0', '$1$397$226$265$270$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (271, 265, '满意度指标配置', '/sys/appeal/satisfaction/satisfaction.jsp', 245, '0', '$1$397$226$265$271$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (272, 398, '访谈系统', '', 220, '0', '$1$396$6$398$272$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (273, 398, '问卷调查', '', 221, '0', '$1$396$6$398$273$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (279, 272, '访谈模型管理', '/sys/interview/subject/subjectCategory.jsp', 257, '0', '$1$396$6$398$272$279$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (281, 272, '主题管理', '/sys/interview/subject/subjectManager.jsp', 258, '0', '$1$396$6$398$272$281$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (282, 272, '访谈统计', '', 0, '0', '$1$396$6$398$272$282$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (283, 282, '访谈模型统计', '/sys/interview/count/categoryCount.jsp', 260, '0', '$1$396$6$398$272$282$283$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (284, 282, '访谈热度排行', '/sys/interview/count/subjectCount.jsp', 261, '0', '$1$396$6$398$272$282$284$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (286, 265, '常用语', '/sys/appeal/lang/commonLangList.jsp', 246, '0', '$1$397$226$265$286$', 7, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (287, 349, '会员管理', '/sys/member/manager/memberList.jsp', 153, '0', '$1$396$6$349$287$', 12, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (288, 287, '注册设置', '/sys/member/config/member_config.jsp', 154, '0', '$1$396$6$349$287$288$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (289, 287, '会员管理', '/sys/member/manager/memberList.jsp', 155, '0', '$1$396$6$349$287$289$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (290, 287, '会员审核', '/sys/member/check/memberCheckList.jsp', 156, '0', '$1$396$6$349$287$290$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (291, 287, '会员分类管理', '/sys/member/category/memberCategoryList.jsp', 157, '0', '$1$396$6$349$287$291$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (292, 265, '地区分类', '/sys/appeal/area/areaList.jsp', 247, '0', '$1$397$226$265$292$', 8, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (294, 6, '素材管理', '/sys/material/operate/managerList.jsp', 158, '0', '$1$396$6$294$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (295, 273, '分类管理', '/sys/survey/surveyCategoryList.jsp', 262, '0', '$1$396$6$398$273$295$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (296, 273, '调查管理', '/sys/survey/surveyList.jsp', 263, '0', '$1$396$6$398$273$296$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (297, 273, '问卷统计', '', 0, '0', '$1$396$6$398$273$297$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (298, 297, '问卷类型统计', '/sys/survey/count/categoryCount.jsp', 265, '0', '$1$396$6$398$273$297$298$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (299, 297, '问卷热度排行', '/sys/survey/count/surveyCount.jsp', 266, '0', '$1$396$6$398$273$297$299$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (302, 262, '信件类型', '/sys/appeal/count/dept/letterType.jsp', 233, '0', '$1$397$226$259$262$302$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (308, 259, '按诉求目的', '', 0, '0', '$1$397$226$259$308$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (309, 308, '办理情况', '/sys/appeal/count/aim/letterHandle.jsp', 229, '0', '$1$397$226$259$308$309$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (310, 308, '信件类型', '/sys/appeal/count/aim/letterType.jsp', 230, '0', '$1$397$226$259$308$310$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (311, 308, '警示状态', '/sys/appeal/count/aim/letterCaution.jsp', 231, '0', '$1$397$226$259$308$311$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (312, 262, '办理情况', '/sys/appeal/count/dept/letterHandle.jsp', 232, '0', '$1$397$226$259$262$312$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (313, 262, '警示状态', '/sys/appeal/count/dept/letterCaution.jsp', 234, '0', '$1$397$226$259$262$313$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (316, 264, '按处理部门', '/sys/appeal/count/record/letterHandleDept.jsp', 236, '0', '$1$397$226$259$264$316$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (317, 264, '按诉求目的', '/sys/appeal/count/record/letterPur.jsp', 237, '0', '$1$397$226$259$264$317$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (318, 262, '诉求目的', '/sys/appeal/count/dept/letterPur.jsp', 235, '0', '$1$397$226$259$262$318$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (319, 246, '评论管理', '', 0, '0', '$1$397$226$246$319$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (320, 319, '已审核', '/sys/system/comment/commentList.jsp?type=checked', 296, '0', '$1$397$226$246$319$320$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (321, 319, '待审核', '/sys/system/comment/commentList.jsp?type=unchecked', 297, '0', '$1$397$226$246$319$321$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (329, 265, '内容分类', '/sys/appeal/category/categoryList.jsp', 248, '0', '$1$397$226$265$329$', 9, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (330, 265, '诉求目的', '/sys/appeal/purpose/purposeList.jsp', 249, '0', '$1$397$226$265$330$', 10, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (331, 265, '特征标记', '/sys/system/assist/tags/tagsDataList.jsp?app=appeal', 250, '0', '$1$397$226$265$331$', 11, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (332, 228, '延期待审', '/sys/appeal/sq/sqList.jsp?sq_status=0,1&limit_flag=2&is_back=0&page_type=dsj', 186, '0', '$1$397$226$227$228$332$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (333, 265, '工作流管理', '/sys/cms/workflow/workFlowList.jsp?app_id=appeal', 251, '0', '$1$397$226$265$333$', 12, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (334, 265, '领导人注册', '/sys/appeal/cpLead/cpLeadList.jsp', 241, '0', '$1$397$226$265$334$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (335, 265, '节假日管理', '/sys/appeal/calendar/calendarList.jsp', 252, '0', '$1$397$226$265$335$', 13, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (338, 156, '模板管理', '/sys/system/template/templateDataList.jsp', 0, '0', '$1$396$5$156$338$', 11, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (339, 236, '回复件', '/sys/appeal/sq/transactSQList.jsp?pro_type=1', 206, '0', '$1$397$226$227$236$339$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (340, 236, '内容审核件', '/sys/appeal/sq/transactSQList.jsp?pro_type=11,12', 207, '0', '$1$397$226$227$236$340$', 10, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (341, 236, '延期审核件', '/sys/appeal/sq/transactSQList.jsp?pro_type=9,10', 208, '0', '$1$397$226$227$236$341$', 11, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (342, 349, '标签分类', '/sys/system/ware/wareCategoryList.jsp?app_id=cms', 209, '0', '$1$396$6$349$342$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (343, 349, '标签管理', '/sys/system/ware/wareList.jsp?app_id=cms', 210, '0', '$1$396$6$349$343$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (347, 6, '页面管理', '/sys/page/pageList.jsp?app_id=cms', 212, '0', '$1$396$6$347$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (348, 349, '模板管理', '/sys/system/template/templateCategoryList.jsp?tid=0&app_id=cms', 213, '0', '$1$396$6$349$348$', 1, '', 'getTemplateCategoryTreeJsonData()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (349, 6, '系统管理', '', 0, '0', '$1$396$6$349$', 8, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (350, 6, '内容维护', '', 215, '0', '$1$396$6$350$', 1, '', 'getInfoCategoryTreeByUserID()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (351, 6, '标签维护', '', 216, '0', '$1$396$6$351$', 3, '', 'getWareCategoryTreeJsonData()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (353, 3, '系统管理', '', 0, '0', '$1$396$3$353$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (354, 353, '过滤词', '/sys/system/filterWord/filterWordList.jsp', 219, '0', '$1$396$3$353$354$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (355, 3, '数据字典', '/sys/system/dict/dataDictList.jsp', 222, '0', '$1$396$3$355$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (360, 399, '服务目录管理', '/sys/cms/zt/fwList.jsp', 370, '0', '$1$397$399$360$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (361, 399, '内容维护', '', 369, '0', '$1$397$399$361$', 1, '', 'getFWCategoryTreeByUserID()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (362, 4, '系统管理', '', 0, '0', '$1$397$4$362$', 8, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (364, 362, '节点分类管理', '/sys/zwgk/node/gkCategoryList.jsp', 275, '0', '$1$397$4$362$364$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (365, 362, '节点注册', '/sys/zwgk/node/gkNodeList.jsp', 276, '0', '$1$397$4$362$365$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (366, 362, '用户注册', '/sys/zwgk/user/gkUserList.jsp', 277, '0', '$1$397$4$362$366$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (367, 362, '角色管理', '/sys/org/role/roleList.jsp?app=zwgk', 278, '0', '$1$397$4$362$367$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (369, 8, '内容维护', '', 280, '0', '$1$397$4$8$369$', 1, '', 'getInfoCategoryTreeByUserID()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (370, 372, '用户管理', '/sys/org/siteUser/SiteUserList.jsp?appId=zwgk', 281, '0', '$1$397$4$8$372$370$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (371, 372, '目录管理', '/sys/cms/category/categoryList.jsp?app_id=zwgk', 282, '0', '$1$397$4$8$372$371$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (372, 8, '系统管理', '', 0, '0', '$1$397$4$8$372$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (373, 372, '节点配置', '/sys/zwgk/node/gkNode_update.jsp', 293, '0', '$1$397$4$8$372$373$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (374, 8, '公开指南', '/sys/cms/info/article/m_gk_gkzn.jsp?cid=10&app_id=zwgk', 288, '0', '$1$397$4$8$374$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (375, 8, '公开年报', '/sys/cms/info/article/articleDataList.jsp?cat_id=11&app_id=zwgk', 289, '0', '$1$397$4$8$375$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (376, 8, '依申请公开', '/sys/zwgk/ysqgk/operate/ysqgk_list.jsp?app_id=zwgk', 290, '0', '$1$397$4$8$376$', 4, '依申请公开列表', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (377, 8, '公开统计', '', 291, '0', '$1$397$4$8$377$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (378, 4, '公开指引', '/sys/cms/info/article/articleDataList.jsp?cat_id=12&app_id=zwgk', 283, '0', '$1$397$4$378$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (379, 4, '公开统计', '', 287, '0', '$1$397$4$379$', 7, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (380, 4, '公开指南', '/sys/cms/info/article/gkZNList.jsp?cat_id=10&app_id=zwgk', 284, '0', '$1$397$4$380$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (381, 4, '公开年报', '/sys/cms/info/article/gkZNList.jsp?cat_id=11&app_id=zwgk', 285, '0', '$1$397$4$381$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (382, 398, '依申请公开', '/sys/zwgk/ysqgk/operate/ysqgk_allLists.jsp?app_id=zwgk', 286, '0', '$1$396$6$398$382$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (383, 3, '工作流管理', '/sys/cms/workflow/workFlowList.jsp?app_id=system', 294, '0', '$1$396$3$383$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (384, 353, '索引规则管理', '/sys/zwgk/index/indexRoleList.jsp', 311, '0', '$1$396$3$353$384$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (385, 353, '邮件设置', '/sys/system/config/mail/smtpMail.jsp?app_id=system', 312, '0', '$1$396$3$353$385$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (387, 349, '附件设置', '/sys/system/config/attachment/attachment.jsp?app_id=cms', 314, '0', '$1$396$6$349$387$', 11, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (388, 349, '依申请业务管理', '/sys/zwgk/ysqgk/operate/ysqgk_yw.jsp?app_id=zwgk', 320, '0', '$1$396$6$349$388$', 9, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (389, 377, '公开信息统计', '/sys/zwgk/count/gkCountList.jsp', 316, '0', '$1$397$4$8$377$389$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (390, 379, '公开信息量统计', '', 317, '0', '$1$397$4$379$390$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (391, 390, '信息量统计', '/sys/zwgk/count/countListBySite.jsp', 318, '0', '$1$397$4$379$390$391$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (392, 390, '信息量排行', '/sys/zwgk/count/infoRank.jsp', 319, '0', '$1$397$4$379$390$392$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (393, 349, '依申请常用语', '/sys/zwgk/ysqgk/operate/ysqgk_phrasal.jsp?app_id=zwgk', 321, '0', '$1$396$6$349$393$', 8, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (394, 247, '已发布件', '/sys/appeal/sq/sqList.jsp?sq_flag=0,1,2&publish_status=1&is_manager_page=true', 323, '0', '$1$397$226$246$247$394$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (395, 247, '未发布件', '/sys/appeal/sq/sqList.jsp?sq_flag=0,1,2&publish_status=0&is_manager_page=true', 324, '0', '$1$397$226$246$247$395$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (396, 1, '内容管理系统', '', 0, '0', '$1$396$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (397, 1, '政务构件库', '', 0, '0', '$1$397$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (398, 6, '应用组件', '', 0, '0', '$1$396$6$398$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (399, 397, '公共服务系统', '', 0, '0', '$1$397$399$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (402, 1, '我的平台', '/sys/org/desktop/desktopInit.jsp', 0, '0', '$1$402$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (403, 402, '修改密码', '/sys/org/user/updatePassword.jsp', 0, '0', '$1$402$403$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (404, 402, '我的权限', '/sys/org/operate/my_operate.jsp', 0, '0', '$1$402$404$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (405, 390, '工作量排行', '/sys/zwgk/count/workloadStatistics.jsp', 363, '0', '$1$397$4$379$390$405$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (406, 379, '依申请公开统计', '', 364, '0', '$1$397$4$379$406$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (407, 406, '处理方式统计', '/sys/zwgk/count/ysqTypeCnt.jsp', 365, '0', '$1$397$4$379$406$407$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (408, 406, '处理状态统计', '/sys/zwgk/count/ysqStatusCnt.jsp', 366, '0', '$1$397$4$379$406$408$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (409, 377, '申请单统计', '/sys/zwgk/count/ysqStatusByMonth.jsp', 367, '0', '$1$397$4$8$377$409$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (410, 362, '公开系统配置', '/sys/zwgk/node/node_set.jsp', 274, '0', '$1$397$4$362$410$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (411, 156, '共享资源管理', '/sys/system/template/shared_res.jsp', 368, '0', '$1$396$5$156$411$', 12, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (412, 399, '系统管理', '', 0, '0', '$1$397$399$412$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (413, 412, '系统配置', '/sys/ggfw/sys/fw_set.jsp', 372, '0', '$1$397$399$412$413$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (414, 412, '用户管理', '/sys/org/siteUser/SiteUserList.jsp?appId=ggfw&site_id=ggfw', 373, '0', '$1$397$399$412$414$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (415, 412, '角色管理', '/sys/org/role/roleList.jsp?app=ggfw&site_id=ggfw', 374, '0', '$1$397$399$412$415$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (416, 156, '搜索管理', '/sys/system/search/searchList.jsp', 376, '0', '$1$396$5$156$416$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (417, 349, '场景主题管理', '/sys/ggfw/ser/serList.jsp', 377, '0', '$1$396$6$349$417$', 10, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (418, 398, '场景式服务', '', 378, '0', '$1$396$6$398$418$', 3, '', 'getSerCategoryRootJSONTree()');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (419, 353, '系统配置', '/sys/control/sys/sys_config.jsp', 379, '0', '$1$396$3$353$419$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (420, 349, '生成内容页', '/sys/cms/info/article/createHtml.jsp?app_id=cms', 380, '0', '$1$396$6$349$420$', 13, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (421, 372, '生成内容页', '/sys/cms/info/article/createHtml.jsp?app_id=zwgk', 381, '0', '$1$397$4$8$372$421$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (422, 412, '生成内容页', '/sys/cms/info/article/createHtml.jsp?app_id=ggfw', 382, '0', '$1$397$399$412$422$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (423, 349, '索引码重置', '/sys/zwgk/index/reloadIndex.jsp', 383, '0', '$1$397$4$8$372$423$', 7, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (424, 353, '索引码重置', '/sys/zwgk/index/reloadIndex.jsp', 384, '0', '$1$397$4$362$424$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (425, 362, '生成内容页', '/sys/cms/info/article/createHtml2.jsp?app_id=zwgk', 385, '0', '$1$397$4$362$425$', 8, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (426, 246, '信件管理', '', 386, '0', '$1$397$226$246$426$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (427, 426, '待处理件', '/sys/appeal/sq/sqList.jsp?sq_flag=0,1,2&is_manager_page=true&sq_status=0', 387, '0', '$1$397$226$246$426$427$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (428, 426, '处理中件', '/sys/appeal/sq/sqList.jsp?sq_flag=0,1,2&is_manager_page=true&sq_status=1', 388, '0', '$1$397$226$246$426$428$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (429, 426, '待审核件', '/sys/appeal/sq/sqList.jsp?sq_flag=0,1,2&is_manager_page=true&sq_status=2', 389, '0', '$1$397$226$246$426$429$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (430, 426, '已办结件', '/sys/appeal/sq/sqList.jsp?sq_flag=0,1,2&is_manager_page=true&sq_status=3', 390, '0', '$1$397$226$246$426$430$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (431, 6, '应用目录', '/sys/zwgk/catalog/catalogList.jsp', 391, '0', '$1$396$6$431$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (432, 265, '系统配置', '/sys/appeal/sys/sys_config.jsp', 392, '0', '$1$397$226$265$432$', 14, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (433, 398, '自定义查询', '', 393, '0', '$1$396$6$398$433$', 7, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (434, 433, '查询业务配置', '/sys/query/query_conf.jsp', 394, '0', '$1$396$6$398$433$434$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (436, 353, '页面制作资源', '', 0, '0', '$1$396$3$353$436$', 7, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (437, 436, '主题风格', '/sys/system/design/cssList.jsp', 397, '0', '$1$396$3$353$436$437$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (438, 436, '页面布局', '/sys/system/design/layoutList.jsp', 398, '0', '$1$396$3$353$436$438$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (439, 436, '外框样式', '/sys/system/design/frameList.jsp', 399, '0', '$1$396$3$353$436$439$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (440, 436, '内容样式', '/sys/system/design/styleList.jsp', 400, '0', '$1$396$3$353$436$440$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (441, 436, '模块资源', '/sys/system/design/moduleList.jsp', 401, '0', '$1$396$3$353$436$441$', 5, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (442, 436, '方案套餐', '/sys/system/design/caseList.jsp', 402, '0', '$1$396$3$353$436$442$', 6, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (444, 6, '站点统计', '', 405, '0', '$1$396$6$444$', 7, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (445, 444, '信息统计', '/sys/cms/cmsCount/cmsCountList.jsp', 406, '0', '$1$396$6$444$445$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (446, 444, '工作量考核', '/sys/cms/cmsCount/cmsAssessList.jsp', 407, '0', '$1$396$6$444$446$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (447, 398, '公务员名录', '/sys/minlu/minlu_set.jsp', 409, '0', '$1$396$6$398$447$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (448, 349, '页面纠错管理', '/sys/system/error/error_list.jsp', 410, '0', '$1$396$6$398$447$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (449, 402, '桌面设置', '/sys/org/desktop/desktopMain.jsp', 0, '0', '$1$402$449$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (454, 362, '公开目录导出', '/sys/zwgk/export/exportInfo.jsp',411, '0', '$1$397$4$362$454$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (455, 402, '待办事项', '/sys/org/desktop/desktopInit.jsp',0, '0', '$1$402$455$', 4, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (457, 372, '公开目录导出', '/sys/zwgk/export/exportInfo.jsp',413, '0', '$1$397$4$8$372$457$', 999, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (458, 398, '留言板', '',414, '0', '$1$396$6$398$458$', 8, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (459, 458, '留言分类管理', '/sys/appCom/guestbook/gbCategoryList.jsp',415, '0', '$1$396$6$398$458$459$', 1, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (460, 458, '留言主题管理', '/sys/appCom/guestbook/guestBookSubList.jsp',416, '0', '$1$396$6$398$458$460$', 2, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (461, 458, '留言分类统计', '/sys/appCom/guestbook/gbCatCount.jsp',417, '0', '$1$396$6$398$458$461$', 3, '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (462,398,'评论管理','',418,0,'$1$396$6$398$462$',7,'','');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (463,462,'评论配置','/sys/comment/commentSet.jsp?app_id=cms',419,0,'$1$396$6$398$462$463$',1,'','');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (488,462,'评论审核','/sys/comment/commentList.jsp?info_type=info',445,0,'$1$396$6$398$462$488$',2,'','');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (489,462,'敏感评论列表','/sys/comment/commentQuestList.jsp?info_type=info',446,0,'$1$396$6$398$462$489$',3,'','');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (490,221,'批量修改','/sys/cms/category/updateCategory.jsp?app_id=cms',447,0,'$1$396$6$349$221$490$',999,'','');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (491,371,'批量修改','/sys/cms/category/updateCategory.jsp?app_id=zwgk',448,0,'$1$397$4$8$372$371$491$',999,'','');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (492,412,'批量修改目录','/sys/cms/category/updateCategory.jsp?app_id=ggfw',449,0,'$1$397$399$412$492$',999,'','');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, handls, opt_id, menu_level, menu_position, menu_sort, menu_memo) values ('555', '552', '数据采集记录', '/sys/dataCollection/previewCollegeData.jsp', '', '505', '0', '$1$396$6$398$552$555$', '3', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, handls, opt_id, menu_level, menu_position, menu_sort, menu_memo) values ('554', '552', '采集进度', '/sys/dataCollection/collProgress.jsp', '', '504', '0', '$1$396$6$398$552$554$', '2', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, handls, opt_id, menu_level, menu_position, menu_sort, menu_memo) values ('553', '552', '规则管理', '/sys/dataCollection/rule_list.jsp', '', '503', '0', '$1$396$6$398$552$553$', '1', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, handls, opt_id, menu_level, menu_position, menu_sort, menu_memo) values ('552', '398', '数据采集', '/sys/dataCollection/ruleCategoryList.jsp', '', '502', '0', '$1$396$6$398$552$', '999', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, handls, opt_id, menu_level, menu_position, menu_sort, menu_memo) values ('556', '552', '分类管理', '/sys/dataCollection/ruleCategoryList.jsp', '', '506', '0', '$1$396$6$398$552$556$', '0', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, handls, opt_id, menu_level, menu_position, menu_sort, menu_memo) values ('551', '444', '信息更新统计', '/sys/cms/cmsCount/cmsUpdateList.jsp', '', '501', '0', '$1$396$6$444$551$', '999', '');
insert into cs_info_category (id, cat_id, class_id, parent_id, wf_id, is_wf_publish, cat_code, cat_ename, cat_cname, cat_position, cat_level, is_mutilpage, template_index, template_list, is_generate_index, urlrule_index, urlrule_list, urlrule_content, is_allow_submit, is_allow_comment, is_comment_checked, is_show, cat_keywords, cat_description, cat_sort, is_sync, cat_source_id, cat_class_id, is_disabled, cat_type, zt_cat_id, is_archive, cat_memo, app_id, site_id, jump_url, hj_sql, is_show_tree)values (244, 244, 70, 0, 0, 0, '', 'zt', '主题分类', '$0$244$', 1, 0, 0, 0, 0, '', '', '', 0, 0, 0, 1, '', '', 9999, 0, 244, 0, 1, 0, 0, 0, '', 'system', '', '', '', 1);
insert into cs_info_category (id, cat_id, class_id, parent_id, wf_id, is_wf_publish, cat_code, cat_ename, cat_cname, cat_position, cat_level, is_mutilpage, template_index, template_list, is_generate_index, urlrule_index, urlrule_list, urlrule_content, is_allow_submit, is_allow_comment, is_comment_checked, is_show, cat_keywords, cat_description, cat_sort, is_sync, cat_source_id, cat_class_id, is_disabled, cat_type, zt_cat_id, is_archive, cat_memo, app_id, site_id, jump_url, hj_sql, is_show_tree)values (367, 367, 74, 0, 0, 0, '', 'tcfl', '体裁分类', '$0$367$', 1, 0, 0, 0, 0, '', '', '', 0, 0, 0, 1, '', '', 9999, 0, 367, 0, 1, 0, 0, 0, '', 'system', '', '', '', 1);
insert into cs_info_category (id, cat_id, class_id, parent_id, wf_id, is_wf_publish, cat_code, cat_ename, cat_cname, cat_position, cat_level, is_mutilpage, template_index, template_list, is_generate_index, urlrule_index, urlrule_list, urlrule_content, is_allow_submit, is_allow_comment, is_comment_checked, is_show, cat_keywords, cat_description, cat_sort, is_sync, cat_source_id, cat_class_id, is_disabled, cat_type, zt_cat_id, is_archive, cat_memo, app_id, site_id, jump_url, hj_sql, is_show_tree)values (383, 383, 76, 0, 0, 0, '', 'fwdxfl', '服务对象分类', '$0$383$', 1, 0, 0, 0, 0, '', '', '', 0, 0, 0, 1, '', '', 9999, 0, 383, 0, 1, 0, 0, 0, '', 'system', '', '', '', 1);
insert into cs_info_class (class_id, class_ename, class_cname, class_codo, class_define, class_type, app_ids)values (74, 'tcfl', '体裁分类', '', '', 0, 'zwgk');
insert into cs_info_class (class_id, class_ename, class_cname, class_codo, class_define, class_type, app_ids)values (76, 'fwdxfl', '服务对象分类', '', '', 0, 'appeal');
insert into cs_info_class (class_id, class_ename, class_cname, class_codo, class_define, class_type, app_ids)values (70, 'ztfl', '主题分类', '', '', 0, 'zwgk');
insert into cs_site (site_id, parent_id, server_id, dept_id, site_name, site_cdkey, site_createtime, site_pausetime, site_position, site_status, site_sort, site_path, sgroup_id, site_demo)values ('HIWCMcgroup', '0', 1, 1, 'SITE.SERVER', '', '', '', '0$HIWCMcgroup', 0, 0, '', '', '');
insert into cs_site_group (sgroup_id, parent_id, sgroup_name, sgroup_ip, sgroup_prot, dept_id, sgroup_sort, sgroup_memo)values ('CMS', '0', '网站群', '127.0.0.1', '', 0, 0, '');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6887, 1, 280);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6772, 1, 3);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6891, 1, 291);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6906, 1, 318);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6910, 1, 365);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6914, 1, 369);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6929, 1, 184);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6933, 1, 187);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6937, 1, 191);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6952, 1, 172);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6956, 1, 176);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6960, 1, 211);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6975, 1, 296);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6979, 1, 388);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6983, 1, 226);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6998, 1, 241);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7002, 1, 246);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7006, 1, 250);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6775, 1, 127);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6778, 1, 379);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6798, 1, 346);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6801, 1, 348);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6821, 1, 212);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6824, 1, 130);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6844, 1, 301);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6847, 1, 304);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6867, 1, 265);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6870, 1, 394);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6890, 1, 290);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6893, 1, 367);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6913, 1, 8);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6916, 1, 371);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6936, 1, 190);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6939, 1, 193);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6959, 1, 180);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6962, 1, 161);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6982, 1, 224);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6985, 1, 230);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7005, 1, 249);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7008, 1, 252);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6764, 1, 334);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6787, 1, 294);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6810, 1, 357);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6856, 1, 360);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6879, 1, 277);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6902, 1, 285);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6925, 1, 162);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6948, 1, 168);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6971, 1, 201);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6994, 1, 237);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6769, 1, 327);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6792, 1, 339);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6815, 1, 153);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6838, 1, 215);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6861, 1, 260);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6884, 1, 384);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6907, 1, 319);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6930, 1, 185);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6953, 1, 173);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6976, 1, 297);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6999, 1, 242);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6788, 1, 336);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6926, 1, 181);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6949, 1, 169);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6972, 1, 202);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6995, 1, 225);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6759, 1, 108);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6782, 1, 399);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6805, 1, 352);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6851, 1, 308);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6874, 1, 7);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6897, 1, 293);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6920, 1, 377);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6943, 1, 208);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6966, 1, 196);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6989, 1, 233);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6779, 1, 396);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6794, 1, 343);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6800, 1, 342);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6802, 1, 349);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6817, 1, 155);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6823, 1, 129);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6825, 1, 151);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6840, 1, 270);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6846, 1, 303);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6848, 1, 305);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6863, 1, 221);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6869, 1, 393);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6871, 1, 405);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6886, 1, 279);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6892, 1, 316);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6894, 1, 292);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6909, 1, 364);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6915, 1, 370);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6917, 1, 372);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6932, 1, 163);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6938, 1, 192);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6940, 1, 194);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6955, 1, 175);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6961, 1, 322);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6963, 1, 195);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6978, 1, 387);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6984, 1, 229);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6986, 1, 231);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7001, 1, 245);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6771, 1, 329);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6777, 1, 312);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7007, 1, 251);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7009, 1, 392);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6768, 1, 326);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6776, 1, 219);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6791, 1, 338);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6795, 1, 344);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6799, 1, 347);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6814, 1, 6);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6818, 1, 156);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6822, 1, 214);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6837, 1, 380);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6841, 1, 298);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6845, 1, 302);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6860, 1, 259);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6864, 1, 262);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6868, 1, 266);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6883, 1, 321);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7004, 1, 248);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6774, 1, 218);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6797, 1, 341);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6767, 1, 325);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6770, 1, 328);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6786, 1, 222);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6789, 1, 337);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6790, 1, 5);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6793, 1, 340);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6809, 1, 356);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6812, 1, 368);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6813, 1, 376);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6816, 1, 154);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6835, 1, 313);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6836, 1, 314);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6839, 1, 216);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6855, 1, 332);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6858, 1, 257);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6859, 1, 258);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6862, 1, 261);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6878, 1, 276);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6881, 1, 311);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6882, 1, 320);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6885, 1, 385);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6901, 1, 284);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6904, 1, 287);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6905, 1, 317);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6908, 1, 363);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6924, 1, 160);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6927, 1, 182);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6928, 1, 183);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6931, 1, 186);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6947, 1, 167);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6950, 1, 170);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6951, 1, 171);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6954, 1, 174);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6970, 1, 200);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6973, 1, 203);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6974, 1, 295);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6977, 1, 386);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6993, 1, 236);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6996, 1, 239);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6997, 1, 240);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7000, 1, 244);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6763, 1, 333);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6766, 1, 110);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6784, 1, 401);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6807, 1, 354);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6830, 1, 210);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6853, 1, 310);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6876, 1, 274);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6899, 1, 383);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6922, 1, 378);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6945, 1, 165);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6968, 1, 198);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6991, 1, 235);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6761, 1, 330);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6811, 1, 358);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6857, 1, 220);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6880, 1, 278);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6903, 1, 286);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6765, 1, 335);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6758, 1, 2);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6773, 1, 135);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6781, 1, 398);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6796, 1, 345);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6804, 1, 351);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6819, 1, 157);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6827, 1, 149);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6842, 1, 299);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6850, 1, 307);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6865, 1, 263);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6873, 1, 407);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6888, 1, 288);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6896, 1, 282);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6911, 1, 366);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6919, 1, 374);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6934, 1, 188);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6942, 1, 207);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6957, 1, 177);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6965, 1, 324);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6980, 1, 389);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6988, 1, 232);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7003, 1, 247);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6757, 1, 1);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6760, 1, 109);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6762, 1, 331);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6780, 1, 397);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6783, 1, 400);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6785, 1, 402);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6803, 1, 350);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6826, 1, 152);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6829, 1, 209);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6831, 1, 213);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6849, 1, 306);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6852, 1, 309);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6854, 1, 315);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6872, 1, 406);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6875, 1, 273);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6877, 1, 275);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6895, 1, 281);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6898, 1, 381);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6900, 1, 283);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6918, 1, 373);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6921, 1, 382);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6923, 1, 159);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6941, 1, 206);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6944, 1, 164);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6946, 1, 166);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6964, 1, 323);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6967, 1, 197);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6969, 1, 199);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6987, 1, 227);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6990, 1, 234);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6992, 1, 228);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7010, 1, 238);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6820, 1, 158);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6843, 1, 300);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6866, 1, 264);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6889, 1, 289);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6912, 1, 391);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6935, 1, 189);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6958, 1, 178);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6981, 1, 390);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5520, 2, 110);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5521, 2, 325);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5525, 2, 329);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5516, 2, 109);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5518, 2, 334);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5519, 2, 335);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5523, 2, 327);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5524, 2, 328);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5517, 2, 333);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5522, 2, 326);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6655, 3, 358);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6637, 3, 340);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6642, 3, 346);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6673, 3, 210);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6634, 3, 5);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6653, 3, 356);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6678, 3, 313);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6639, 3, 344);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6640, 3, 345);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6645, 3, 348);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6646, 3, 349);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6654, 3, 357);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6641, 3, 341);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6643, 3, 347);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6656, 3, 368);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6635, 3, 338);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6638, 3, 343);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6648, 3, 351);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6651, 3, 354);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6649, 3, 352);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6636, 3, 339);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6644, 3, 342);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6657, 3, 376);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7024, 5, 149);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7041, 5, 301);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7043, 5, 303);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7050, 5, 310);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7060, 5, 261);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7012, 5, 153);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7022, 5, 151);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7062, 5, 262);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7038, 5, 298);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7057, 5, 258);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7019, 5, 214);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7023, 5, 152);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7028, 5, 213);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7032, 5, 313);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7033, 5, 314);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7042, 5, 302);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7047, 5, 307);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7013, 5, 154);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7014, 5, 155);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7051, 5, 315);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7052, 5, 332);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7061, 5, 221);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7066, 5, 266);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7070, 5, 405);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7071, 5, 406);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7034, 5, 380);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7040, 5, 300);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7048, 5, 308);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7053, 5, 404);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7015, 5, 156);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7021, 5, 130);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7059, 5, 260);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7067, 5, 393);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7072, 5, 407);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7027, 5, 210);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7011, 5, 6);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7046, 5, 306);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7049, 5, 309);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7065, 5, 265);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7068, 5, 394);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7020, 5, 129);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7039, 5, 299);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7058, 5, 259);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7026, 5, 209);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7044, 5, 304);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7045, 5, 305);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7063, 5, 263);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7064, 5, 264);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7054, 5, 360);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7016, 5, 157);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7035, 5, 215);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7037, 5, 270);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7056, 5, 257);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7018, 5, 212);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7055, 5, 220);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7017, 5, 158);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7036, 5, 216);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5425, 5, 377);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5427, 5, 378);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5422, 6, 372);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5421, 6, 371);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5423, 6, 373);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5419, 6, 369);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5424, 6, 374);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5420, 6, 370);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5418, 6, 8);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5426, 6, 382);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (73, 10, 110);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (74, 10, 131);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (75, 10, 132);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6095, 5, 320);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6108, 11, 281);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6121, 11, 363);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6082, 11, 308);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6087, 11, 7);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6100, 11, 280);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6113, 11, 283);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6074, 11, 300);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6079, 11, 305);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6085, 11, 315);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6092, 11, 277);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6102, 11, 289);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6105, 11, 316);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6115, 11, 285);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6118, 11, 317);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6072, 11, 298);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6077, 11, 303);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6090, 11, 275);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6103, 11, 290);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6116, 5, 286);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6096, 5, 321);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6109, 11, 282);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6122, 11, 364);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6083, 11, 309);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6075, 11, 301);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6088, 11, 273);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6101, 11, 288);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6114, 11, 284);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6076, 11, 302);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6081, 11, 307);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6089, 11, 274);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6094, 11, 311);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6098, 11, 385);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6107, 11, 292);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6111, 11, 381);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6120, 11, 319);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6124, 11, 366);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6073, 11, 299);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6080, 11, 306);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6086, 11, 332);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6093, 11, 278);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6099, 11, 279);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6106, 11, 367);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6112, 5, 383);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6119, 11, 318);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6125, 5, 391);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6078, 11, 304);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6084, 11, 310);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6091, 11, 276);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6097, 11, 384);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6104, 11, 291);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6110, 11, 293);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6117, 11, 287);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (6123, 11, 365);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5556, 12, 332);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5543, 12, 299);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5549, 12, 305);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5554, 12, 310);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5560, 12, 289);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5566, 12, 281);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5548, 12, 304);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5550, 12, 306);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5555, 12, 315);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5565, 12, 292);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5567, 12, 282);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5545, 12, 301);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5562, 12, 291);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5568, 12, 293);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5570, 12, 383);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5551, 12, 307);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5553, 12, 309);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5542, 12, 298);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5559, 12, 288);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5563, 12, 316);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5546, 12, 302);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5558, 12, 280);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5557, 12, 279);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5544, 12, 300);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5561, 12, 290);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5552, 12, 308);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5569, 12, 381);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5547, 12, 303);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (5564, 12, 367);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7131, 30, 230);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7137, 30, 235);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7146, 30, 244);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7148, 30, 246);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7154, 30, 252);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7084, 30, 189);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7098, 30, 169);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7101, 30, 172);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7081, 30, 163);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7115, 30, 196);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7118, 30, 199);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7132, 30, 231);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7135, 30, 233);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7149, 30, 247);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7152, 30, 250);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7087, 30, 192);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7104, 30, 175);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7121, 30, 202);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7138, 30, 228);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7155, 30, 392);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7089, 30, 194);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7096, 30, 167);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7106, 30, 177);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7113, 30, 323);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7123, 30, 386);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7130, 30, 229);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7140, 30, 237);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7079, 30, 185);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7147, 30, 245);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7083, 30, 188);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7073, 30, 160);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7090, 30, 206);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7100, 30, 171);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7107, 30, 178);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7117, 30, 198);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7124, 30, 387);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7134, 30, 232);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7141, 30, 225);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7151, 30, 249);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7085, 30, 190);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7099, 30, 170);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7102, 30, 173);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7116, 30, 197);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7119, 30, 200);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7133, 30, 227);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7082, 30, 187);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7136, 30, 234);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7150, 30, 248);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7153, 30, 251);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7077, 30, 183);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7092, 30, 208);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7094, 30, 165);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7109, 30, 211);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7111, 30, 408);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7126, 30, 389);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7128, 30, 224);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7143, 30, 240);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7145, 30, 242);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7075, 30, 181);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7088, 30, 193);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7122, 30, 203);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7139, 30, 236);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7156, 30, 238);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7105, 30, 176);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7076, 30, 182);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7091, 30, 207);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7093, 30, 164);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7108, 30, 180);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7110, 30, 322);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7125, 30, 388);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7074, 30, 162);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7127, 30, 390);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7142, 30, 239);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7144, 30, 241);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7078, 30, 184);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7080, 30, 186);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7086, 30, 191);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7095, 30, 166);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7097, 30, 168);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7103, 30, 174);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7112, 30, 195);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7114, 30, 324);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7120, 30, 201);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7129, 30, 226);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7169, 31, 190);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7176, 31, 208);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7186, 31, 173);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7193, 31, 211);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7203, 31, 199);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7210, 31, 297);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7159, 31, 181);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7180, 31, 167);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7187, 31, 174);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7197, 31, 195);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7204, 31, 200);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7163, 31, 185);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7170, 31, 191);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7162, 31, 184);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7165, 31, 163);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7179, 31, 166);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7182, 31, 169);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7196, 31, 161);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7199, 31, 324);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7174, 31, 206);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7189, 31, 176);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7191, 31, 178);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7206, 31, 202);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7208, 31, 295);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7157, 31, 160);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7172, 31, 193);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7168, 31, 189);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7185, 31, 172);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7202, 31, 198);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7171, 31, 192);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7173, 31, 194);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7188, 31, 175);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7190, 31, 177);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7205, 31, 201);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7207, 31, 203);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7158, 31, 162);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7160, 31, 182);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7166, 31, 187);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7175, 31, 207);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7177, 31, 164);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7183, 31, 170);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7192, 31, 180);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7194, 31, 322);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7200, 31, 196);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7209, 31, 296);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7178, 31, 165);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7181, 31, 168);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7195, 31, 408);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7198, 31, 323);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7161, 31, 183);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7164, 31, 186);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7167, 31, 188);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7184, 31, 171);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7201, 31, 197);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id) values ('10152', '5', '505');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id) values ('10289', '1', '505');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id) values ('10151', '5', '504');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id) values ('10288', '1', '504');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id) values ('10150', '5', '503');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id) values ('10287', '1', '503');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id) values ('10149', '5', '502');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id) values ('10286', '1', '502');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id) values ('10158', '5', '501');
insert into cs_info_model (model_id, model_ename, model_name, table_name, model_sort, template_list, template_show, app_id, disabled, model_memo, model_icon, add_page, view_page)values (20, 'gkfbszn', '服务', 'cs_gk_f_bszn', 11, '', '163', 'cms', 0, '', 'ico_fuwu', 'm_gk_bszn.jsp', '');
insert into cs_info_model (model_id, model_ename, model_name, table_name, model_sort, template_list, template_show, app_id, disabled, model_memo, model_icon, add_page, view_page)values (14, 'gkftygs', '通用格式', 'cs_gk_f_tygs', 5, '', '153', 'cms', 1, '', 'ico_gov', 'm_gk_tygs.jsp', '');
insert into cs_info_model (model_id, model_ename, model_name, table_name, model_sort, template_list, template_show, app_id, disabled, model_memo, model_icon, add_page, view_page)values (16, 'gkfldcy', '领导', 'cs_gk_f_ldcy', 7, '', '159', 'cms', 0, '政务公开领导成员', 'ico_lead', 'm_gk_ldcy.jsp', '');
insert into cs_info_model (model_id, model_ename, model_name, table_name, model_sort, template_list, template_show, app_id, disabled, model_memo, model_icon, add_page, view_page)values (10, 'pic', '组图', 'cs_info_pic', 2, '', '166', 'cms', 0, 'sdg', 'ico_pic', 'm_pic.jsp', '');
insert into cs_info_model (model_id, model_ename, model_name, table_name, model_sort, template_list, template_show, app_id, disabled, model_memo, model_icon, add_page, view_page)values (12, 'link', '链接', 'infoLink', 3, '', '', 'cms', 0, '', 'ico_link', 'm_link.jsp', '');
insert into cs_info_model (model_id, model_ename, model_name, table_name, model_sort, template_list, template_show, app_id, disabled, model_memo, model_icon, add_page, view_page)values (13, 'video', '视频', 'cs_info_video', 4, '', '167', 'cms', 0, '', 'ico_video', 'm_video.jsp', '');
insert into cs_info_model (model_id, model_ename, model_name, table_name, model_sort, template_list, template_show, app_id, disabled, model_memo, model_icon, add_page, view_page)values (11, 'article', '文章', 'cs_info_article', 1, '', '148', 'cms', 0, '', 'ico_article', 'm_article.jsp', '');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (397, 'gbt_2260', 'FJ', '福建省', 13, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (264, 'gk_format', 'doc', 'doc', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (399, 'gbt_2260', 'SD', '山东省', 15, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (408, 'gbt_2260', 'GZ', '贵州省', 24, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (260, 'gk_yuzhong', '汉语', '汉语', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (512, 'ser_rsource', '1', '办理之前', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (514, 'ser_rsource', '3', '办理之后', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (507, 'gk_zflb', '公共服务类', '公共服务类', 7, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (343, 'gbt_4658', '50', '高中', 6, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (267, 'gk_gkfs', '1', '不公开', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (393, 'gbt_2260', 'SH', '上海市', 9, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (229, 'gk_carrier', '胶卷', '胶卷', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (239, 'gk_gkxs', '电子信息屏', '电子信息屏', 5, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (244, 'gk_gkxs', '报纸', '报纸', 10, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (402, 'gbt_2260', 'HN', '湖南省', 18, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (396, 'gbt_2260', 'AH', '安徽省', 12, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (418, 'gbt_2260', 'MO', '澳门特别行政区', 34, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (503, 'gk_zflb', '行政征收', '行政征收', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (499, 'gk_fwlb', '公共服务项目', '公共服务项目', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (228, 'gk_carrier', '纸质', '纸质', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (263, 'gk_format', 'html', 'html', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (334, 'gbt_2261_2', '10', '未婚', 1, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (341, 'gbt_4658', '30', '中等专业学校', 4, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (345, 'gbt_4658', '70', '小学', 8, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (412, 'gbt_2260', 'GS', '甘肃省', 28, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (415, 'gbt_2260', 'XJ', '新疆维吾尔自治区', 31, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (387, 'gbt_2260', 'HE', '河北省', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (390, 'gbt_2260', 'LN', '辽宁省', 6, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (224, 'gk_gkfanwei', '面向社会', '面向社会', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (218, 'gk_gkshixian', '及时公开', '及时公开', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (326, 'gbt_16987', '10', '企业', 1, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (234, 'gk_validity', '废止', '废止', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (333, 'gbt_16987', '90', '其他', 8, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (268, 'gk_gkfs', '2', '依申请公开', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (337, 'gbt_2261_2', '40', '离婚', 4, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (339, 'gbt_4658', '10', '大学本科', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (410, 'gbt_2260', 'XZ', '西藏自治区', 26, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (385, 'gbt_2260', 'BJ', '北京市', 1, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (500, 'gk_fwlb', '行政许可项目', '行政许可项目', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (504, 'gk_zflb', '行政裁决', '行政裁决', 4, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (400, 'gbt_2260', 'HA', '河南省', 16, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (505, 'gk_zflb', '行政确认', '行政确认', 5, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (506, 'gk_zflb', '行政给付', '行政给付', 6, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (508, 'gk_zflb', '其他行政行为', '其他行政行为', 8, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (230, 'gk_carrier', '磁带', '磁带', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (266, 'gk_gkfs', '0', '主动公开', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (327, 'gbt_16987', '20', '事业单位', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (340, 'gbt_4658', '20', '大学专科和专科学校', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (328, 'gbt_16987', '30', '机关', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (389, 'gbt_2260', 'NM', '内蒙古自治区', 5, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (414, 'gbt_2260', 'NX', '宁夏回族自治区', 30, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (403, 'gbt_2260', 'GD', '广东省', 19, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (502, 'gk_zflb', '行政强制', '行政强制', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (232, 'gk_carrier', '光盘', '光盘', 5, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (225, 'gk_gkfanwei', '面向所管辖单位', '面向所管辖单位', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (226, 'gk_gkfanwei', '面向企业', '面向企业', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (405, 'gbt_2260', 'HI', '海南省', 21, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (331, 'gbt_16987', '60', '城镇个体工商户', 6, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (220, 'gk_gkshixian', '公开一年', '公开一年', 4, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (407, 'gbt_2260', 'SC', '四川省', 23, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (262, 'gk_yuzhong', '藏语', '藏语', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (227, 'gk_gkfanwei', '面向申请人', '面向申请人', 4, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (217, 'gk_gkshixian', '长期公开', '长期公开', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (231, 'gk_carrier', '磁盘', '磁盘', 4, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (338, 'gbt_4658', '00', '研究生', 1, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (409, 'gbt_2260', 'YN', '云南省', 25, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (411, 'gbt_2260', 'SN', '陕西省', 27, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (386, 'gbt_2260', 'TJ', '天津市', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (392, 'gbt_2260', 'HL', '黑龙江省', 8, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (417, 'gbt_2260', 'HK', '香港特别行政区', 33, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (513, 'ser_rsource', '2', '办理中', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (236, 'gk_gkxs', '政府信息公开大厅', '政府信息公开大厅', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (219, 'gk_gkshixian', '限时公开', '限时公开', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (241, 'gk_gkxs', '新闻发布会', '新闻发布会', 7, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (238, 'gk_gkxs', '信息公告栏', '信息公告栏', 4, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (243, 'gk_gkxs', '图书馆', '图书馆', 9, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (246, 'gk_gkxs', '电视', '电视', 12, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (265, 'gk_format', 'pdf', 'pdf', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (406, 'gbt_2260', 'CQ', '重庆市', 22, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (395, 'gbt_2260', 'ZJ', '浙江省', 11, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (401, 'gbt_2260', 'HB', '湖北省', 17, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (261, 'gk_yuzhong', '维语', '维语', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (346, 'gbt_4658', '80', '文盲或半文盲', 9, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (332, 'gbt_16987', '70', '再就业服务中心', 7, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (394, 'gbt_2260', 'JS', '江苏省', 10, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (335, 'gbt_2261_2', '20', '已婚', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (233, 'gk_validity', '有效', '有效', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (391, 'gbt_2260', 'JL', '吉林省', 7, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (416, 'gbt_2260', 'TW', '台湾省', 32, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (235, 'gk_gkxs', '政府网站', '政府网站', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (398, 'gbt_2260', 'JX', '江西省', 14, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (240, 'gk_gkxs', '便民手册', '便民手册', 6, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (245, 'gk_gkxs', '广播', '广播', 11, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (501, 'gk_zflb', '法律责任', '法律责任', 1, 1, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (237, 'gk_gkxs', '政府公报', '政府公报', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (330, 'gbt_16987', '50', '民办非企业单位', 5, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (242, 'gk_gkxs', '档案馆文件查阅中心', '档案馆文件查阅中心', 8, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (336, 'gbt_2261_2', '30', '丧偶', 3, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (344, 'gbt_4658', '60', '初中', 7, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (329, 'gbt_16987', '40', '社团', 4, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (413, 'gbt_2260', 'QH', '青海省', 29, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (342, 'gbt_4658', '40', '技工学校', 5, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (388, 'gbt_2260', 'SX', '山西省', 4, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (404, 'gbt_2260', 'GX', '广西壮族自治区', 20, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (526, 'pre_title', '0', '推荐', 1, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (527, 'pre_title', '520', '最新', 2, 0, '0', '0');
insert into cs_sys_dict (id, dict_cat_id, dict_id, dict_name, dict_sort, is_defult, app_id, site_id)values (528, 'file_head', 'zbf.gif', '政办发', 1, 0, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (86, 'file_head', '文件题头', ' ', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (53, 'gk_yuzhong', '语种', ' ', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (56, 'gk_gkxs', '公开形式', ' ', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (58, 'gk_gkfanwei', '公开范围', '', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (59, 'gk_carrier', '载体类型', '', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (72, 'gbt_16987', '单位类型(性质)代码', '基本采用GB/T 16987《全国组织机构代码信息数据库（基本库）机读格式规范》的单位类型代码（01-08）。', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (77, 'gk_zflb', '执法类别', '执法类别', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (74, 'gbt_4658', '文化程度代码', '采用GB/T 4658-1984《文化程度代码》的2 位数字码。', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (57, 'gk_gkshixian', '公开时限', '', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (82, 'ser_rsource', '场景服务资源', '场景服务步骤资源', 0, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (52, 'gk_gkfs', '公开方式', ' ', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (60, 'gk_validity', '信息有效性', '', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (73, 'gbt_2261_2', '婚姻状况代码', '采用GB/T 2261.2-2003《婚姻状况代码》。', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (78, 'gk_fwlb', '服务类别', '服务类别', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (63, 'gk_format', '信息格式', '', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (68, 'gbt_2260', '行政区划代码', '采用GB/T 2260-1999 《中华人民共和国行政区划代码》', 1, '0', '0');
insert into cs_sys_dict_category (id, dict_cat_id, dict_cat_name, dict_cat_memo, is_sys, app_id, site_id)values (85, 'pre_title', '标题前缀', '', 1, '0', '0');
insert into cs_design_css (id, css_id, css_ename, css_name, thumb_url, weight, app_id, site_id)values (10, 10, 'blue', '蓝色', '/cms.files/design/thumb/201203070953011.gif', 60, 'system', '');
insert into cs_design_css (id, css_id, css_ename, css_name, thumb_url, weight, app_id, site_id)values (9, 9, 'orange', '橙色', '/cms.files/design/thumb/201203070953001.gif', 60, 'system', '');
insert into cs_design_css (id, css_id, css_ename, css_name, thumb_url, weight, app_id, site_id)values (8, 8, 'gray', '灰色', '/cms.files/design/thumb/201203070951040.gif', 60, 'system', '');
insert into cs_design_css (id, css_id, css_ename, css_name, thumb_url, weight, app_id, site_id)values (7, 7, 'red', '红色', '/cms.files/design/thumb/201203070950057.gif', 90, 'system', '');
insert into cs_design_css (id, css_id, css_ename, css_name, thumb_url, weight, app_id, site_id)values (6, 6, 'green', '绿色', '/cms.files/design/thumb/201203070951027.gif', 60, 'system', '');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(1,1,'一列','<table width="100%" border="0" cellpadding="0" cellspacing=""  class="layout_table">
<tr>
<td valign="top" class="column layout_td">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203050955019.gif','70','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(3,3,'两列1','<table width="100%" border="0" cellpadding="0" cellspacing="" class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space" width="50%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space" width="50%">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203050948042.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(4,4,'两列2','<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space" width="25%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space" width="75%">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203051002018.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(5,5,'两列3','<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space" width="75%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space" width="25%">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203050950027.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(6,6,'两列4','<table width="100%" border="0" cellpadding="0" cellspacing="0" class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space" width="25%" rowspan="2"><div class="portlet"> </div></td>
<td valign="top" class="column layout_td right_td_space top_td_space" width="75%"><div class="portlet"> </div></td>
</tr>
<tr>
<td valign="top" class="column layout_td right_td_space bottom_td_space" width="75%"><div class="portlet"> </div></td>
</tr>
</table>','/cms.files/design/thumb/201203050952006.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(7,7,'两列5','<table width="100%" border="0" cellpadding="0" cellspacing="" class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space top_td_space" width="75%"><div class="portlet"> </div></td>
<td valign="top" class="column layout_td right_td_space" width="25%" rowspan="2"><div class="portlet"> </div></td>
</tr>
<tr>
<td valign="top" class="column layout_td right_td_space bottom_td_space" width="75%"><div class="portlet"> </div></td>
</tr>
</table>','/cms.files/design/thumb/201203050952030.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(8,8,'三列1','<table width="100%" border="0" cellpadding="0" cellspacing="" class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space" width="25%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td left_td_space right_td_space" width="50%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space" width="25%">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203050952051.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(9,9,'三列2','<table width="100%" border="0" cellpadding="0" cellspacing="" class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space" width="20%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td left_td_space right_td_space" width="40%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space" width="40%">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203050953005.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(10,10,'三列3','<table width="100%" border="0" cellpadding="0" cellspacing="" class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space" width="40%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td left_td_space right_td_space" width="40%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space" width="20%">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203050953018.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(11,11,'三列4','<table width="100%" border="0" cellpadding="0" cellspacing="" class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space" rowspan="2" width="20%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td left_td_space right_td_space top_td_space" width="40%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space  bottom_td_space" width="40%">
<div class="portlet">
</div>
</td>
</tr>
<tr>
<td valign="top" class="column layout_td left_td_space right_td_space  top_td_space" width="40%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space  bottom_td_space" width="40%">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203050953031.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(12,12,'三列5','<table width="100%" border="0" cellpadding="0" cellspacing="" class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space top_td_space" width="40%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td left_td_space right_td_space bottom_td_space" width="40%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space" rowspan="2" width="20%">
<div class="portlet">
</div>
</td>
</tr>
<tr>
<td valign="top" class="column layout_td left_td_space top_td_space" width="40%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td left_td_space right_td_space bottom_td_space" width="40%">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203050953044.gif','60','system','');
insert into cs_design_layout(id,layout_id,layout_name,layout_content,thumb_url,weight,app_id,site_id)values(13,13,'四列','<table width="100%" border="0" cellpadding="0" cellspacing="" class="layout_table">
<tr>
<td valign="top" class="column layout_td left_td_space" width="25%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td left_td_space right_td_space" width="25%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td left_td_space right_td_space" width="25%">
<div class="portlet">
</div>
</td>
<td valign="top" class="column layout_td right_td_space" width="25%">
<div class="portlet">
</div>
</td>
</tr>
</table>','/cms.files/design/thumb/201203050954005.gif','60','system','');
insert into cs_design_style(id,style_id,style_name,class_name,thumb_url,weight,app_id,site_id)values(2,2,'红色方块','li_icon_red_diamonds','/cms.files/design/thumb/201202240928053.gif','60','system','');
insert into cs_design_style(id,style_id,style_name,class_name,thumb_url,weight,app_id,site_id)values(3,3,'黑色方块','li_icon_black_diamonds','/cms.files/design/thumb/201202240921051.gif','60','system','');
insert into cs_design_style(id,style_id,style_name,class_name,thumb_url,weight,app_id,site_id)values(4,4,'红色虚三角','li_icon_red_triangle','/cms.files/design/thumb/201202240941059.gif','60','system','');
insert into cs_design_style(id,style_id,style_name,class_name,thumb_url,weight,app_id,site_id)values(5,5,'红色实三角','li_icon_red_triangle_soild','/cms.files/design/thumb/201202240930003.gif','60','system','');
insert into cs_design_style(id,style_id,style_name,class_name,thumb_url,weight,app_id,site_id)values(6,6,'无标识','li_icon_none','/cms.files/design/thumb/201202240943011.gif','60','system','');
insert into cs_design_style(id,style_id,style_name,class_name,thumb_url,weight,app_id,site_id)values(7,7,'文本内容一','div_content1','/cms.files/design/thumb/201202241054000.gif','60','system','');
insert into cs_design_frame(id,frame_id,frame_name,frame_content,thumb_url,weight,app_id,site_id)values(2,2,'框架一','<div class="module_newsList_box1">
<div class="module_head">
<div class="module_head_right">
<span class="more"><a href="#">更多>></a></span>
<h2><a href="#" target="_blank">标题文字</a></h2>
</div>
</div>
<div class="module_body con_normal">
</div>
</div>','frame_1.gif','60','system','');
insert into cs_design_frame(id,frame_id,frame_name,frame_content,thumb_url,weight,app_id,site_id)values(3,3,'框架二','<div class="module_newsList_box2">
<div class="module_head">
<div class="module_head_right">
<span class="more"><a href="#">更多>></a></span>
<h2><a href="#" target="_blank">标题文字</a></h2>
</div>
</div>
<div class="module_body">
</div>
</div>','frame_2.gif','60','system','');
insert into cs_design_frame(id,frame_id,frame_name,frame_content,thumb_url,weight,app_id,site_id)values(4,4,'框架三','<div class="module_newsList_box3">
<div class="module_head">
<div class="module_head_right">
<span class="more"><a href="#">更多>></a></span>
<h2><a href="#" target="_blank">标题文字</a></h2>
</div>
</div>
<div class="module_body con_normal">
</div>
</div>','frame_3.gif','60','system','');
insert into cs_design_frame(id,frame_id,frame_name,frame_content,thumb_url,weight,app_id,site_id)values(5,5,'框架四','<div class="module_newsList_box4">
<div class="module_head">
<div class="module_head_right">
<span class="more"><a href="#">更多>></a></span>
<h2><a href="#" target="_blank">标题文字</a></h2>
</div>
</div>
<div class="module_body">
</div>
<div class="module_foot">
<div class="module_foot_right"></div>
</div>
</div>','frame_4.gif','60','system','');
insert into cs_design_frame(id,frame_id,frame_name,frame_content,thumb_url,weight,app_id,site_id)values(6,6,'框架五','<div class="module_newsList_box5">
<div class="module_head">
<div class="module_head_right">
<span class="more"><a href="#">更多>></a></span>
<h2><a href="#" target="_blank">标题文字</a></h2>
</div>
</div>
<div class="module_body con_normal">
</div>
</div>','frame_5.gif','60','system','');
insert into cs_design_frame(id,frame_id,frame_name,frame_content,thumb_url,weight,app_id,site_id)values(7,7,'空框架','<div class="">
<div class="module_body">
</div>
</div>','frame_0.gif','60','system','');
insert into cs_design_frame(id,frame_id,frame_name,frame_content,thumb_url,weight,app_id,site_id)values(8,8,'导航框架','<!-- 自由文本：主导航: 开始 -->
<div class="module_nav_box" >
<div class="module_body" >
</div>
</div>
<!-- 自由文本：主导航: 结束 -->','frame_6.gif','60','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(3,3,1,'自由展示','<div>
</div>','','7','ShowMore,ModuleTitle,ModuleTitleUrl','/cms.files/design/thumb/201203060821019.png','99','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(2,2,0,'信息列表','<div class="contentBox6">
<ul class="commonList">
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
<li><span>[12-12]</span><a href="#" target="_blank">以用户需求和业务主题为出发点</a></li>
</ul>
</div>','<!-- ------------------信息列表　开始---------------- -->
<ul class=commonList>
#foreach( $r in $InfoUtilData.getInfoList("site_id=$site_id;cat_id=$cat_id;cur_page=$cur_page;size=$size;orderby=ci.released_dtime desc"));
<li><span>${FormatUtil.formatDate($r.released_dtime,"$time_format")}</span><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,$title_count,"...")}</a></li>
#end
</ul>
<!-- ------------------信息列表　结束---------------- -->
','2,3,4,5,6','ListCount,TitleCount,ShowMore,ShowTime,TimeFormat,ModuleTitle,ModuleTitleUrl','/cms.files/design/thumb/201203060821008.png','89','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(4,4,0,'多行图片','<div class="contentBox6">
<table border="0" width="100%" cellpadding="0" cellspacing="0">
<tr>
<td align="center" valign="top" style="padding-bottom:6px;"><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="32" height="32" alt="" /><ul><li><a style="line-height:26px;">以用户需求和业务主题为出发点</a></li><ul></td>
</tr>
　　</table>
</div>','<table border="0" width="100%" cellpadding="0" cellspacing="0">
<tr>
<!-- ------------------信息列表　开始
#set($count = $size);
#foreach( $r in $InfoUtilData.getInfoList("site_id=$site_id;cat_id=$cat_id;is_show_thumb=true;cur_page=$cur_page;size=$size;orderby=ci.released_dtime desc"));
---------------- -->
<td align="center" valign="top" style="padding-bottom:6px;">
<img src="$r.thumb_url" width="$img_width" height="$img_height"/>
<ul><li><a href="$r.content_url" target="_blank" title="${r.title}" style="line-height:26px;">${FormatUtil.cutString($r.title,$title_count,"...")}</a></li></ul>
</td>
<!-- #if($velocityCount % $row_count == 0) -->
</tr>
<tr>
<!-- #end
#set($list_count = $velocityCount);
#end -->
<!-- ------------------信息列表　结束---------------- -->
<!-- #if($list_count < $count);
#set($count = $count + -1);
#foreach ( $foo in [$list_count..$count] ) -->
<td>&nbsp;</td>
<!-- #if(($list_count+$velocityCount) % $row_count == 0) -->
</tr>
<tr>
<!-- #end
#end
#end -->
</tr>
</table>','','ListCount,TitleCount,ShowMore,ModuleTitle,ModuleTitleUrl,RowCount,ImgWidth,ImgHeight,ShowTitle','/cms.files/design/thumb/201203081053011.png','79','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(5,5,0,'图文简介','<div class="contentBox6">
<ul>
<li style="clear:both">
<div style="float:left;margin:3px 9px 3px 0px;">
<img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg">
</div>
<div class="contentBox6">
<p class="p_title"><a>英国降蓝色“果冻”雨 内里柔软无味似弹珠</a></p>
<p class="intro_li">据英国《卫报》30日报道，英国多塞特郡伯恩茅斯(Bournemouth)61岁老人史蒂夫霍恩斯比(Steve Hornsby)在26日遭遇到一件新鲜事，他家后院突然天降奇雨，雨点除了普通的冰雹之外，还夹杂有大量弹珠般的果冻状蓝色小球。当地时间26日下午4点10分左右，史蒂夫向自家车库走去，眼看天色突然变暗，一场暴风雨马上就要来临，他急忙寻处躲避。天空果然下起冰雹，只不过20秒就停住了。史蒂夫说道：“我原以为下的就是普通的雹子，可当我往自家草坪上望去，发现上面到处撒满像弹珠一样的蓝色小球，一踩就消失不见，捡也捡不起来。我就用勺子把它们小心地弹进一个果酱罐里。”这些珠子闻上去没有味道，在水里也浮不起来，表面有一层壳，内里柔软。史蒂夫感叹道：“我作为一个航空工程师这么多年，还从来没遇见过这等怪事。”史蒂夫认为，这可能由空气污染造成的，譬如某种化学物质混入大气层，在高空被卷进一场暴风雨，其间凝结成球并伴随冰雹重新降落到地面。伯恩茅斯大学一科研人员则表示，这些珠子可能来自海洋无脊椎动物所产的卵。<span><a href="#">【全文】</a></span></p>
</div>
</li>
<li style="clear:both">
<div style="float:left;margin:3px 9px 3px 0px;">
<img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg">
</div>
<div class="contentBox6">
<p class="p_title"><a>英国降蓝色“果冻”雨 内里柔软无味似弹珠</a></p>
<p class="intro_li">据英国《卫报》30日报道，英国多塞特郡伯恩茅斯(Bournemouth)61岁老人史蒂夫霍恩斯比(Steve Hornsby)在26日遭遇到一件新鲜事，他家后院突然天降奇雨，雨点除了普通的冰雹之外，还夹杂有大量弹珠般的果冻状蓝色小球。当地时间26日下午4点10分左右，史蒂夫向自家车库走去，眼看天色突然变暗，一场暴风雨马上就要来临，他急忙寻处躲避。天空果然下起冰雹，只不过20秒就停住了。史蒂夫说道：“我原以为下的就是普通的雹子，可当我往自家草坪上望去，发现上面到处撒满像弹珠一样的蓝色小球，一踩就消失不见，捡也捡不起来。我就用勺子把它们小心地弹进一个果酱罐里。”这些珠子闻上去没有味道，在水里也浮不起来，表面有一层壳，内里柔软。史蒂夫感叹道：“我作为一个航空工程师这么多年，还从来没遇见过这等怪事。”史蒂夫认为，这可能由空气污染造成的，譬如某种化学物质混入大气层，在高空被卷进一场暴风雨，其间凝结成球并伴随冰雹重新降落到地面。伯恩茅斯大学一科研人员则表示，这些珠子可能来自海洋无脊椎动物所产的卵。<span><a href="#">【全文】</a></span></p>
</div>
</li>
</ul>
</div>','	<ul>
#foreach( $r in $InfoUtilData.getInfoList("site_id=$site_id;cat_id=$cat_id;is_show_thumb=true;cur_page=$cur_page;size=$size;orderby=ci.released_dtime desc"));
<li style="clear:both">
<div style="float:left;margin:3px 9px 3px 0px;">
<a href="$r.content_url" target="_blank" title="${r.title}"><img src="$r.thumb_url" width="$img_width" height="$img_height" border="0"></a>
</div>
<div class="contentBox6">
<p class="p_title"><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,$title_count,"...")}</a></p>
<p>${FormatUtil.cutString($r.description,$intro_count,"...")}<span><a href="$r.content_url">【全文】</a></span></p>
</div>
</li>
#end
</ul>
<div class="clearBoth"></div>','','ListCount,TitleCount,ShowMore,ModuleTitle,ModuleTitleUrl,ImgWidth,ImgHeight,ShowTitle,IntroCount,ShowIntroLink','/cms.files/design/thumb/201203050348009.png','78','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(8,8,0,'图片转场','<div class="">
<link type="text/css" rel="stylesheet" href="/cms.files/js/jquery-plugin/trancsImg/cms.trancs_img.css" />
<div id="ai1" class="transform_img_parent" style="width:10px; height:10px;">
<div class="transform_img_parent_bg"></div>
<div class="transform_img_parent_info">世界上最美的50个地方</div>
<div class="transform_img_parent_list">
<a href="#" target="_blank"><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="200px" height="200px" title="世界上最美的50个地方" alt="世界上最美的50个地方" /></a>
</div>
</div>
</div>','&lt;link type="text/css" rel="stylesheet" href="/cms.files/js/jquery-plugin/trancsImg/cms.trancs_img.css" /&gt;
<!-- script标签动态写不进去,使用转义符号 -->
&lt;script type="text/javascript" src="/cms.files/js/jquery-plugin/trancsImg/cms.trancs_img.js"&gt;&lt;/script&gt;
<div id="$trancsimg_div_id" class="transform_img_parent" style="width:$img_width; height:$img_height;">
<div class="transform_img_parent_bg"></div>
<div class="transform_img_parent_info"></div>
<div class="transform_img_parent_list">
#foreach( $r in $InfoUtilData.getInfoList("site_id=$site_id;cat_id=$cat_id;is_show_thumb=true;cur_page=$cur_page;size=$size;orderby=ci.released_dtime desc"));
<a href="$r.content_url" target="_blank"><img src="$r.thumb_url" width="$img_width" height="$img_height" title="${r.title}" alt="${FormatUtil.cutString($r.title,$title_count,''...'')}" /></a>
#end
</div>
</div>
&lt;script type="text/javascript"&gt;
$("#$trancsimg_div_id").setTransformImg($trancsimg_time,0,0,0);
&lt;/script&gt;
','','ListCount,TitleCount,ShowMore,ModuleTitle,ModuleTitleUrl,ImgWidth,ImgHeight,TrancsImgTime','/cms.files/design/thumb/201203050441015.png','77','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(10,10,0,'横向滚动标题','<div>
<div id="marquee_div" style="height:20px;overflow:hidden;line-height:20px">
<ul>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="float:left;padding:3 5"><a href="#" target="_blank">世界上最美的50个地方</a></li>
</ul>
</div>
</div>','<!-- ------------------信息列表　开始---------------- -->
<!-- script标签动态写不进去,使用转义符号 -->
&lt;script type="text/javascript" src="/cms.files/js/jquery-plugin/marqueeTools/cms.marqueeTools.js"&gt;&lt;/script&gt;
<div id="$marquee_div_id" style="width:100%;height:20px;overflow:hidden;line-height:20px">
<ul>
#foreach( $r in $InfoUtilData.getInfoList("site_id=$site_id;cat_id=$cat_id;cur_page=$cur_page;size=$size;orderby=ci.released_dtime desc"));
<li style="float:left;padding:3px 5px"><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,$title_count,"...")}</a></li>
#end
</ul>
</div>
<!-- ------------------信息列表　结束---------------- -->','','ListCount,TitleCount,ShowMore,ModuleTitle,ShowTitle,MarqueeDirectionCrosswise,MarqueeSpeed','/cms.files/design/thumb/201203061204059.png','69','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(11,11,0,'垂直滚动标题','<div>
<div id="marquee_div" style="overflow:hidden">
<div class="contentBox6">
<ul>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
<li style="padding:3px 0px"><a href="#" target="_blank">世界上最美的50个地方</a></li>
</ul>
</div>
</div>
</div>','<!-- ------------------信息列表　开始---------------- -->
<!-- script标签动态写不进去,使用转义符号 -->
&lt;script type="text/javascript" src="/cms.files/js/jquery-plugin/marqueeTools/cms.marqueeTools.js"&gt;&lt;/script&gt;
<div id="$marquee_div_id" style="width:100%;height:$marquee_div_height;overflow:hidden">
<div class="contentBox6">
<ul>
#foreach( $r in $InfoUtilData.getInfoList("site_id=$site_id;cat_id=$cat_id;cur_page=$cur_page;size=$size;orderby=ci.released_dtime desc"));
<li style="padding:3px 0px"><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,$title_count,"...")}</a></li>
#end
</ul>
</div>
</div>
<!-- ------------------信息列表　结束---------------- -->','','ListCount,TitleCount,ShowMore,ModuleTitle,ModuleTitleUrl,MarqueeDirectionLengthways,MarqueeDivHeight,MarqueeSpeed','/cms.files/design/thumb/201203061250008.png','68','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(9,9,0,'滚动图片','<div class="contentBox6">
<div id="marquee_div" style="height:60px;overflow:hidden">
<ul class="marquee_pic_ul">
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/></li>
</ul>
</div>
</div>','<!-- script标签动态写不进去,使用转义符号 -->
&lt;script type="text/javascript" src="/cms.files/js/jquery-plugin/marqueeTools/cms.marqueeTools.js"&gt;&lt;/script&gt;
<div id="$marquee_div_id" style="width:100%;overflow:hidden">
<ul class="marquee_pic_ul">
#foreach( $r in $InfoUtilData.getInfoList("site_id=$site_id;cat_id=$cat_id;is_show_thumb=true;cur_page=$cur_page;size=$size;orderby=ci.released_dtime desc"));
<li style="float:left"><a href="$r.content_url" target="_blank"><img src="$r.thumb_url" width="$img_width" height="$img_height" title="${r.title}" /></a></li>
#end
</ul>
</div>','','ListCount,ImgWidth,ImgHeight,MarqueeDirectionCrosswise,MarqueeSpeed','/cms.files/design/thumb/201203061023036.png','66','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(18,18,0,'滚动图片+标题','<div class="contentBox6">
<div id="marquee_div_title" style="height:60px;overflow:hidden">
<ul class="marquee_pic_ul">
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
<li><img src="/cms.files/js/jquery-plugin/trancsImg/demo.jpg" width="90px" height="60px" title="世界上最美的50个地方" border="0"/><p><a href="#">世界上最美的50个地方</a></p></li>
</ul>
</div>
</div>','<!-- script标签动态写不进去,使用转义符号 -->
&lt;script type="text/javascript" src="/cms.files/js/jquery-plugin/marqueeTools/cms.marqueeTools.js"&gt;&lt;/script&gt;
<div id="$marquee_div_id" style="width:100%;overflow:hidden">
<ul class="marquee_pic_ul">
#foreach( $r in $InfoUtilData.getInfoList("site_id=$site_id;cat_id=$cat_id;is_show_thumb=true;cur_page=$cur_page;size=$size;orderby=ci.released_dtime desc"));
<li>
<a href="$r.content_url" target="_blank"><img src="$r.thumb_url" width="$img_width" height="$img_height" title="${r.title}" /></a>
<p><a href="$r.content_url" target="_blank" title="${r.title}">${FormatUtil.cutString($r.title,$title_count,"...")}</a></p>
</li>
#end
</ul>
</div>','','ListCount,TitleCount,ImgWidth,ImgHeight,MarqueeDirectionCrosswise,MarqueeSpeed','/cms.files/design/thumb/201203080949040.png','64','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(6,6,0,'导航','<div class="">
<ul class="commonNav_ul">
<li><a href="#">导航栏目</a></li>
<li><a href="#">导航栏目</a></li>
<li><a href="#">导航栏目</a></li>
<li><a href="#">导航栏目</a></li>
<li><a href="#">导航栏目</a></li>
<li><a href="#">导航栏目</a></li>
<li><a href="#">导航栏目</a></li>
</ul>
</div>','<ul class="commonNav_ul">
#foreach( $r in $InfoUtilData.getChildCategoryList("$cat_id","$site_id"));
#if($velocityCount < $menu_count);
<li><a href="/info/iList.jsp?cat_id=$r.cat_id">$r.cat_cname</a></li>
#end
#end
</ul>','','MenuCount','/cms.files/design/thumb/201203050406024.png','50','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(7,7,0,'当前位置','<div class="contentBox6">
<div><a href="#">首页</a><span class="position_symbol">></span><a href="#">当前栏目</a></div>
</div>','<ul>
#if($!{cat_id});
#set($tmp_cat_id = "$cat_id");
#else
#set($tmp_cat_id = "${InfoData.cat_id}");
#end
#set($category_list = $InfoUtilData.getCategoryPosition("$tmp_cat_id","$site_id","$node_id","list"));
#foreach( $pos in $category_list);
<li style="float:left; padding:3px 10px 3px 0px;">
#if($position_all_path == 1) <!-- 这里判断是否显示所有路径 -->
#if(${velocityCount} == 1);
<a target="$position_jump_type" href="$pos.url">$position_index_page</a>
#else
#if(${velocityCount}> 1) $position_symbol #end
<a target="$position_jump_type" href="$pos.url">$pos.cat_cname</a>
#end
#else <!-- 否则只显示最后一个栏目的 -->
#if(${velocityCount} == $category_list.size());
<a target="$position_jump_type" href="$pos.url">$pos.cat_cname</a>
#end
#end
</li>
#end
</ul>','','PositionSymbol,PosShowAllPath,PosHasLink,PosJumpType,PosIndexPage','/cms.files/design/thumb/201203050414053.png','49','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(12,12,0,'列表翻页','<div class="contentBox6">
<div class="pages">
共有50条&nbsp;&nbsp;当前第1页&nbsp;&nbsp;共3页&nbsp;&nbsp;
<a href="#">首页</a>&nbsp;&nbsp;
<a href="#">上一页</a>&nbsp;&nbsp;
<a href="#">下一页</a>&nbsp;&nbsp;
<a href="#">末页</a>
</div>
</div>','#set($turnpage=$InfoUtilData.getInfoCount("site_id=$site_id;cat_id=$cat_id;tag=$tag;cur_page=$cur_page;size=$size;orderby=ci.released_dtime desc"));
<div class="pages">
共有${turnpage.count}条&nbsp;&nbsp;当前第${turnpage.cur_page}页&nbsp;&nbsp;共${turnpage.page_count}页&nbsp;&nbsp;
<a href="?cat_id=$cat_id&cur_page=1">首页</a>&nbsp;&nbsp;
<a href="?cat_id=$cat_id&cur_page=$turnpage.prev_num">上一页</a>&nbsp;&nbsp;
<a href="?cat_id=$cat_id&cur_page=$turnpage.next_num">下一页</a>&nbsp;&nbsp;
<a href="?cat_id=$cat_id&cur_page=$turnpage.page_count">末页</a></div>
</div>
','','ListCount','/cms.files/design/thumb/201203060232027.png','45','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(20,20,0,'内容页：上标题','<div class="contentBox6">
<div id="info_toptitle">全世界最美的50个地方</div>
</div>','<div id="info_toptitle">$!{InfoData.top_title}</div>','','','/cms.files/design/thumb/201203060149033.png','40','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(13,13,0,'内容页：标题','<div class="contentBox6">
<div id="info_title">全世界最美的50个地方</div>
</div>','<div id="info_title">$!{InfoData.title}</div>','','','/cms.files/design/thumb/201203060149033.png','39','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(19,19,0,'内容页：副标题','<div class="contentBox6">
<div id="info_subtitle">全世界最美的50个地方</div>
</div>','<div id="info_subtitle">$!{InfoData.subtitle}</div>','','','/cms.files/design/thumb/201203060149033.png','38','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(14,14,0,'来源+作者+时间','<div class="contentBox6">
<div style="text-align:center">
<span id="info_source"  class="info_source">来源：中国政府网</span>
<span id="info_author" class="info_author">作者：向天</span>
<span id="info_released_dtime" class="info_time">时间：2011-02-08</span>
</div>
</div>','<div class="contentBox6">
<div style="text-align:center">
<span id="info_source" class="info_source">来源：$!{InfoData.source}</span>
<span id="info_author" class="info_author">作者：$!{InfoData.author}</span>
<span id="info_released_dtime" class="info_time">时间：${FormatUtil.formatDate($InfoData.released_dtime,"$time_format")}</span>
</div>
</div>','','ShowTime,TimeFormat,ShowSource,ShowAuthor','/cms.files/design/thumb/201203060237055.png','34','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(17,17,0,'字号大中小','<div class="info_content_opt">
字号：
<span class="pointer" >小</span>
<span class="pointer" >中</span>
<span class="pointer" >大</span>
</div>','<div class="info_content_opt">
字号：
<span class="pointer" onclick="doZoom(''small'');">小</span>
<span class="pointer" onclick="doZoom(''mid'');">中</span>
<span class="pointer" onclick="doZoom(''big'');">大</span>
</div>','','','/cms.files/design/thumb/201203060258045.png','32','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(15,15,0,'新闻内容','<div class="contentBox6">	   <div>	　　　新华网布鲁塞尔2月8日电（记者孙闻）欧盟对外行动署执行秘书长皮埃尔·维蒙8日在布鲁塞尔说，欧盟将在本月27日举行的成员国外长会上作出对叙利亚实施新一轮制裁的决定。<br />	<br />	　　维蒙对新闻界说，欧盟成员国已就对叙利亚实施新一轮制裁达成原则一致，目前正在讨论相关细节。制裁内容包括冻结叙中央银行在欧资产，禁止欧盟成员国从叙进口磷酸盐、贵金属和石油等。<br /></div>	</div>','#if(${InfoData.model_id}==13)	<div class="textCenter" style="width: 480px; height: 380px;margin:0 auto;">	<div id="mediaplayer" class="textCenter">mediaplayer</div>	&lt;script type="text/javascript" src="/cms.files/js/jwplayer/jwplayer.js"&gt;&lt;/script&gt;	&lt;script type="text/javascript"&gt;var video_path = "${InfoData.video_path}";var video_pic = "${InfoData.thumb_url}";		if(video_path.indexOf(".flv")>0 || video_path.indexOf(".mp4")>0){jwplayer("mediaplayer").setup({flashplayer: "/cms.files/js/jwplayer/player.swf",file: video_path,image: video_pic,width:480,height:380});}else{$("#mediaplayer").html(''<embed id="live_video_embed" width="480" height="380" src="${InfoData.video_path}" mediatype="video" autostart="false" loop="false" menu="true" sck_id="0" type="application/x-mplayer2" ></embed>'');}&lt;/script&gt;	</div>	#end	#if(${InfoData.model_id}==10)	&lt;link rel="stylesheet" type="text/css"  href="/js/photo/nph_gallery_2.11.css" /&gt; 	&lt;script type="text/javascript" src="/js/photo/ntes_jslib_1.x.js"&gt;&lt;/script&gt; 	&lt;script type="text/javascript" src="/js/photo/ntes_jslib_ui_0.x.js"&gt;&lt;/script&gt; 	&lt;script type="text/javascript" src="/js/photo/ntes_ui_scroll.js"&gt;&lt;/script&gt; 	&lt;script type="text/javascript" src="/js/photo/nph_gallery_2.11.js"&gt;&lt;/script&gt; 	&lt;script type="text/javascript" src="/js/photo/wb2.0.8.js"&gt;&lt;/script&gt;	&lt;link rel="stylesheet" type="text/css" href="/styles/news.skin/pic_small.css" /&gt;	&lt;script type="text/javascript"&gt;	NTES.ready(function ($) { new nph.Gallery({ sn: "1011" }); });	&lt;/script&gt;	<div id="info_pic">	<!--组图显示 开始-->	<div id="pic_item_box">	<div class="nph_gallery nph_skin_white" id="gallery1011">	<div class="nph_bg">	<div id="modePhoto1011" class="nph_photo">	<div class="nph_photo_view">	<div class="nph_cnt" id="photoView1011"><i></i><img src="../js/photo/bg06.png" id="photo1011" /></div>	<div class="nph_photo_prev"><a href="#" hidefocus="true" class="nph_btn_pphoto" id="photoPrev1011" target="_self"></a></div>	<div class="nph_photo_next"><a href="#" hidefocus="true" class="nph_btn_nphoto" id="photoNext1011" target="_self"></a></div>	<div class="nph_photo_loading" id="photoLoading1011"></div>	</div>	<div class="nph_cnt">	<div id="setInfo1011"><span id="photoType1011" class="nph_set_cur">（<span class="nph_c_lh" id="photoIndex1011"></span>/$InfoData.item_list.size()）</span></div>	<div class="nph_photo_desc" id="photoDesc1011"></div>	</div>	<span class="nph_hr_solid"></span>	<div class="nph_cnt clearfix">	<div class="nph_photo_thumb" id="scrl1011">	<div class="clearfix">	<div class="nph_scrl">	<div class="nph_scrl_thumb">	<div class="nph_scrl_main">	<ul class="nph_list_thumb" id="thumb1011"></ul>	</div>	<div class="nph_scrl_bar clearfix">	<span class="nph_scrl_lt"></span>	<span class="nph_scrl_rt"></span>	<div class="nph_scrl_bd">	<div class="nph_scrl_ct">	<a href="" hidefocus="true" class="nph_btn_scrl" id="bar1011">	<b class="nph_btn_lt"></b>	<b class="nph_btn_rt"></b>	<span class="nph_btn_bd"><span><b class="nph_btn_ct"></b></span></span>	</a>	</div>	</div>	</div>	</div>	</div>	<span class="nph_scrl_prev"><a href="" hidefocus="true" class="nph_btn_pscrl" id="scrlPrev1011"></a></span>	<span class="nph_scrl_next"><a href="" hidefocus="true" class="nph_btn_nscrl" id="scrlNext1011"></a></span>	</div>	</div>	</div>	</div>	<textarea class="hidden" id="photoList1011">	#foreach( $r in $InfoData.item_list)		<li>		<a href="#p=$velocityCount" hidefocus="true"><img src="${r.pic_path}" alt="${r.pic_title}" /></a>		<h2>${r.pic_title}</h2>		<p>${r.pic_note}</p>		<i title="img">${r.pic_path}</i>		<i title="timg">${r.pic_path}</i>		</li>	#end	</textarea>	<div id="galleryTpl1011" class="hidden"> </div>	</div>	</div>	</div>	<!--组图显示 结束-->	</div>	<div id="info_content">			${InfoData.pic_content}	</div>	#end	<div id="info_content" class="info_content_mid">	　　　$!{InfoData.info_content}	</div>','','','/cms.files/design/thumb/201203060150026.png','30','system','');
insert into cs_design_module(id,module_id,datasouce_type,module_name,module_content,v_code,style_ids,attr_ids,thumb_url,weight,app_id,site_id)values(16,16,0,'新闻编辑','<div class="contentBox6">
<div style="text-align:right">
<span id="info_editor">编辑：问天</span>
</div>
</div>','<div class="contentBox6">
<div style="text-align:right">
<span id="info_editor">编辑：$!{InfoData.editor}</span>
</div>
</div>','','','/cms.files/design/thumb/201203060238042.png','28','system','');
insert into cs_gk_indexrole (id, ir_id, ir_item, ir_value, ir_space, is_valid, sort_id, ir_type) values (3, 'C', '信息分类号', '0', '-', 0, 3, 0);
insert into cs_gk_indexrole (id, ir_id, ir_item, ir_value, ir_space, is_valid, sort_id, ir_type) values (1, 'A', '前段码', 'G', null, 0, 1, 0);
insert into cs_gk_indexrole (id, ir_id, ir_item, ir_value, ir_space, is_valid, sort_id, ir_type) values (2, 'B', '区域及部门编号', 'XA001', '-', 0, 2, 0);
insert into cs_gk_indexrole (id, ir_id, ir_item, ir_value, ir_space, is_valid, sort_id, ir_type) values (4, 'D', '信息编制年份', 'yyyy', '-', 1, 4, 0);
insert into cs_gk_indexrole (id, ir_id, ir_item, ir_value, ir_space, is_valid, sort_id, ir_type) values (5, 'E', '信息流水号', '6', null, 1, 5, 0);
insert into cs_gk_indexrole (id, ir_id, ir_item, ir_value, ir_space, is_valid, sort_id, ir_type) values (6, 'F', '流水号生成规则', '100', null, 1, 0, 1);
insert into cs_gk_indexrole (id, ir_id, ir_item, ir_value, ir_space, is_valid, sort_id, ir_type) values (7, 'G', '流水号循环周期', 'Y', null, 1, 0, 1);
insert into cp_dept(dept_id,parent_id,dept_name,dept_position,dept_level,sort_id) values(1,0,'部门注册','$1$',0,1);
insert into cp_area(area_id,parent_id,area_cname,area_position,area_level,sort_id) values(1,0,'地区分类','$1$',1,0);
insert into cp_category(cat_id,parent_id,cat_cname,cat_position,cat_level,sort_id) values(1,0,'内容分类','$0$1',0,0);
insert into cs_member_config(config_id, is_allow, is_check, reg_pro, close_pro, forbidden_name)values(1, 0, 1, '<p>1、注册<br />（1）请您在首次写信之前先注册。<br />（2）注册的用户名和密码，使用英文字母或数字均可。密码至少6位，英文字母和数字可以混合排列。<br />（3）为便于有关部门向您了解相关情况和反馈办理结果，请填写真实姓名及联系方式。<br /><br />2、写信<br />（1）在您注册成功后，请直接在内容框内写信。本网站不接收文本附件。<br />（2）当您附加照片时，请选择"附加图片"选框，同时选择jpg或gif图形文件。<br />（3）在"是否可公开"栏目中，如果您选择"是"，我们将按照有关规定把您所反映的问题及处理情况在网上公开；如果您选择"否"，我们将按规定对您的信件严格保密。<br />（4）如果再次写信，不需要重新注册。在登录同一用户名和密码后，即可重新写信。<br /><br />3、查询<br />（1）如果查询您反映的问题及办理情况，请先登录您的用户名，输入密码。<br />（2）登录后，可以看到您历次提交的信件列表。点击某一信件的标题，即可查看所提事项的办理状态或答复意见。<br />（3）请记住您的用户名和密码。密码丢失时，请使用取回密码功能。<br />（4）希望您对答复意见作出评价（满意、基本满意、不满意），如果您没有作出评价，我们将认为您的评价是基本满意。</p><p><br />4、信访法规<br />提供给您的与信访相关的法规文件。</p>', '<p>停止注册了!</p>', '');
create index info_id on cs_info (info_id);
create index info_final on cs_info (final_status);
create index info_cat_id on cs_info (cat_id);
create index info_site_id on cs_info (site_id);
create index info_status on cs_info (info_status);
create index article_info_id on cs_info_article (info_id);
create index cat_id on cs_info_category (cat_id);
create index cat_site_id on cs_info_category (site_id);
create index gk_info_id on cs_gk_info (info_id);
create index sq_model_id on cp_sq (model_id);
create index sq_type on cp_sq (sq_type);
create index sq_do_dept on cp_sq (do_dept);
create index sq_step_id on cp_sq (step_id);
create index sq_status on cp_sq (sq_status);
create index sq_is_open on cp_sq (is_open);
create index cp_sub_id on cp_subject (sub_id);
create index cp_sub_category_id on cp_subject (category_id);
create index cp_sub_status on cp_subject (status);
create index cp_publish_status on cp_subject (publish_status);
create index att_f_id on cs_attachment (f_id);
create index att_site_id on cs_attachment (site_id);
create index user_dept_id on cs_org_user (dept_id);
create index survey_id on cp_survey (s_id);
create index survey_cat_id on cp_survey (category_id);
create index survey_p_status on cp_survey (publish_status);
create index survey_s_type on cp_survey (s_type);
create index survey_site_id on cp_survey (site_id);
create index template_t_id on cs_template (t_id);
create index template_site_id on cs_template (site_id);
create index temedit_id on cs_template_edit (t_id);
create index temedit_tcat_id on cs_template_edit (tcat_id);
create index temedit_site_id on cs_template_edit (site_id);
create index temedit_status on cs_template_edit (t_status);
create index temver_id on cs_template_ver (t_id);
create index temver_tcat_id on cs_template_ver (tcat_id);
create index temver_site_id on cs_template_ver (site_id);
create index temver_status on cs_template_ver (t_status);
create index com_info_id on cs_comment_main (info_id);
create index com_site_id on cs_comment_main (site_id);
create index com_info_type on cs_comment_main (info_type);
create index com_is_status on cs_comment_main (is_status);
create index com_is_quest on cs_comment_main (is_quest);
create index su_anw_id on cp_survey_answer (answer_id);
create index su_anw_s_id on cp_survey_answer (s_id);
create index info_cat_model_cat_id on cs_info_category_model (cat_id);
create index info_cat_model_site_id on cs_info_category_model (site_id);
create index info_cat_model_app_id on cs_info_category_model (app_id);
create index info_cat_model_model_id on cs_info_category_model (model_id);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7211, 1, 431);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7212, 3, 431);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7213, 1, 447);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7214, 5, 447);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7215, 5, 350);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7216, 1, 410);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7217, 5, 410);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7218, 1, 404);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7219, 1, 409);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7220, 5, 409);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7221, 1, 414);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7222, 5, 414);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7223, 1, 415);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7224, 5, 415);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7225, 1, 416);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7226, 5, 416);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7227, 1, 417);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7228, 5, 417);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7229, 1, 418);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7230, 5, 418);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7231, 1, 419);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7232, 5, 419);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7233, 1, 445);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7234, 5, 445);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7235, 1, 446);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7236, 5, 446);
insert into cs_org_opt (opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (450, 405, '$1$6$405$450$', '访问量统计', 'cms', '', '', '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (493, 444, '访问量统计', '/sys/cms/cmsCount/AccessCountList.jsp', 450, '0', '$1$396$6$444$493$', 3, '', '');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7237, 1, 450);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7238, 5, 450);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7239, 1, 411);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7240, 11, 411);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7241, 1, 448);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7242, 11, 448);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7243, 12, 448);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7244, 1, 413);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7245, 11, 413);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7246, 12, 413);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7247, 1, 449);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7248, 6, 449);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7249, 1, 408);
update cs_info_model set model_type=0;
update cs_org_menu set menu_url='/sys/model/fields/fields_list.jsp' where menu_id=178;
insert into cs_org_opt(opt_id, parent_id, tree_position, opt_name, app_id, controller, action, opt_flag, opt_memo)values (451, 414, '$1$6$360$414$451$', '留言管理', 'cms', '', '', '', '');
insert into cs_org_menu (menu_id, parent_id, menu_name, menu_url, opt_id, menu_level, menu_position, menu_sort, menu_memo, handls)values (494, 458,'留言管理', '/sys/appCom/guestbook/guestBookSubForUser.jsp',451, '0', '$1$396$6$398$458$494$', 4, '', '');
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7250, 1, 451);
insert into cs_org_role_opt (role_opt_id, role_id, opt_id)values (7251, 5, 451);
insert into cs_site_server values('1', '发布服务器', '127.0.0.1','10001', '2', '');

