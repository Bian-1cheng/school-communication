package com.commucation.demo.util;

import java.io.FileInputStream;

/**
 * @Author:jzs
 * @Date: 2021/02/27
 * @Time：15:15
 */
public class PictureModel {
    private String dir;
    private byte[] pic;

    public PictureModel(String dir) {
        this.dir = dir;
    }

    public byte[] getPic() {
        try {
            FileInputStream is = null;
            int size = -1;
            pic = new byte[0];
            if (!dir.equals("")) {
                is = new FileInputStream(dir);
                if (is != null) {
                    size = is.available();
                    pic = new byte[size];
                    is.read(pic);
                }
            }
            return pic;
        } catch (Exception e) {
            pic = new byte[0];
            return pic;
        }
    }
}
