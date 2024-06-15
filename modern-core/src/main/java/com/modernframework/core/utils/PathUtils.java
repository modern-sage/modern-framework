package com.modernframework.core.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * NIO中Path对象操作封装
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class PathUtils {

    /**
     * 判断是否为目录，如果file为null，则返回false<br>
     * 此方法不会追踪到软链对应的真实地址，即软链被当作文件
     *
     * @param path {@link Path}
     * @return 如果为目录true
     */
    public static boolean isDirectory(Path path) {
        return isDirectory(path, false);
    }

    /**
     * 判断是否为目录，如果file为null，则返回false
     *
     * @param path          {@link Path}
     * @param isFollowLinks 是否追踪到软链对应的真实地址
     * @return 如果为目录true
     */
    public static boolean isDirectory(Path path, boolean isFollowLinks) {
        if (null == path) {
            return false;
        }
        final LinkOption[] options = isFollowLinks ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
        return Files.isDirectory(path, options);
    }

    /**
     * 递归遍历目录以及子目录中的所有文件<br>
     * 如果提供path为文件，直接返回过滤结果
     *
     * @param path       当前遍历文件或目录
     * @param maxDepth   遍历最大深度，-1表示遍历到没有目录为止
     * @param fileFilter 文件过滤规则对象，选择要保留的文件，只对文件有效，不过滤目录，null表示接收全部文件
     * @return 文件列表
     */
    public static List<File> loopFiles(Path path, int maxDepth, FileFilter fileFilter) {
        final List<File> fileList = new ArrayList<>();

        if (null == path || !Files.exists(path)) {
            return fileList;
        } else if (!isDirectory(path)) {
            final File file = path.toFile();
            if (null == fileFilter || fileFilter.accept(file)) {
                fileList.add(file);
            }
            return fileList;
        }

        walkFiles(path, maxDepth, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                final File file = path.toFile();
                if (null == fileFilter || fileFilter.accept(file)) {
                    fileList.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return fileList;
    }

    /**
     * 遍历指定path下的文件并做处理
     *
     * @param start   起始路径，必须为目录
     * @param visitor {@link FileVisitor} 接口，用于自定义在访问文件时，访问目录前后等节点做的操作
     * @see Files#walkFileTree(Path, java.util.Set, int, FileVisitor)
     */
    public static void walkFiles(Path start, FileVisitor<? super Path> visitor) {
        walkFiles(start, -1, visitor);
    }

    /**
     * 遍历指定path下的文件并做处理
     *
     * @param start    起始路径，必须为目录
     * @param maxDepth 最大遍历深度，-1表示不限制深度
     * @param visitor  {@link FileVisitor} 接口，用于自定义在访问文件时，访问目录前后等节点做的操作
     * @see Files#walkFileTree(Path, java.util.Set, int, FileVisitor)
     */
    public static void walkFiles(Path start, int maxDepth, FileVisitor<? super Path> visitor) {
        if (maxDepth < 0) {
            // < 0 表示遍历到最底层
            maxDepth = Integer.MAX_VALUE;
        }
        try {
            Files.walkFileTree(start, EnumSet.noneOf(FileVisitOption.class), maxDepth, visitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
