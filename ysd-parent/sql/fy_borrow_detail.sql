ALTER TABLE fy_borrow_detail add bank_freeze_id varchar(32) COMMENT '渤海银行返回的冻结id.投资人投标成功后返回的。放款业务会使用' default NULL;
ALTER TABLE fy_borrow_detail add bank_trans_id varchar(32) COMMENT '账户存管平台流水号' default NULL;
ALTER TABLE fy_borrow_detail add bank_fee_amt decimal(5,2) COMMENT '平台收取商户手续费，具体金额以签约协议为准.单位是分' default 0;