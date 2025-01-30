document.addEventListener("DOMContentLoaded", () => {
    const themeToggle = document.getElementById("themeToggle");
    const htmlElement = document.documentElement;
    const longUrlInput = document.getElementById("longUrlInput");
    const customAliasInput = document.getElementById("customAliasInput");
    const shortenButton = document.getElementById("shortenButton");
    const resultSection = document.getElementById("resultSection");
    const shortUrlOutput = document.getElementById("shortUrlOutput");
    const copyButton = document.getElementById("copyButton");
    const qrCodeImage = document.getElementById("qrCodeImage");

    const API_BASE_URL = "http://localhost:8080/api/url"; // Change this when deploying

    // 🌙 Toggle Dark/Light Mode
    themeToggle.addEventListener("click", () => {
        const currentTheme = htmlElement.classList.contains("dark") ? "dark" : "light";
        htmlElement.classList.toggle("dark");
        localStorage.setItem("theme", currentTheme === "light" ? "dark" : "light");
    });

    // Initialize Theme from Local Storage
    if (localStorage.getItem("theme") === "dark") {
        htmlElement.classList.add("dark");
    }

    // 🔗 Shorten URL Function
    shortenButton.addEventListener("click", async () => {
        const longUrl = longUrlInput.value.trim();
        const customAlias = customAliasInput.value.trim();

        if (!longUrl) {
            alert("Please enter a URL to shorten");
            return;
        }

        // Create request payload
        const payload = { longUrl, alias: customAlias || null };

        try {
            const response = await fetch(`${API_BASE_URL}/shorten`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                throw new Error(await response.text());
            }

            const shortUrl = await response.text(); // Expecting plain text response
            shortUrlOutput.value = shortUrl;
            resultSection.style.display = "block";

            // Generate QR Code from alias (last part of short URL)
            const alias = shortUrl.split("/").pop();
            qrCodeImage.src = `${API_BASE_URL}/qr/${alias}`;
            qrCodeImage.classList.remove("hidden");

        } catch (error) {
            console.error("Error shortening URL:", error);
            alert(`Error: ${error.message}`);
        }
    });

    // 📋 Copy to Clipboard
    copyButton.addEventListener("click", () => {
        shortUrlOutput.select();
        navigator.clipboard.writeText(shortUrlOutput.value).then(() => {
            copyButton.textContent = "Copied!";
            setTimeout(() => {
                copyButton.textContent = "Copy";
            }, 2000);
        });
    });
});
