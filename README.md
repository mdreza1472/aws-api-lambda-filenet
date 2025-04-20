# Serverless Spring Boot Application on AWS Lambda

This project demonstrates a **Spring Boot 3 application** deployed as an **AWS Lambda function**, exposed via **API Gateway (REST API)** with Proxy Integration.

It supports:
- RESTful APIs (Ping, FileNet operations)
- File downloads (binary stream support)
- Lambda Proxy Integration with API Gateway
- Automatic JSON serialization
- Stage variable support

---

## Project Overview

| Feature | Details |
|:--------|:--------|
| Java Version | Java 17 |
| Framework | Spring Boot 3.x |
| Deployment | AWS Lambda |
| API Gateway | REST API (Proxy Integration) |
| Serverless Adapter | `aws-serverless-java-container-springboot3` |
| Key APIs | `/ping`, `/filenet/download/{guid}`, `/filenet/update-properties`, `/filenet/update-content` |
| Binary Downloads | Supported (`application/octet-stream`) |

---

## Technology Stack

- Java 17
- Spring Boot 3.4.x
- AWS Lambda
- AWS API Gateway (REST API)
- Lombok
- serverless-java-container-springboot3
- Maven for build

---

## Architecture Overview

```text
(Client: Postman/Browser/Frontend)
         ↓
(API Gateway: REST API + Proxy ANY /{proxy+})
         ↓
(AWS Lambda Function: StreamLambdaHandler)
         ↓
(Spring Boot Application Context)
         ↓
(Controller Layer: PingController, FileNetController)
```


## Test event payload:
{
  "resource": "/{proxy+}",
  "path": "/test",
  "httpMethod": "GET",
  "headers": {
    "Accept": "application/json",
    "Content-Type": "application/json"
  },
  "multiValueHeaders": {
    "Accept": ["application/json"]
  },
  "queryStringParameters": {
    "name": "hello"
  },
  "multiValueQueryStringParameters": {
    "name": ["hello"]
  },
  "pathParameters": {
    "proxy": "test"
  },
  "requestContext": {
    "resourcePath": "/{proxy+}",
    "httpMethod": "GET",
    "path": "/test",
    "identity": {
      "accessKey": "fake-access-key",
      "accountId": "123456789012",
      "caller": "fake-caller",
      "sourceIp": "1.2.3.4",
      "user": "fake-user"
    }
  },
  "body": null,
  "isBase64Encoded": false
}
{
  "resource": "/{proxy+}",
  "path": "/ping",
  "httpMethod": "GET",
  "headers": {
    "Accept": "application/json",
    "Content-Type": "application/json"
  },
  "multiValueHeaders": {
    "Accept": ["application/json"]
  },
  "queryStringParameters": {
    "name": "hello"
  },
  "multiValueQueryStringParameters": {
    "name": ["hello"]
  },
  "pathParameters": {
    "proxy": "test"
  },
  "requestContext": {
    "resourcePath": "/{proxy+}",
    "httpMethod": "GET",
    "path": "/test",
    "identity": {
      "accessKey": "fake-access-key",
      "accountId": "123456789012",
      "caller": "fake-caller",
      "sourceIp": "1.2.3.4",
      "user": "fake-user"
    }
  },
  "body": null,
  "isBase64Encoded": false
}

## Response

{
  "statusCode": 200,
  "multiValueHeaders": {
    "Content-Type": [
      "application/json"
    ]
  },
  "body": "{\"pong\":\"Hello, World!\"}",
  "isBase64Encoded": false
}

##Use below command to create the fat/uber jar
clean package -Passembly-zip
