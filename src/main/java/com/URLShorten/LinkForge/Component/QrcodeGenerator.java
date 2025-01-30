package com.URLShorten.LinkForge.Component;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;

@Component
public class QrcodeGenerator {
    public static byte[] generateQRCodeImage(String text) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                bufferedImage.setRGB(i, j, bitMatrix.get(i, j) ? 0x000000 : 0xFFFFFF); // black & white pixels
            }
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
