package com.sanjay.springwithlocalstack.springwithlocalstack;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringWithLocalstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWithLocalstackApplication.class, args);
	}


	@Bean
	DynamoDbClient amazonDynamoDBClient(AWSConfiguration awsConfiguration){
		System.out.println("value : "+awsConfiguration.getDynomoUri());
		return DynamoDbClient.builder().endpointOverride(awsConfiguration.getDynomoUri()).build();
	}

	@Bean
	ApplicationRunner applicationRunner(DynamoDbClient dynamoDbClient){
		return args -> {
			dynamoDbClient.listTables().tableNames()
					.forEach(System.out::println);
			CreateTableRequest createTable = CreateTableRequest.builder()
												.tableName("mybook").keySchema(KeySchemaElement.builder()
														.keyType(KeyType.HASH)
														.attributeName("id").build())
												.attributeDefinitions(AttributeDefinition.builder()
														.attributeName("id")
														.attributeType(ScalarAttributeType.S).build())
												.provisionedThroughput(ProvisionedThroughput.builder()
														.writeCapacityUnits(5L)
														.readCapacityUnits(5L)
														.build())
												.build();
			dynamoDbClient.createTable(createTable);
			dynamoDbClient.listTables().tableNames()
							.forEach(System.out::println);
		};
	}


}
