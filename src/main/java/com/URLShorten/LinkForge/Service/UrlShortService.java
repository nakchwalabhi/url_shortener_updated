package com.URLShorten.LinkForge.Service;

import com.URLShorten.LinkForge.Component.QrcodeGenerator;
import com.URLShorten.LinkForge.Model.Url;
import com.URLShorten.LinkForge.Repo.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class UrlShortService {

    private static final Logger logger = Logger.getLogger(UrlShortService.class.getName());

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private QrcodeGenerator qrCodeGenerator;

    // Shorten URL with alias if provided, else generate a random alias
    public String shortenUrlWithAlias(String url) throws Exception {
        // Trim unnecessary quotes from the input URL
        url = url.replaceAll("^\"|\"$", "").trim();

        if (url == null || url.isEmpty()) {
            throw new Exception("URL is required.");
        }

        Url existingUrl = new Url();
        existingUrl.setLongUrl(url);
        existingUrl.setCreatedAt(LocalDateTime.now());

        String alias = generateRandomString(8);
        existingUrl.setAlias(alias);
        existingUrl.setShortUrl("http://localhost:8080/api/url/redirect/" + alias);



        urlRepository.save(existingUrl);
        return existingUrl.getShortUrl();
    }

    // Retrieve original URL from alias
    public Url getOriginalUrl(String alias) throws Exception {
        logger.info("Searching for alias: " + alias); // Log for debugging
        Optional<Url> optionalUrl = urlRepository.findByAlias(alias);

        if (optionalUrl.isEmpty()) {
            logger.warning("Alias not found in database: " + alias);
            throw new Exception("URL not found for the provided alias.");
        }

        logger.info("Found long URL: " + optionalUrl.get().getLongUrl());
        return optionalUrl.get();
    }



    // Generate QR code for the short URL
    public byte[] generateQRCode(String shortUrl) throws Exception {
        return qrCodeGenerator.generateQRCodeImage(shortUrl);
    }

    // Generate random string for alias
    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return stringBuilder.toString();
    }
}