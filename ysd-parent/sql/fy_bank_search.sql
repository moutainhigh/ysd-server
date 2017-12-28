CREATE TABLE `fy_bank_search` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `search_type` char(1) NOT NULL COMMENT '1充值 2提现',
  `order_id` bigint(20) NOT NULL COMMENT '充值表or提现表id',
  `bank_order_id` VARCHAR(64) NOT NULL COMMENT '提交到银行的orderid',
  `retry_num` tinyint(4) DEFAULT NULL COMMENT '重试次数，重试3次不再重试',
  `status` char(1) DEFAULT NULL COMMENT '0初始状态，1成功 ， 2失败',
  `create_date` datetime  NOT NULL COMMENT '创建时间',
  `modify_date` datetime  NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


alter table fy_bank_search add  ADD UNIQUE KEY  (search_type,order_id);
