# Anota Ai Challenge

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen)
![AWS SNS SDK](https://img.shields.io/badge/AWS%20SNS%20SDK-1.12.787-yellow)
![Docker](https://img.shields.io/badge/Docker-✓-blue)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red)
![MongoDB](https://img.shields.io/badge/MongoDB-green)

This project is the resolution of the [AnotaAi Backend Challenge](https://github.com/githubanotaai/new-test-backend-nodejs), developed in: Java, Java Spring, AWS SQS, AWS S3 and MongoDB.

## How to Use

This section provides comprehensive instructions for setting up and running the application.

### Prerequisites

- Docker
- AWS Account

### Environment Variables

Create a `.env` file in the project root with the following variables:

```env
MONGO_ROOT_USERNAME=your_db_username
MONGO_ROOT_PASSWORD=your_db_password
MONGO_DATABASE=database
SPRING_DATA_MONGODB_URI=mongo_uri

AWS_ACCESS_KEY_ID=your_access_key_id
AWS_SECRET_ACCESS_KEY=your_secret_key
AWS_REGION=your_region
AWS_SNS_TOPIC_ARN=your_sns_topic_arn
```

### Running the Application

#### Docker Deployment
Run the container:
   ```bash
   docker-compose up -d --build
   ```
> Remember to replace the .env file path to the path of your environment file that you created.

#### AWS SQS Configuration

1. Create an default SQS queue in your AWS account, example: `anota-ai-challenge-catalog-update`

#### AWS SNS Configuration

1. Create a default SNS topic, example name: `catalog-emit`
2. Create a subscription to the topic ARN, with the Amazon SQS protocol and in endpoint, select the queue created previously (`anota-ai-challenge-catalog-update`)
3. Update the `AWS_SNS_TOPIC_ARN` in your `.env` file with the value found in `Details > ARN`
   
#### AWS S3 Configuration

1. Create an S3 bucket in your AWS account, example name: `anota-ai-challenge-catalog-marketplace`
2. Ensure your AWS credentials have appropriate permissions to access the S3 bucket

#### AWS Lambda Configuration

1. Create a lambda to consume the SQS queue, example name: `catalogEmitConsumer`
2. In 'Add Trigger', select the SQS queue created in the previous step
3. Develop the lambda code that will be responsible for consuming events from the queue and sending them to the S3 bucket. [Lambda function example](https://github.com/DouglasVulcano/anota-ai-challenge/blob/main/aws-lambda-example/catalogEmitConsumer.js)
4. Access `IAM`, in the `IAM Features > Functions` select the labda created, it is necessary to assign the `AmazonS3FullAccess` and `AWSLambdaSQSQueueExecutionRole` roles 

## License
[MIT License](https://github.com/DouglasVulcano/anota-ai-challenge/blob/main/LICENSE)

