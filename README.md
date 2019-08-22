##ProjectOne - my first REST-full API.

### Installation
1. Install JDK 1.8
2. Add local variables
Name: ```JAVA_HOME```, Value:```C:\Program Files\Java\jdk1.8.0_131```
and add to path:```;%JAVA_HOME%\bin```
Check installation:in cmd ```echo %JAVA_HOME%```
You must get ```java -version```
3. Install maven http://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.6.1/binaries/apache-maven-3.6.1-bin.zip
Extract archive to c:/maven, or other.
add local variables:Name:```M2_HOME```, Value:```c:/maven```
add to path:```;%M2_HOME%\bin```
Check installation: in cmd ```mvn --version```
3. Start the project:
    open console on project path, and run ```mvn clean install```
    after successful build project, you can run ```mvn exec:java -Dexec.mainClass=StartUi```
4. Import to Intellij IDEA
File->Open
Change pom.xml in the root project path
Ok->Open as Project

###Using
You must use only required commands:
1) NEWPRODUCT <name> - Create new product on DB, always use unique name of product.
2) PURCHASE <name> <amount> <price> <date> - Yse for bye goods. Input product name, amount, unit price and date.
3) DEMAND <name> <amount> <price> <date> - For sell goods. Input product name, amount, unit price and date.
4) SALESREPORT <name> <date> - Calculate profit. Input product name and report date. 







