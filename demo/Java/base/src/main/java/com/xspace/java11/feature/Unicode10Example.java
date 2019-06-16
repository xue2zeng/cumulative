package com.xspace.java11.feature;

import lombok.extern.slf4j.Slf4j;

/**
 * JEP327: Unicode 10
 *
 * @author xue.zeng
 * @date 2019-06-16 17:15
 */
@Slf4j
public class Unicode10Example {
  public static void main(String[] args) {
    log.info("\uD83E\uDDDA");
    log.info("\uD83E\uDD92");
    log.info("\uD83E\uDD95");
    log.info("\uD83E\uDDD9");
    log.info("\uD83E\uDDDB");
    log.info("\uD83E\uDD2E");
  }
}
