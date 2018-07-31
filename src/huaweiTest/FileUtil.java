package huaweiTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class FileUtil
{
    /** 
     * 璇诲彇鏂囦欢骞舵寜琛岃緭鍑�
     * @param filePath
     * @param spec 鍏佽瑙ｆ瀽鐨勬渶澶ц鏁帮紝 spec==null鏃讹紝瑙ｆ瀽鎵�鏈夎
     * @return
     * @author l00428364
     * @since 2017-12-8
     */
    public static String[] read(final String filePath, final Integer spec)
    {
        File file = new File(filePath);
        // 褰撴枃浠朵笉瀛樺湪鎴栬�呬笉鍙鏃�
        if ((!isFileExists(file)) || (!file.canRead()))
        {
            System.out.println("file [" + filePath + "] is not exist or cannot read!!!");
            return null;
        }
        
        List<String> lines = new LinkedList<String>();
        BufferedReader br = null;
        FileReader fb = null;
        try
        {
            fb = new FileReader(file);
            br = new BufferedReader(fb);

            String str = null;
            int index = 0;
            while (((spec == null) || index++ < spec) && (str = br.readLine()) != null)
            {
                lines.add(str);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeQuietly(br);
            closeQuietly(fb);
        }

        return lines.toArray(new String[lines.size()]);
    }
    /** 
     * 鍐欐枃浠�
     * @param filePath 杈撳嚭鏂囦欢璺緞
     * @param content 瑕佸啓鍏ョ殑鍐呭
     * @param append 鏄惁杩藉姞
     * @return
     * @author l00428364
     * @since 2017-12-8
     */
    public static int write(final String filePath, final String[] contents, final boolean append)
    {
        File file = new File(filePath);
        if (contents == null)
        {
            System.out.println("file [" + filePath + "] invalid!!!");
            return 0;
        }

        // 褰撴枃浠跺瓨鍦ㄤ絾涓嶅彲鍐欐椂
        if (isFileExists(file) && (!file.canRead()))
        {
            return 0;
        }

        FileWriter fw = null;
        BufferedWriter bw = null;
        try
        {
            if (!isFileExists(file))
            {
                file.createNewFile();
            }

            fw = new FileWriter(file, append);
            bw = new BufferedWriter(fw);
            for (String content : contents)
            {
                if (content == null)
                {
                    continue;
                }
                bw.write(content);
                bw.newLine();
                
            }
            
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            closeQuietly(bw);
            closeQuietly(fw);
        }

        return 1;
    }

    private static void closeQuietly(Closeable closeable)
    {
        try
        {
            if (closeable != null)
            {
                closeable.close();
            }
        }
        catch (IOException e)
        {
        }
    }

    private static boolean isFileExists(final File file)
    {
        if (file.exists() && file.isFile())
        {
            return true;
        }

        return false;
    }

}
