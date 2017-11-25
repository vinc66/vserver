package cn.vinc.core.support;

import cn.vinc.core.common.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vinc on 2016/9/14.
 */
public class HttpClinetUtils {


    public static String invokePost(String url, Map<String, String> params, Map<String, String> headers) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        StringBuffer sb = new StringBuffer();
        CloseableHttpResponse response2 = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            for (Map.Entry<String, String> entrySet:params.entrySet()
                 ) {
                nvps.add(new BasicNameValuePair(entrySet.getKey(),entrySet.getValue()));
            }
            final EntityBuilder entityBuilder = EntityBuilder.create().setParameters(nvps).setContentEncoding(Constants.DEFAULT_CHARSET);
            httpPost.setEntity(entityBuilder.build());
            response2 =   httpclient.execute(httpPost);
            HttpEntity entity2 = response2.getEntity();
            InputStream content = entity2.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));

            String str = null;
            while((str = bufferedReader.readLine()) != null){
                sb.append(str);
            }
            bufferedReader.close();
            EntityUtils.consume(entity2);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static String invokeGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = null;
        try {
            response1 = httpclient.execute(httpGet);
            HttpEntity entity1 = response1.getEntity();
            EntityUtils.consume(entity1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


}
