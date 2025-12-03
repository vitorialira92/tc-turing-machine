FROM public.ecr.aws/lambda/java:21

COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:0.9.1 /lambda-adapter /opt/extensions/lambda-adapter
RUN chmod +x /opt/extensions/lambda-adapter

ENV PORT=8080

COPY target/Turing-Machine-0.0.1-SNAPSHOT.jar ${LAMBDA_TASK_ROOT}/app.jar

ENTRYPOINT ["/opt/extensions/lambda-adapter"]
CMD ["java", "-jar", "app.jar"]