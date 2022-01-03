### Start OpenTelemetry
```
docker compose up
```

### Start Application
```
./gradlew quarkusDev
```

### Succeeding tests
```
curl -X GET 'http://localhost:8080/hello/getsync?name=fred'
curl -X GET 'http://localhost:8080/hello/getasync?name=fred'
curl -X GET 'http://localhost:8080/hello/getcoroutine?name=fred'
curl -X POST 'http://localhost:8080/hello/qpsync?name=fred'
curl -X POST 'http://localhost:8080/hello/qpasync?name=fred'
curl -X POST 'http://localhost:8080/hello/qpcoroutine?name=fred'
curl -X POST 'http://localhost:8080/hello/bodysync?name=fred'
```

### Failing tests
```
curl -X POST 'http://localhost:8080/hello/bodysync' --data-raw 'fred'
curl -X POST 'http://localhost:8080/hello/bodycoroutine' --data-raw 'fred'
```