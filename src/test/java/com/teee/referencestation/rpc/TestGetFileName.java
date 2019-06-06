package com.teee.referencestation.rpc;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class TestGetFileName {

    public static void main(String[] args) throws IOException {
        List<String> fileNames = new LinkedList<>();
        Files.walkFileTree(Paths.get("C:\\Users\\Yuxb\\Desktop\\rs_rpc_protocolv1.0\\slice"), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // TODO Auto-generated method stub
                // return super.preVisitDirectory(dir, attrs);
                System.out.println("正在访问：" + dir + "目录");
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // TODO Auto-generated method stub
                // return super.visitFile(file, attrs);
                if (file.toString().endsWith(".ice")) {
                    fileNames.add(file.getFileName().toString());
                    return FileVisitResult.CONTINUE; // 找到了就终止
                }
                return FileVisitResult.CONTINUE; // 没找到继续找
            }
        });

        for (String fileName : fileNames) {
            System.out.print(fileName + " ");
        }
    }
}
