# AzureStorageTest

How to start the AzureStorageTest application
---

1. Run `mvn clean install` to build application
1. Start application with `java -jar target/azurestorage-1.0-SNAPSHOT.jar server config.yml`
1. To test application enter url:
    1. `http://localhost:8080/download/rear/testproject` downloads `rear.png` from `testproject` package
    1. `http://localhost:8080/download/front/testproject` downloads `front.png` from `testproject` package
    1. `http://localhost:8080/unpack/project/testproject` fetches `testproject` from storage unpacks it and stores files on storage


Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
