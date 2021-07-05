import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;


import java.io.*;
import java.nio.charset.StandardCharsets;


public class javaSDk {
//    @Test
//    public void up(){
//        //构造一个带指定Zone对象的配置类,Zone0表示华东区
//        Configuration cfg = new Configuration(Zone.zone0());
//        //其它参考类
//        UploadManager uploadManager=new UploadManager(cfg);
//        //生成上传凭证,然后准备上传
//        String accessKey ="NdptPP3vUWYIrMzVX7tqcTVURhzsjMm--dQVyBfU";
//        String secretKey ="KhQpNJFOQK29YhHvVBvSCdJ_rHU_HO4ZrLcEJl_8";
//        String bucket ="1001chaochao";
//        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = null;
//        byte[] uploadBytes ="hello qiniu cloud".getBytes(StandardCharsets.UTF_8);
//        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(uploadBytes);
//        Auth auth = Auth.create(accessKey,secretKey);
//        String upToken = auth.uploadToken(bucket);
//
//        try {
//            Response response =  uploadManager.put(byteArrayInputStream,key,upToken,null,null);
//            //解析上传结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
//        } catch (QiniuException e) {
//            e.printStackTrace();
//        }
//
//    }
    @Test
    public void delete(){
        //构造一个带指定Zone对象的配置类，zone0表示华东地区（默认）
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释

        String accessKey = "NdptPP3vUWYIrMzVX7tqcTVURhzsjMm--dQVyBfU";
        String secretKey = "KhQpNJFOQK29YhHvVBvSCdJ_rHU_HO4ZrLcEJl_8";

        String bucket = "1001chaochao";
        String key = "Fu3P9AgpOo_0q_4KnJdn9ZXVimfD";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
