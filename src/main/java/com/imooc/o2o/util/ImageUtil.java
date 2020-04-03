package com.imooc.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将MultipartFile转换成File
     * @param multipartFile
     * @return
     */
    public static File transferMultipartFileToFile(MultipartFile multipartFile){
        File file = new File(multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 处理缩略图，返回新生成图片的相对值路径
     * @param file
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(File file, String targetAddr) {
        //获取不重复的随机名
        String realFileName = getRandomFileName();
        //获取文件的扩展名如：png,jpg等
        String extension = getFileExtension(file);
        //如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        //获取文件存储的相对路径（带文件名）
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" + relativeAddr);
        //获取文件要保存的目标路径
        File dest = new File(PathUtil.getImageBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImageBasePath() + relativeAddr);
        logger.debug("basePath is" + basePath);
        //调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(file).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        //返回图片相对路径地址
        return relativeAddr;
    }

    /**
     * 处理图片，返回新生成图片的相对值路径
     * @param productImgFile
     * @param targetAddr
     * @return
     */
    public static String generateNormalImg(File productImgFile, String targetAddr) {
        //获取不重复的随机名
        String realFileName = getRandomFileName();
        //获取文件的扩展名如：png,jpg等
        String extension = getFileExtension(productImgFile);
        //如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        //获取文件存储的相对路径（带文件名）
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" + relativeAddr);
        //获取文件要保存的目标路径
        File dest = new File(PathUtil.getImageBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImageBasePath() + relativeAddr);
        logger.debug("basePath is" + basePath);
        //调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(productImgFile).size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")),0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建图片失败：" + e.toString());
        }
        //返回图片相对路径地址
        return relativeAddr;
    }

    /**
     * 获取文件名的扩展名
     * @param file
     * @return
     */
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxx.jpg, 那么 home work xiangze
     * 这三个文件夹都得自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImageBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟 + 五位随机数
     * @return
     */
    private static String getRandomFileName() {
        //获取随机五位数
        int  rannum= random.nextInt(89999) + 10000;
        //获取当前系统时间
        String nowTimeStr = simpleDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    /**
     * 判断storePath是文件路径还是目录的路径
     * 如果storePath是文件路径则删除该文件
     * 如果storePath是目录路径则删除该目录下所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImageBasePath() + storePath);
        if (fileOrPath.isDirectory()){
            File[] files = fileOrPath.listFiles();
            for (File file : files){
                file.delete();
            }
        }
        fileOrPath.delete();
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("/Users/yangkun/Pictures/image/xiaohuangren.jpg")).size(200,200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")),0.25f)
                .outputQuality(0.8f).toFile("/Users/yangkun/Pictures/image/xhr.jpg");
    }
}
