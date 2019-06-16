package com.xspace.java11.feature;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 新增加的 Files API
 *
 * @author xue.zeng
 * @date 2019-06-16 18:20
 */
@Slf4j
public class FilesExample {
  public static void main(String[] args) throws IOException {
    String path = "./jdk11_new_feature.txt";
    Files.writeString(Path.of(path), "jdk11 new feature.", StandardCharsets.UTF_8);
    log.info(Files.readString(Paths.get(path), StandardCharsets.UTF_8));
  }
}
