package com.xuecheng.test.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fanfan on 2019/12/04.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFastDFS {

    //上传文件
    @Test
    public void testUpload() {
        try {
            //加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);
            //创建客户端
            TrackerClient trackerClient = new TrackerClient();
            //连接tacker Server
            TrackerServer connection = trackerClient.getConnection();
            if (connection == null) {
                System.out.println("getConnection return null");
                return ;
            }
            //获取一个storage server
            StorageServer storeStorage = trackerClient.getStoreStorage(connection);
            //创建一个storage储存客户端
            StorageClient1 storageClient1 = new StorageClient1(connection, storeStorage);
            //本地文件路径
            String item = "F:\\C11E26C3DDC530DA9C13CFA2A9714DCF.jpg";
            String fileid = storageClient1.upload_file1(item, "jpg", null);

            System.out.println("Upload local file " + item + " ok, fileid = " + fileid);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }

    //文件查询
    @Test
    public void testQueryFile() {
        try {
            //读取配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //创建客户端
            TrackerClient trackerClient = new TrackerClient();
            //连接tacker Server
            TrackerServer connection = trackerClient.getConnection();
            if (connection == null) {
                System.out.println("getConnection return null");
                return ;
            }
            //获取一个storage server
            StorageServer storeStorage = null;
            //创建一个storage储存客户端
            StorageClient storageClient = new StorageClient(connection, storeStorage);

            //查询文件
            FileInfo fileInfo = storageClient.query_file_info("group1", "M00/00/00/wKhLgl3nOceAACUnAADYknPHvAw457.jpg");

            //打印输出结果
            System.out.println(fileInfo);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }

    //文件下载
    @Test
    public void testDownloadFile() {
        //读取配置文件
        try {
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //创建客户端
            TrackerClient trackerClient = new TrackerClient();
            //连接tacker Server
            TrackerServer connection = trackerClient.getConnection();
            if (connection == null) {
                System.out.println("getConnection return null");
                return ;
            }
            //获取一个storage server
            StorageServer storeStorage = null;
            //创建一个storage储存客户端
            StorageClient1 storageClient1 = new StorageClient1(connection, storeStorage);

            //下载文件
            byte[] result = storageClient1.download_file1("group1/M00/00/00/wKhLgl3nOceAACUnAADYknPHvAw457.jpg");

            //向硬盘写入文件
            File file = new File("d:/1.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(result);
            fileOutputStream.close();
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }
}
