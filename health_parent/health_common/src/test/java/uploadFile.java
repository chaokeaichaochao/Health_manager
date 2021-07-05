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

public class uploadFile {

    @Test
    public void up(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其它参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey ="BRnY252R553mJjYZFF120MQru1fof8f1LjUEsmA6";
        String secretKey ="109Fl6qo_qTGlKR_bwOjSHXdN8YGswIPu3GqwkSe";
        String bucket ="chaokeai";
        //如果是windows格式D:\\qiniu\\test.png
        String localFilePath="C:\\psc4.png";
        //默认不指定key情况下,以文件内容的hash值作为文件名
        String key = null;
        Auth  auth = Auth.create(accessKey,secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet=new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException e) {
            e.printStackTrace();
            Response r = e.response;
            System.out.println(r.toString());
            try {
                System.out.println(r.bodyString());
            } catch (QiniuException qiniuException) {

            }
        }

    }
    @Test
    public void delete(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其它参数参考类注释
        String accessKey ="BRnY252R553mJjYZFF120MQru1fof8f1LjUEsmA6";
        String secretKey ="109Fl6qo_qTGlKR_bwOjSHXdN8YGswIPu3GqwkSe";
        String bucket ="chaokeai";
        //文件存储名字
        String key="FheZGO-2cvLrYte8PMsDiZXZm539";
        //默认不指定key情况下,以文件内容的hash值作为文件名
        Auth  auth = Auth.create(accessKey,secretKey);
        BucketManager bUcketManager = new BucketManager(auth,cfg);

        try {
            bUcketManager.delete(bucket,key);

        } catch (QiniuException e) {
            e.printStackTrace();

            System.out.println(e.code());
            System.out.println(e.response.toString());

        }

    }

}
