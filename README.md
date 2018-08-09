# 为PDF添加目录

目录文本文件格式

```$xslt
offset=14 (页面偏移量）
第１章　xxx@1
1.1　xxx@2
1.1.1　xxx@2
```
目录索引与目录标题之间隔一个空格，标题正文与页码之间用`@`分隔（不能使用中文的`＠`）。文件编码请用UTF-8。

使用maven编处理依赖
```$xslt
mvn dependency:resolve
```

打包生成可执行jar文件, 目标文件在target中。
```$xslt
mvn clean compile assembly:single
```

命令行运行，新生成的pdf文件与原pdf文件夹路径相同。
```$xslt
cd target
java -jar pdfmarker-1.0-SNAPSHOT-jar-with-dependencies.jar ./classes/bookmark_demo.txt ./classes/mysql_crush.pdf
```

> 注意：有关pdf的操作使用的是itext，如果目录达到4层，会导致栈溢出，猜测是使用了递归导致的，暂时没有解决。