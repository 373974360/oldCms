

CREATE TABLE "dz_cdgjj_source_info_category" (
  "cat_id" bigint(20) NOT NULL COMMENT '栏目id',
  "source" varchar(45) DEFAULT NULL COMMENT '渠道 : app，wx，pc'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公积金 渠道和栏目的绑定关系';
