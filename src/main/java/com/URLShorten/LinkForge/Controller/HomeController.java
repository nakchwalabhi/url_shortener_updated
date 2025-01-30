package com.URLShorten.LinkForge.Controller;

import com.URLShorten.LinkForge.Component.QrcodeGenerator;
import com.URLShorten.LinkForge.Model.Url;
import com.URLShorten.LinkForge.Service.UrlShortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/api/url")
public class HomeController {

    private final UrlShortService urlShortService;

    @Autowired
    public HomeController(UrlShortService urlShortService) {
        this.urlShortService = urlShortService;
    }

    @GetMapping("/redirect/{alias}")
    public ResponseEntity<Object> redirectToLongURL(@PathVariable String alias) {
        System.out.println("Received alias for redirection: " + alias);
        try {
            Url originalUrl = urlShortService.getOriginalUrl(alias);
            String longUrl = originalUrl.getLongUrl();

            // Sanitize and ensure the URL is valid
            longUrl = longUrl.replaceAll("^\"|\"$", ""); // Remove any double quotes
            if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
                longUrl = "http://" + longUrl;
            }

            URI redirectUri = URI.create(longUrl);
            return ResponseEntity.status(302).location(redirectUri).build();
        } catch (Exception e) {
            System.out.println("Error during redirection: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/shorten")
    public ResponseEntity<Object> shortenURL(@RequestBody String longUrl) {
        try {
            String shortUrl = urlShortService.shortenUrlWithAlias(longUrl);
            return ResponseEntity.ok(shortUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error shortening URL: " + e.getMessage());
        }
    }

    @GetMapping("/qr/{alias}")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable String alias) {
        try {
            // Construct the full shortened URL
            String shortUrl = "https://yourdomain.com/" + alias;

            // Generate the QR code with the full URL
            byte[] qrCode = QrcodeGenerator.generateQRCodeImage(shortUrl);

            return ResponseEntity.ok()
                    .header("Content-Type", "image/png")
                    .body(qrCode);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}