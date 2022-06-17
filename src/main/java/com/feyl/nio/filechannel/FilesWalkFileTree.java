package com.feyl.nio.filechannel;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 遍历（删除）多级目录
 *
 * @author Feyl
 */
public class FilesWalkFileTree {

    /**
     * 删除路径（文件夹/文件）中的所有内容
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
//        Files.delete(Paths.get("E:\del"));
        Files.walkFileTree(Paths.get("E:\\del"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    /**
     * 统计 JDK1.8文件中 .jar的数量
     *
     * @throws IOException
     */
    private static void m2() throws IOException {
        AtomicInteger jarCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("D:\\Java\\JDK1.8"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".jar")) {
                    System.out.println(file);
                    jarCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("jar count:" +jarCount);
    }

    /**
     * 统计路径（文件夹/文件）中文件夹和文件的数量
     *
     * @throws IOException
     */
    private static void m1() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        //设计模式：访问者模式

        Files.walkFileTree(Paths.get("D:\\Java\\JDK1.8"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("====>"+dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("dir count:" +dirCount);
        System.out.println("file count:" +fileCount);
    }
}
