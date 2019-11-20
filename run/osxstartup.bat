start cmd /k "java -jar F:\Design\project\WRICS\WRICS\WRICS-BFRAME\lud-service-core\target\lud-service-core.jar >> F:\Design\project\WRICS\WRICS\WRICS-BFRAME\run\log\core.log"
TIMEOUT /T 45 /NOBREAK

start cmd /k "java -jar F:\Design\project\WRICS\WRICS\WRICS-BFRAME\lud-service-business\target\lud-service-business.jar >> F:\Design\project\WRICS\WRICS\WRICS-BFRAME\run\log\business.log"
