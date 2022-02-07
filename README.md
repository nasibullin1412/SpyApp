# SpyApp
The application downloads a dex file, which is then dynamically launched, collects data from the device and sends it to DropBox

Actions:
1. Download and up backend with dex file
2. Clone repository in Android Studio
3. Change BASE_URL in NetworkConstants.kt and ACCESS_TOKEN (token generated in DropBox) to your own in master and app_without_spy_package branch
4. Build APK and see in which dex file the spyservice functionality
5. Switch to the app_without_spy_package branch and change DEX_FILENAME in MainActivity to the file from the paragraph above
6. Run the application and click on the start button
7. Star the repository  

for questions, write to telegram @Dvagiz1412
