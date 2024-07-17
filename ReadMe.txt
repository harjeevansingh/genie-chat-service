# Genie Chat Bot - Chat Service

## Project Description
Genie Chat Bot is an AI-powered chatbot system providing instant lending-related query resolution. It supports multiple concurrent user sessions with real-time responses and comprehensive conversation history storage. The system is built using a microservices architecture for scalability and efficient query resolution.

## Chat Service Description
The Chat Service is the core component of the Genie Chat Bot system. It handles real-time communication with users, manages chat sessions, and coordinates with other services for message processing and storage. This service also sets up shared resources like MySQL, Redis, and Kafka, which are used by other services.

## Dependencies
Before running the Chat Service, ensure the following repositories are cloned:
- History Service: https://github.com/harjeevansingh/genie-history-service
- Language Model Service: https://github.com/harjeevansingh/genie-language-model-service
- UI Service: https://github.com/harjeevansingh/genie-ui-service

## Prerequisites
- Docker and Docker Compose installed on your system
- Ports 3306 (MySQL), 6379 (Redis), 9092 (Kafka), and 2181 (Zookeeper) should be available

## Steps to Run
1. Navigate to the Chat Service directory:
    cd /path/to/genie-chat-service
2. Start the service:
    docker-compose up
3. Verify that the service and its dependencies are running:
    docker ps

For full deployment of the Genie Chat Bot system, refer to the main deployment guide.
    - https://docs.google.com/document/d/1UWd703j5do7ilt7zrmmJodJIY9KydrCAX3lkqXyKH2w/edit?usp=sharing