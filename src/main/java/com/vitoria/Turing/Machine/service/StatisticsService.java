package com.vitoria.Turing.Machine.service;

import com.vitoria.Turing.Machine.dto.ExecutionResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
public class StatisticsService {

    @Value("${machine.statistics.bucket}")
    private String bucketName;
    @Value("${machine.statistics.file}")
    private String fileName;
    @Value("${machine.statistics.access_key}")
    private String accessKey;
    @Value("${machine.statistics.secret_access_key}")
    private String secretAccessKey;

    private S3Client s3Client;

    private S3Client getS3Client() {
        if (s3Client == null) {
            s3Client = S3Client.builder()
                    .credentialsProvider(StaticCredentialsProvider
                            .create( AwsBasicCredentials.create(accessKey, secretAccessKey) ))
                    .region(Region.US_WEST_2)
                    .build();
        }

        return s3Client;
    }

    public StatisticsService() { }

    public void recordExecution(ExecutionResultDTO resultDTO) {
        S3Client client = getS3Client();
        String currentContent = "";
        boolean writeHeader = false;

        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = client.getObjectAsBytes(getRequest);
            currentContent = objectBytes.asString(StandardCharsets.UTF_8);

            if (currentContent.isEmpty()) {
                writeHeader = true;
            }

        } catch (NoSuchKeyException e) {
            writeHeader = true;
        } catch (Exception e) {
            System.err.println("Erro ao tentar ler o arquivo S3: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        StringBuilder newFileContent = new StringBuilder();

        newFileContent.append(currentContent);

        if (writeHeader) {
            newFileContent.append(buildCsvHeader());
        }

        newFileContent.append(formatCsvRow(resultDTO));

        try {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .acl("bucket-owner-full-control")
                    .contentType("text/csv")
                    .build();

            client.putObject(putRequest, RequestBody.fromString(newFileContent.toString(), StandardCharsets.UTF_8));

        } catch (Exception e) {
            System.err.println("Erro ao tentar gravar no arquivo S3: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String buildCsvHeader() {
        return "strategy,original_decimal,original_binary,is_prime,execution_time_ms,total_steps,total_subtractions,total_comparisons,total_divisor_increments,timestamp\n";
    }

    private String formatCsvRow(ExecutionResultDTO resultDTO) {
        return resultDTO.getStrategy() + "," +
                resultDTO.getOriginalNumberDecimal() + "," +
                resultDTO.getOriginalNumberBinary() + "," +
                resultDTO.isPrime() + "," +
                resultDTO.getExecutionTimeMillis() + "," +
                resultDTO.getTotalSteps() + "," +
                resultDTO.getTotalSubtractions() + "," +
                resultDTO.getTotalComparisons() + "," +
                resultDTO.getTotalDivisorIncrements() + "," +
                Instant.now().toString() + "\n";
    }
}
