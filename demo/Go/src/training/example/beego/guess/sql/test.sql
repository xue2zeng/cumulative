CREATE TABLE `subject` (
          `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
          `img` varchar(512) NOT NULL COMMENT '图片',
          `option` varchar(512) NOT NULL DEFAULT '' COMMENT '题目选项，为json格式',
          `answer_key` varchar(32) NOT NULL DEFAULT '' COMMENT '选择题的key，为ABCD等字符',
          `answer_value` varchar(32) NOT NULL DEFAULT '' COMMENT '答案value，电影名',
          `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
          `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
          `hash` char(32) NOT NULL DEFAULT '' COMMENT '哈希值',
          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2046 DEFAULT CHARSET=utf8 COMMENT='题目表';