Quickstart:

1. Download "target/session-service.jar"
2. Start with: java -jar session-service.jar


Example requests:

Insert: curl -v -H "Content-Type: application/json" --data '{"username":"Tester"}' localhost:8080/session

Update: curl -v -H "Content-Type: application/json" --data '{"id":"{valid session id}","username":"Tester2"}' localhost:8080/session

Get: curl -v localhost:8080/session/{valid session id}